package mtr.data;

import net.minecraft.network.FriendlyByteBuf;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import sun.misc.Unsafe;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

/** Exercises the generated 26.2 save module, including its atomic-write/failure-preservation port. */
public final class SaveQueueCompatibilityCheck {

	private static final String[] DIRTY_FIELDS = {"dirtyStationIds", "dirtyPlatformIds", "dirtySidingIds", "dirtyRouteIds", "dirtyDepotIds", "dirtyLiftIds", "dirtyRailPositions", "dirtySignalBlocks"};
	private static final Method WRITE_DIRTY = Arrays.stream(RailwayDataFileSaveModule.class.getDeclaredMethods()).filter(method -> method.getName().equals("writeDirtyDataToFile")).findFirst().orElseThrow();

	public static void main(String[] args) throws Exception {
		WRITE_DIRTY.setAccessible(true);
		final boolean baseline = Arrays.asList(args).contains("--baseline");
		final Path directory = Files.createTempDirectory("mtr-save-queue-check-");
		try {
			final RailwayDataFileSaveModule module = new RailwayDataFileSaveModule(null, null, new HashMap<>(), directory, new SignalBlocks());
			checkWrites(module, directory);
			checkDeletionOrder(directory);
			checkFullSaveReset(directory);
			if (baseline || Arrays.asList(args).contains("--benchmark")) benchmark(module, directory);
			if (!baseline) {
				for (String field : DIRTY_FIELDS) {
					require(read(module, field) instanceof Deque<?>, field + " must not shift all remaining entries on front removal");
				}
				require(read(module, "checkFilesToDelete") instanceof Set<?>, "Pending save paths must support indexed removal, not a linear list scan");
			}
			System.out.println("PASS: FIFO and duplicate records, missing IDs, retained/stale file order, unchanged hashes, replacement, encoding/write failure preservation, fullSave reset" + (baseline ? " (baseline collections allowed)" : ", indexed queue structures"));
		} finally {
			try (var paths = Files.walk(directory)) {
				for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) {
					Files.delete(path);
				}
			}
		}
	}

	private static void checkWrites(RailwayDataFileSaveModule module, Path directory) throws Exception {
		final Collection<Long> dirty = collection(module, "dirtyStationIds");
		final Collection<Path> pending = collection(module, "checkFilesToDelete");
		final Path records = directory.resolve("stations");
		final Path first = recordPath(records, 1);
		final Path second = recordPath(records, 2);
		final Path staleFirst = recordPath(records, 3);
		final Path staleSecond = recordPath(records, 4);
		final List<Long> visited = new ArrayList<>();
		pending.addAll(List.of(staleFirst, first, second, staleSecond));
		dirty.addAll(List.of(2L, 1L, 999L, 2L));
		drain(module, dirty, id -> {
			visited.add(id);
			return id == 999 ? null : new TestRecord("record " + id, false);
		}, records);
		require(visited.equals(List.of(2L, 1L, 999L, 2L)), "Dirty records lost FIFO order, duplicates or absent-ID handling");
		require(new ArrayList<>(pending).equals(List.of(staleFirst, staleSecond)), "Live records were left for deletion or stale deletion order changed");
		require((int) read(module, "filesWritten") == 2, "Duplicate unchanged record must retain its cached hash");
		try (var unpacker = MessagePack.newDefaultUnpacker(Files.readAllBytes(first))) {
			require(unpacker.unpackMapHeader() == 1 && unpacker.unpackString().equals("value") && unpacker.unpackString().equals("record 1"), "Saved payload changed");
		}

		// A shorter replacement must not retain trailing bytes from the previous record.
		dirty.add(1L);
		drain(module, dirty, id -> new TestRecord("x", false), records);
		try (var unpacker = MessagePack.newDefaultUnpacker(Files.readAllBytes(first))) {
			unpacker.unpackMapHeader();
			unpacker.unpackString();
			require(unpacker.unpackString().equals("x") && !unpacker.hasNext(), "Atomic replacement retained trailing bytes");
		}

		final byte[] saved = Files.readAllBytes(first);
		pending.add(first);
		dirty.add(1L);
		captureExpectedFailure(() -> drain(module, dirty, id -> new TestRecord("unencodable", true), records));
		require(Arrays.equals(saved, Files.readAllBytes(first)) && !pending.contains(first), "Failed encoding did not protect the previous file from deletion");

		// A nonempty directory cannot be replaced by a file, on Windows or Unix.
		final Path blocked = recordPath(records, 5);
		Files.createDirectories(blocked);
		final Path child = blocked.resolve("keep");
		Files.write(child, new byte[]{42});
		pending.add(blocked);
		dirty.add(5L);
		captureExpectedFailure(() -> drain(module, dirty, id -> new TestRecord("cannot publish", false), records));
		require(Arrays.equals(Files.readAllBytes(child), new byte[]{42}) && !pending.contains(blocked), "Failed publication did not protect the existing path");
		require(new ArrayList<>(pending).equals(List.of(staleFirst, staleSecond)), "A failed save changed unrelated pending deletions");
		pending.clear();
	}

	private static void checkFullSaveReset(Path directory) throws Exception {
		final RailwayDataFileSaveModule module = new RailwayDataFileSaveModule(emptyData(), null, new HashMap<>(), directory, new SignalBlocks());
		for (String field : DIRTY_FIELDS) {
			collection(module, field).add(new Object());
		}
		collection(module, "checkFilesToDelete").add(directory.resolve("not-an-existing-file"));
		module.fullSave();
		for (String field : DIRTY_FIELDS) {
			require(collection(module, field).isEmpty(), "fullSave did not discard the previous partial queue: " + field);
		}
		require(collection(module, "checkFilesToDelete").isEmpty(), "fullSave did not reset pending deletions");
		require(!(boolean) read(module, "useReducedHash") && !(boolean) read(module, "canAutoSave"), "fullSave mode or completion changed");
		require(module.autoSaveTick(), "Completed full save must not continue writing");
	}

	private static void checkDeletionOrder(Path directory) throws Exception {
		final RailwayDataFileSaveModule module = new RailwayDataFileSaveModule(emptyData(), null, new HashMap<>(), directory, new SignalBlocks());
		final Path first = directory.resolve("stale-first");
		final Path second = directory.resolve("stale-second");
		Files.write(first, new byte[]{1});
		Files.write(second, new byte[]{2});
		final Collection<Path> pending = collection(module, "checkFilesToDelete");
		pending.addAll(List.of(first, second));
		final Field canAutoSave = RailwayDataFileSaveModule.class.getDeclaredField("canAutoSave");
		canAutoSave.setAccessible(true);
		canAutoSave.set(module, true);
		require(!module.autoSaveTick(), "A tick must leave the second stale file queued");
		require(!Files.exists(first) && Files.exists(second), "Deletion must process the oldest remaining path, one per tick");
		require(new ArrayList<>(pending).equals(List.of(second)), "Deletion did not remove exactly its first pending entry");
	}

	private static RailwayData emptyData() throws Exception {
		// These paths need only an empty data cache. Avoid constructing a server/world.
		final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
		unsafeField.setAccessible(true);
		final RailwayData data = (RailwayData) ((Unsafe) unsafeField.get(null)).allocateInstance(RailwayData.class);
		final Field cache = RailwayData.class.getDeclaredField("dataCache");
		cache.setAccessible(true);
		cache.set(data, new DataCache(Set.of(), Set.of(), Set.of(), Set.of(), Set.of(), Set.of()));
		return data;
	}

	private static void benchmark(RailwayDataFileSaveModule module, Path directory) throws Exception {
		final Collection<Long> dirty = collection(module, "dirtyStationIds");
		final Collection<Path> pending = collection(module, "checkFilesToDelete");
		final List<Path> paths = new ArrayList<>();
		for (int i = 0; i < 20_000; i++) {
			paths.add(directory.resolve("synthetic-" + i));
		}
		final List<Path> removalOrder = new ArrayList<>(paths);
		Collections.shuffle(removalOrder, new Random(8262));
		final long[] queueTimes = new long[3];
		final long[] deletionTimes = new long[3];
		for (int round = -1; round < 3; round++) {
			for (long i = 0; i < 100_000; i++) {
				dirty.add(i);
			}
			final long[] next = {0};
			long start = System.nanoTime();
			drain(module, dirty, id -> {
				require(id == next[0]++, "Synthetic save workload changed FIFO order");
				return null;
			}, directory);
			if (round >= 0) queueTimes[round] = System.nanoTime() - start;
			require(next[0] == 100_000, "Synthetic queue lost a record");
			pending.addAll(paths);
			start = System.nanoTime();
			for (Path path : removalOrder) require(pending.remove(path), "Synthetic deletion check lost a path");
			if (round >= 0) deletionTimes[round] = System.nanoTime() - start;
			require(pending.isEmpty(), "Synthetic deletion checks did not finish");
		}
		Arrays.sort(queueTimes);
		Arrays.sort(deletionTimes);
		System.out.printf("Synthetic bookkeeping median (3 runs, 1 warmup; excludes disk/game load): %s drain 100000 IDs %.3f ms; %s remove 20000 paths %.3f ms%n", dirty.getClass().getSimpleName(), queueTimes[1] / 1_000_000D, pending.getClass().getSimpleName(), deletionTimes[1] / 1_000_000D);
	}

	private static <T extends SerializedDataBase> void drain(RailwayDataFileSaveModule module, Collection<Long> dirty, Function<Long, T> lookup, Path path) throws Exception {
		while (!(boolean) WRITE_DIRTY.invoke(module, dirty, lookup, (Function<Long, Long>) id -> id, path)) {
			// Preserve the production 2 ms writer yield and resume exactly the same queue.
		}
	}

	private static Path recordPath(Path directory, long id) {
		return directory.resolve(String.valueOf(id % 100)).resolve(String.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	private static <T> Collection<T> collection(Object target, String name) throws Exception {
		return (Collection<T>) read(target, name);
	}

	private static Object read(Object target, String name) throws Exception {
		final Field field = target.getClass().getDeclaredField(name);
		field.setAccessible(true);
		return field.get(target);
	}

	private static void captureExpectedFailure(ThrowingRunnable action) throws Exception {
		final PrintStream originalError = System.err;
		final ByteArrayOutputStream errors = new ByteArrayOutputStream();
		try (PrintStream captured = new PrintStream(errors, true, StandardCharsets.UTF_8)) {
			System.setErr(captured);
			action.run();
		} finally {
			System.setErr(originalError);
		}
		require(errors.size() > 0, "Failure fixture did not exercise the save error path");
	}

	private static void require(boolean condition, String message) {
		if (!condition) throw new AssertionError(message);
	}

	private interface ThrowingRunnable {
		void run() throws Exception;
	}

	private static final class TestRecord extends SerializedDataBase {
		private final String value;
		private final boolean fail;

		private TestRecord(String value, boolean fail) {
			this.value = value;
			this.fail = fail;
		}

		@Override
		public void toMessagePack(MessagePacker packer) throws IOException {
			if (fail) throw new IOException("Expected synthetic record encoding failure");
			packer.packString("value").packString(value);
		}

		@Override
		public int messagePackLength() {
			return 1;
		}

		@Override
		public void writePacket(FriendlyByteBuf packet) {
		}
	}
}

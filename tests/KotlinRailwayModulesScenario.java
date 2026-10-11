package mtr.data;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.msgpack.core.MessagePacker;
import sun.misc.Unsafe;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class KotlinRailwayModulesScenario {
    private static final List<String> records = new ArrayList<>(), events = new ArrayList<>();
    private static final RuntimeException MARKER = new IllegalStateException("marker");
    private static int assertions;
    private static Unsafe unsafe;

    public static String run() throws Exception {
        net.minecraft.SharedConstants.tryDetectVersion(); net.minecraft.server.Bootstrap.bootStrap();
        var field = Unsafe.class.getDeclaredField("theUnsafe"); field.setAccessible(true); unsafe = (Unsafe) field.get(null);
        paths(); driving(); logging();
        System.out.println("Railway module assertions: " + assertions + ", golden records: " + records.size());
        return String.join("\n", records) + "\n";
    }
    public static void packet(Level world, long id, int result) { require(world == null, "Packet world changed"); events.add("packet:" + id + ":" + result); }

    private static void paths() throws Exception {
        RailwayProbe data = (RailwayProbe) unsafe.allocateInstance(RailwayProbe.class);
        DataCache cache = new DataCache(new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>());
        Set<Siding> sidings = new LinkedHashSet<>();
        setField(RailwayData.class, data, "dataCache", cache); setField(RailwayData.class, data, "sidings", sidings);
        Map<BlockPos, Map<BlockPos, Rail>> rails = new HashMap<>();
        DepotProbe depot = new DepotProbe(1, cache, sidings, rails); DepotProbe other = new DepotProbe(2, cache, sidings, rails);
        cache.depotIdMap.put(1L, depot); cache.depotIdMap.put(2L, other);
        RailwayDataPathGenerationModule module = new RailwayDataPathGenerationModule(data, null, rails);
        List<Worker> workers = new ArrayList<>();
        try {
            events.clear(); records.add("path-missing\t" + captureOut(() -> module.generatePath(null, 99)) + ":" + events);
            require(events.equals(List.of("packet:99:0")), "Missing depot effects changed");
            for (int i = 0; i < 7; i++) workers.add(new Worker(i + 1));
            events.clear(); depot.next = workers.get(0);
            records.add("path-first\t" + captureOut(() -> module.generatePath(null, 1)) + ":" + events);
            require(events.equals(List.of("generate:1", "callback:1", "started:1", "reset:1")), "Generate/callback/reset ordering changed");
            events.clear(); depot.next = workers.get(1);
            records.add("path-restart\t" + captureOut(() -> module.generatePath(null, 1)) + ":" + events);
            require(events.getFirst().equals("interrupt:1") && workers.get(0).isAlive(), "Live old worker not interrupted before replacement");
            events.clear(); other.next = workers.get(2); captureOut(() -> module.generatePath(null, 2));
            require(events.stream().noneMatch(event -> event.startsWith("interrupt")), "Unrelated depot worker interrupted");
            workers.get(1).finish(); depot.next = workers.get(3); events.clear();
            String cleanup = captureOut(() -> module.generatePath(null, 1));
            require(cleanup.contains("Starting") && !cleanup.contains("Restarting"), "Terminated worker was not pruned");
            records.add("path-cleanup\t" + cleanup + ":" + events);
            depot.failBefore = true; events.clear();
            captureOut(() -> sameFailure(() -> module.generatePath(null, 1)));
            require(events.equals(List.of("interrupt:4", "generate:1")), "Pre-callback failure order changed");
            records.add("path-before-failure\t" + events); depot.failBefore = false;
            depot.next = workers.get(4); depot.failAfter = true; events.clear();
            captureOut(() -> sameFailure(() -> module.generatePath(null, 1)));
            require(events.equals(List.of("interrupt:4", "generate:1", "callback:5")) && workers.get(4).getState() == Thread.State.NEW, "Post-callback failure changed");
            records.add("path-after-failure\t" + events); depot.failAfter = false;
            depot.next = workers.get(5); data.failReset = true; events.clear();
            captureOut(() -> sameFailure(() -> module.generatePath(null, 1)));
            require(events.equals(List.of("generate:1", "callback:6", "started:6", "reset:1")), "Unstarted cleanup/reset failure ordering changed");
            records.add("path-reset-failure\t" + events); data.failReset = false;
            depot.name = null; events.clear(); captureOut(() -> fails(NullPointerException.class, () -> module.generatePath(null, 1)));
            require(events.equals(List.of("interrupt:6")), "Null name no longer fails after interruption"); depot.name = "";
            depot.next = workers.get(6); events.clear(); records.add("path-empty-name\t" + captureOut(() -> module.generatePath(null, 1)) + ":" + events);
            depot.next = null; depot.failAfter = true; captureOut(() -> sameFailure(() -> module.generatePath(null, 1)));
            events.clear(); captureOut(() -> fails(NullPointerException.class, () -> module.generatePath(null, 99)));
            require(events.isEmpty(), "Malformed callback entry was silently ignored");
            new RailwayDataPathGenerationModule(null, null, null);
        } finally { for (Worker worker : workers) worker.finish(); }
    }

    private static void driving() throws Exception {
        PlayerProbe first = player(new UUID(1, 2)); PlayerProbe second = player(new UUID(3, 4));
        DriveProbe train = (DriveProbe) unsafe.allocateInstance(DriveProbe.class);
        Set<UUID> riders = new LinkedHashSet<>(); setField(Train.class, train, "ridingEntities", riders);
        train.calls = new ArrayList<>(); riders.add(first.identity); riders.add(second.identity);
        RailwayDataDriveTrainModule module = new RailwayDataDriveTrainModule(null, null, null);
        StringBuilder matrix = new StringBuilder();
        for (int flags = 0; flags < 8; flags++) for (int returns = 0; returns < 8; returns++) {
            module.tick(); train.calls.clear(); first.uuidReads = 0;
            train.accelerate = (returns & 1) != 0; train.brake = (returns & 2) != 0; train.doors = (returns & 4) != 0;
            module.drive(first, (flags & 1) != 0, (flags & 2) != 0, (flags & 4) != 0);
            require(first.uuidReads == Integer.bitCount(flags), "Input UUID evaluation count changed");
            boolean dirty = module.drive(train);
            boolean expected = (flags & 1) != 0 && train.accelerate || (flags & 2) != 0 && train.brake || (flags & 4) != 0 && train.doors;
            require(dirty == expected, "Driving dirty-state changed");
            matrix.append(flags).append('/').append(returns).append('=').append(dirty).append(':').append(train.calls).append(';');
        }
        records.add("drive-matrix\t" + matrix);
        module.tick(); train.calls.clear(); train.accelerate = true; train.brake = true; train.doors = true;
        module.drive(first, true, false, false); module.drive(first, false, true, true); module.drive(second, false, true, true);
        require(module.drive(train) && train.calls.equals(List.of("accelerate", "doors", "brake", "doors")), "Input union/rider order changed");
        records.add("drive-union\t" + train.calls); train.calls.clear();
        require(module.drive(train) && train.calls.size() == 4, "Drive unexpectedly consumed this-tick input");
        module.tick(); train.calls.clear(); require(!module.drive(train) && train.calls.isEmpty(), "Tick did not clear all input sets");
        module.drive((ServerPlayer) null, false, false, false);
        fails(NullPointerException.class, () -> module.drive((ServerPlayer) null, false, false, true));
        fails(NullPointerException.class, () -> module.drive((TrainServer) null));
        module.drive(first, true, true, true); train.fail = true; sameFailure(() -> module.drive(train));
        require(train.calls.equals(List.of("accelerate")), "Driving exception no longer short-circuits");
        train.fail = false; train.calls.clear(); require(module.drive(train), "Failed drive consumed input");
    }

    private static void logging() throws Exception {
        PlayerProbe player = player(new UUID(1, 2));
        Path root = Files.createTempDirectory("mtr-module-compat-").toRealPath();
        try {
            RailwayDataLoggingModule module = new RailwayDataLoggingModule(null, null, null, root);
            module.save(); require(!Files.exists(root.resolve("logs")), "Empty save created a log");
            List<String> oldData = new ArrayList<>(List.of("\"same\":1", "\"changed\":\"old\""));
            List<String> newData = new ArrayList<>(List.of("\"same\":1", "\"changed\":\"new,\\\"quoted\\\"\""));
            module.addEvent(null, null, oldData, oldData); // No changed data must not dereference player/class.
            module.addEvent(player, Depot.class, 7, "First|Second,\"quoted\"", oldData, newData, new BlockPos(20, 64, 1), null, new BlockPos(2, -3, 4));
            oldData.set(1, "\"changed\":\"mutated\""); newData.clear();
            module.save(); Path log = onlyLog(root);
            require(log.getFileName().toString().equals("20270115-080000123.csv"), "Fixed timestamp filename changed: " + log);
            String first = Files.readString(log); records.add("log-edit\t" + base64(first));
            require(first.contains("EDIT") && !first.contains("mutated") && first.contains("First Second"), "Queued log retained input lists or wrong formatting");
            module.save(); require(Files.readString(log).equals(first), "Repeated save duplicated entries");
            module.addEvent(player, Route.class, List.of(), List.of("\"new\":3")); module.save();
            module.addEvent(player, Station.class, 0, "", List.of("\"old\":4"), List.of()); module.save();
            String appended = Files.readString(log); records.add("log-appended\t" + base64(appended));
            require(appended.startsWith(first) && appended.contains("CREATE") && appended.contains("DELETE") && appended.indexOf("Timestamp") == appended.lastIndexOf("Timestamp"), "Append/header policy changed");
            fails(NullPointerException.class, () -> module.addEvent(player, Route.class, List.of(), List.of(), (BlockPos[]) null));
            fails(NullPointerException.class, () -> module.addEvent(player, Route.class, Arrays.asList((String) null), List.of("x")));
            fails(RuntimeException.class, () -> module.addEvent(player, Route.class, List.of(), List.of("not-json")));
            module.save(); require(Files.readString(log).equals(appended), "Failed event enqueued partial CSV");
            Path blocked = root.resolve("blocked"); Files.createDirectory(blocked); Files.writeString(blocked.resolve("logs"), "marker");
            RailwayDataLoggingModule retry = new RailwayDataLoggingModule(null, null, null, blocked);
            retry.addEvent(player, Route.class, List.of(), List.of("\"retry\":5")); captureError(retry::save);
            require(Files.readString(blocked.resolve("logs")).equals("marker"), "Failed save overwrote obstacle");
            Files.delete(blocked.resolve("logs")); retry.save(); records.add("log-retry\t" + base64(Files.readString(onlyLog(blocked))));
            require(Files.readString(onlyLog(blocked)).contains("retry"), "Failed save discarded queued event");
            class Dispatch extends RailwayDataLoggingModule {
                int calls;
                Dispatch() { super(null, null, null, root.resolve("dispatch")); }
                @Override public void addEvent(ServerPlayer p, Class<?> type, long id, String name, List<String> before, List<String> after, BlockPos... positions) {
                    require(p == player && type == Depot.class && id == 0 && name.equals("") && positions.length == 1, "Convenience overload forwarding changed"); calls++;
                }
            }
            Dispatch dispatch = new Dispatch(); dispatch.addEvent(player, Depot.class, List.of(), List.of(), BlockPos.ZERO); require(dispatch.calls == 1, "Convenience overload stopped virtual dispatch");
            dataExtraction();
        } finally {
            // Delete only this fixture's freshly created, canonical temporary tree.
            try (var files = Files.walk(root)) {
                for (Path file : files.sorted(Comparator.reverseOrder()).toList()) {
                    require(file.toAbsolutePath().normalize().startsWith(root) && root.getFileName().toString().startsWith("mtr-module-compat-"), "Unsafe fixture cleanup path");
                    Files.delete(file);
                }
            }
        }
    }

    private static void dataExtraction() {
        SerializedDataBase full = new SerializedDataBase() {
            @Override public void toMessagePack(MessagePacker packer) throws IOException { packer.packString("first").packInt(1).packString("list").packArrayHeader(2).packBoolean(true).packString("text").packString("dangling"); }
            @Override public int messagePackLength() { throw new AssertionError("Must not write map headers"); }
            @Override public void writePacket(FriendlyByteBuf packet) { throw new AssertionError(); }
        };
        List<String> data = RailwayDataLoggingModule.getData(full); records.add("log-full-data\t" + data);
        require(data.equals(List.of("\"first\":1", "\"list\":[true,\"text\"]")), "Pair extraction/dangling-key behavior changed"); data.add("mutable");
        class Reduced extends SerializedDataBase implements IReducedSaveData {
            @Override public void toReducedMessagePack(MessagePacker packer) throws IOException { packer.packString("reduced").packInt(9); }
            @Override public int reducedMessagePackLength() { throw new AssertionError(); }
            @Override public void toMessagePack(MessagePacker packer) { throw new AssertionError("Must prefer reduced data"); }
            @Override public int messagePackLength() { throw new AssertionError(); }
            @Override public void writePacket(FriendlyByteBuf packet) { throw new AssertionError(); }
        }
        require(RailwayDataLoggingModule.getData(new Reduced()).equals(List.of("\"reduced\":9")), "Reduced serialization not preferred");
        captureError(() -> require(RailwayDataLoggingModule.getData((SerializedDataBase) null).isEmpty(), "Null serialization fallback changed"));
        CompoundTag tag = new CompoundTag(); tag.putByte("false", (byte) 0); tag.putByte("true", (byte) 1); tag.putInt("number", -23);
        tag.putString("empty", ""); tag.putString("text", "words"); tag.putLongArray("longs", new long[]{1, -2, Long.MAX_VALUE});
        tag.putIntArray("ints", new int[]{3, 4}); tag.putByteArray("bytes", new byte[]{0, 1, 2}); tag.putFloat("float", -0.0F);
        CompoundTag nested = new CompoundTag(); nested.putString("key", "value"); tag.put("nested", nested);
        records.add("log-nbt-data\t" + RailwayDataLoggingModule.getData(tag));
        fails(NullPointerException.class, () -> RailwayDataLoggingModule.getData((CompoundTag) null));
    }

    private static PlayerProbe player(UUID id) throws Exception { PlayerProbe player = (PlayerProbe) unsafe.allocateInstance(PlayerProbe.class); player.identity = id; return player; }
    private static Path onlyLog(Path root) throws Exception { try (var files = Files.list(root.resolve("logs"))) { var logs = files.toList(); require(logs.size() == 1, "Expected one CSV"); return logs.getFirst(); } }
    private static String base64(String value) { return Base64.getEncoder().encodeToString(value.replace("\r\n", "\n").getBytes(StandardCharsets.UTF_8)); }
    private static void setField(Class<?> owner, Object target, String name, Object value) throws Exception { var field = owner.getDeclaredField(name); field.setAccessible(true); field.set(target, value); }
    private static String captureOut(Runnable action) {
        PrintStream old = System.out; ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (PrintStream stream = new PrintStream(buffer, true, StandardCharsets.UTF_8)) { System.setOut(stream); action.run(); return buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n").replace("\n", "|"); }
        finally { System.setOut(old); }
    }
    private static void captureError(Runnable action) {
        PrintStream old = System.err; ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (PrintStream stream = new PrintStream(buffer)) { System.setErr(stream); action.run(); require(buffer.size() > 0, "Expected failure diagnostic"); }
        finally { System.setErr(old); }
    }
    private static void sameFailure(Runnable action) { try { action.run(); } catch (RuntimeException error) { require(error == MARKER, "Exception identity changed"); return; } throw new AssertionError("Expected marker"); }
    private static void fails(Class<? extends Throwable> type, Runnable action) { try { action.run(); } catch (Throwable error) { require(type.isInstance(error), "Unexpected failure " + error); return; } throw new AssertionError("Expected " + type); }
    private static void require(boolean condition, String message) { assertions++; if (!condition) throw new AssertionError(message); }

    public static final class RailwayProbe extends RailwayData {
        boolean failReset;
        private RailwayProbe() { super(null); }
        @Override public void resetTrainDelays(Depot depot) { events.add("reset:" + depot.id); if (failReset) throw MARKER; }
    }
    public static final class DepotProbe extends Depot {
        final DataCache cache; final Set<Siding> sidings; final Map<BlockPos, Map<BlockPos, Rail>> rails;
        Worker next; boolean failBefore, failAfter;
        DepotProbe(long id, DataCache cache, Set<Siding> sidings, Map<BlockPos, Map<BlockPos, Rail>> rails) { super(id, TransportMode.TRAIN); this.cache = cache; this.sidings = sidings; this.rails = rails; name = "Fixture"; }
        @Override public void generateMainRoute(MinecraftServer server, Level world, DataCache dataCache, Map<BlockPos, Map<BlockPos, Rail>> rails, Set<Siding> sidings, Consumer<Thread> callback) {
            require(server == null && world == null && dataCache == cache && this.rails == rails && this.sidings == sidings, "Generation context identity changed");
            events.add("generate:" + id); if (failBefore) throw MARKER;
            callback.accept(next); events.add("callback:" + (next == null ? "null" : next.number)); if (failAfter) throw MARKER;
            next.start(); next.awaitStart(); events.add("started:" + next.number);
        }
    }
    public static final class Worker extends Thread {
        final int number; final CountDownLatch started = new CountDownLatch(1), release = new CountDownLatch(1);
        Worker(int number) { this.number = number; setDaemon(true); }
        @Override public void run() { started.countDown(); while (release.getCount() != 0) try { release.await(); } catch (InterruptedException ignored) { /* Stay alive to test request overlap. */ } }
        @Override public void interrupt() { events.add("interrupt:" + number); super.interrupt(); }
        void awaitStart() { try { require(started.await(5, TimeUnit.SECONDS), "Worker not started"); } catch (InterruptedException error) { throw new AssertionError(error); } }
        void finish() throws InterruptedException { release.countDown(); join(5000); require(!isAlive(), "Worker did not finish"); }
    }
    public static final class PlayerProbe extends ServerPlayer {
        UUID identity; int uuidReads;
        private PlayerProbe() { super(null, null, (GameProfile) null, (ClientInformation) null); }
        @Override public UUID getUUID() { uuidReads++; return identity; }
        @Override public Component getName() { return Component.literal("Player,\"One\""); }
    }
    public static final class DriveProbe extends TrainServer {
        List<String> calls; boolean accelerate, brake, doors, fail;
        private DriveProbe() { super(1, 1, 1F, "train", "train", 1, List.of(), List.of(), 0, 0, 0.01F, List.of(), false, 1, 1); }
        @Override public boolean changeManualSpeed(boolean increase) { calls.add(increase ? "accelerate" : "brake"); if (fail) throw MARKER; return increase ? accelerate : brake; }
        @Override public boolean toggleDoors() { calls.add("doors"); return doors; }
    }
}

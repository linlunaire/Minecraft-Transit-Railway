package mtr.data;

import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import mtr.mappings.Tuple;
import org.msgpack.core.MessagePack;
import org.msgpack.value.*;
import sun.misc.Unsafe;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

/** Loaded beside the selected Depot; all actions use its public interface. */
public final class KotlinDepotScenario {
    private static final List<String> records = new ArrayList<>();
    private static int assertions;
    private static final long NOW = 1_800_000_000_000L;
    private static final List<Runnable> ownerQueue = new ArrayList<>();
    private static final List<Integer> packets = new ArrayList<>();
    private static Thread ownerThread;

    public static void publish(mtr.path.PathGenerationTask.Request request, java.util.concurrent.Executor ignored, Runnable action) {
        mtr.path.PathGenerationTask.publish(request, ownerQueue::add, action);
    }

    public static void packet(Level ignored, long id, int result) {
        require(Thread.currentThread() == ownerThread, "Managed packet left owner thread");
        packets.add(result);
    }

    public static void workerContracts() throws Exception {
        var field = Unsafe.class.getDeclaredField("theUnsafe"); field.setAccessible(true);
        Level world = (Level) ((Unsafe) field.get(null)).allocateInstance(Class.forName(KotlinDepotScenario.class.getName() + "$ClockLevel"));
        ownerThread = Thread.currentThread();
        for (String mode : List.of("success", "failure", "pre-cancel", "interrupt", "mid-cancel", "queued-cancel")) {
            ownerQueue.clear(); packets.clear();
            Depot depot = new Depot(123, TransportMode.TRAIN);
            depot.corner1 = new Tuple<>(-100, -100); depot.corner2 = new Tuple<>(100, 100);
            var request = new mtr.path.PathGenerationTask.Request();
            Set<Siding> sidings = new HashSet<>();
            if (mode.equals("failure") || mode.equals("mid-cancel")) sidings.add(new CancellingSiding(mode.equals("mid-cancel")));
            var worker = new java.util.concurrent.atomic.AtomicReference<Thread>();
            var uncaught = new java.util.concurrent.atomic.AtomicReference<Throwable>();
            ByteArrayOutputStream errors = new ByteArrayOutputStream(); PrintStream previous = System.err;
            try (PrintStream output = new PrintStream(errors, true, StandardCharsets.UTF_8)) {
                System.setErr(output);
                depot.generateMainRoute(null, world, null, new HashMap<>(), sidings, thread -> {
                    worker.set(thread);
                    thread.setUncaughtExceptionHandler((ignored, error) -> uncaught.set(error));
                    mtr.path.PathGenerationTask.register(thread, request);
                    if (mode.equals("pre-cancel")) request.cancel();
                    if (mode.equals("interrupt")) thread.interrupt();
                });
                worker.get().join(5000);
            } finally { System.setErr(previous); }
            require(!worker.get().isAlive() && uncaught.get() == null, "Worker failed or hung in " + mode + ": " + uncaught.get());
            boolean computed = mode.equals("success") || mode.equals("failure") || mode.equals("queued-cancel");
            require(ownerQueue.size() == (computed ? 1 : 0) && packets.isEmpty(), "Cancellation enqueued completion/failure in " + mode);
            require((errors.size() > 0) == mode.equals("failure"), "Cancellation logged as failure in " + mode);
            if (mode.equals("queued-cancel")) request.cancel();
            ownerQueue.forEach(Runnable::run);
            require(packets.equals(mode.equals("success") ? List.of(Integer.MAX_VALUE) : mode.equals("failure") ? List.of(0) : List.of()), "Unexpected status in " + mode);
        }
        System.out.println("PASS: actual MTR Depot worker, pre/in-flight cancellation, owner-thread success/failure and superseded notification");
    }

    public static final class CancellingSiding extends Siding {
        final boolean cancel;
        CancellingSiding(boolean cancel) { super(1, TransportMode.TRAIN, BlockPos.ZERO, new BlockPos(10, 0, 0), 10); this.cancel = cancel; }
        @Override public int generateRoute(net.minecraft.server.MinecraftServer server, List<mtr.path.PathData> path, int count, Map<BlockPos, Map<BlockPos, Rail>> rails, SavedRailBase first, SavedRailBase last, boolean repeat, int altitude, boolean fast) {
            if (cancel) throw new java.util.concurrent.CancellationException("fixture cancel");
            throw new IllegalStateException("fixture failure");
        }
    }

    public static String run() throws Exception {
        serialization(); updates(); scheduling(); deployment();
        System.out.println("Depot assertions: " + assertions + ", golden records: " + records.size());
        return String.join("\n", records) + "\n";
    }

    private static Depot sample(TransportMode mode) {
        Depot depot = new Depot(123, mode);
        depot.name = "Depot 1"; depot.color = 0xABCDEF; depot.lastDeployedMillis = NOW - 12345;
        depot.routeIds.addAll(List.of(-1L, 2L, 2L, Long.MAX_VALUE));
        depot.departures.addAll(List.of(-1000, 2000, 2000, 86401000));
        depot.useRealTime = true; depot.repeatInfinitely = true; depot.cruisingAltitude = 384;
        for (int i = 0; i < 24; i++) depot.setFrequency(i * 4, i);
        return depot;
    }

    private static void serialization() throws Exception {
        Depot fresh = new Depot(TransportMode.BOAT);
        require(fresh.transportMode == TransportMode.BOAT && fresh.cruisingAltitude == 256 && fresh.lastDeployedMillis == 0
                && fresh.routeIds.isEmpty() && fresh.platformTimes.isEmpty() && fresh.departures.isEmpty() && fresh.tempDepartures.isEmpty()
                && !fresh.useRealTime && !fresh.repeatInfinitely, "Defaults changed");
        require(new Depot(1, null).isTransportMode(null), "Nullable mode changed");
        for (TransportMode mode : TransportMode.values()) {
            Depot depot = sample(mode);
            record("mode-" + mode, depot);
            Map<String, Value> map = unpack(packed(depot, false));
            Depot saved = new Depot(map);
            require(Arrays.equals(packed(depot, false), packed(saved, false)), "Save roundtrip changed");
            try (Buffer b = buffer()) {
                depot.writePacket(b.packet);
                Depot restored = new Depot(b.packet);
                require(b.packet.readableBytes() == 0 && Arrays.equals(packed(depot, false), packed(restored, false)), "Packet roundtrip changed");
            }
            require(depot.messagePackLength() == 15 && depot.reducedMessagePackLength() == 13, "Legacy map-header lengths changed");
            // The Java implementation writes 16/14 entries despite reporting 15/13; migration preserves this quirk.
            require(map.size() == 16 && unpack(packed(depot, true)).size() == 14, "Save field set changed");
        }
        Map<String, Value> map = unpack(packed(sample(TransportMode.TRAIN), false));
        map.put("frequencies", ValueFactory.newArray(ValueFactory.newInteger(11), ValueFactory.newInteger(22), ValueFactory.newString("bad")));
        Depot partial = withDiagnostics(() -> new Depot(map));
        require(partial.getFrequency(0) == 11 && partial.getFrequency(1) == 22 && partial.getFrequency(2) == 0, "Frequency partial recovery changed");
        record("short-frequencies", partial);
        record("missing-map-fields", withDiagnostics(() -> new Depot(new HashMap<>())));
        map.put("route_ids", ValueFactory.newArray(ValueFactory.newInteger(1), ValueFactory.newString("bad")));
        expect("bad-route-value", RuntimeException.class, () -> new Depot(map));
        CompoundTag tag = new CompoundTag();
        tag.putLong("id", 99); tag.putString("transport_mode", "AIRPLANE"); tag.putString("name", "Legacy"); tag.putInt("color", -1);
        tag.putLongArray("route_ids", new long[]{8, 8, -2}); tag.putLong("last_deployed", 789); tag.putInt("deploy_index", 7);
        tag.putBoolean("use_real_time", true); tag.putBoolean("repeat_infinitely", true); tag.putInt("cruising_altitude", 99);
        for (int i = 0; i < 24; i++) tag.putInt("frequencies" + i, i);
        Depot legacy = new Depot(tag);
        require(!legacy.useRealTime && legacy.departures.isEmpty() && legacy.lastDeployedMillis == NOW - 789, "NBT legacy fields changed");
        record("nbt", legacy); record("empty-nbt", new Depot(new CompoundTag()));
        try (Buffer b = buffer()) {
            b.packet.writeLong(5).writeUtf("BAD").writeUtf(" A | B ").writeInt(9);
            for (int i = 0; i < 4; i++) b.packet.writeInt(0);
            b.packet.writeInt(-3).writeBoolean(true);
            for (int i = 0; i < 24; i++) b.packet.writeInt(i);
            b.packet.writeInt(-4).writeLong(456).writeInt(-8).writeBoolean(false).writeInt(-20).writeByte(67);
            Depot negative = new Depot(b.packet);
            require(negative.routeIds.isEmpty() && negative.departures.isEmpty() && b.packet.readByte() == 67, "Negative counts or packet tail changed");
            record("negative-counts", negative);
        }
        expect("null-map", NullPointerException.class, () -> new Depot((Map<String, Value>) null));
        expect("null-packet", NullPointerException.class, () -> new Depot((FriendlyByteBuf) null));
        expect("null-nbt", NullPointerException.class, () -> new Depot((CompoundTag) null));
    }

    private static void updates() throws Exception {
        Depot depot = sample(TransportMode.TRAIN);
        List<Long> routes = depot.routeIds; List<Integer> departures = depot.departures;
        depot.departures.addAll(Arrays.asList(999, -86401000, Integer.MAX_VALUE, Integer.MIN_VALUE, 0));
        depot.setData(packet -> {
            try {
                records.add("set-data-packet\t" + hex(bytes(packet)));
                require(packet.readLong() == 123 && packet.readUtf().equals("TRAIN") && packet.readUtf().equals("frequencies"), "Update header changed");
                Depot receiver = new Depot(9, TransportMode.TRAIN);
                receiver.update("frequencies", packet);
                require(receiver.departures.equals(depot.departures) && receiver.routeIds.equals(depot.routeIds) && packet.readableBytes() == 0, "Update payload changed");
            } finally { packet.release(); }
        });
        require(routes == depot.routeIds && departures == depot.departures && departures.equals(List.of(-1000, -1000, 0, 1000, 2000, 2000)), "Departure normalization or list identity changed");
        record("normalized", depot);
        RuntimeException marker = new RuntimeException("callback-marker");
        try { depot.setData(packet -> { packet.release(); throw marker; }); throw new AssertionError("Callback failure swallowed"); }
        catch (RuntimeException failure) { require(failure == marker, "Callback failure identity changed"); }
        try (Buffer b = buffer()) {
            b.packet.writeUtf("Changed").writeInt(7).writeBoolean(false).writeInt(101).writeInt(102);
            expect("short-update", IndexOutOfBoundsException.class, () -> depot.update("frequencies", b.packet));
            require(depot.name.equals("Changed") && depot.getFrequency(0) == 101 && depot.getFrequency(1) == 102 && depot.getFrequency(2) == 8
                    && depot.departures == departures && depot.routeIds == routes, "Partial update order changed");
        }
        record("partial-update", depot);
        for (int index : new int[]{Integer.MIN_VALUE, -1, 24, Integer.MAX_VALUE}) {
            depot.setFrequency(999, index); require(depot.getFrequency(index) == 0, "Out-of-bounds frequency changed");
        }
        depot.setFrequency(Integer.MIN_VALUE, 3); require(depot.getFrequency(3) == Integer.MIN_VALUE, "Frequency clamped unexpectedly");
        depot.update("ignored", null);
        expect("null-update-key", NullPointerException.class, () -> depot.update(null, null));
        Depot nullable = sample(TransportMode.TRAIN); nullable.routeIds.add(null);
        expect("null-route-save", NullPointerException.class, () -> packed(nullable, true));
        nullable.routeIds.removeLast(); nullable.departures.add(null);
        expect("null-departure-save", NullPointerException.class, () -> packed(nullable, true));
    }

    private static void scheduling() throws Exception {
        net.minecraft.SharedConstants.tryDetectVersion(); net.minecraft.server.Bootstrap.bootStrap();
        var unsafeField = Unsafe.class.getDeclaredField("theUnsafe"); unsafeField.setAccessible(true);
        Level world = (Level) ((Unsafe) unsafeField.get(null)).allocateInstance(Class.forName(KotlinDepotScenario.class.getName() + "$ClockLevel"));
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        for (TransportMode mode : TransportMode.values()) for (boolean real : new boolean[]{false, true}) for (long worldTime : new long[]{0, 6000, 17999, 23999, 24_000_001}) {
            KotlinDepotCompatibilityCheck.worldTime = worldTime;
            Depot depot = sample(mode); depot.useRealTime = real;
            // Mixed disabled hours and finite positive frequencies, including integer truncation.
            for (int i = 0; i < 24; i++) depot.setFrequency(i % 4 == 0 ? 0 : i % 3 == 0 ? 7 : 80, i);
            List<Integer> identity = depot.tempDepartures;
            depot.generateTempDepartures(world);
            require(identity == depot.tempDepartures, "Temporary departure identity changed");
            if (real && !mode.continuousMovement) require(depot.tempDepartures.equals(depot.departures), "Realtime copy changed");
            else require(!depot.tempDepartures.isEmpty(), "World timetable unexpectedly empty");
            digest.update((mode + ":" + real + ":" + worldTime + ":" + depot.tempDepartures + "\n").getBytes(StandardCharsets.UTF_8));
        }
        records.add("world-timetables\t" + hex(digest.digest()));
        Random random = new Random(551719);
        for (int scenario = 0; scenario < 256; scenario++) {
            Depot depot = new Depot(1, TransportMode.TRAIN);
            int count = scenario % 13;
            for (int i = 0; i < count; i++) depot.tempDepartures.add(random.nextInt(86400) * 1000);
            if (scenario % 3 == 0 && count > 1) depot.tempDepartures.set(1, depot.tempDepartures.get(0));
            depot.tempDepartures.sort(Integer::compareTo);
            long clock = switch (scenario % 4) { case 0 -> 0; case 1 -> 86399999; default -> random.nextInt(86400000); };
            KotlinDepotCompatibilityCheck.now = 20 * 86400000L + clock;
            depot.lastDeployedMillis = KotlinDepotCompatibilityCheck.now - random.nextInt(172800000);
            for (int offset : new int[]{Integer.MIN_VALUE, -1, 0, 1, 2, count, count + 1, Integer.MAX_VALUE}) for (int delta : new int[]{-86400000, -1000, 0, 1000, 86400000}) {
                int result = depot.getMillisUntilDeploy(offset, delta);
                require(result >= -1, "Departure wait below sentinel");
                digest.update((scenario + ":" + offset + ":" + delta + "=" + result + ";").getBytes(StandardCharsets.UTF_8));
            }
        }
        records.add("departure-offsets\t" + hex(digest.digest()));
        KotlinDepotCompatibilityCheck.now = NOW;
        class Overrides extends Depot {
            int generateCalls, queryCalls, lastOffset;
            Overrides() { super(1, TransportMode.TRAIN); }
            @Override public void generateTempDepartures(Level ignored) { generateCalls++; super.generateTempDepartures(null); }
            @Override public int getMillisUntilDeploy(int offset, int delta) { queryCalls++; lastOffset = offset; return -7; }
        }
        Overrides child = new Overrides();
        require(child.getMillisUntilDeploy(7) == -7 && child.lastOffset == 7, "Virtual query dispatch changed");
        require(child.getNextDepartureMillis() == -1 && child.lastOffset == 1, "Departure offset/clamp changed");
        child.deployTrain(null, null); child.deployTrain(null, null);
        require(child.generateCalls == 1, "Dirty flag was not cleared");
        child.setFrequency(1, -1); child.deployTrain(null, null);
        require(child.generateCalls == 2 && child.getNextDepartureMillis() == -1 && child.lastOffset == 1, "Invalid-frequency dirtying or offset reset changed");
        child.requestDeploy(5, null); child.deployTrain(null, null); int queried = child.queryCalls; child.deployTrain(null, null);
        require(child.queryCalls == queried, "Pending deployment requests not cleared");
        Depot real = new Depot(1, TransportMode.TRAIN); real.useRealTime = true; real.departures.addAll(Arrays.asList(1, null, -2));
        real.generateTempDepartures(null); require(real.tempDepartures.equals(real.departures) && real.tempDepartures != real.departures, "Realtime nullable copy changed");
        real.useRealTime = false; real.generateTempDepartures(null); require(real.tempDepartures.isEmpty(), "Null-world clearing changed");
    }

    private static void record(String label, Depot depot) throws Exception {
        try (Buffer b = buffer()) { depot.writePacket(b.packet); records.add(label + "\t" + hex(packed(depot, false)) + "\t" + hex(packed(depot, true)) + "\t" + hex(bytes(b.packet))); }
    }

    private static void deployment() throws Exception {
        var field = Unsafe.class.getDeclaredField("theUnsafe"); field.setAccessible(true); Unsafe unsafe = (Unsafe) field.get(null);
        RailwayData data = (RailwayData) unsafe.allocateInstance(RailwayData.class);
        Set<Siding> sidings = new LinkedHashSet<>();
        var sidingsField = RailwayData.class.getField("sidings"); sidingsField.setAccessible(true); sidingsField.set(data, sidings);
        for (int i = 0; i < 4; i++) {
            Siding siding = new Siding(i + 1, i == 3 ? TransportMode.BOAT : TransportMode.TRAIN, new BlockPos(i == 2 ? 1000 : 1, 64, 1), new BlockPos(i == 2 ? 1001 : 2, 64, 1), 1);
            siding.name = i == 0 ? "20" : "10"; sidings.add(siding);
        }
        Depot depot = new Depot(1, TransportMode.TRAIN) {
            @Override public int getMillisUntilDeploy(int offset) { return 0; }
        };
        depot.corner1 = new Tuple<>(-10, -10); depot.corner2 = new Tuple<>(10, 10);
        CapturingTrain first = (CapturingTrain) unsafe.allocateInstance(CapturingTrain.class);
        CapturingTrain second = (CapturingTrain) unsafe.allocateInstance(CapturingTrain.class);
        CapturingTrain ignored = (CapturingTrain) unsafe.allocateInstance(CapturingTrain.class);
        for (int round = 0; round < 5; round++) {
            depot.requestDeploy(1, first); depot.requestDeploy(2, second); depot.requestDeploy(3, ignored); depot.requestDeploy(4, ignored);
            depot.deployTrain(data, null);
            require(first.calls == (round + 1) / 2 && second.calls == (round + 2) / 2 && ignored.calls == 0, "Siding sort/filter/round-robin changed");
            require(depot.lastDeployedMillis == NOW, "Deployment did not publish clock");
        }
        int calls = first.calls + second.calls; depot.deployTrain(data, null);
        require(first.calls + second.calls == calls, "Old requests survived normal dispatch");
        depot.requestDeploy(2, second); depot.requestDeploy(3, ignored); depot.deployTrain(data, null);
        require(second.calls == 4 && first.calls == 2 && ignored.calls == 0, "Unavailable siding skip changed");
        second.fail = true; depot.requestDeploy(2, second);
        expect("train-deploy-failure", IllegalStateException.class, () -> depot.deployTrain(data, null));
        second.fail = false; depot.deployTrain(data, null);
        require(second.calls == 6, "Failure no longer retains pending requests");
        records.add("deployment\t" + first.calls + ":" + second.calls + ":" + ignored.calls + ":" + depot.lastDeployedMillis);
    }

    /** Only the external train action is replaced; constructor is deliberately not run. */
    public static final class CapturingTrain extends TrainServer {
        int calls; boolean fail;
        private CapturingTrain() { super(1, 1, 1F, "train", "train", 1, List.of(), List.of(), 0, 0, 0.01F, List.of(), false, 1, 1); }
        @Override public void deployTrain() { calls++; if (fail) throw new IllegalStateException("train"); }
    }
    // Deliberately omit the historically incorrect map header so every emitted field is compared.
    private static byte[] packed(Depot depot, boolean reduced) throws Exception {
        try (var packer = MessagePack.newDefaultBufferPacker()) { if (reduced) depot.toReducedMessagePack(packer); else depot.toMessagePack(packer); return packer.toByteArray(); }
    }
    private static Map<String, Value> unpack(byte[] bytes) throws Exception {
        Map<String, Value> result = new LinkedHashMap<>();
        try (var unpacker = MessagePack.newDefaultUnpacker(bytes)) { while (unpacker.hasNext()) result.put(unpacker.unpackString(), unpacker.unpackValue()); }
        return result;
    }
    private static Depot withDiagnostics(java.util.function.Supplier<Depot> create) {
        PrintStream old = System.err; ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream stream = new PrintStream(output)) { System.setErr(stream); Depot result = create.get(); require(output.size() > 0, "Missing malformed-save diagnostic"); return result; }
        finally { System.setErr(old); }
    }
    private static String hex(byte[] bytes) { return HexFormat.of().formatHex(bytes); }
    private static byte[] bytes(FriendlyByteBuf packet) { byte[] bytes = new byte[packet.readableBytes()]; packet.getBytes(packet.readerIndex(), bytes); return bytes; }
    private static Buffer buffer() { return new Buffer(new FriendlyByteBuf(Unpooled.buffer())); }
    private record Buffer(FriendlyByteBuf packet) implements AutoCloseable { public void close() { packet.release(); } }
    private interface Action { void run() throws Exception; }
    private static void expect(String label, Class<? extends Throwable> type, Action action) {
        try { action.run(); } catch (Throwable failure) { require(type.isInstance(failure), label + " wrong exception " + failure); records.add(label + "\t" + failure.getClass().getName()); return; }
        throw new AssertionError(label + " did not fail");
    }
    private static void require(boolean condition, String message) { assertions++; if (!condition) throw new AssertionError(message); }
}

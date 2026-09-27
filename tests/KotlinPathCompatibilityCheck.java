package mtr.path;

import io.netty.buffer.Unpooled;
import mtr.data.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.msgpack.core.MessagePack;
import org.msgpack.value.Value;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.*;

/** Real path data/search, shared by the frozen Java release and Kotlin production classes. */
public final class KotlinPathCompatibilityCheck {
    private static final List<String> records = new ArrayList<>();
    private static int assertions;
    private static Finder finder;
    private static boolean connectedFlights;

    /** The two production search policies share graphs and assertions, not copied search implementations. */
    public interface Finder {
        int findPath(List<PathData> path, Map<BlockPos, Map<BlockPos, Rail>> rails, List<SavedRailBase> stops, int offset, int altitude, boolean fast);
        void appendPath(List<PathData> path, List<PathData> partial);
    }

    public static void main(String[] args) throws Exception {
        check(args, new Finder() {
            public int findPath(List<PathData> path, Map<BlockPos, Map<BlockPos, Rail>> rails, List<SavedRailBase> stops, int offset, int altitude, boolean fast) {
                return PathFinder.findPath(path, rails, stops, offset, altitude, fast);
            }
            public void appendPath(List<PathData> path, List<PathData> partial) { PathFinder.appendPath(path, partial); }
        }, true);
        if (Arrays.stream(PathFinder.class.getDeclaredAnnotations()).anyMatch(a -> a.annotationType().getName().equals("kotlin.Metadata"))) checkCancellation();
    }

    /** Called only for current implementations; cancellation intentionally differs from the frozen Java baseline. */
    public static void checkCancellation() {
        List<SavedRailBase> stops = stops(TransportMode.TRAIN, pos(0), pos(1), pos(19), pos(20));
        class InterruptingGraph extends LinkedHashMap<BlockPos, Map<BlockPos, Rail>> {
            int reads; boolean armed;
            @Override public Map<BlockPos, Rail> get(Object key) {
                if (armed && ++reads == 3) Thread.currentThread().interrupt();
                return super.get(key);
            }
        }
        InterruptingGraph graph = new InterruptingGraph();
        for (int i = 0; i < 20; i++) connect(graph, pos(i), pos(i + 1), RailType.IRON, TransportMode.TRAIN, true);
        for (boolean inFlight : new boolean[]{false, true}) {
            graph.armed = inFlight; graph.reads = 0;
            if (!inFlight) Thread.currentThread().interrupt();
            try {
                finder.findPath(new ArrayList<>(), graph, stops, 1, 256, true);
                throw new AssertionError("Cancelled search returned normally; inFlight=" + inFlight);
            } catch (java.util.concurrent.CancellationException expected) {
                require(Thread.currentThread().isInterrupted(), "Search consumed cancellation flag");
                require(!inFlight || graph.reads < 10, "Search did not stop near the interrupted expansion");
            } finally { Thread.interrupted(); }
        }
        System.out.println("PASS: search pre-cancellation and deterministic in-flight cancellation, flag retained");
    }

    public static void check(String[] args, Finder selectedFinder, boolean flightsConnect) throws Exception {
        records.clear();
        assertions = 0;
        finder = selectedFinder;
        connectedFlights = flightsConnect;
        if (args.length < 2) throw new IllegalArgumentException("goldenFile implementationSource [--record]");
        for (Class<?> type : List.of(PathData.class, PathFinder.class)) {
            require(Path.of(type.getProtectionDomain().getCodeSource().getLocation().toURI()).toRealPath()
                    .equals(Path.of(args[1]).toRealPath()), "Wrong implementation: " + type);
        }
        dataContracts();
        appendContracts();
        searchContracts();
        String actual = String.join("\n", records) + "\n";
        if (args.length == 3 && args[2].equals("--record")) Files.writeString(Path.of(args[0]), actual);
        else require(Files.readString(Path.of(args[0])).replace("\r\n", "\n").equals(actual), "Path behavior differs from frozen Java golden");
        System.out.println("PASS: " + assertions + " path assertions, " + records.size()
                + " Java golden records; serialization, aliasing, DFS/backtracking, turns, flights and 2,000-edge scale case");
    }

    private static void dataContracts() throws Exception {
        BlockPos start = new BlockPos(-31, -18, 57), end = new BlockPos(400, 97, -81);
        Rail rail = rail(start, end, RailType.IRON, TransportMode.TRAIN);
        PathData data = new PathData(rail, Long.MIN_VALUE + 9, -7, start, end, Integer.MAX_VALUE);
        require(data.rail == rail && data.startingPos == start && data.messagePackLength() == 6, "Constructor copies references or map size changed");
        require(data.getRailProduct().equals(new UUID(Math.min(start.asLong(), end.asLong()), Math.max(start.asLong(), end.asLong()))), "Signed UUID ordering changed");
        require(data.getRailProduct() == data.getRailProduct(), "UUID cache identity changed");
        require(PathData.getRailProduct(start, end).equals(PathData.getRailProduct(end, start)), "UUID must be undirected");
        require(data.isSameRail(new PathData(null, 0, 0, new BlockPos(-31, -18, 57), end, 0)), "Same rail is based on coordinates, not rail identity");
        require(data.isOppositeRail(new PathData(null, 0, 0, end, start, 0)), "Opposite rail changed");
        require(!data.isSameRail(new PathData(rail, 0, 0, end, start, 0)), "Reverse rail incorrectly deduplicated");
        PathData nullable = new PathData(null, 3, 4, null, null, 5);
        require(nullable.rail == null && nullable.startingPos == null && nullable.messagePackLength() == 6, "Null constructor values must be retained until used");
        expectNull(nullable::getRailProduct);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(1, 2, 3);
        PathData aliased = new PathData(rail, 0, 0, mutable, end, 0);
        UUID cached = aliased.getRailProduct();
        mutable.set(8, 9, 10);
        require(aliased.startingPos == mutable && aliased.getRailProduct() == cached, "Mutable position alias/cache behavior changed");

        byte[] packed = packed(data);
        Map<String, Value> map;
        try (var unpacker = MessagePack.newDefaultUnpacker(packed)) {
            map = RailwayData.castMessagePackValueToSKMap(unpacker.unpackValue());
        }
        require(Arrays.equals(packed, packed(new PathData(map))), "MessagePack roundtrip changed");
        FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.buffer());
        try {
            data.writePacket(packet);
            byte[] wire = new byte[packet.readableBytes()];
            packet.getBytes(0, wire);
            PathData decoded = new PathData(packet);
            require(packet.readableBytes() == 0 && Arrays.equals(packed, packed(decoded)), "Packet ordering/consumption changed");
            records.add("packet\t" + HexFormat.of().formatHex(wire));
        } finally { packet.release(); }
        records.add("messagepack\t" + HexFormat.of().formatHex(packed));
        PathData defaults = new PathData(new HashMap<String, Value>());
        require(defaults.savedRailBaseId == 0 && defaults.dwellTime == 0 && defaults.stopIndex == 0 && defaults.startingPos.equals(BlockPos.ZERO), "Missing map defaults changed");
        require(Arrays.equals(packed(defaults), packed(new PathData(new CompoundTag()))), "Legacy NBT missing-field defaults changed");
        records.add("defaults\t" + HexFormat.of().formatHex(packed(defaults)));
        CompoundTag nbt = new CompoundTag();
        nbt.putLong("saved_rail_base_id", -123);
        nbt.putInt("dwell_time", 17);
        nbt.putInt("stop_index", -4);
        nbt.putLong("starting_pos", start.asLong());
        nbt.putLong("ending_pos", end.asLong());
        PathData legacy = new PathData(nbt);
        require(legacy.savedRailBaseId == -123 && legacy.dwellTime == 17 && legacy.stopIndex == -4 && legacy.startingPos.equals(start), "Legacy NBT fields changed");
        records.add("nbt\t" + HexFormat.of().formatHex(packed(legacy)));
    }

    private static void appendContracts() {
        BlockPos a = pos(0), b = pos(1), c = pos(2);
        PathData first = new PathData(null, 11, 12, a, b, 13);
        PathData same = new PathData(null, 21, 22, a, b, 23);
        PathData last = new PathData(null, 31, 32, b, c, 33);
        List<PathData> path = new ArrayList<>(List.of(first));
        finder.appendPath(path, new ArrayList<>(List.of(same, last)));
        require(path.size() == 2 && path.get(0) == first && path.get(1) == last, "Append deduplication/metadata identity changed");
        finder.appendPath(path, new ArrayList<>());
        require(path.isEmpty(), "Empty partial path must clear destination");
        finder.appendPath(path, new ArrayList<>(Collections.singletonList(null)));
        require(path.size() == 1 && path.getFirst() == null, "Unused null element must be appended unchanged");
        path.clear();
        path.add(first);
        PathData reverse = new PathData(null, 0, 0, b, a, 0);
        finder.appendPath(path, new ArrayList<>(List.of(reverse)));
        require(path.size() == 2 && path.getLast() == reverse, "Reverse segment must not be deduplicated");
        require(finder.findPath(path, null, new ArrayList<>(), 0, 0, false) == 0 && path.isEmpty(), "Empty search must clear output before returning without using rails");
        path.add(first);
        expectNull(() -> finder.findPath(path, null, null, 0, 0, false));
        require(path.isEmpty(), "Output must clear before null stop list fails");
        new PathFinder(); // Public legacy construction remains legal from Java.
    }

    private static void searchContracts() throws Exception {
        for (int segments : new int[]{3, 7, 64, 2000}) {
            Map<BlockPos, Map<BlockPos, Rail>> graph = new LinkedHashMap<>();
            for (int i = 0; i < segments; i++) connect(graph, pos(i), pos(i + 1), RailType.IRON, TransportMode.TRAIN, true);
            List<SavedRailBase> stops = stops(TransportMode.TRAIN, pos(0), pos(1), pos(segments - 1), pos(segments));
            List<PathData> path = run("line-" + segments, graph, stops, 2);
            require(path.size() == segments && path.getFirst().savedRailBaseId == 100 && path.getLast().savedRailBaseId == 200, "Line endpoints changed");
            require(path.getLast().dwellTime == 20 && path.getLast().stopIndex == 8 && path.getFirst().stopIndex == 7, "Station dwell/stop index changed");
        }
        for (int seed = 0; seed < 128; seed++) {
            Random random = new Random(seed);
            Map<BlockPos, Map<BlockPos, Rail>> graph = new LinkedHashMap<>();
            for (int i = 0; i < 12; i++) connect(graph, pos(i), pos(i + 1), RailType.IRON, TransportMode.TRAIN, true);
            for (int i = 1; i < 11; i++) {
                BlockPos branch = new BlockPos(i * 10, 64, 10 + seed % 3);
                RailType type = switch (random.nextInt(4)) { case 0 -> RailType.TURN_BACK; case 1 -> RailType.NONE; case 2 -> RailType.DIAMOND; default -> RailType.WOODEN; };
                connect(graph, pos(i), branch, type, TransportMode.TRAIN, random.nextBoolean());
                if (random.nextBoolean()) connect(graph, branch, pos(i + 1), RailType.STONE, TransportMode.TRAIN, true);
            }
            run("branches-" + seed, graph, stops(TransportMode.TRAIN, pos(0), pos(1), pos(11), pos(12)), 2);
        }
        Map<BlockPos, Map<BlockPos, Rail>> disconnected = new LinkedHashMap<>();
        for (int i : new int[]{0, 1, 2, 8}) connect(disconnected, pos(i), pos(i + 1), RailType.IRON, TransportMode.TRAIN, true);
        List<SavedRailBase> three = stops(TransportMode.TRAIN, pos(0), pos(1), pos(2), pos(3));
        three.add(new Platform(300, TransportMode.TRAIN, pos(8), pos(9)));
        require(run("failed-second-leg", disconnected, three, 2).isEmpty(), "Failure must discard earlier successful leg");
        require(run("unreachable", disconnected, stops(TransportMode.TRAIN, pos(0), pos(1), pos(8), pos(9)), 1).isEmpty(), "Unreachable destination changed");
        for (int angle = 0; angle < 16; angle++) {
            RailAngle facing = RailAngle.values()[angle];
            BlockPos a = new BlockPos(-600, 60, -200), b = new BlockPos(-500, 60, -200);
            BlockPos c = new BlockPos(700, 72, 300), d = new BlockPos(800, 72, 300);
            Map<BlockPos, Map<BlockPos, Rail>> graph = new LinkedHashMap<>();
            connect(graph, a, b, RailType.RUNWAY, TransportMode.AIRPLANE, true);
            graph.put(c, new LinkedHashMap<>(Map.of(d, new Rail(c, facing, d, facing.getOpposite(), RailType.RUNWAY, TransportMode.AIRPLANE))));
            graph.put(d, new LinkedHashMap<>(Map.of(c, new Rail(d, facing.getOpposite(), c, facing, RailType.RUNWAY, TransportMode.AIRPLANE))));
            for (boolean fast : new boolean[]{false, true}) {
                List<PathData> result = new ArrayList<>();
                int status = finder.findPath(result, graph, stops(TransportMode.AIRPLANE, a, b, c, d), 7, angle % 2 == 0 ? 256 : 32, fast);
                require(connectedFlights ? status == 2 && !result.isEmpty() : status == 1 && result.isEmpty(), "Flight policy changed: " + angle);
                record("flight-" + angle + "-" + fast, status, result);
            }
        }
    }

    private static List<PathData> run(String name, Map<BlockPos, Map<BlockPos, Rail>> graph, List<SavedRailBase> stops, int expected) throws Exception {
        List<PathData> path = new ArrayList<>();
        int status = finder.findPath(path, graph, stops, 7, 256, true);
        require(status == expected, name + " returned " + status + " instead of " + expected);
        record(name, status, path);
        return path;
    }

    private static void record(String name, int status, List<PathData> path) throws Exception {
        MessageDigest hash = MessageDigest.getInstance("SHA-256");
        for (PathData part : path) hash.update(packed(part));
        records.add(name + "\t" + status + "\t" + path.size() + "\t" + HexFormat.of().formatHex(hash.digest()));
    }

    private static byte[] packed(PathData data) throws Exception {
        try (var packer = MessagePack.newDefaultBufferPacker()) {
            packer.packMapHeader(data.messagePackLength());
            data.toMessagePack(packer);
            return packer.toByteArray();
        }
    }

    private static List<SavedRailBase> stops(TransportMode mode, BlockPos a, BlockPos b, BlockPos c, BlockPos d) {
        return new ArrayList<>(List.of(new Platform(100, mode, a, b), new Platform(200, mode, c, d)));
    }

    private static BlockPos pos(int index) { return new BlockPos(index * 10, 64, 0); }

    private static Rail rail(BlockPos a, BlockPos b, RailType type, TransportMode mode) {
        RailAngle facing = RailAngle.fromAngle((float) Math.toDegrees(Math.atan2(b.getZ() - a.getZ(), b.getX() - a.getX())));
        return new Rail(a, facing, b, facing.getOpposite(), type, mode);
    }

    private static void connect(Map<BlockPos, Map<BlockPos, Rail>> graph, BlockPos a, BlockPos b, RailType type, TransportMode mode, boolean both) {
        graph.computeIfAbsent(a, ignored -> new LinkedHashMap<>()).put(b, rail(a, b, type, mode));
        graph.computeIfAbsent(b, ignored -> new LinkedHashMap<>());
        if (both) graph.get(b).put(a, rail(b, a, type, mode));
    }

    private static void expectNull(Runnable action) {
        try { action.run(); } catch (NullPointerException expected) { assertions++; return; }
        throw new AssertionError("Expected deferred NullPointerException");
    }

    private static void require(boolean condition, String message) {
        assertions++;
        if (!condition) throw new AssertionError(message);
    }
}

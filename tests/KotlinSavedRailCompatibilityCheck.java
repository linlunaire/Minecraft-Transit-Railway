package mtr.data;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.msgpack.core.MessagePack;
import org.msgpack.value.Value;
import org.msgpack.value.ValueFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/** Original Java and production Kotlin share scenarios, not a copied implementation. */
public final class KotlinSavedRailCompatibilityCheck {
    private static final List<String> records = new ArrayList<>();
    private static int assertions;

    public static void main(String[] args) throws Exception {
        if (args.length < 2) throw new IllegalArgumentException("goldenFile implementationSource [--record]");
        Path implementation = Path.of(args[1]).toRealPath();
        for (Class<?> type : List.of(SavedRailBase.class, Platform.class)) {
            require(Path.of(type.getProtectionDomain().getCodeSource().getLocation().toURI()).toRealPath().equals(implementation), "Wrong implementation: " + type);
            if (args.length == 3) require(Files.isRegularFile(implementation) && Arrays.stream(type.getDeclaredAnnotations())
                    .noneMatch(annotation -> annotation.annotationType().getName().equals("kotlin.Metadata")), "Goldens must come from Java");
        }
        dataContracts();
        dwellContracts();
        geometryContracts();
        railValidation();
        comparisons();
        String actual = String.join("\n", records) + "\n";
        if (args.length == 3 && args[2].equals("--record")) Files.writeString(Path.of(args[0]), actual);
        else require(Files.readString(Path.of(args[0])).replace("\r\n", "\n").equals(actual), "Saved-rail behavior differs from frozen Java golden");
        System.out.println("PASS: " + assertions + " saved-rail/platform assertions, " + records.size()
                + " Java golden records; endpoint order/identity, duplicate/null/mutable positions, dwell mutation, numeric comparison and serialization");
    }

    private static void dataContracts() throws Exception {
        BlockPos a = new BlockPos(-30, -9, 70), b = new BlockPos(64, 24, -40);
        for (TransportMode mode : TransportMode.values()) {
            Platform platform = new Platform(91, mode, a, b);
            require(platform.name.equals("1") && platform.getDwellTime() == (mode.continuousMovement ? 1 : 20), "New platform defaults changed");
            require(platform.containsPos(a) && platform.containsPos(new BlockPos(b)) && !platform.containsPos(BlockPos.ZERO), "Position set membership changed");
            byte[] packed = packed(platform);
            require(Arrays.equals(packed, packed(new Platform(unpack(packed)))) && platform.messagePackLength() == 7, "Saved-rail map roundtrip changed");
            try (Buffer buffer = buffer()) {
                platform.writePacket(buffer.packet);
                Platform copy = new Platform(buffer.packet);
                require(Arrays.equals(packed, packed(copy)) && buffer.packet.readableBytes() == 0, "Saved-rail wire roundtrip changed");
            }
            records.add("mode-" + mode + "\t" + HexFormat.of().formatHex(packed) + "\t" + wire(platform));
            Platform generatedId = new Platform(mode, a, b);
            require(generatedId.transportMode == mode && generatedId.containsPos(a), "ID-generating constructor changed");
            Map<String, Value> map = unpack(packed);
            map.put("dwell_time", ValueFactory.newInteger(-9));
            Platform invalidDwell = new Platform(map);
            records.add("map-dwell-" + mode + "\t" + wire(invalidDwell));
            require(invalidDwell.getDwellTime() == (mode.continuousMovement ? 1 : 20), "Map dwell correction changed");
            CompoundTag tag = new CompoundTag();
            tag.putLong("id", 71); tag.putString("transport_mode", mode.toString()); tag.putString("name", "9 | P");
            tag.putInt("color", 22); tag.putLong("pos_1", a.asLong()); tag.putLong("pos_2", b.asLong()); tag.putInt("dwell_time", 1301);
            Platform legacy = new Platform(tag);
            records.add("nbt-" + mode + "\t" + wire(legacy));
            require(legacy.containsPos(a) && legacy.containsPos(b) && legacy.getDwellTime() == (mode.continuousMovement ? 1 : 20), "NBT positions/dwell changed");
        }
        Platform defaults = new Platform(new HashMap<>());
        require(defaults.id == 0 && defaults.name.equals("") && defaults.getOtherPosition(BlockPos.ZERO).equals(BlockPos.ZERO), "Missing saved-rail defaults changed");
        records.add("default-map\t" + wire(defaults));
        records.add("default-nbt\t" + wire(new Platform(new CompoundTag())));
        Platform duplicate = new Platform(3, TransportMode.TRAIN, a, new BlockPos(a));
        require(duplicate.getOtherPosition(a).equals(BlockPos.ZERO), "Duplicate endpoint must retain legacy missing-second zero fallback");
        records.add("duplicate\t" + wire(duplicate));
        Platform nullable = new Platform(4, TransportMode.TRAIN, a, null);
        require(nullable.containsPos(null) && nullable.getOtherPosition(a) == null, "Null endpoint must remain stored until used");
        expect("null-midpoint", NullPointerException.class, nullable::getMidPos);
        expect("null-order", NullPointerException.class, () -> nullable.getOrderedPositions(a, false));
        expect("null-other-argument", NullPointerException.class, () -> duplicate.getOtherPosition(null));
        expect("null-mode", NullPointerException.class, () -> new Platform(1, null, a, b));
        expect("null-map", NullPointerException.class, () -> new Platform((Map<String, Value>) null));
        expect("null-nbt", NullPointerException.class, () -> new Platform((CompoundTag) null));
        expect("null-packet", NullPointerException.class, () -> new Platform((FriendlyByteBuf) null));
    }

    private static void dwellContracts() throws Exception {
        for (TransportMode mode : TransportMode.values()) for (int dwell : new int[]{Integer.MIN_VALUE, -1, 0, 1, 19, 20, 1199, 1200, 1201, Integer.MAX_VALUE}) {
            Platform platform = new Platform(72, mode, new BlockPos(1, 2, 3), new BlockPos(4, 5, 6));
            try (Buffer buffer = buffer()) {
                buffer.packet.writeUtf(" 9 | P ").writeInt(-15).writeInt(dwell);
                platform.update("dwell_time", buffer.packet);
                require(buffer.packet.readableBytes() == 0 && platform.name.equals(" 9 | P ") && platform.color == -15, "Platform update changed");
            }
            String before = wire(platform);
            int expected = mode.continuousMovement ? 1 : dwell <= 0 || dwell > SavedRailBase.MAX_DWELL_TIME ? 20 : dwell;
            require(platform.getDwellTime() == expected, "Lazy dwell correction changed");
            List<String> sent = new ArrayList<>();
            platform.setDwellTime(dwell, packet -> {
                try { sent.add(bytes(packet)); require(platform.getDwellTime() == expected, "Callback must observe corrected dwell"); }
                finally { packet.release(); }
            });
            require(sent.size() == 1, "Dwell callback count changed");
            records.add("dwell-" + mode + "-" + dwell + "\t" + before + "\t" + wire(platform) + "\t" + sent.getFirst());
            Probe probe = new Probe(mode);
            probe.dwellTime = dwell;
            require(probe.getDwellTime() == expected && probe.dwellTime == (dwell <= 0 || dwell > 1200 ? 20 : dwell), "Getter's protected-field mutation changed");
            expect("null-dwell-packet-" + mode + "-" + dwell, NullPointerException.class, () -> probe.writeDwellTimePacket(null, dwell));
            require(probe.dwellTime == expected, "Protected packet writer must mutate before null-packet failure");
        }
        Platform platform = new Platform(72, TransportMode.TRAIN, BlockPos.ZERO, new BlockPos(4, 5, 6));
        platform.update("unknown", null);
        expect("null-update-key", NullPointerException.class, () -> platform.update(null, null));
        expect("null-callback", NullPointerException.class, () -> platform.setDwellTime(88, null));
        require(platform.getDwellTime() == 88, "Null callback must not prevent dwell mutation");
        RuntimeException marker = new RuntimeException("callback-marker");
        try { platform.setDwellTime(45, packet -> { packet.release(); throw marker; }); throw new AssertionError("Callback failure swallowed"); }
        catch (RuntimeException failure) { require(failure == marker && platform.getDwellTime() == 45, "Callback failure identity/mutation changed"); }
        try (Buffer buffer = buffer()) {
            buffer.packet.writeUtf("short").writeInt(17);
            expect("truncated-dwell", IndexOutOfBoundsException.class, () -> platform.update("dwell_time", buffer.packet));
            require(platform.name.equals("short") && platform.color == 17 && platform.getDwellTime() == 45, "Partial packet mutations changed");
        }
    }

    private static void geometryContracts() throws Exception {
        Random random = new Random(219731);
        StringBuilder fingerprint = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            BlockPos a = new BlockPos(random.nextInt(201) - 100, random.nextInt(101) - 50, random.nextInt(201) - 100);
            BlockPos b = new BlockPos(random.nextInt(201) - 100, random.nextInt(101) - 50, random.nextInt(201) - 100);
            BlockPos query = new BlockPos(random.nextInt(201) - 100, random.nextInt(101) - 50, random.nextInt(201) - 100);
            Platform platform = new Platform(12, TransportMode.TRAIN, a, b);
            require(platform.getOtherPosition(a) == b && platform.getOtherPosition(b) == a, "Endpoint references copied");
            require(platform.getMidPos().equals(new BlockPos((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2, (a.getZ() + b.getZ()) / 2)), "Midpoint changed");
            require(platform.getMidPos(true).equals(new BlockPos((a.getX() + b.getX()) / 2, 0, (a.getZ() + b.getZ()) / 2)), "Zero-Y midpoint changed");
            require(platform.getAxis() == (Math.abs(a.getX() - b.getX()) > Math.abs(a.getZ() - b.getZ()) ? Direction.Axis.X : Direction.Axis.Z), "Axis choice changed");
            List<BlockPos> forward = platform.getOrderedPositions(query, false), reverse = platform.getOrderedPositions(query, true);
            require(forward.get(0) == reverse.get(1) && forward.get(1) == reverse.get(0), "Reverse endpoint order changed");
            require(forward.get(0).distSqr(query) <= forward.get(1).distSqr(query), "Endpoint distance ordering changed");
            forward.clear();
            require(platform.containsPos(a) && platform.containsPos(b), "Ordered position list must not own the set");
            boolean close = platform.isCloseToSavedRail(query, 17, 11, 9);
            fingerprint.append(platform.getOtherPosition(query) == a ? 'a' : 'b').append(close ? '1' : '0');
        }
        records.add("geometry\t" + fingerprint);
        Platform tied = new Platform(2, TransportMode.TRAIN, new BlockPos(-10, 0, 0), new BlockPos(10, 0, 0));
        records.add("distance-tie\t" + tied.getOrderedPositions(BlockPos.ZERO, false) + "\t" + tied.getOrderedPositions(BlockPos.ZERO, true));
        require(tied.getAxis() == Direction.Axis.X && tied.isCloseToSavedRail(new BlockPos(11, 1, 1), 1, 1, 1)
                && !tied.isCloseToSavedRail(new BlockPos(12, 1, 1), 1, 1, 1), "Inclusive distance box edge changed");
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(1, 2, 3);
        BlockPos fixed = new BlockPos(8, 9, 10);
        Platform aliased = new Platform(4, TransportMode.TRAIN, mutable, fixed);
        mutable.set(-8, -9, -10);
        require(aliased.getOtherPosition(fixed) == mutable && aliased.getMidPos().equals(BlockPos.ZERO), "Mutable endpoint alias changed");
        records.add("mutable\t" + aliased.containsPos(mutable) + "\t" + wire(aliased));
        Platform overflow = new Platform(9, TransportMode.TRAIN, new BlockPos(Integer.MAX_VALUE, Integer.MIN_VALUE, 0), new BlockPos(Integer.MAX_VALUE - 2, Integer.MIN_VALUE + 2, 0));
        records.add("overflow\t" + overflow.getMidPos() + "\t" + overflow.getAxis() + "\t" + overflow.isCloseToSavedRail(BlockPos.ZERO, Integer.MAX_VALUE, 0, 0));
    }

    private static void railValidation() {
        BlockPos a = new BlockPos(0, 64, 0), b = new BlockPos(10, 64, 0);
        Platform platform = new Platform(1, TransportMode.TRAIN, a, b);
        Map<BlockPos, Map<BlockPos, Rail>> rails = new HashMap<>();
        require(platform.isInvalidSavedRail(rails), "Missing rail must be invalid");
        rails.put(a, new HashMap<>()); rails.put(b, new HashMap<>());
        for (RailType type : RailType.values()) {
            rails.get(a).put(b, new Rail(a, RailAngle.E, b, RailAngle.W, type, TransportMode.TRAIN));
            rails.get(b).put(a, new Rail(b, RailAngle.W, a, RailAngle.E, type, TransportMode.TRAIN));
            require(platform.isInvalidSavedRail(rails) == !type.hasSavedRail && SavedRailBase.isInvalidSavedRail(rails, a, b) == !type.hasSavedRail, "Saved-rail type validation changed: " + type);
        }
        rails.get(a).put(b, new Rail(a, RailAngle.E, b, RailAngle.W, RailType.PLATFORM, TransportMode.TRAIN));
        rails.get(b).clear();
        require(platform.isInvalidSavedRail(rails), "One-way rail must be invalid");
        expect("null-rails", NullPointerException.class, () -> platform.isInvalidSavedRail(null));
        require(Probe.isInvalidSavedRail(null, null, null), "Java static hiding changed");
    }

    private static void comparisons() {
        String[] names = {"1", "2", "10", "01", "1.0", "-2", "-.5", ".5", "2147483648", "9999999999999999999999999999999999999999", "NaN", "1e2", "A", "", null};
        StringBuilder results = new StringBuilder();
        for (String left : names) for (String right : names) {
            Platform a = new Platform(1, TransportMode.TRAIN, BlockPos.ZERO, BlockPos.ZERO);
            Platform b = new Platform(2, TransportMode.TRAIN, BlockPos.ZERO, BlockPos.ZERO);
            a.name = left; b.name = right; a.color = -1; b.color = 15;
            try { results.append(a.compareTo(b)).append(','); }
            catch (NullPointerException expected) { results.append("null,"); }
        }
        records.add("numeric-comparison\t" + results);
    }

    private static byte[] packed(SerializedDataBase value) throws Exception {
        try (var packer = MessagePack.newDefaultBufferPacker()) { packer.packMapHeader(value.messagePackLength()); value.toMessagePack(packer); return packer.toByteArray(); }
    }
    private static Map<String, Value> unpack(byte[] packed) throws Exception {
        try (var unpacker = MessagePack.newDefaultUnpacker(packed)) { return RailwayData.castMessagePackValueToSKMap(unpacker.unpackValue()); }
    }
    private static String wire(SerializedDataBase value) { try (Buffer buffer = buffer()) { value.writePacket(buffer.packet); return bytes(buffer.packet); } }
    private static String bytes(FriendlyByteBuf packet) {
        byte[] bytes = new byte[packet.readableBytes()]; packet.getBytes(packet.readerIndex(), bytes); return HexFormat.of().formatHex(bytes);
    }
    private static Buffer buffer() { return new Buffer(new FriendlyByteBuf(Unpooled.buffer())); }
    private record Buffer(FriendlyByteBuf packet) implements AutoCloseable { public void close() { packet.release(); } }
    private static void require(boolean condition, String message) { assertions++; if (!condition) throw new AssertionError(message); }
    private static void expect(String label, Class<? extends Throwable> type, Runnable action) {
        try { action.run(); }
        catch (Throwable failure) {
            require(type.isInstance(failure), "Wrong exception for " + label + ": " + failure);
            records.add(label + "\t" + failure.getClass().getName()); return;
        }
        throw new AssertionError("Missing exception: " + label);
    }
    private static class Probe extends SavedRailBase {
        Probe(TransportMode mode) { super(1, mode, BlockPos.ZERO, BlockPos.ZERO); }
        public static boolean isInvalidSavedRail(Map<BlockPos, Map<BlockPos, Rail>> rails, BlockPos a, BlockPos b) { return true; }
    }
}

package mtr.data;

import io.netty.buffer.Unpooled;
import mtr.mappings.Tuple;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.msgpack.core.MessagePack;
import org.msgpack.value.Value;
import org.msgpack.value.ValueFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

/** Runs the same public-interface scenarios against the frozen Java JAR and production Kotlin. */
public final class KotlinStationCompatibilityCheck {
    private static final List<String> records = new ArrayList<>();
    private static int assertions;

    public static void main(String[] args) throws Exception {
        if (args.length < 2) throw new IllegalArgumentException("goldenFile implementationSource [--record]");
        Path implementation = Path.of(args[1]).toRealPath();
        for (Class<?> type : List.of(NameColorDataBase.class, AreaBase.class, Station.class)) {
            require(Path.of(type.getProtectionDomain().getCodeSource().getLocation().toURI()).toRealPath().equals(implementation), "Wrong implementation: " + type);
            if (args.length == 3) require(Files.isRegularFile(implementation) && Arrays.stream(type.getDeclaredAnnotations())
                    .noneMatch(annotation -> annotation.annotationType().getName().equals("kotlin.Metadata")), "Goldens must come from the Java release");
        }
        constructorsAndSerialization();
        areaContracts();
        updatesAndCallbacks();
        exitsAndComparison();
        String actual = String.join("\n", records) + "\n";
        if (args.length == 3 && args[2].equals("--record")) Files.writeString(Path.of(args[0]), actual);
        else require(Files.readString(Path.of(args[0])).replace("\r\n", "\n").equals(actual), "Station behavior differs from frozen Java golden");
        System.out.println("PASS: " + assertions + " station/area assertions, " + records.size()
                + " Java golden records; save/wire formats, legacy NBT, nullable fields, aliasing, callback order and geometry");
    }

    private static void constructorsAndSerialization() throws Exception {
        Station fresh = new Station();
        require(fresh.name.equals("") && fresh.color == 0 && fresh.zone == 0 && fresh.exits.isEmpty()
                && fresh.transportMode == TransportMode.TRAIN && fresh.corner1 == null && fresh.corner2 == null, "New station defaults changed");
        require(new Probe().name.equals("") && new Probe(TransportMode.BOAT).transportMode == TransportMode.BOAT, "Base convenience constructors changed");
        Probe nullable = new Probe(-42, null);
        require(nullable.transportMode == null && nullable.isTransportMode(null) && !nullable.isTransportMode(TransportMode.TRAIN), "Nullable mode contract changed");
        require(new Station(42).id == 42 && new Station(-42).id == -42, "Explicit station ID changed");
        Station defaults = new Station(new HashMap<>());
        require(defaults.id == 0 && defaults.name.equals("") && defaults.transportMode == TransportMode.TRAIN, "Missing saved ID must stay zero");
        record("defaults", defaults);
        require(defaults.isTransportMode(null) && defaults.isTransportMode(TransportMode.AIRPLANE), "Stations must accept every transport mode");

        Station station = example();
        byte[] packed = packed(station);
        Station unpacked = new Station(unpack(packed));
        require(Arrays.equals(packed, packed(unpacked)) && unpacked.messagePackLength() == 10, "MessagePack station roundtrip changed");
        record("populated", station);
        try (Buffer buffer = buffer()) {
            station.writePacket(buffer.packet);
            Station decoded = new Station(buffer.packet);
            require(buffer.packet.readableBytes() == 0 && decoded.name.equals("中央|Central") && decoded.zone == -9, "Packet name normalization or read order changed");
            record("packet-normalized", decoded);
        }
        Map<String, Value> values = unpack(packed);
        values.put("transport_mode", ValueFactory.newString("unknown"));
        values.put("x_min", ValueFactory.newInteger(0));
        values.put("z_min", ValueFactory.newInteger(0));
        Station fallback = new Station(values);
        require(fallback.transportMode == TransportMode.TRAIN && fallback.corner1 == null && fallback.corner2 != null, "Mode fallback or zero corner sentinel changed");
        record("unknown-mode-zero-corner", fallback);

        CompoundTag tag = new CompoundTag();
        tag.putLong("id", -700); tag.putString("transport_mode", "AIRPLANE"); tag.putString("name", " Old | Name ");
        tag.putInt("color", 0xABCDEF); tag.putInt("zone", 17);
        tag.putInt("x_min", 11); tag.putInt("z_min", -22); tag.putInt("x_max", -33); tag.putInt("z_max", 44);
        CompoundTag destinations = new CompoundTag(), exits = new CompoundTag();
        destinations.putString("one", "Airport"); destinations.putString("two", "Bus");
        exits.put("A1", destinations); tag.put("exits", exits);
        Station legacy = new Station(tag);
        require(legacy.id == -700 && legacy.transportMode == TransportMode.AIRPLANE && legacy.name.equals(" Old | Name ")
                && new HashSet<>(legacy.exits.get("A1")).equals(Set.of("Airport", "Bus")), "Legacy NBT conversion changed");
        record("legacy-nbt", legacy);
        record("empty-nbt", new Station(new CompoundTag()));

        try (Buffer buffer = buffer()) {
            writePrefix(buffer.packet, "BAD_MODE", " A  |  B ");
            buffer.packet.writeInt(3).writeInt(-2).writeByte(99);
            Station negative = new Station(buffer.packet);
            require(negative.exits.isEmpty() && negative.name.equals(" A | B ") && buffer.packet.readUnsignedByte() == 99, "Negative exit count or single-pass trimming changed");
            record("negative-exit-count", negative);
        }
        try (Buffer buffer = buffer()) {
            writePrefix(buffer.packet, "BOAT", "Duplicate");
            buffer.packet.writeInt(8).writeInt(2).writeUtf("A").writeInt(1).writeUtf("old").writeUtf("A").writeInt(-1).writeByte(77);
            Station duplicate = new Station(buffer.packet);
            require(duplicate.exits.get("A").isEmpty() && buffer.packet.readUnsignedByte() == 77, "Duplicate parent/negative destination count changed");
            record("duplicate-parent", duplicate);
        }
        expect("null-map", NullPointerException.class, () -> new Station((Map<String, Value>) null));
        expect("null-tag", NullPointerException.class, () -> new Station((CompoundTag) null));
        expect("null-packet", NullPointerException.class, () -> new Station((FriendlyByteBuf) null));
        values.put("exits", ValueFactory.newMap(ValueFactory.newString("A"), ValueFactory.newInteger(2)));
        expect("invalid-exit-array", RuntimeException.class, () -> new Station(values));
    }

    private static void areaContracts() throws Exception {
        Probe empty = new Probe(12);
        require(!AreaBase.nonNullCorners(null) && !AreaBase.nonNullCorners(empty) && !empty.inArea(0, 0)
                && empty.getCenter() == null && !empty.intersecting(null), "Empty/null area behavior changed");
        require(HiddenProbe.nonNullCorners(null), "Java static hiding changed");
        Probe area = area(8, 7, -3, -4);
        require(area.inArea(8, 7) && area.inArea(-3, -4) && area.inArea(0, 0) && !area.inArea(9, 0), "Inclusive/reversed corners changed");
        require(area.getCenter().equals(new BlockPos(2, 0, 1)), "Center must truncate toward zero");
        require(area.intersecting(area(0, 0, 1, 1)) && !area.intersecting(null), "Containment/null intersection changed");
        require(!area(-10, -1, 10, 1).intersecting(area(-1, -10, 1, 10)), "Legacy corner-containment intersection changed for crossing strips");
        Probe overflow = area(Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE - 2, Integer.MIN_VALUE + 2);
        records.add("overflow-center\t" + overflow.getCenter().getX() + "," + overflow.getCenter().getZ());
        Tuple<Integer, Integer> reference = area.corner1;
        area.corner2 = area.corner1;
        require(area.corner1 == reference && area.getCenter().equals(new BlockPos(8, 0, 7)), "Corner reference identity changed");
        area.corner1 = new Tuple<>(null, 2);
        require(AreaBase.nonNullCorners(area), "Corner availability must not inspect tuple contents");
        expect("null-corner-value", NullPointerException.class, () -> area.inArea(1, 2));
        try (Buffer buffer = buffer()) {
            buffer.packet.writeInt(0).writeInt(0).writeInt(0).writeInt(-2);
            empty.update("corners", buffer.packet);
            require(empty.corner1 == null && empty.corner2.getA() == 0 && empty.corner2.getB() == -2, "Corner update sentinels changed");
            records.add("corner-update\t" + wire(empty));
        }
        Random random = new Random(7112781);
        StringBuilder geometry = new StringBuilder();
        for (int i = 0; i < 512; i++) {
            int x1 = random.nextInt(401) - 200, z1 = random.nextInt(401) - 200, x2 = random.nextInt(401) - 200, z2 = random.nextInt(401) - 200;
            Probe sample = area(x1, z1, x2, z2);
            int x = random.nextInt(601) - 300, z = random.nextInt(601) - 300;
            boolean inside = x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && z >= Math.min(z1, z2) && z <= Math.max(z1, z2);
            require(sample.inArea(x, z) == inside, "Area containment changed at " + i);
            require(sample.getCenter().equals(new BlockPos((x1 + x2) / 2, 0, (z1 + z2) / 2)), "Area center changed at " + i);
            geometry.append(sample.intersecting(area(-31, -23, 41, 53)) ? '1' : '0');
        }
        records.add("area-intersections\t" + geometry);
    }

    private static void updatesAndCallbacks() throws Exception {
        Station station = example();
        station.update("unknown", null);
        expect("null-update-key", NullPointerException.class, () -> station.update(null, null));
        try (Buffer buffer = buffer()) {
            buffer.packet.writeUtf("missing").writeInt(123).writeByte(9);
            station.update("exit_destinations", buffer.packet);
            require(buffer.packet.readInt() == 123 && buffer.packet.readUnsignedByte() == 9, "Missing parent consumed destination payload");
        }
        List<String> original = station.exits.get("A1");
        update(station, "exit_edit_parent", packet -> packet.writeUtf("A1").writeUtf("B1"));
        require(station.exits.get("B1") == original && !station.exits.containsKey("A1"), "Rename must transfer the original list");
        update(station, "exit_destinations", packet -> packet.writeUtf("B1").writeInt(2).writeUtf("New").writeUtf("Other"));
        require(original.equals(List.of("New", "Other")), "Destination update must mutate existing list");
        update(station, "exit_destinations", packet -> packet.writeUtf("B1").writeInt(-7));
        require(original.isEmpty(), "Negative destination count must clear the list");
        original.add("before");
        try (Buffer buffer = buffer()) {
            buffer.packet.writeUtf("B1");
            expect("truncated-destinations", IndexOutOfBoundsException.class, () -> station.update("exit_destinations", buffer.packet));
            require(original.isEmpty(), "Truncated destination update must clear before reading count");
        }
        update(station, "zone", packet -> packet.writeUtf(" Untrimmed | Zone ").writeInt(-91).writeInt(Integer.MIN_VALUE));
        require(station.name.equals(" Untrimmed | Zone ") && station.color == -91 && station.zone == Integer.MIN_VALUE, "Zone update ordering/normalization changed");
        update(station, "name", packet -> packet.writeUtf(" Name | Only ").writeInt(15));
        require(station.zone == Integer.MIN_VALUE && station.name.equals(" Name | Only "), "Name update must not change zone or trim");
        update(station, "exit_delete_parent", packet -> packet.writeUtf("B1"));
        require(!station.exits.containsKey("B1"), "Parent deletion failed");
        record("updated", station);

        capture("send-name", station::setNameColor);
        station.setNameColor(null);
        capture("send-corners", station::setCorners);
        capture("send-zone", station::setZone);
        List<String> moved = station.exits.get("B2");
        capture("send-rename", consumer -> station.setExitParent("B2", "C1", packet -> {
            require(station.exits.get("C1") == moved && !station.exits.containsKey("B2"), "Rename callback must observe completed mutation");
            consumer.accept(packet);
        }));
        capture("send-destinations", consumer -> station.setExitDestinations("C1", consumer));
        capture("send-delete", consumer -> station.deleteExitParent("C1", packet -> {
            require(!station.exits.containsKey("C1"), "Delete callback must observe completed mutation");
            consumer.accept(packet);
        }));
        station.setExitDestinations("absent", null);
        station.setExitDestinations(null, null);
        station.exits.put("null-list", null);
        capture("rename-null-list", consumer -> station.setExitParent("null-list", "restored", consumer));
        require(station.exits.get("restored").isEmpty(), "Null list must become an empty list on rename");
        station.exits.put(null, new ArrayList<>(List.of("nullable-key")));
        expect("rename-null-new", NullPointerException.class, () -> station.setExitParent("restored", null, packet -> packet.release()));
        require(station.exits.containsKey(null) && !station.exits.containsKey("restored"), "Null-key rename must mutate before packet failure");
        expect("delete-null", NullPointerException.class, () -> station.deleteExitParent(null, packet -> packet.release()));
        require(!station.exits.containsKey(null), "Null-key delete must mutate before packet failure");
        expect("null-zone-callback", NullPointerException.class, () -> station.setZone(null));
        expect("null-corner-callback", NullPointerException.class, () -> station.setCorners(null));
        RuntimeException marker = new RuntimeException("callback-marker");
        try {
            station.setExitParent("missing", "created", packet -> { packet.release(); throw marker; });
            throw new AssertionError("Callback exception swallowed");
        } catch (RuntimeException failure) { require(failure == marker && station.exits.containsKey("created"), "Callback exception identity/mutation changed"); }
    }

    private static void exitsAndComparison() throws Exception {
        Station station = new Station(11);
        List<String> a = new ArrayList<>(List.of("root")), a1 = new ArrayList<>(List.of("one")), a2 = new ArrayList<>(List.of("two"));
        station.exits.put("A", a); station.exits.put("A1", a1); station.exits.put("A2", a2);
        station.exits.put("B1", new ArrayList<>(List.of("three")));
        Map<String, List<String>> generated = station.getGeneratedExits();
        require(generated.get("A") == a && generated.get("A1") == a1 && a.equals(List.of("root", "one", "two")), "Legacy grouped exits/source aliasing changed");
        require(generated.get("B") != station.exits.get("B1") && generated.get("B").equals(List.of("three")), "Synthetic prefix must own a separate aggregate");
        station.getGeneratedExits();
        require(a.equals(List.of("root", "one", "two", "one", "two")), "Repeated legacy grouping behavior changed");
        record("grouped-aliasing", station);
        station.exits.put("", new ArrayList<>());
        expect("empty-exit-parent", StringIndexOutOfBoundsException.class, station::getGeneratedExits);
        station.exits.clear(); station.exits.put("A1", null);
        expect("null-exit-list", NullPointerException.class, station::getGeneratedExits);

        for (String exit : List.of("", "A", "A12", "abcdefgh", "abcdefghi", "站口", "\u0000A", "😀1", "\uFFFF")) {
            long code = Station.serializeExit(exit);
            records.add("exit-code\tutf16:" + HexFormat.of().formatHex(exit.getBytes(java.nio.charset.StandardCharsets.UTF_16BE)) + "\t" + code + "\tutf16:"
                    + HexFormat.of().formatHex(Station.deserializeExit(code).getBytes(java.nio.charset.StandardCharsets.UTF_16BE)));
        }
        require(Station.deserializeExit(-1).isEmpty() && Station.deserializeExit(Long.MIN_VALUE).isEmpty(), "Signed exit decoding changed");
        expect("null-exit-code", NullPointerException.class, () -> Station.serializeExit(null));
        Locale previous = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            String[] names = {"ISTANBUL", "istanbul", "Alpha", "alpha", "站|Z", "Iİıi", "", null};
            StringBuilder comparisons = new StringBuilder();
            for (int left = 0; left < names.length - 1; left++) for (int right = 0; right < names.length; right++) {
                Station first = new Station(1), second = new Station(2);
                first.name = names[left]; first.color = -left;
                second.name = names[right]; second.color = right * 10;
                int result = first.compareTo(second);
                require(((Comparable<NameColorDataBase>) first).compareTo(second) == result, "Comparable bridge changed");
                comparisons.append(result).append(',');
            }
            records.add("comparisons\t" + comparisons);
            Station nullName = new Station(1); nullName.name = null;
            expect("null-left-name", NullPointerException.class, () -> nullName.compareTo(new Station(2)));
            expect("null-compare", NullPointerException.class, () -> new Station(1).compareTo(null));
        } finally { Locale.setDefault(previous); }
    }

    private static Station example() {
        Station station = new Station(Long.MIN_VALUE + 17);
        station.name = "中央 | Central"; station.color = 0x123456; station.zone = -9;
        station.corner1 = new Tuple<>(-13, 27); station.corner2 = new Tuple<>(68, -44);
        station.exits.put("A1", new ArrayList<>(List.of("Street", "街道")));
        station.exits.put("B2", new ArrayList<>());
        return station;
    }
    private static Probe area(int x1, int z1, int x2, int z2) {
        Probe result = new Probe(12);
        result.corner1 = new Tuple<>(x1, z1); result.corner2 = new Tuple<>(x2, z2);
        return result;
    }
    private static void writePrefix(FriendlyByteBuf packet, String mode, String name) {
        packet.writeLong(45).writeUtf(mode).writeUtf(name).writeInt(98).writeInt(1).writeInt(2).writeInt(3).writeInt(4);
    }
    private static void update(Station station, String key, Consumer<FriendlyByteBuf> writer) {
        try (Buffer buffer = buffer()) {
            writer.accept(buffer.packet); station.update(key, buffer.packet);
            require(buffer.packet.readableBytes() == 0, "Unexpected update payload remainder: " + key);
        }
    }
    private static void capture(String label, Consumer<Consumer<FriendlyByteBuf>> action) {
        int[] calls = {0};
        action.accept(packet -> {
            try { records.add(label + "\t" + bytes(packet)); calls[0]++; }
            finally { packet.release(); }
        });
        require(calls[0] == 1, "Expected one callback: " + label);
    }
    private static byte[] packed(SerializedDataBase value) throws Exception {
        try (var packer = MessagePack.newDefaultBufferPacker()) {
            packer.packMapHeader(value.messagePackLength()); value.toMessagePack(packer); return packer.toByteArray();
        }
    }
    private static Map<String, Value> unpack(byte[] packed) throws Exception {
        try (var unpacker = MessagePack.newDefaultUnpacker(packed)) { return RailwayData.castMessagePackValueToSKMap(unpacker.unpackValue()); }
    }
    private static void record(String label, SerializedDataBase value) throws Exception {
        records.add(label + "\t" + HexFormat.of().formatHex(packed(value)) + "\t" + wire(value));
    }
    private static String wire(SerializedDataBase value) {
        try (Buffer buffer = buffer()) { value.writePacket(buffer.packet); return bytes(buffer.packet); }
    }
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
    private static class Probe extends AreaBase {
        Probe() { super(); }
        Probe(long id) { super(id); }
        Probe(TransportMode mode) { super(mode); }
        Probe(long id, TransportMode mode) { super(id, mode); }
        @Override protected boolean hasTransportMode() { return true; }
    }
    private static final class HiddenProbe extends Probe {
        public static boolean nonNullCorners(AreaBase area) { return true; }
    }
}

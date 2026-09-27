package mtr.data;

import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.msgpack.core.MessagePack;
import org.msgpack.value.Value;
import org.msgpack.value.ValueFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.*;
import java.util.function.Consumer;

/** Same public-interface checks for the frozen Java release and production Kotlin Route. */
public final class KotlinRouteCompatibilityCheck {
    private static final List<String> records = new ArrayList<>();
    private static int assertions;

    public static void main(String[] args) throws Exception {
        if (args.length < 2) throw new IllegalArgumentException("goldenFile implementationSource [--record]");
        Path implementation = Path.of(args[1]).toRealPath();
        for (Class<?> type : List.of(Route.class, Route.RoutePlatform.class, Route.CircularState.class)) {
            require(Path.of(type.getProtectionDomain().getCodeSource().getLocation().toURI()).toRealPath().equals(implementation), "Wrong implementation: " + type);
            if (args.length == 3) require(Files.isRegularFile(implementation) && Arrays.stream(type.getDeclaredAnnotations())
                    .noneMatch(annotation -> annotation.annotationType().getName().equals("kotlin.Metadata")), "Record only from the original Java release");
        }
        serialization();
        destinationAndMembership();
        updates();
        String actual = String.join("\n", records) + "\n";
        if (args.length == 3 && args[2].equals("--record")) Files.writeString(Path.of(args[0]), actual);
        else require(Files.readString(Path.of(args[0])).replace("\r\n", "\n").equals(actual), "Route behavior differs from frozen Java golden");
        System.out.println("PASS: " + assertions + " route assertions, " + records.size()
                + " Java golden records; constructors/save/wire formats, enum/flag combinations, destination resets, list identity and partial updates");
    }

    private static void serialization() throws Exception {
        require(Arrays.equals(Route.CircularState.values(), new Route.CircularState[]{Route.CircularState.NONE, Route.CircularState.CLOCKWISE, Route.CircularState.ANTICLOCKWISE}), "Circular enum order changed");
        Route fresh = new Route(TransportMode.BOAT);
        require(fresh.transportMode == TransportMode.BOAT && fresh.name.equals("") && fresh.platformIds.isEmpty() && fresh.routeType == RouteType.NORMAL
                && fresh.circularState == Route.CircularState.NONE && fresh.lightRailRouteNumber.equals("") && !fresh.isHidden && !fresh.isLightRailRoute && !fresh.disableNextStationAnnouncements, "New route defaults changed");
        Route nullable = new Route(41, null);
        require(nullable.transportMode == null && nullable.isTransportMode(null) && !nullable.isTransportMode(TransportMode.TRAIN), "Nullable mode constructor changed");
        Route empty = new Route(new HashMap<>());
        require(empty.id == 0 && empty.messagePackLength() == 12, "Default saved ID or map length changed");
        record("empty", empty);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        for (TransportMode mode : TransportMode.values()) for (RouteType type : RouteType.values()) for (Route.CircularState circle : Route.CircularState.values()) for (int flags = 0; flags < 8; flags++) {
            Route route = example(mode);
            route.routeType = type; route.circularState = circle;
            route.isLightRailRoute = (flags & 1) != 0; route.isHidden = (flags & 2) != 0; route.disableNextStationAnnouncements = (flags & 4) != 0;
            byte[] packed = packed(route);
            Route restored = new Route(unpack(packed));
            require(Arrays.equals(packed, packed(restored)), "Route MessagePack roundtrip changed");
            try (Buffer buffer = buffer()) {
                route.writePacket(buffer.packet);
                byte[] wire = bytes(buffer.packet);
                Route packetCopy = new Route(buffer.packet);
                require(buffer.packet.readableBytes() == 0 && Arrays.equals(packed, packed(packetCopy)), "Route packet order/flags changed");
                digest.update(wire);
            }
            digest.update(packed);
        }
        records.add("all-mode-type-circle-flags\t" + HexFormat.of().formatHex(digest.digest()));
        Route populated = example(TransportMode.TRAIN);
        record("populated", populated);
        Map<String, Value> map = unpack(packed(populated));
        map.put("custom_destinations", ValueFactory.newArray(ValueFactory.newString("one")));
        Route shorter = new Route(map);
        require(shorter.platformIds.get(0).customDestination.equals("one") && shorter.platformIds.get(1).customDestination.isEmpty(), "Missing custom destination must default to empty");
        record("short-destinations", shorter);
        map.put("platform_ids", ValueFactory.newArray(ValueFactory.newInteger(99)));
        map.put("custom_destinations", ValueFactory.newArray(ValueFactory.newString("one"), ValueFactory.newString("ignored")));
        map.put("route_type", ValueFactory.newString("unknown")); map.put("circular_state", ValueFactory.newString("clockwise"));
        Route longer = new Route(map);
        require(longer.platformIds.size() == 1 && longer.routeType == RouteType.NORMAL && longer.circularState == Route.CircularState.NONE, "Excess destination or enum fallback changed");
        record("long-destinations", longer);
        CompoundTag tag = new CompoundTag();
        tag.putLong("id", 65); tag.putString("transport_mode", "AIRPLANE"); tag.putString("name", "Old | Route"); tag.putInt("color", -123);
        tag.putLongArray("platform_ids", new long[]{-3, Long.MAX_VALUE, -3}); tag.putString("route_type", "HIGH_SPEED");
        tag.putString("circular_state", "ANTICLOCKWISE"); tag.putBoolean("is_light_rail_route", true); tag.putBoolean("is_route_hidden", true);
        tag.putBoolean("disable_next_station_announcements", true); tag.putString("light_rail_route_number", "R42");
        Route legacy = new Route(tag);
        require(legacy.platformIds.size() == 3 && legacy.platformIds.stream().allMatch(platform -> platform.customDestination.isEmpty()), "Legacy NBT platform restoration changed");
        record("nbt", legacy);
        record("nbt-defaults", new Route(new CompoundTag()));
        try (Buffer buffer = buffer()) {
            buffer.packet.writeLong(5).writeUtf("BAD_MODE").writeUtf(" A | B ").writeInt(2).writeInt(-9)
                    .writeUtf("BAD_TYPE").writeBoolean(true).writeBoolean(false).writeBoolean(true).writeUtf("number").writeUtf("BAD_CIRCLE").writeByte(77);
            Route negative = new Route(buffer.packet);
            require(negative.platformIds.isEmpty() && negative.name.equals(" A|B ") && negative.routeType == RouteType.NORMAL
                    && negative.circularState == Route.CircularState.NONE && buffer.packet.readUnsignedByte() == 77, "Negative platform count/packet defaults changed");
            record("negative-count", negative);
        }
        expect("null-map", NullPointerException.class, () -> new Route((Map<String, Value>) null));
        expect("null-nbt", NullPointerException.class, () -> new Route((CompoundTag) null));
        expect("null-packet", NullPointerException.class, () -> new Route((FriendlyByteBuf) null));
        map.put("custom_destinations", ValueFactory.newArray(ValueFactory.newInteger(5)));
        expect("malformed-destination", RuntimeException.class, () -> new Route(map));
    }

    private static void destinationAndMembership() {
        Route empty = new Route(1, TransportMode.TRAIN);
        require(empty.getFirstPlatformId() == 0 && empty.getLastPlatformId() == 0 && empty.getPlatformIdIndex(0) == -1 && !empty.containsPlatformId(0)
                && empty.getDestination(Integer.MAX_VALUE) == null && empty.getDestination(Integer.MIN_VALUE) == null, "Empty route queries changed");
        Route route = example(TransportMode.TRAIN);
        require(route.getFirstPlatformId() == -1 && route.getLastPlatformId() == -1 && route.getPlatformIdIndex(-1) == 0 && route.containsPlatformId(7), "Duplicate ID query order changed");
        require(route.platformIds.get(0) != route.platformIds.get(2), "Duplicate entries must retain distinct identities");
        for (String reset : List.of("\\r", "\\reset")) require(Route.destinationIsReset(reset), "Missing destination reset marker");
        for (String text : List.of("", "\r", "\\R", "\\RESET", " \\r", "\\reset ")) require(!Route.destinationIsReset(text), "Reset matching became permissive");
        expect("null-reset", NullPointerException.class, () -> Route.destinationIsReset(null));
        List<String> destinations = Arrays.asList("First", "", "\\r", "", "第二|Second", "\\reset", "", "Final");
        route.platformIds.clear();
        for (int i = 0; i < destinations.size(); i++) { Route.RoutePlatform entry = new Route.RoutePlatform(i); entry.customDestination = destinations.get(i); route.platformIds.add(entry); }
        StringBuilder result = new StringBuilder();
        for (int index : new int[]{Integer.MIN_VALUE, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, Integer.MAX_VALUE}) result.append(index).append('=').append(route.getDestination(index)).append(';');
        records.add("destinations\t" + result);
        require(route.getDestination(1).equals("First") && route.getDestination(3) == null && route.getDestination(4).equals("第二|Second"), "Destination inheritance/reset changed");
        route.platformIds.get(4).customDestination = "changed";
        require(route.getDestination(4).equals("changed"), "Mutable destination changes are not visible");
        route.platformIds.get(4).customDestination = null;
        expect("null-destination", NullPointerException.class, () -> route.getDestination(4));
        route.platformIds.add(null);
        expect("null-platform", NullPointerException.class, route::getLastPlatformId);
        Route.RoutePlatform subclass = new Route.RoutePlatform(19) {};
        require(subclass.platformId == 19 && subclass.customDestination.isEmpty(), "RoutePlatform stopped supporting Java subclasses");
        Random random = new Random(519317);
        StringBuilder membership = new StringBuilder();
        route.platformIds.clear();
        for (int i = 0; i < 512; i++) route.platformIds.add(new Route.RoutePlatform(random.nextInt(100) - 50));
        for (long query = -75; query <= 75; query++) {
            int expected = -1;
            for (int i = 0; i < route.platformIds.size(); i++) if (route.platformIds.get(i).platformId == query) { expected = i; break; }
            require(route.getPlatformIdIndex(query) == expected && route.containsPlatformId(query) == (expected >= 0), "Ordered membership changed");
            membership.append(expected).append(',');
        }
        records.add("membership\t" + membership);
    }

    private static void updates() throws Exception {
        Route route = example(TransportMode.TRAIN);
        List<Route.RoutePlatform> identity = route.platformIds;
        route.update("unknown", null);
        expect("null-key", NullPointerException.class, () -> route.update(null, null));
        expect("null-platform-update", NullPointerException.class, () -> route.update("platform_ids", null));
        require(identity == route.platformIds && identity.isEmpty(), "Platform update must clear before packet failure, preserving list identity");
        try (Buffer buffer = buffer()) {
            buffer.packet.writeInt(2).writeLong(91).writeUtf("First").writeLong(92);
            expect("partial-platform-update", IndexOutOfBoundsException.class, () -> route.update("platform_ids", buffer.packet));
            require(identity.size() == 1 && identity.get(0).platformId == 91 && identity.get(0).customDestination.equals("First"), "Partial update committed an incomplete entry");
        }
        try (Buffer buffer = buffer()) {
            buffer.packet.writeInt(-7).writeByte(42); route.update("platform_ids", buffer.packet);
            require(identity.isEmpty() && buffer.packet.readUnsignedByte() == 42, "Negative platform update count changed");
        }
        Route source = example(TransportMode.BOAT);
        source.routeType = RouteType.HIGH_SPEED; source.circularState = Route.CircularState.CLOCKWISE;
        source.isLightRailRoute = true; source.isHidden = false; source.disableNextStationAnnouncements = true;
        capture("send-platforms", source::setPlatformIds, route);
        capture("send-extra", source::setExtraData, route);
        require(route.routeType == source.routeType && route.circularState == source.circularState && route.isLightRailRoute
                && !route.isHidden && route.disableNextStationAnnouncements && route.lightRailRouteNumber.equals(source.lightRailRouteNumber), "Extra update order differs from sender");
        require(route.platformIds == identity && route.platformIds.get(0) != source.platformIds.get(0), "Packet update list/entry ownership changed");
        record("updated", route);
        try (Buffer buffer = buffer()) {
            buffer.packet.writeUtf("Partial | Name").writeInt(456).writeUtf("unknown").writeBoolean(false);
            expect("partial-extra-update", IndexOutOfBoundsException.class, () -> route.update("is_light_rail_route", buffer.packet));
            require(route.name.equals("Partial | Name") && route.color == 456 && route.routeType == RouteType.NORMAL && !route.isLightRailRoute
                    && route.circularState == Route.CircularState.CLOCKWISE, "Partial extra update/enum fallback changed");
        }
        expect("null-platform-callback", NullPointerException.class, () -> route.setPlatformIds(null));
        expect("null-extra-callback", NullPointerException.class, () -> route.setExtraData(null));
        RuntimeException marker = new RuntimeException("callback-marker");
        try { route.setPlatformIds(packet -> { packet.release(); throw marker; }); throw new AssertionError("Callback exception swallowed"); }
        catch (RuntimeException failure) { require(failure == marker, "Callback exception identity changed"); }
    }

    private static Route example(TransportMode mode) {
        Route route = new Route(-91, mode); route.name = "路线|Route"; route.color = 0xABCDEF; route.lightRailRouteNumber = "12A";
        for (long id : new long[]{-1, 7, -1}) route.platformIds.add(new Route.RoutePlatform(id));
        route.platformIds.get(0).customDestination = "终点|End";
        route.platformIds.get(2).customDestination = "\\reset";
        return route;
    }
    private static void capture(String label, Consumer<Consumer<FriendlyByteBuf>> sender, Route target) {
        int[] calls = {0};
        sender.accept(packet -> {
            try {
                records.add(label + "\t" + HexFormat.of().formatHex(bytes(packet)));
                require(packet.readLong() == -91 && packet.readUtf().equals("BOAT"), "Update envelope changed");
                target.update(packet.readUtf(), packet); require(packet.readableBytes() == 0, "Unread update payload"); calls[0]++;
            } finally { packet.release(); }
        });
        require(calls[0] == 1, "Expected one callback");
    }
    private static byte[] packed(Route route) throws Exception {
        try (var packer = MessagePack.newDefaultBufferPacker()) { packer.packMapHeader(route.messagePackLength()); route.toMessagePack(packer); return packer.toByteArray(); }
    }
    private static Map<String, Value> unpack(byte[] bytes) throws Exception {
        try (var unpacker = MessagePack.newDefaultUnpacker(bytes)) { return RailwayData.castMessagePackValueToSKMap(unpacker.unpackValue()); }
    }
    private static void record(String label, Route route) throws Exception {
        try (Buffer buffer = buffer()) { route.writePacket(buffer.packet); records.add(label + "\t" + HexFormat.of().formatHex(packed(route)) + "\t" + HexFormat.of().formatHex(bytes(buffer.packet))); }
    }
    private static byte[] bytes(FriendlyByteBuf packet) { byte[] bytes = new byte[packet.readableBytes()]; packet.getBytes(packet.readerIndex(), bytes); return bytes; }
    private static Buffer buffer() { return new Buffer(new FriendlyByteBuf(Unpooled.buffer())); }
    private record Buffer(FriendlyByteBuf packet) implements AutoCloseable { public void close() { packet.release(); } }
    private static void require(boolean condition, String message) { assertions++; if (!condition) throw new AssertionError(message); }
    private static void expect(String label, Class<? extends Throwable> type, Runnable action) {
        try { action.run(); } catch (Throwable failure) {
            require(type.isInstance(failure), "Wrong exception for " + label + ": " + failure);
            records.add(label + "\t" + failure.getClass().getName()); return;
        }
        throw new AssertionError("Missing exception: " + label);
    }
}

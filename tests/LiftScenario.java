package mtr.data;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import mtr.block.BlockLiftTrackFloor;
import mtr.block.BlockPSDAPGDoorBase;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import org.msgpack.core.MessagePack;
import org.msgpack.value.Value;
import org.msgpack.value.ValueFactory;
import sun.misc.Unsafe;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.function.*;

/** Real lift algorithms and codecs. No game, network, renderer or audio device is started. */
public final class LiftScenario {
    private static final UUID RIDER = new UUID(1, 2), SECOND = new UUID(3, 4);
    private static final List<String> records = new ArrayList<>(), events = new ArrayList<>();
    private static final Map<Player, BlockPos> positions = new IdentityHashMap<>();
    private static final Map<Player, UUID> uuids = new IdentityHashMap<>();
    private static final Map<BlockPos, BlockEntity> blockEntities = new HashMap<>();
    private static final Set<BlockPos> unloaded = new HashSet<>(), locked = new HashSet<>();
    private static final List<Player> online = new ArrayList<>();
    private static Unsafe unsafe;
    private static Level world;
    private static Minecraft client;
    private static Block floorBlock;
    private static boolean clientSide, shouldDing, validFloor, nearPlayer;
    private static int assertions, nextPlayer;
    private static String failAt;
    private static Consumer<Set<UUID>> mountMutation;
    private static Vec3 offset = Vec3.ZERO;

    public static String run() throws Exception {
        net.minecraft.SharedConstants.tryDetectVersion(); net.minecraft.server.Bootstrap.bootStrap();
        Field singleton = Unsafe.class.getDeclaredField("theUnsafe"); singleton.setAccessible(true); unsafe = (Unsafe) singleton.get(null);
        world = (Level) unsafe.allocateInstance(ServerLevel.class);
        client = (Minecraft) unsafe.allocateInstance(Minecraft.class);
        floorBlock = (Block) unsafe.allocateInstance(BlockLiftTrackFloor.class);
        constructorsAndCodecs(); floorsAndFailures(); packetCallbackContracts(); motionAndDoors(); serverSync(); clientLifecycle();
        System.out.println("Lift assertions: " + assertions + ", records: " + records.size());
        return String.join("\n", records) + "\n";
    }

    private static void reset() {
        online.clear(); positions.clear(); uuids.clear(); events.clear(); blockEntities.clear(); unloaded.clear(); locked.clear();
        clientSide = false; shouldDing = true; validFloor = true; nearPlayer = true; failAt = null; mountMutation = null; offset = Vec3.ZERO; nextPlayer = 0;
    }
    private static Map<String, Value> saved(double y) {
        Map<String, Value> map = new HashMap<>();
        map.put("id", ValueFactory.newInteger(77)); map.put("name", ValueFactory.newString("电梯 | Lift🚉")); map.put("color", ValueFactory.newInteger(0xABCDEF));
        map.put("lift_height", ValueFactory.newInteger(4)); map.put("lift_width", ValueFactory.newInteger(2)); map.put("lift_depth", ValueFactory.newInteger(3));
        map.put("lift_style", ValueFactory.newString("TRANSPARENT")); map.put("facing", ValueFactory.newInteger(90));
        map.put("current_position_x", ValueFactory.newFloat(8.5)); map.put("current_position_y", ValueFactory.newFloat(y)); map.put("current_position_z", ValueFactory.newFloat(-12.5));
        return map;
    }
    private static Probe probe(double y) { return new Probe(saved(y)); }
    private static FriendlyByteBuf buffer() { return new FriendlyByteBuf(Unpooled.buffer()); }
    private static byte[] packet(Lift lift) {
        FriendlyByteBuf packet = buffer(); try { lift.writePacket(packet); return ByteBufUtil.getBytes(packet); } finally { packet.release(); }
    }
    private static Probe fromPacket(byte[] bytes) { FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.wrappedBuffer(bytes)); try { return new Probe(packet); } finally { packet.release(); } }
    private static LiftClient client(Lift lift) { FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.wrappedBuffer(packet(lift))); try { return new LiftClient(packet); } finally { packet.release(); } }
    private static String messagePack(Lift lift) throws Exception { try (var packer = MessagePack.newDefaultBufferPacker()) { packer.packMapHeader(lift.messagePackLength()); lift.toMessagePack(packer); return HexFormat.of().formatHex(packer.toByteArray()); } }
    private static String state(Lift lift) {
        return lift.liftHeight + ":" + lift.liftWidth + ":" + lift.liftDepth + ":" + lift.liftOffsetX + ":" + lift.liftOffsetY + ":" + lift.liftOffsetZ + ":" + lift.isDoubleSided + ":" + lift.liftStyle + ":" + lift.facing
                + ":" + Double.toHexString(lift.getPositionX()) + ":" + Double.toHexString(lift.getPositionY()) + ":" + Double.toHexString(lift.getPositionZ()) + ":" + lift.getLiftDirection()
                + ":" + Double.toHexString(lift.speed) + ":" + lift.doorOpen + ":" + Float.toHexString(lift.doorValue) + ":" + lift.frontCanOpen + ":" + lift.backCanOpen + ":" + lift.floors + ":" + lift.ridingEntities + ":" + hexInstructions(lift);
    }
    private static String hexInstructions(Lift lift) { FriendlyByteBuf buf = buffer(); try { lift.liftInstructions.writePacket(buf); return ByteBufUtil.hexDump(buf); } finally { buf.release(); } }
    private static void record(String name, Object value) { records.add(name + "\t" + value); }
    private static void require(boolean condition, String message) { assertions++; if (!condition) throw new AssertionError(message); }
    private static String attempt(Throwing action) { try { action.run(); return "ok"; } catch (Exception error) { return error.getClass().getSimpleName(); } }

    private static void constructorsAndCodecs() throws Exception {
        reset(); Probe lift = new Probe(new BlockPos(8, 7, -12), Direction.WEST);
        require(lift.id != 0 && lift.isTransportMode(null), "Lift construction / transport independence");
        record("position-constructor", state(lift));
        record("nullable-facing-constructor", state(new Probe(BlockPos.ZERO, null)));
        record("null-position", attempt(() -> new Probe((BlockPos) null, Direction.NORTH)));
        record("empty-map", state(new Probe(new HashMap<>())));
        Map<String, Value> malformed = saved(12); malformed.put("lift_style", ValueFactory.newString("unknown"));
        record("unknown-style", state(new Probe(malformed)));
        malformed.put("riding_entities", ValueFactory.newArray(ValueFactory.newString("not-a-uuid")));
        record("invalid-uuid", attempt(() -> new Probe(malformed)));
        record("null-map", attempt(() -> new Probe((Map<String, Value>) null)));
        record("null-packet", attempt(() -> new Probe((FriendlyByteBuf) null)));

        for (Direction direction : Direction.values()) for (Lift.LiftStyle style : Lift.LiftStyle.values()) {
            lift = probe(22.75); lift.facing = direction; lift.liftStyle = style; lift.isDoubleSided = style == Lift.LiftStyle.OPAQUE;
            lift.liftOffsetX = -3; lift.liftOffsetY = 2; lift.liftOffsetZ = 5; lift.liftWidth = 7; lift.liftHeight = 6;
            lift.currentPositionX = -0.0; lift.speed = 0.333; lift.doorOpen = false; lift.doorValue = 3.5F; lift.liftDirection = Lift.LiftDirection.DOWN;
            lift.frontCanOpen = true; lift.backCanOpen = true;
            lift.setFloors(new ArrayList<>(List.of(new BlockPos(8, 0, -12), new BlockPos(8, 20, -12), new BlockPos(8, 40, -12))));
            lift.ridingEntities.add(RIDER); lift.ridingEntities.add(SECOND); lift.pressButton(40); lift.pressButton(0);
            byte[] bytes = packet(lift); Probe copy = fromPacket(bytes);
            require(copy.frontCanOpen == false && copy.backCanOpen == false, "Door availability is computed, not on wire");
            require(copy.name.equals("电梯|Lift🚉"), "Packet name normalization");
            record("wire:" + direction + ":" + style, HexFormat.of().formatHex(bytes) + ":" + state(copy));
            String savedBytes = messagePack(lift); record("save:" + direction + ":" + style, savedBytes);
            try (var unpacker = MessagePack.newDefaultUnpacker(HexFormat.of().parseHex(savedBytes))) {
                Map<String, Value> map = new HashMap<>(); unpacker.unpackValue().asMapValue().map().forEach((key, value) -> map.put(key.asStringValue().asString(), value));
                Probe restored = new Probe(map);
                require(restored.getPositionY() == 20 && restored.speed == 0 && restored.doorOpen && !restored.liftInstructions.hasInstructions(), "Save must snap to floor and reset motion / instructions");
                require(restored.ridingEntities.equals(lift.ridingEntities) && restored.floors.equals(lift.floors), "Saved riders / floors");
            }
            if (direction == Direction.NORTH && style == Lift.LiftStyle.TRANSPARENT) {
                for (int size = 0; size < bytes.length; size++) {
                    final byte[] partial = Arrays.copyOf(bytes, size);
                    require(!attempt(() -> fromPacket(partial)).equals("ok"), "Truncated packet accepted at " + size);
                }
            }
        }
        FriendlyByteBuf counts = buffer();
        try {
            counts.writeLong(3); counts.writeUtf("TRAIN"); counts.writeUtf("n"); counts.writeInt(4);
            for (int i = 0; i < 6; i++) counts.writeInt(i);
            counts.writeBoolean(false); counts.writeUtf("unknown"); counts.writeInt(-450);
            counts.writeDouble(1); counts.writeDouble(2); counts.writeDouble(3); counts.writeUtf("unknown"); counts.writeDouble(0); counts.writeBoolean(true); counts.writeFloat(0);
            counts.writeInt(-1); counts.writeInt(-1); counts.writeInt(-1);
            record("negative-counts", state(new Probe(counts)));
        } finally { counts.release(); }
    }

    private static void floorsAndFailures() throws Exception {
        reset(); Probe lift = probe(10);
        record("empty-floor", lift.getCurrentFloorBlockPos() + ":" + lift.isInvalidLift(null));
        record("empty-save", attempt(() -> messagePack(lift)));
        List<BlockPos> list = new ArrayList<>(List.of(new BlockPos(1, 0, 1), new BlockPos(2, 20, 2), new BlockPos(3, 40, 3)));
        lift.setFloors(list); list.clear(); require(lift.floors.size() == 3, "Floor setter must copy contents");
        for (double y : new double[]{-99, 0, 9.99, 10, 10.01, 30, 40, 99, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY}) {
            lift.currentPositionY = y; boolean[] buttons = {false, false}; lift.hasUpDownButtonForFloor((int)y, buttons);
            record("closest:" + y, lift.getCurrentFloorBlockPos() + ":" + Arrays.toString(buttons));
        }
        lift.currentPositionY = 38; lift.setFloors(new ArrayList<>(List.of(new BlockPos(0, 40, 0), new BlockPos(0, 0, 0), new BlockPos(0, 39, 0))));
        record("unsorted-floor", lift.getCurrentFloorBlockPos());
        require(lift.hasFloor(new BlockPos(0, 39, 0)) && !lift.hasFloor(null), "Floor membership");
        record("valid-floor", lift.isInvalidLift(world)); validFloor = false;
        record("removed-floor", lift.isInvalidLift(world)); unloaded.addAll(lift.floors);
        record("unloaded-floors", lift.isInvalidLift(world));
        record("null-floor-set", attempt(() -> lift.setFloors(null)) + ":" + lift.floors.size());
        lift.setFloors(new ArrayList<>(List.of(BlockPos.ZERO))); lift.setFloors(lift.floors);
        require(lift.floors.isEmpty(), "Self floor copy clears list in Java");
        lift.hasUpDownButtonForFloor(0, null); lift.floors.add(new BlockPos(0, 5, 0));
        record("null-buttons", attempt(() -> lift.hasUpDownButtonForFloor(0, null)));
        boolean[] buttons = {false}; record("short-buttons-up-only", attempt(() -> lift.hasUpDownButtonForFloor(0, buttons)) + ":" + Arrays.toString(buttons));
        record("short-buttons-down", attempt(() -> lift.hasUpDownButtonForFloor(10, buttons)));
        lift.floors.add(null); record("null-floor-element", attempt(lift::getCurrentFloorBlockPos));
        lift.floors.clear(); lift.floors.add(BlockPos.ZERO); lift.ridingEntities.add(null);
        record("null-rider-wire", attempt(() -> packet(lift))); record("null-rider-save", attempt(() -> messagePack(lift)));
        lift.ridingEntities.clear(); lift.liftStyle = null; record("null-style-wire", attempt(() -> packet(lift)));
        lift.liftStyle = Lift.LiftStyle.TRANSPARENT; lift.facing = null; record("null-facing-yaw", attempt(lift::yaw));

        Probe source = probe(10); source.liftHeight = 9; source.liftWidth = 3; source.liftDepth = 8; source.liftOffsetX = 1; source.liftOffsetY = 2; source.liftOffsetZ = 3; source.isDoubleSided = true;
        LiftClient editor = client(source); FriendlyByteBuf[] update = {null}; editor.setExtraData(value -> update[0] = value);
        require(update[0] != null && update[0].refCnt() == 1, "Extra packet ownership belongs to callback");
        try {
            FriendlyByteBuf all = update[0]; require(all.readLong() == 77 && all.readUtf().equals("TRAIN"), "Extra packet identity");
            String key = all.readUtf(); byte[] payload = ByteBufUtil.getBytes(all);
            for (int size = 0; size <= payload.length; size++) {
                Probe target = probe(0); FriendlyByteBuf part = new FriendlyByteBuf(Unpooled.wrappedBuffer(Arrays.copyOf(payload, size)));
                try { record("partial-update:" + size, attempt(() -> target.update(key, part)) + ":" + state(target) + ":" + part.readerIndex()); }
                finally { part.release(); }
            }
        } finally { update[0].release(); }
        Probe target = probe(0); target.update("unknown", null); record("null-key", attempt(() -> target.update(null, null)));
        FriendlyByteBuf name = buffer(); try { name.writeUtf("New name"); name.writeInt(19); target.update("name", name); require(target.name.equals("New name") && target.color == 19, "Superclass update delegation"); } finally { name.release(); }
    }

    private static void packetCallbackContracts() {
        Probe lift = probe(7);
        lift.floors.add(BlockPos.ZERO); lift.floors.add(new BlockPos(0, 10, 0));
        FriendlyByteBuf mutating = new FriendlyByteBuf(Unpooled.buffer()) {
            @Override public FriendlyByteBuf writeBlockPos(BlockPos pos) {
                lift.floors.removeLast();
                return super.writeBlockPos(pos);
            }
        };
        try {
            require(attempt(() -> lift.writePacket(mutating)).equals("ConcurrentModificationException"), "Packet floor traversal lost Java ArrayList.forEach's fail-fast behavior after the final callback");
            require(lift.floors.size() == 1, "Packet callback mutation ordering changed");
        } finally { mutating.release(); }
        lift.floors.clear(); lift.floors.add(null); lift.ridingEntities.add(null);
        List<String> calls = new ArrayList<>();
        FriendlyByteBuf acceptingNull = new FriendlyByteBuf(Unpooled.buffer()) {
            @Override public FriendlyByteBuf writeUUID(UUID uuid) { calls.add("uuid:" + uuid); return this; }
            @Override public FriendlyByteBuf writeBlockPos(BlockPos pos) { calls.add("pos:" + pos); return this; }
        };
        try {
            lift.writePacket(acceptingNull);
            require(calls.equals(List.of("uuid:null", "pos:null")), "Nullable Java elements did not reach the overridable packet methods");
        } finally { acceptingNull.release(); }
    }

    private static void motionAndDoors() throws Exception {
        for (float elapsed : new float[]{0, 0.25F, 1, 2.5F}) for (int destination : new int[]{-8, 20}) {
            reset(); Probe lift = probe(7); lift.facing = Direction.NORTH;
            lift.setFloors(new ArrayList<>(List.of(new BlockPos(8, -8, -13), new BlockPos(8, 7, -13), new BlockPos(8, 20, -13)))); lift.pressButton(destination);
            var dingTile = (BlockLiftTrackFloor.TileEntityLiftTrackFloor) unsafe.allocateInstance(BlockLiftTrackFloor.TileEntityLiftTrackFloor.class);
            blockEntities.put(new BlockPos(8, destination, -13), dingTile);
            MessageDigest trace = MessageDigest.getInstance("SHA-256"); int sounds = 0;
            for (int tick = 0; tick < 1600; tick++) {
                events.clear(); lift.advance(world, elapsed); sounds += Collections.frequency(events, "sound");
                trace.update((state(lift) + events + "\n").getBytes(StandardCharsets.UTF_8));
                require(lift.doorValue >= 0 && lift.doorValue <= Lift.DOOR_MAX * 2, "Door bounds");
            }
            if (elapsed > 0) require(lift.getPositionY() == destination && !lift.liftInstructions.hasInstructions() && sounds == 1, "Lift failed to arrive/ding once");
            record("motion:" + elapsed + ":" + destination, HexFormat.of().formatHex(trace.digest()) + ":" + sounds + ":" + state(lift));
        }
        for (Direction facing : List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)) for (boolean doubleSided : List.of(false, true)) for (boolean clientWorld : List.of(false, true)) {
            reset(); Probe lift = probe(12); lift.facing = facing; lift.currentPositionX = 2; lift.currentPositionZ = -3; lift.liftDepth = 3; lift.liftOffsetX = -1; lift.liftOffsetZ = 1; lift.liftOffsetY = 2; lift.isDoubleSided = doubleSided; clientSide = clientWorld;
            for (int sign : new int[]{1, -1}) for (int i = -1; i <= 1; i++) {
                BlockPos pos = BlockPos.containing(2 - 0.5 - facing.getStepX() * sign * 2 + facing.getClockWise().getStepX() * i, 14, -3 + 0.5 - facing.getStepZ() * sign * 2 + facing.getClockWise().getStepZ() * i);
                blockEntities.put(pos, (BlockEntity) unsafe.allocateInstance(mtr.block.BlockAPGDoor.TileEntityAPGDoor.class));
                blockEntities.put(pos.above(), (BlockEntity) unsafe.allocateInstance(mtr.block.BlockAPGDoor.TileEntityAPGDoor.class));
            }
            lift.advance(world, 3.5F); require(lift.frontCanOpen && lift.backCanOpen == doubleSided, "Door geometry / double sided");
            record("doors:" + facing + ":" + doubleSided + ":" + clientWorld, state(lift) + ":" + events);
            events.clear(); nearPlayer = false; lift.advance(world, 1); require(!lift.frontCanOpen && !lift.backCanOpen, "No nearby player must not open doors");
            record("no-player:" + facing + ":" + doubleSided + ":" + clientWorld, events);
            events.clear(); nearPlayer = true; locked.addAll(blockEntities.keySet()); lift.advance(world, 1); require(!lift.frontCanOpen && !lift.backCanOpen, "Locked doors");
            record("locked:" + facing + ":" + doubleSided + ":" + clientWorld, events);
            events.clear(); locked.clear(); unloaded.addAll(blockEntities.keySet()); lift.advance(world, 1); require(!lift.frontCanOpen && !lift.backCanOpen, "Unloaded doors");
        }
        for (float elapsed : new float[]{-1, Float.NaN, Float.POSITIVE_INFINITY}) {
            reset(); Probe lift = probe(7); lift.advance(world, elapsed); record("unusual-tick:" + elapsed, state(lift));
        }
    }

    private static Player player(BlockPos position, UUID uuid) throws Exception {
        Player player = (Player) unsafe.allocateInstance(ServerPlayer.class); online.add(player); positions.put(player, position); uuids.put(player, uuid); return player;
    }
    private static void serverSync() throws Exception {
        reset(); LiftServer lift = new LiftServer(saved(7));
        Player inside = player(new BlockPos(63, 0, 0), new UUID(10, 0)), edge = player(new BlockPos(64, 0, 0), new UUID(11, 0)), rider = player(new BlockPos(900, 900, 900), RIDER);
        lift.setFloors(new ArrayList<>(List.of(BlockPos.ZERO))); lift.ridingEntities.add(RIDER);
        Map<Player, Set<LiftServer>> range = new IdentityHashMap<>(); Set<LiftServer> existing = new LinkedHashSet<>(); range.put(inside, existing); Set<LiftServer> sync = new HashSet<>();
        lift.tickServer(world, range, sync);
        require(range.get(inside) == existing && existing.contains(lift) && !range.containsKey(edge) && range.get(rider).contains(lift) && sync.isEmpty(), "Player range boundary / rider / map identity");
        record("server-range", range.size() + ":" + state(lift) + ":" + events);
        events.clear(); lift.pressButton(20); lift.tickServer(world, range, sync); require(sync.contains(lift), "Dirty instructions sync");
        sync.clear(); lift.tickServer(world, range, sync); require(sync.isEmpty(), "Dirty flag consumption");
        mountMutation = set -> set.add(SECOND); lift.tickServer(world, range, sync); require(sync.contains(lift), "Rider count sync");
        sync.clear(); mountMutation = set -> { set.remove(RIDER); set.add(new UUID(5, 6)); }; lift.tickServer(world, range, sync); require(sync.isEmpty(), "Same-size rider replacement retains Java behavior");
        record("server-dirty-count", state(lift) + ":" + sync.size());
        mountMutation = null; lift.setFloors(new ArrayList<>()); range.clear(); events.clear(); lift.tickServer(world, null, sync);
        require(range.isEmpty() && !events.contains("players"), "Empty floors skip range scan only"); record("server-empty-floors", events);
        lift.pressButton(40); failAt = "mount"; record("server-mount-failure", attempt(() -> lift.tickServer(world, range, sync)) + ":" + lift.liftInstructions.isDirty());
        reset(); Probe target = probe(7); target.doorOpen = false; target.doorValue = 0; target.pressButton(8); target.currentPositionY = 8;
        failAt = "blockEntity"; record("arrival-failure", attempt(() -> target.advance(world, 1)) + ":" + state(target));
    }

    private static void clientLifecycle() throws Exception {
        reset(); Probe source = probe(7); source.setFloors(new ArrayList<>(List.of(BlockPos.ZERO, new BlockPos(0, 10, 0)))); source.ridingEntities.add(RIDER);
        source.liftOffsetX = 3; source.liftOffsetY = -2; source.liftOffsetZ = -5; source.liftWidth = 5; source.liftDepth = 7; source.speed = 0.3;
        LiftClient lift = client(source); var firstModel = lift.getModel(); require(firstModel != null && firstModel == lift.getModel(), "Model lazy identity");
        lift.liftHeight = 7; require(firstModel == lift.getModel(), "No implicit model invalidation on public field mutation");
        for (float elapsed : new float[]{0, 0.5F, 1, -1}) {
            events.clear(); offset = new Vec3(1.25, -2.5, 3.75);
            lift.tickClient(world, (x, y, z, front, back) -> events.add("render:" + x + ":" + y + ":" + z + ":" + front + ":" + back), elapsed);
            require(events.indexOf("begin") < events.indexOf("end") && events.indexOf("end") < events.indexOf("renderOffset"), "Client phase ordering");
            require(events.contains("movePlayer") == (elapsed > 0), "Positive tick rider movement only"); record("client-tick:" + elapsed, state(lift) + ":" + events);
        }
        events.clear(); lift.startRidingClient(RIDER, Float.NaN, -0.0F); lift.updateRiderPercentages(null, 2, -1); require(lift.getViewOffset() == offset, "Offset identity"); record("client-rider-delegation", events);
        List<BlockPos> floors = new ArrayList<>(); lift.iterateFloors(floors::add); require(floors.equals(source.floors), "Client floor iteration");
        LiftClient other = client(source); other.frontCanOpen = true; other.backCanOpen = true; other.liftInstructions.addInstruction(7, false, 10);
        List<BlockPos> retainedFloors = lift.floors; Set<UUID> retainedRiders = lift.ridingEntities; LiftInstructions retainedInstructions = lift.liftInstructions;
        lift.copyFromLift(other); require(lift.floors == retainedFloors && lift.ridingEntities == retainedRiders && lift.liftInstructions == retainedInstructions, "Copy must retain container identities");
        require(lift.getModel() != firstModel && state(lift).equals(state(other)), "Copy state / model invalidation");
        other.floors.clear(); other.ridingEntities.clear(); other.liftInstructions.arrived(); require(!lift.floors.isEmpty() && !lift.ridingEntities.isEmpty() && lift.liftInstructions.hasInstructions(), "Copy must not alias mutable containers");
        record("client-copy", state(lift)); lift.copyFromLift(lift); record("client-self-copy", state(lift));
        record("client-null-copy", attempt(() -> lift.copyFromLift(null)));
        record("empty-null-floor-consumer", attempt(() -> lift.iterateFloors(null))); lift.floors.add(BlockPos.ZERO); record("null-floor-consumer", attempt(() -> lift.iterateFloors(null)));
        events.clear(); failAt = "renderOffset"; record("client-render-failure", attempt(() -> lift.tickClient(world, null, 1)) + ":" + events);
    }

    // World and IO adapters intentionally expose calls/failures; production policies are not replaced.
    private static void event(String name) { events.add(name); if (name.equals(failAt)) throw new IllegalStateException("fixture failure"); }
    public static boolean clientSide(Level ignored) { return clientSide; }
    public static boolean loaded(Level ignored, BlockPos pos) { event("loaded:" + pos); return !unloaded.contains(pos); }
    public static BlockEntity blockEntity(Level ignored, BlockPos pos) { event("blockEntity"); return blockEntities.get(pos); }
    public static BlockState blockState(Level ignored, BlockPos pos) { return Blocks.AIR.defaultBlockState(); }
    public static Block block(BlockState ignored) { return validFloor ? floorBlock : Blocks.AIR; }
    public static Player nearestPlayer(Level ignored, double x, double y, double z, double distance, Predicate<Entity> predicate) {
        event("nearest"); require(distance == Train.MAX_CHECK_DISTANCE, "Player scan distance");
        if (!nearPlayer) return null;
        try { if (online.isEmpty()) return player(BlockPos.ZERO, new UUID(0, ++nextPlayer)); } catch (Exception error) { throw new AssertionError(error); }
        return online.getFirst();
    }
    public static Comparable<?> property(BlockGetter ignored, BlockPos pos, Property<?> property) { event("property:" + pos); return !locked.contains(pos); }
    public static boolean ding(BlockLiftTrackFloor.TileEntityLiftTrackFloor ignored) { event("ding"); return shouldDing; }
    public static void openDoor(BlockPSDAPGDoorBase.TileEntityPSDAPGDoorBase ignored, int value) { event("open:" + value); }
    public static void sound(Level ignored, Entity excluded, BlockPos pos, SoundEvent sound, SoundSource category, float volume, float pitch) { require(category == SoundSource.BLOCKS && volume == 16 && pitch == 2, "Lift ding arguments"); event("sound"); }
    public static List<Player> players(Level ignored) { event("players"); return online; }
    public static UUID uuid(Player player) { return uuids.get(player); }
    public static BlockPos position(Player player) { return positions.get(player); }
    public static void mount(Level ignored, Set<UUID> riders, long id, long routeId, double x, double y, double z, double length, double width, float yaw, float pitch, boolean doorOpen, boolean canMount, int percentageOffset, Identifier packetId, Function<Player, Boolean> canRide, Consumer<Player> callback) {
        event("mount"); require(id == 77 && routeId == 1 && canMount && percentageOffset == 0 && packetId.toString().equals("mtr:update_lift_passengers"), "Mount identity / packet / boarding flags");
        require(canRide.apply(null), "Lift boarding predicate"); callback.accept(null);
        events.add("mount-position:" + x + ":" + y + ":" + z + ":" + length + ":" + width + ":" + yaw + ":" + pitch + ":" + doorOpen);
        if (mountMutation != null) mountMutation.accept(riders);
    }
    public static void mount(VehicleRidingServer.Companion ignored, Level world, Set<UUID> riders, long id, long routeId, double x, double y, double z, double length, double width, float yaw, float pitch, boolean doorOpen, boolean canMount, int percentageOffset, Identifier packetId, Function<Player, Boolean> canRide, Consumer<Player> callback) { mount(world, riders, id, routeId, x, y, z, length, width, yaw, pitch, doorOpen, canMount, percentageOffset, packetId, canRide, callback); }
    public static Minecraft minecraft() { event("minecraft"); return client; }
    public static void begin(VehicleRidingClient ignored) { event("begin"); }
    public static void movePlayer(VehicleRidingClient ignored, Consumer<UUID> callback) { event("movePlayer"); callback.accept(RIDER); }
    public static void offsets(VehicleRidingClient ignored, UUID uuid, double x, double y, double z, float yaw, float pitch, double length, int width, boolean front, boolean back, boolean ascending, boolean descending, float riderOffset, float dismountOffset, boolean moving, boolean closed, Runnable callback) { events.add("offsets:" + uuid + ":" + x + ":" + y + ":" + z + ":" + yaw + ":" + pitch + ":" + length + ":" + width + ":" + front + ":" + back + ":" + ascending + ":" + descending + ":" + riderOffset + ":" + dismountOffset + ":" + moving + ":" + closed); callback.run(); }
    public static void moveSelf(VehicleRidingClient ignored, long id, UUID uuid, double length, int width, float yaw, int percentageOffset, int maxPercentage, boolean front, boolean back, boolean dismount, float elapsed) { events.add("moveSelf:" + id + ":" + uuid + ":" + length + ":" + width + ":" + yaw + ":" + percentageOffset + ":" + maxPercentage + ":" + front + ":" + back + ":" + dismount + ":" + elapsed); }
    public static void end(VehicleRidingClient ignored) { event("end"); }
    public static Vec3 renderOffset(VehicleRidingClient ignored) { event("renderOffset"); return offset; }
    public static Vec3 viewOffset(VehicleRidingClient ignored) { event("viewOffset"); return offset; }
    public static void startRiding(VehicleRidingClient ignored, UUID uuid, float x, float z) { event("start:" + uuid + ":" + x + ":" + z); }
    public static void percentages(VehicleRidingClient ignored, UUID uuid, float x, float z) { event("percentages:" + uuid + ":" + x + ":" + z); }

    private interface Throwing { void run() throws Exception; }
    public static class Probe extends Lift {
        public Probe(BlockPos pos, Direction facing) { super(pos, facing); }
        public Probe(Map<String, Value> map) { super(map); }
        public Probe(FriendlyByteBuf packet) { super(packet); }
        public void advance(Level world, float elapsed) { tick(world, elapsed); }
        public float yaw() { return getYaw(); }
    }
}

package probe;

import cn.zbx1425.mtrsteamloco.Main;
import cn.zbx1425.mtrsteamloco.block.BlockEyeCandy;
import cn.zbx1425.mtrsteamloco.data.TrainExtraSupplier;
import cn.zbx1425.mtrsteamloco.gui.DirectNodeScreen;
import cn.zbx1425.mtrsteamloco.gui.SelectScreen;
import cn.zbx1425.mtrsteamloco.mixin.SidingAccessor;
import cn.zbx1425.mtrsteamloco.network.PacketReplaceRailNode;
import cn.zbx1425.mtrsteamloco.network.PacketScreen;
import cn.zbx1425.mtrsteamloco.network.PacketUpdateHoldingItem;
import cn.zbx1425.mtrsteamloco.network.PacketUpdateTrainCustomConfigs;
import com.mojang.logging.LogUtils;
import io.netty.buffer.Unpooled;
import mtr.data.RailwayData;
import mtr.data.Siding;
import mtr.data.Train;
import mtr.data.TrainServer;
import mtr.data.TransportMode;
import mtr.mappings.ItemStackUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mod("maintenance_health_probe")
public final class HealthRuntimeProbe {
    private static final org.slf4j.Logger LOGGER = LogUtils.getLogger();
    private static boolean capturing;
    private static byte[] captured;
    private static FriendlyByteBuf capturedBuffer;
    private static ResourceLocation capturedId;
    private int ticks, phase, readyAt, failures, passes;
    private boolean closing;

    public HealthRuntimeProbe(IEventBus ignored) { NeoForge.EVENT_BUS.addListener(this::tick); }

    public static boolean capture(ResourceLocation id, FriendlyByteBuf packet) {
        if (!capturing) return false;
        captured = new byte[packet.readableBytes()];
        packet.getBytes(packet.readerIndex(), captured);
        capturedBuffer = packet;
        capturedId = id;
        return true;
    }

    private static byte[] capturePacket(Checked action) throws Exception {
        capturing = true;
        captured = null;
        capturedBuffer = null;
        try {
            action.run();
            require(captured != null, "Actual sender did not reach the transport boundary");
            return captured;
        } finally {
            capturing = false;
            // Some baseline senders leak their local buffer. The fixture owns only its captured reference.
            if (capturedBuffer != null && capturedBuffer.refCnt() > 0) capturedBuffer.release();
        }
    }

    private void tick(ClientTickEvent.Post event) {
        if (closing) return;
        Minecraft client = Minecraft.getInstance();
        try {
            GLFW.glfwHideWindow(client.getWindow().getWindow());
            if (++ticks > 2400) throw new AssertionError("Health fixture exceeded 2400 client ticks");
            if (phase == 0) {
                if (client.getOverlay() != null || ticks < 60) return;
                phase = 1;
                client.options.renderDistance().set(4);
                client.options.simulationDistance().set(6);
                client.options.pauseOnLostFocus = false;
                client.options.framerateLimit().set(60);
                client.createWorldOpenFlows().createFreshLevel("health-probe-" + System.currentTimeMillis(),
                        new LevelSettings("Disposable maintenance fixture", GameType.CREATIVE, false, Difficulty.PEACEFUL,
                                true, new GameRules(), WorldDataConfiguration.DEFAULT), new WorldOptions(0L, false, false),
                        registries -> registries.registryOrThrow(net.minecraft.core.registries.Registries.WORLD_PRESET)
                                .getOrThrow(WorldPresets.FLAT).createWorldDimensions(), client.screen);
                return;
            }
            if (client.player == null || client.level == null || client.getSingleplayerServer() == null) return;
            if (readyAt == 0) readyAt = ticks + 40;
            if (ticks < readyAt) return;
            phase = 2;
            runCase("selection-initial-list", () -> checkSelection(client));
            runCase("direct-node-screen", () -> checkDirectNode(client));
            runCase("held-eyecandy-writeback", () -> checkEyeCandy(client));
            runCase("train-config-distinct-ids", () -> checkTrainConfig(client));
            onServer(client, () -> {
                ServerPlayer player = client.getSingleplayerServer().getPlayerList().getPlayers().getFirst();
                ServerLevel level = player.serverLevel();
                runCase("route-first-node-writeback", () -> checkRouteCreator(level, player));
                runCase("negative-chunk-boundaries", () -> checkChunks(level));
                runCase("holding-legal-tools", () -> checkLegalHolding(player));
                runCase("holding-foreign-type", () -> checkRejectedHolding(player, "type"));
                runCase("holding-count", () -> checkRejectedHolding(player, "count"));
                runCase("holding-foreign-component", () -> checkRejectedHolding(player, "component"));
                runCase("holding-unowned-data", () -> checkRejectedHolding(player, "data"));
                runCase("holding-adventure-player", () -> checkRejectedHolding(player, "adventure"));
                runCase("node-replacement-non-node", () -> checkNodeReplacement(level, player, false));
                runCase("node-replacement-legal", () -> checkNodeReplacement(level, player, true));
            });
            LOGGER.info("HEALTH_RUNTIME_{}: {} passed; {} failed; actual 1.21.1 YLM/YLTE artifact methods", failures == 0 ? "PASS" : "FAIL", passes, failures);
            closing = true;
            client.stop();
        } catch (Throwable error) {
            LOGGER.error("HEALTH_CASE_FAIL: fixture infrastructure", error);
            closing = true;
            client.stop();
        }
    }

    private void runCase(String name, Checked action) {
        try {
            action.run();
            passes++;
            LOGGER.info("HEALTH_CASE_PASS: {}", name);
        } catch (Throwable error) {
            failures++;
            LOGGER.error("HEALTH_CASE_FAIL: " + name, error);
        }
    }

    private static void checkSelection(Minecraft client) throws Exception {
        SelectScreen screen = new SelectScreen(() -> null, Component.literal("Health rail selection"),
                Map.of("rail", "轨道A1", "station", "车站"), () -> "rail", (mc, selection, key) -> {}, "");
        try {
            client.setScreen(screen);
            Field filtered = SelectScreen.class.getDeclaredField("filteredButtons");
            filtered.setAccessible(true);
            require(((Map<?, ?>) filtered.get(screen)).size() == 2, "New selection screen hid all entries before typing");
            Field search = SelectScreen.class.getDeclaredField("searchField");
            search.setAccessible(true);
            EditBox box = (EditBox) search.get(screen);
            box.setValue("A1");
            require(((Map<?, ?>) filtered.get(screen)).size() == 1, "Literal search lost its match");
            box.setValue("guidao");
            require(((Map<?, ?>) filtered.get(screen)).containsKey("rail"), "Full pinyin search lost its match");
            require(((Map<?, ?>) filtered.get(screen)).size() == 1, "Full pinyin search included an unrelated match");
            box.setValue("gda1");
            require(((Map<?, ?>) filtered.get(screen)).containsKey("rail"), "Pinyin initials search lost its match");
            require(((Map<?, ?>) filtered.get(screen)).size() == 1, "Pinyin initials search included an unrelated match");
            screen.resize(client, client.getWindow().getGuiScaledWidth(), client.getWindow().getGuiScaledHeight());
            require(((Map<?, ?>) filtered.get(screen)).size() == 1, "Resize discarded the active filter");
            box.setValue("");
            require(((Map<?, ?>) filtered.get(screen)).size() == 2, "Clearing search did not restore entries");
        } finally { client.setScreen(null); }
    }

    private static void checkDirectNode(Minecraft client) {
        BlockPos pos = client.player.blockPosition().offset(2, 0, 0);
        client.level.setBlockAndUpdate(pos, Main.BLOCK_DIRECT_NODE.get().defaultBlockState());
        FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.buffer());
        try {
            packet.writeInt(1).writeUtf("direct_node").writeBlockPos(pos);
            PacketScreen.receiveScreenS2C(packet);
            require(client.screen instanceof DirectNodeScreen, "Direct-node packet opened the wrong screen");
        } finally {
            packet.release();
            client.setScreen(null);
        }
    }

    private static void checkEyeCandy(Minecraft client) throws Exception {
        Class<?> virtual = Class.forName("cn.zbx1425.mtrsteamloco.gui.EyeCandyScreen$VirtualEyeCandy");
        Constructor<?> constructor = virtual.getDeclaredConstructor(Supplier.class, InteractionHand.class);
        constructor.setAccessible(true);
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack old = client.player.getItemInHand(hand);
            try {
                ItemStack item = new ItemStack(Main.ITEM_EYE_CANDY.get());
                CompoundTag root = new CompoundTag();
                root.putString("Unrelated", "keep");
                root.put("BlockEntityTag", new CompoundTag());
                ItemStackUtilities.setCustomData(item, root);
                client.player.setItemInHand(hand, item);
                Supplier<CompoundTag> supplier = () -> ItemStackUtilities.getCustomData(client.player.getItemInHand(hand)).getCompound("BlockEntityTag");
                BlockEyeCandy.BlockEntityEyeCandy editor = (BlockEyeCandy.BlockEntityEyeCandy) constructor.newInstance(supplier, hand);
                editor.fullLight = true;
                editor.translateX = 2.5F;
                byte[] wire = capturePacket(editor::sendUpdateC2S);
                CompoundTag saved = ItemStackUtilities.getCustomData(item);
                require(saved.getCompound("BlockEntityTag").getBoolean("fullLight")
                        && saved.getCompound("BlockEntityTag").getFloat("translateX") == 2.5F, "Held eye-candy edit vanished from CustomData: " + hand);
                require(saved.getString("Unrelated").equals("keep"), "Eye-candy edit removed unrelated root data");
                FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.wrappedBuffer(wire));
                try {
                    require(packet.readBoolean() == (hand == InteractionHand.MAIN_HAND), "Wrong eye-candy hand");
                    ItemStack received = ItemStack.STREAM_CODEC.decode(new RegistryFriendlyByteBuf(packet, client.player.registryAccess()));
                    require(ItemStack.matches(item, received), "Outgoing eye-candy packet did not carry saved components");
                } finally { packet.release(); }
            } finally { client.player.setItemInHand(hand, old); }
        }
    }

    private static void checkTrainConfig(Minecraft client) throws Exception {
        TrainServer target = train(202, 101);
        Map<String, String> expected = Map.of("mode", "auto");
        ((TrainExtraSupplier) target).setCustomConfigs(expected);
        byte[] wire = capturePacket(() -> PacketUpdateTrainCustomConfigs.sendUpdateC2S(target));
        require(capturedId.equals(PacketUpdateTrainCustomConfigs.C2S), "Train configuration channel changed");
        FriendlyByteBuf order = new FriendlyByteBuf(Unpooled.wrappedBuffer(wire));
        try { require(order.readLong() == 101 && order.readLong() == 202, "Train configuration wire order changed"); }
        finally { order.release(); }
        ((TrainExtraSupplier) target).setCustomConfigs(Map.of());
        onServer(client, () -> {
            ServerPlayer player = client.getSingleplayerServer().getPlayerList().getPlayers().getFirst();
            RailwayData data = RailwayData.getInstance(player.level());
            Siding siding = new Siding(101, TransportMode.TRAIN, BlockPos.ZERO, new BlockPos(16, 0, 0), 16);
            TrainServer other = train(303, 101);
            ((SidingAccessor) siding).getTrains().addAll(List.of(target, other));
            data.sidings.add(siding);
            data.dataCache.sidingIdMap.put(siding.id, siding);
            FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.wrappedBuffer(wire));
            try {
                PacketUpdateTrainCustomConfigs.receiveUpdateC2S(client.getSingleplayerServer(), player, packet);
                flushServerTasks(player);
                require(((TrainExtraSupplier) target).getCustomConfigs().equals(expected), "Distinct siding/train IDs lost the target configuration");
                require(((TrainExtraSupplier) target).isConfigsChanged(), "Updated configuration did not mark the train changed");
                require(((TrainExtraSupplier) other).getCustomConfigs().isEmpty(), "Configuration changed a different train");
            } finally {
                packet.release();
                data.sidings.remove(siding);
                data.dataCache.sidingIdMap.remove(siding.id);
            }
        });
    }

    private static TrainServer train(long id, long sidingId) {
        return new TrainServer(id, sidingId, 16, "sp1900", "train_24_2", 1,
                new ArrayList<>(), new ArrayList<>(), 0, 0, Train.ACCELERATION_DEFAULT, new ArrayList<>(), true, 80, 0);
    }

    private static void checkRouteCreator(ServerLevel level, ServerPlayer player) {
        ItemStack old = player.getMainHandItem();
        try {
            ItemStack item = new ItemStack(Main.ROUTE_PATH_CREATOR.get());
            CompoundTag root = new CompoundTag();
            root.putString("Unrelated", "keep");
            ItemStackUtilities.setCustomData(item, root);
            player.setItemInHand(InteractionHand.MAIN_HAND, item);
            BlockPos pos = player.blockPosition().offset(3, 0, 0);
            level.setBlockAndUpdate(pos, mtr.Blocks.RAIL_NODE.get().defaultBlockState());
            Main.ROUTE_PATH_CREATOR.get().useOn(new UseOnContext(player, InteractionHand.MAIN_HAND,
                    new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false)));
            CompoundTag saved = ItemStackUtilities.getCustomData(item);
            require(saved.getCompound("ANTE-Data").contains("last_pos") && saved.getCompound("ANTE-Data").getLong("last_pos") == pos.asLong(),
                    "Path creator forgot its first node");
            require(saved.getString("Unrelated").equals("keep"), "Path creator changed unrelated custom data");
        } finally { player.setItemInHand(InteractionHand.MAIN_HAND, old); }
    }

    private static void checkChunks(ServerLevel level) {
        level.getChunk(-20, -20);
        int sensitive = 0, mismatches = 0;
        for (int x = -32; x < 0; x++) for (int z = -32; z < 0; z++) {
            BlockPos pos = new BlockPos(x * 16 + 15, 5, z * 16 + 15);
            boolean expected = loaded(level, SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()));
            boolean truncated = loaded(level, pos.getX() / 16, pos.getZ() / 16);
            if (expected != truncated) sensitive++;
            if (RailwayData.chunkLoaded(level, pos) != expected) mismatches++;
        }
        require(sensitive > 0, "Chunk fixture did not distinguish floor from truncating coordinates");
        require(mismatches == 0, "Negative chunk coordinates queried the wrong chunk in " + mismatches + " cases");
    }

    private static boolean loaded(ServerLevel level, int x, int z) {
        return level.getChunkSource().getChunkNow(x, z) != null && level.hasChunk(x, z);
    }

    private static void checkLegalHolding(ServerPlayer player) {
        Item[] tools = {mtr.Items.BRUSH.get(), Main.ITEM_EYE_CANDY.get(), Main.COMPOUND_CREATOR.get(), Main.ROUTE_PATH_CREATOR.get()};
        String[] keys = {"NTERailBrush", "BlockEntityTag", "tasks", "ANTE-Data"};
        GameType oldMode = player.gameMode.getGameModeForPlayer();
        try {
            player.setGameMode(GameType.SURVIVAL);
            for (InteractionHand hand : InteractionHand.values()) for (int index = 0; index < tools.length; index++) {
                ItemStack old = player.getItemInHand(hand);
                try {
                    ItemStack held = new ItemStack(tools[index]);
                    CompoundTag root = new CompoundTag();
                    root.putString("Unrelated", "keep");
                    ItemStackUtilities.setCustomData(held, root);
                    player.setItemInHand(hand, held);
                    ItemStack incoming = held.copy();
                    root.putString(keys[index], "edit");
                    ItemStackUtilities.setCustomData(incoming, root);
                    receiveHolding(player, hand, incoming);
                    require(ItemStackUtilities.getCustomData(player.getItemInHand(hand)).getString(keys[index]).equals("edit"), "Legal tool edit rejected: " + index + "/" + hand);
                    require(player.getItemInHand(hand).getCount() == 1 && player.getItemInHand(hand).is(tools[index]), "Legal edit changed the inventory item");
                } finally { player.setItemInHand(hand, old); }
            }
        } finally { player.setGameMode(oldMode); }
    }

    private static void checkRejectedHolding(ServerPlayer player, String mode) {
        ItemStack old = player.getMainHandItem();
        GameType oldMode = player.gameMode.getGameModeForPlayer();
        try {
            player.setGameMode(mode.equals("adventure") ? GameType.ADVENTURE : GameType.SURVIVAL);
            ItemStack held = new ItemStack(mtr.Items.BRUSH.get());
            CompoundTag root = new CompoundTag();
            root.putString("Unrelated", "keep");
            ItemStackUtilities.setCustomData(held, root);
            player.setItemInHand(InteractionHand.MAIN_HAND, held);
            ItemStack incoming = mode.equals("type") ? new ItemStack(Items.DIAMOND) : held.copy();
            ItemStack expected = held.copy();
            if (mode.equals("count")) incoming.setCount(64);
            if (mode.equals("component")) incoming.set(DataComponents.CUSTOM_NAME, Component.literal("Untrusted replacement"));
            root.putString(mode.equals("data") ? "Unrelated" : "NTERailBrush", "untrusted");
            ItemStackUtilities.setCustomData(incoming, root);
            receiveHolding(player, InteractionHand.MAIN_HAND, incoming);
            require(ItemStack.matches(expected, player.getMainHandItem()), "Client replaced server-owned inventory state: " + mode);
        } finally {
            player.setItemInHand(InteractionHand.MAIN_HAND, old);
            player.setGameMode(oldMode);
        }
    }

    private static void receiveHolding(ServerPlayer player, InteractionHand hand, ItemStack incoming) {
        FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.buffer());
        try {
            packet.writeBoolean(hand == InteractionHand.MAIN_HAND);
            ItemStack.STREAM_CODEC.encode(new RegistryFriendlyByteBuf(packet, player.registryAccess()), incoming);
            PacketUpdateHoldingItem.receiveUpdateC2S(player.getServer(), player, packet);
            flushServerTasks(player);
        } finally { packet.release(); }
    }

    private static void checkNodeReplacement(ServerLevel level, ServerPlayer player, boolean legal) {
        ItemStack old = player.getMainHandItem();
        BlockPos pos = player.blockPosition().offset(4, 0, 0);
        var before = legal ? mtr.Blocks.RAIL_NODE.get().defaultBlockState() : Blocks.STONE.defaultBlockState();
        level.setBlockAndUpdate(pos, before);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(mtr.Items.BRUSH.get()));
        FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.buffer());
        try {
            packet.writeResourceLocation(level.dimension().location()).writeBlockPos(pos).writeFloat(0).writeUtf("brush_edit_rail");
            PacketReplaceRailNode.receiveUpdateC2S(player.getServer(), player, packet);
            flushServerTasks(player);
            require(legal ? level.getBlockState(pos).is(Main.BLOCK_DIRECT_NODE.get()) : level.getBlockState(pos).equals(before),
                    legal ? "Valid rail node conversion was rejected" : "Client replaced a non-rail block with a direct node");
        } finally { packet.release(); player.setItemInHand(InteractionHand.MAIN_HAND, old); }
    }

    private static void flushServerTasks(ServerPlayer player) {
        var server = player.getServer();
        var barrier = server.submit(() -> {});
        server.managedBlock(barrier::isDone);
        barrier.join();
    }

    private static void onServer(Minecraft client, Checked action) {
        client.getSingleplayerServer().submit(() -> {
            try { action.run(); } catch (Exception error) { throw new RuntimeException(error); }
        }).join();
    }

    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
    private interface Checked { void run() throws Exception; }
}

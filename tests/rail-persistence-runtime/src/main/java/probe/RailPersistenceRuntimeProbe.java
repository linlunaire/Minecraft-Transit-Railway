package probe;

import cn.zbx1425.mtrsteamloco.gui.SelectScreen;
import com.mojang.logging.LogUtils;
import mtr.block.BlockNode;
import mtr.data.Rail;
import mtr.data.RailAngle;
import mtr.data.RailType;
import mtr.data.RailwayData;
import mtr.data.TransportMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.storage.LevelResource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.IOUtilities;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Map;

@Mod("rail_persistence_probe")
public final class RailPersistenceRuntimeProbe {
    private static final org.slf4j.Logger LOGGER = LogUtils.getLogger();
    private static final BlockPos A = new BlockPos(0, 5, 0);
    private static final BlockPos B = new BlockPos(16, 5, 0);
    private static final BlockPos C = new BlockPos(32, 5, 0);
    private static final BlockPos D = new BlockPos(48, 5, 0);
    private int ticks, nextStepAt, phase;
    private volatile boolean failed, closing;
    private ServerLevel testedLevel;

    public RailPersistenceRuntimeProbe(IEventBus ignored) {
        NeoForge.EVENT_BUS.addListener(this::tick);
        NeoForge.EVENT_BUS.addListener(this::stopped);
    }

    private void tick(ClientTickEvent.Post event) {
        if (failed || closing) return;
        Minecraft client = Minecraft.getInstance();
        try {
            GLFW.glfwHideWindow(client.getWindow().getWindow());
            if (++ticks > 2400) throw new AssertionError("Runtime probe exceeded 2400 client ticks");
            if (phase == 0) {
                if (client.getOverlay() != null || ticks < 60) return;
                phase = 1;
                client.options.renderDistance().set(4);
                client.options.simulationDistance().set(6);
                client.options.pauseOnLostFocus = false;
                client.options.framerateLimit().set(60);
                client.createWorldOpenFlows().createFreshLevel("rail-persistence-probe-" + System.currentTimeMillis(),
                        new LevelSettings("Disposable rail persistence fixture", GameType.CREATIVE, false, Difficulty.PEACEFUL,
                                true, new GameRules(), WorldDataConfiguration.DEFAULT), new WorldOptions(0L, false, false),
                        registries -> registries.registryOrThrow(net.minecraft.core.registries.Registries.WORLD_PRESET)
                                .getOrThrow(WorldPresets.FLAT).createWorldDimensions(), client.screen);
                return;
            }
            if (client.player == null || client.level == null || client.getSingleplayerServer() == null) return;
            if (ticks < nextStepAt) return;
            if (phase == 1) {
                checkSearch(client);
                onServer(client, () -> {
                    testedLevel = client.getSingleplayerServer().overworld();
                    connect(testedLevel, A, B);
                    testedLevel.getDataStorage().save();
                });
                phase = 2;
                nextStepAt = ticks + 40;
            } else if (phase == 2) {
                onServer(client, () -> {
                    assertReload(testedLevel, A, B, "first native save", true);
                    connect(testedLevel, B, C);
                    testedLevel.getDataStorage().save();
                });
                phase = 3;
                nextStepAt = ticks + 40;
            } else if (phase == 3) {
                onServer(client, () -> {
                    assertReload(testedLevel, B, C, "second native save", true);
                    connect(testedLevel, C, D);
                    // No explicit save for this final edit: actual shutdown must persist it.
                });
                closing = true;
                client.stop();
            }
        } catch (Throwable error) {
            fail(error);
            client.stop();
        }
    }

    private void stopped(ServerStoppedEvent event) {
        if (failed || !closing || testedLevel == null) return;
        try {
            assertReload(testedLevel, C, D, "native server shutdown", false);
            LOGGER.info("RAIL_PERSISTENCE_RUNTIME_PASS: actual YLM+YLTE NeoForge world; search EditBox.charTyped -> filter, node blocks, two saves, rail reload including YLTE mixins, and a final edit saved by real shutdown");
        } catch (Throwable error) { fail(error); }
    }

    private static void checkSearch(Minecraft client) throws Exception {
        SelectScreen screen = new SelectScreen(() -> null, Component.literal("Runtime rail search"),
                Map.of("rail", "轨道A1", "station", "车站"), () -> "rail", (minecraft, selectScreen, key) -> {}, "");
        client.setScreen(screen);
        Field search = SelectScreen.class.getDeclaredField("searchField");
        search.setAccessible(true);
        EditBox box = (EditBox) search.get(screen);
        Field filtered = SelectScreen.class.getDeclaredField("filteredButtons");
        filtered.setAccessible(true);
        for (String query : new String[]{"guidao", "gd", "轨道", "a1"}) {
            box.setValue("");
            box.setFocused(true);
            for (char character : query.toCharArray()) require(box.charTyped(character, 0), "Search box did not accept input");
            Map<?, ?> matches = (Map<?, ?>) filtered.get(screen);
            require(matches.size() == 1 && matches.containsKey("rail"), "Search lost the expected rail for " + query);
        }
        client.setScreen(null);
        LOGGER.info("RAIL_SEARCH_RUNTIME_PASS: typed full pinyin, initials, Chinese and mixed name through the real search EditBox");
    }

    private static void connect(ServerLevel level, BlockPos start, BlockPos end) {
        level.setBlockAndUpdate(start, mtr.Blocks.RAIL_NODE.get().defaultBlockState());
        level.setBlockAndUpdate(end, mtr.Blocks.RAIL_NODE.get().defaultBlockState());
        RailwayData data = RailwayData.getInstance(level);
        data.addRail(null, TransportMode.TRAIN, start, end, new Rail(start, RailAngle.E, end, RailAngle.E, RailType.WOODEN, TransportMode.TRAIN), false);
        data.addRail(null, TransportMode.TRAIN, end, start, new Rail(end, RailAngle.E, start, RailAngle.E, RailType.WOODEN, TransportMode.TRAIN), false);
    }

    private static void assertReload(ServerLevel level, BlockPos start, BlockPos end, String phase, boolean checkBlocks) throws Exception {
        IOUtilities.waitUntilIOWorkerComplete();
        if (checkBlocks) require(level.getBlockState(start).getBlock() instanceof BlockNode && level.getBlockState(end).getBlock() instanceof BlockNode,
                "Node blocks missing during " + phase);
        Path nbt = level.getServer().getWorldPath(LevelResource.ROOT).resolve("data/mtr_train_data.dat");
        RailwayData restored = new RailwayData(level);
        restored.load(Files.isRegularFile(nbt) ? NbtIo.readCompressed(nbt, NbtAccounter.unlimitedHeap()).getCompound("data") : new CompoundTag());
        Field field = RailwayData.class.getDeclaredField("rails");
        field.setAccessible(true);
        @SuppressWarnings("unchecked") Map<BlockPos, Map<BlockPos, Rail>> rails = (Map<BlockPos, Map<BlockPos, Rail>>) field.get(restored);
        require(rails.containsKey(start) && rails.get(start).containsKey(end) && rails.containsKey(end) && rails.get(end).containsKey(start),
                "Node blocks remain but rail connections missing after " + phase);
        LOGGER.info("RAIL_SAVE_RUNTIME_PASS: {}", phase);
    }

    private static void onServer(Minecraft client, Checked action) {
        client.getSingleplayerServer().submit(() -> {
            try { action.run(); } catch (Exception error) { throw new RuntimeException(error); }
        }).join();
    }
    private void fail(Throwable error) { failed = true; LOGGER.error("RAIL_PERSISTENCE_RUNTIME_FAIL: disposable world", error); }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
    private interface Checked { void run() throws Exception; }
}

package probe;

import com.mojang.logging.LogUtils;
import mtr.data.RailwayData;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.lwjgl.glfw.GLFW;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Mod("time_sync_probe")
public final class TimeSyncRuntimeProbe {
    private static final org.slf4j.Logger LOGGER = LogUtils.getLogger();
    private static final List<String> COMMANDS = new ArrayList<>();
    private static boolean capture;
    private final MutableClock clock = new MutableClock();
    private int ticks, initializedAt;
    private long originalGameTime;
    private boolean starting, complete;

    public TimeSyncRuntimeProbe(IEventBus ignored) {
        NeoForge.EVENT_BUS.addListener(this::tick);
    }

    public static boolean captureCommand(String command) {
        if (!capture) return false;
        COMMANDS.add(command);
        return true;
    }

    private void tick(ClientTickEvent.Post event) {
        if (complete) return;
        final Minecraft client = Minecraft.getInstance();
        try {
            GLFW.glfwHideWindow(client.getWindow().getWindow());
            if (++ticks > 2400) throw new AssertionError("Private world probe timed out");
            if (!starting) {
                if (client.getOverlay() != null || ticks < 60) return;
                starting = true;
                client.options.renderDistance().set(4);
                client.options.simulationDistance().set(6);
                client.options.pauseOnLostFocus = false;
                client.options.framerateLimit().set(60);
                client.createWorldOpenFlows().createFreshLevel("time-sync-probe-" + System.currentTimeMillis(),
                    new LevelSettings("Disposable time sync fixture", GameType.CREATIVE, false, Difficulty.PEACEFUL, true,
                        new GameRules(), WorldDataConfiguration.DEFAULT), new WorldOptions(0L, false, false),
                    registries -> registries.registryOrThrow(Registries.WORLD_PRESET).getOrThrow(WorldPresets.FLAT).createWorldDimensions(), client.screen);
                return;
            }
            if (client.player == null || client.level == null || client.getSingleplayerServer() == null) return;
            client.setScreen(null);
            if (initializedAt == 0) {
                client.getSingleplayerServer().submit(() -> {
                    try { initialize(client.getSingleplayerServer().overworld()); }
                    catch (Exception exception) { throw new RuntimeException(exception); }
                }).join();
                initializedAt = ticks;
                return;
            }
            if (ticks - initializedAt < 40) return;
            client.getSingleplayerServer().submit(() -> {
                try { verify(client.getSingleplayerServer().overworld()); }
                catch (Exception exception) { throw new RuntimeException(exception); }
            }).join();
            LOGGER.info("TIME_SYNC_RUNTIME_PASS: actual NeoForge world and server ticks, zero rejected-command dispatch, native sunrise and 24-hour pace, saved ownership reload, daylight restoration, untouched game time and Nether guard");
            complete = true;
            client.stop();
        } catch (Throwable error) {
            LOGGER.error("TIME_SYNC_RUNTIME_FAIL: native world regression", error);
            complete = true;
            client.stop();
        }
    }

    private void initialize(ServerLevel level) throws Exception {
        final RailwayData data = RailwayData.getInstance(level);
        capture = true;
        data.setUseTimeAndWindSync(false);
        level.getGameRules().getRule(GameRules.RULE_DAYLIGHT).set(true, level.getServer());
        level.setDayTime(12345);
        originalGameTime = level.getGameTime();
        data.setUseTimeAndWindSync(true);
        require(COMMANDS.isEmpty(), "MTR dispatched commands that a plugin can intercept: " + COMMANDS);
        require(!level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT), "Native daylight cycle was not paused");
        require(level.getGameTime() == originalGameTime, "Enabling changed world game time");
        reloadHelper(data, data.save(new CompoundTag(), level.registryAccess()));
        data.setUseTimeAndWindSync(true);
        require(level.getDayTime() == 0, "06:00 did not map to native sunrise");
        clock.millis += 60_000;
    }

    private void verify(ServerLevel level) throws Exception {
        final RailwayData data = RailwayData.getInstance(level);
        require(level.getDayTime() == 17, "Real server ticks did not advance one minute at 24-hour pace: " + level.getDayTime());
        require(level.getGameTime() > originalGameTime, "World simulation stopped while daylight was paused");
        final long gameTime = level.getGameTime();
        final CompoundTag saved = data.save(new CompoundTag(), level.registryAccess());
        require(saved.getBoolean("use_time_and_wind_sync") && saved.getBoolean("real_time_previous_daylight_cycle"), "Actual saved data lost original daylight ownership");
        reloadHelper(data, saved);
        data.setUseTimeAndWindSync(true);
        data.setUseTimeAndWindSync(false);
        require(level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT), "Disabled reloaded sync did not restore original daylight");
        require(level.getDayTime() == 17, "Disable reset world time");

        level.getGameRules().getRule(GameRules.RULE_DAYLIGHT).set(false, level.getServer());
        data.setUseTimeAndWindSync(true);
        data.setUseTimeAndWindSync(false);
        require(!level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT), "Originally paused world was incorrectly resumed");
        level.getGameRules().getRule(GameRules.RULE_DAYLIGHT).set(true, level.getServer());
        final long beforeNether = level.getDayTime();
        final RailwayData netherData = RailwayData.getInstance(level.getServer().getLevel(net.minecraft.world.level.Level.NETHER));
        netherData.setUseTimeAndWindSync(true);
        netherData.setUseTimeAndWindSync(false);
        require(level.getDayTime() == beforeNether && level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT), "Nether sync modified shared Overworld state");
        require(level.getGameTime() == gameTime, "Reload or disabling changed world game time");
        require(COMMANDS.isEmpty(), "Toggle/reload/disable dispatched commands: " + COMMANDS);
        capture = false;
    }

    private void reloadHelper(RailwayData data, CompoundTag saved) throws Exception {
        final Class<?> type = Class.forName("mtr.data.RealTimeSync");
        final var constructor = type.getDeclaredConstructor(Clock.class);
        constructor.setAccessible(true);
        final Object helper = constructor.newInstance(clock);
        final var load = type.getDeclaredMethod("load", CompoundTag.class);
        load.setAccessible(true);
        load.invoke(helper, saved);
        final var field = RailwayData.class.getDeclaredField("realTimeSync");
        field.setAccessible(true);
        field.set(data, helper);
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static final class MutableClock extends Clock {
        private volatile long millis = Instant.parse("2026-10-03T06:00:00Z").toEpochMilli();
        @Override public ZoneId getZone() { return ZoneOffset.UTC; }
        @Override public Clock withZone(ZoneId zone) { throw new UnsupportedOperationException(); }
        @Override public Instant instant() { return Instant.ofEpochMilli(millis); }
        @Override public long millis() { return millis; }
    }
}

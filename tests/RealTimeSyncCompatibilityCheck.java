package mtr.data;

import com.mojang.serialization.Lifecycle;
import net.minecraft.SharedConstants;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedPlayerList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.clock.ClockState;
import net.minecraft.world.clock.PackedClockStates;
import net.minecraft.world.clock.ServerClockManager;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.timeline.Timeline;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/** Exercises the production toggle against 26.2 classes without a loader or plugin command map. */
public final class RealTimeSyncCompatibilityCheck {

	private static final List<String> COMMANDS = new ArrayList<>();
	private static final Unsafe UNSAFE = unsafe();
	private static int assertions;

	public static void main(String[] args) throws Exception {
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		final TestServer server = allocate(TestServer.class);
		server.commands = allocate(TestCommands.class);
		server.players = allocate(TestPlayerList.class);
		server.rules = new GameRules(FeatureFlags.DEFAULT_FLAGS);
		server.rules.set(GameRules.ADVANCE_TIME, false, null);
		final MappedRegistry<WorldClock> clocks = new MappedRegistry<>(Registries.WORLD_CLOCK, Lifecycle.stable());
		final Holder<WorldClock> overworldClock = clocks.register(WorldClocks.OVERWORLD, new WorldClock(), RegistrationInfo.BUILT_IN);
		final Holder<WorldClock> endClock = clocks.register(WorldClocks.THE_END, new WorldClock(), RegistrationInfo.BUILT_IN);
		final MappedRegistry<Timeline> timelines = new MappedRegistry<>(Registries.TIMELINE, Lifecycle.stable());
		server.registries = new RegistryAccess.ImmutableRegistryAccess(List.of(clocks, timelines)).freeze();
		final Constructor<ServerClockManager> constructor = ServerClockManager.class.getDeclaredConstructor(PackedClockStates.class);
		constructor.setAccessible(true);
		server.clocks = constructor.newInstance(new PackedClockStates(Map.of(
			overworldClock, new ClockState(12000, 0, 2, true),
			endClock, new ClockState(1234, 0, 3, true))));
		server.clocks.init(server);
		final TestLevel level = allocate(TestLevel.class);
		level.server = server;
		level.dimension = Level.OVERWORLD;
		level.rules = new GameRules(FeatureFlags.DEFAULT_FLAGS);
		level.rules.set(GameRules.ADVANCE_TIME, false, null);
		write(MinecraftServer.class, server, "levels", Map.of(Level.OVERWORLD, level));
		final RailwayData data = allocate(RailwayData.class);
		write(RailwayData.class, data, "world", level);
		// The pre-fix class has no helper; this option is only a red-control loader for that class.
		if (!Arrays.asList(args).contains("--baseline")) write(RailwayData.class, data, "realTimeSync", new RealTimeSync());
		final PackedClockStates initialState = server.clocks.packState();
		data.setUseTimeAndWindSync(false);
		require(COMMANDS.isEmpty(), "Disabled real-time sync must not dispatch commands");
		require(server.clocks.packState().equals(initialState), "Disabled sync changed the configured clocks");
		final long before = RealTimeSync.ticksAt(LocalTime.now());
		data.setUseTimeAndWindSync(true);
		final long after = RealTimeSync.ticksAt(LocalTime.now());
		require(COMMANDS.isEmpty(), "Real-time sync still dispatches plugin-overridable / missing commands: " + COMMANDS);
		ClockState state = server.clocks.packState().clocks().get(overworldClock);
		require(Math.floorMod(state.totalTicks() - before, 24000) <= Math.floorMod(after - before, 24000), "Time is not synchronized to the server wall clock");
		require(state.rate() == 1F / 72 && !state.paused(), "24-hour native clock rate or unpause was not applied");
		require(server.rules.get(GameRules.ADVANCE_TIME), "The global advance_time rule still prevents clocks from ticking");
		require(level.rules.get(GameRules.ADVANCE_TIME), "A hybrid server's level advance_time override still prevents clocks from ticking");
		require(level.clockManagerCalls > 0, "The target level's clock manager was not used");
		require(server.players.packets == 3, "Native clock changes did not send clock-state packets");
		require(server.clocks.packState().clocks().get(endClock).equals(initialState.clocks().get(endClock)), "Overworld sync modified the End clock");
		server.clocks.setTotalTicks(overworldClock, 0);
		for (int tick = 0; tick < 24 * 60 * 60 * 20; tick++) server.clocks.tick();
		final long elapsed = server.clocks.getTotalTicks(overworldClock);
		require(Math.abs(elapsed - 24000) <= 1, "24 hours at 20 TPS did not advance one Minecraft day: " + elapsed);
		data.setUseTimeAndWindSync(false);
		state = server.clocks.packState().clocks().get(overworldClock);
		require(state.rate() == 2 && state.paused(), "Disabling sync did not restore the original rate and paused state");
		require(state.totalTicks() == elapsed, "Disabling sync reset the time");
		data.setUseTimeAndWindSync(true);
		server.clocks.setRate(overworldClock, 4);
		data.setUseTimeAndWindSync(false);
		require(server.clocks.packState().clocks().get(overworldClock).rate() == 4, "Disabling overwrote a rate subsequently changed by another owner");
		data.setUseTimeAndWindSync(true);
		server.clocks.setPaused(overworldClock, true);
		data.setUseTimeAndWindSync(false);
		require(server.clocks.packState().clocks().get(overworldClock).paused(), "Disabling undid an administrator's subsequent pause");
		final Method loadSync = RailwayData.class.getDeclaredMethod("runRealTimeSync");
		loadSync.setAccessible(true);
		final PackedClockStates beforeDisabledLoad = server.clocks.packState();
		loadSync.invoke(data);
		require(server.clocks.packState().equals(beforeDisabledLoad), "Loading a disabled world modified configured clock rates");
		for (ResourceKey<Level> dimension : List.of(Level.NETHER, Level.END, ResourceKey.create(Registries.DIMENSION, Identifier.parse("multiworld:testwork")))) {
			level.dimension = dimension;
			data.setUseTimeAndWindSync(true);
			loadSync.invoke(data);
			data.setUseTimeAndWindSync(false);
			require(server.clocks.packState().equals(beforeDisabledLoad), "Non-overworld sync changed the shared clocks: " + dimension);
		}
		level.dimension = Level.OVERWORLD;
		write(RailwayData.class, data, "useTimeAndWindSync", true);
		loadSync.invoke(data);
		require(server.clocks.packState().clocks().get(overworldClock).rate() == 1F / 72, "Loading enabled sync did not initialize its native clock");
		// Recreate only MTR's unsaved ownership state while retaining vanilla's persisted clock state.
		write(RailwayData.class, data, "realTimeSync", new RealTimeSync());
		loadSync.invoke(data);
		data.setUseTimeAndWindSync(false);
		state = server.clocks.packState().clocks().get(overworldClock);
		require(state.rate() == 1 && !state.paused(), "Disabling a restored real-time clock did not return to vanilla defaults");
		final PackedClockStates beforeDetachedWorld = server.clocks.packState();
		level.server = null;
		data.setUseTimeAndWindSync(true);
		require(server.clocks.packState().equals(beforeDetachedWorld), "A world without a server changed clocks");
		for (int second = 0; second < 86400; second++) {
			final LocalTime time = LocalTime.ofSecondOfDay(second);
			final long expected = Math.round((time.getHour() + 24 - 6) * 1000 + time.getMinute() / 0.06 + time.getSecond() / 3.6) % 24000;
			require(RealTimeSync.ticksAt(time) == expected, "Legacy second-to-tick rounding changed: " + time);
		}
		require(RealTimeSync.ticksAt(LocalTime.of(6, 0)) == 0, "06:00 must map to sunrise tick zero");
		require(RealTimeSync.ticksAt(LocalTime.MIDNIGHT) == 18000, "Midnight offset changed");
		require(COMMANDS.isEmpty(), "A sync path dispatched a command: " + COMMANDS);
		System.out.println("PASS: " + assertions + " assertions; actual 26.2 global gamerule, clock rate/time/packets, 24-hour progression, dimension guards, disable/reload and no command dispatch");
	}

	private static final class TestServer extends DedicatedServer {
		private Commands commands;
		private TestPlayerList players;
		private GameRules rules;
		private RegistryAccess.Frozen registries;
		private ServerClockManager clocks;
		private TestServer() { super(null, null, null, null, null, null, null, null, null, null); }
		@Override public Commands getCommands() { return commands; }
		@Override public CommandSourceStack createCommandSourceStack() { return null; }
		@Override public DedicatedPlayerList getPlayerList() { return players; }
		@Override public GameRules getGlobalGameRules() { return rules; }
		@Override public RegistryAccess.Frozen registryAccess() { return registries; }
		@Override public ServerClockManager clockManager() { return clocks; }
		@Override public Iterable<ServerLevel> getAllLevels() { return List.of(); }
		@Override public <T> void onGameRuleChanged(GameRule<T> rule, T value) { }
	}

	private static final class TestLevel extends ServerLevel {
		private MinecraftServer server;
		private ResourceKey<Level> dimension;
		private GameRules rules;
		private int clockManagerCalls;
		private TestLevel() { super(null, null, null, null, null, null, false, 0, null, false); }
		@Override public MinecraftServer getServer() { return server; }
		@Override public ResourceKey<Level> dimension() { return dimension; }
		@Override public long getGameTime() { return 123456; }
		@Override public GameRules getGameRules() { return rules; }
		@Override public ServerClockManager clockManager() { clockManagerCalls++; return server.clockManager(); }
	}

	private static final class TestPlayerList extends DedicatedPlayerList {
		private int packets;
		private TestPlayerList() { super(null, null, null); }
		@Override public void broadcastAll(Packet<?> packet) { packets++; }
	}

	private static final class TestCommands extends Commands {
		private TestCommands() { super(null, null); }
		@Override public void performPrefixedCommand(CommandSourceStack source, String command) { COMMANDS.add(command); }
	}

	private static <T> T allocate(Class<T> type) throws InstantiationException {
		return type.cast(UNSAFE.allocateInstance(type));
	}

	private static void write(Class<?> type, Object target, String name, Object value) throws Exception {
		final Field field = type.getDeclaredField(name);
		field.setAccessible(true);
		field.set(target, value);
	}

	private static Unsafe unsafe() {
		try {
			final Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			return (Unsafe) field.get(null);
		} catch (ReflectiveOperationException exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	private static void require(boolean condition, String message) {
		assertions++;
		if (!condition) throw new AssertionError(message);
	}
}

package mtr.data;

import net.minecraft.SharedConstants;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ServerLevelData;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Calls the real MTR setting path, recording command dispatch without running any commands. */
public final class RealTimeSyncCompatibilityCheck {
	private static final Unsafe UNSAFE = unsafe();
	private static final List<String> COMMANDS = new ArrayList<>();
	private static int assertions;

	public static void main(String[] args) throws Exception {
		final boolean baseline = Arrays.asList(args).contains("--baseline");
		if (args.length > 0 && !args[0].startsWith("--")) {
			final Path expectedJar = Path.of(args[0]);
			require(Files.isSameFile(expectedJar, Path.of(RailwayData.class.getProtectionDomain().getCodeSource().getLocation().toURI())), "RailwayData was not loaded from the selected delivery JAR");
			if (!baseline) require(Files.isSameFile(expectedJar, Path.of(RealTimeSync.class.getProtectionDomain().getCodeSource().getLocation().toURI())), "RealTimeSync was not loaded from the selected delivery JAR");
		}
		try {
			Class.forName("net.neoforged.fml.loading.LoadingModList")
					.getMethod("of", List.class, List.class, List.class, List.class, java.util.Map.class)
					.invoke(null, List.of(), List.of(), List.of(), List.of(), java.util.Map.of());
		} catch (ClassNotFoundException ignored) {
			// No NeoForge loader on the common/Fabric classpath.
		}
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		final TestServer server = allocate(TestServer.class);
		server.commands = allocate(TestCommands.class);
		final TestLevel level = allocate(TestLevel.class);
		level.server = server;
		level.dimension = Level.OVERWORLD;
		final GameRules rules = new GameRules();
		final long[] dayTime = {12345};
		final int[] timeWrites = {0};
		final ServerLevelData levelData = (ServerLevelData) Proxy.newProxyInstance(ServerLevelData.class.getClassLoader(), new Class<?>[]{ServerLevelData.class}, (proxy, method, values) -> switch (method.getName()) {
			case "getDayTime" -> dayTime[0];
			case "setDayTime" -> { dayTime[0] = (long) values[0]; timeWrites[0]++; yield null; }
			case "getGameTime" -> 123456L;
			case "getGameRules" -> rules;
			default -> throw new AssertionError("Unexpected level-data access: " + method);
		});
		write(Level.class, level, "levelData", levelData);
		write(ServerLevel.class, level, "serverLevelData", levelData);
		final MutableClock clock = new MutableClock();
		RealTimeSync sync = new RealTimeSync(clock);
		final RailwayData data = allocate(RailwayData.class);
		write(RailwayData.class, data, "world", level);
		// Only the explicit old-JAR red control omits the helper absent in that release.
		if (!baseline) write(RailwayData.class, data, "realTimeSync", sync);
		data.setUseTimeAndWindSync(false);
		require(COMMANDS.isEmpty(), "Disabled sync dispatched commands");
		require(dayTime[0] == 12345 && rules.getBoolean(GameRules.RULE_DAYLIGHT), "Disabled setting changed world state");
		data.setUseTimeAndWindSync(true);
		require(COMMANDS.isEmpty(), "Real-time sync dispatched missing/plugin-overridable commands: " + COMMANDS);
		require(dayTime[0] == 0 && !rules.getBoolean(GameRules.RULE_DAYLIGHT), "06:00 did not set native sunrise time and stop 20-minute auto advance");
		require(data.isDirty(), "Changing sync was not marked for saving");

		clock.millis += 60_000;
		// Execute the real simulation entrypoint until its first unrelated player query.
		try { data.simulateTrains(); } catch (SimulationBoundary expected) { }
		require(dayTime[0] == 17, "Production tick entrypoint did not advance wall-clock time");
		final int writes = timeWrites[0];
		for (int tick = 0; tick < 10_000; tick++) sync.tick(level, true);
		require(timeWrites[0] == writes, "Repeated ticks within one wall-clock second rewrote time");
		final long midnight = Instant.parse("2026-09-28T00:00:00Z").toEpochMilli();
		for (int second = 0; second < 86400; second++) {
			clock.millis = midnight + second * 1000L;
			sync.tick(level, true);
			final LocalTime time = LocalTime.ofSecondOfDay(second);
			final long expected = Math.round((time.getHour() + 24 - 6) * 1000 + time.getMinute() / 0.06 + time.getSecond() / 3.6) % 24000;
			require(dayTime[0] == expected, "24-hour native progression/legacy rounding changed at " + time);
		}
		require(level.getGameTime() == 123456, "Sync changed game time rather than only day time");

		final CompoundTag saved = data.save(new CompoundTag(), null);
		require(saved.getBoolean("use_time_and_wind_sync") && saved.getBoolean("real_time_previous_daylight_cycle"), "Original daylight rule was not persisted with enabled sync");
		sync = new RealTimeSync(clock);
		sync.load(saved);
		write(RailwayData.class, data, "realTimeSync", sync);
		data.setDirty(false);
		final var loadSync = RailwayData.class.getDeclaredMethod("runRealTimeSync");
		loadSync.setAccessible(true);
		loadSync.invoke(data);
		require(data.isDirty(), "Load-time daylight ownership was not marked for persistence");
		data.setUseTimeAndWindSync(false);
		require(rules.getBoolean(GameRules.RULE_DAYLIGHT), "Disabling after restart left the vanilla daylight rule frozen");
		data.save(saved, null);
		require(!saved.contains("real_time_previous_daylight_cycle"), "Disabled sync retained stale ownership metadata");

		rules.getRule(GameRules.RULE_DAYLIGHT).set(false, server);
		data.setUseTimeAndWindSync(true);
		data.setUseTimeAndWindSync(false);
		require(!rules.getBoolean(GameRules.RULE_DAYLIGHT), "Disabling sync undid an originally paused world");
		data.setUseTimeAndWindSync(true);
		rules.getRule(GameRules.RULE_DAYLIGHT).set(true, server);
		final long adminTime = dayTime[0];
		clock.millis += 60_000;
		sync.tick(level, true);
		require(dayTime[0] == adminTime, "Sync overwrote time after an administrator resumed vanilla time");
		data.setUseTimeAndWindSync(false);
		require(rules.getBoolean(GameRules.RULE_DAYLIGHT), "Disabling undid a later administrator rule change");

		for (ResourceKey<Level> dimension : List.of(Level.NETHER, Level.END, ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("multiworld:testwork")))) {
			level.dimension = dimension;
			data.setUseTimeAndWindSync(true);
			sync.tick(level, true);
			data.setUseTimeAndWindSync(false);
			require(dayTime[0] == adminTime && rules.getBoolean(GameRules.RULE_DAYLIGHT), "A non-overworld sync changed shared time/rules: " + dimension);
		}
		level.dimension = Level.OVERWORLD;
		level.server = null;
		data.setUseTimeAndWindSync(true);
		sync.tick(level, true);
		require(dayTime[0] == adminTime && rules.getBoolean(GameRules.RULE_DAYLIGHT), "A detached world changed time");
		require(COMMANDS.isEmpty(), "A toggle/tick/reload path still dispatched commands: " + COMMANDS);
		System.out.println("PASS: " + assertions + " assertions; actual 1.21.1 day-time setter and gamerule, production toggle/tick path, 24-hour progression, save/reload/disable, dimension guards and zero command dispatch");
	}

	private static final class TestServer extends DedicatedServer {
		private Commands commands;
		private TestServer() { super(null, null, null, null, null, null, null, null); }
		@Override public Commands getCommands() { return commands; }
		@Override public CommandSourceStack createCommandSourceStack() { return null; }
	}

	private static final class TestLevel extends ServerLevel {
		private MinecraftServer server;
		private ResourceKey<Level> dimension;
		private TestLevel() { super(null, null, null, null, null, null, null, false, 0, null, false, null); }
		@Override public MinecraftServer getServer() { return server; }
		@Override public ResourceKey<Level> dimension() { return dimension; }
		@Override public List<ServerPlayer> players() { throw new SimulationBoundary(); }
	}

	private static final class SimulationBoundary extends RuntimeException { }

	private static final class MutableClock extends Clock {
		private long millis = Instant.parse("2026-09-28T06:00:00Z").toEpochMilli();
		@Override public ZoneId getZone() { return ZoneOffset.UTC; }
		@Override public Clock withZone(ZoneId zone) { throw new UnsupportedOperationException(); }
		@Override public Instant instant() { return Instant.ofEpochMilli(millis); }
		@Override public long millis() { return millis; }
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

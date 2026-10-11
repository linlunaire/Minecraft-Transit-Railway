package mtr.data;

import net.minecraft.core.Holder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.clock.ClockState;
import net.minecraft.world.clock.ServerClockManager;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;

import java.time.LocalTime;

/** Native 26.2 replacement for the optional Time and Wind command integration. */
final class RealTimeSync {

	static final float REAL_TIME_RATE = 1F / 72;
	private ClockState previousState;

	void apply(Level world, boolean enabled) {
		// Vanilla shares its clocks; hybrid servers may instead keep a manager per level.
		if (!(world instanceof ServerLevel serverLevel) || !Level.OVERWORLD.equals(world.dimension()) || (!enabled && previousState == null)) {
			return;
		}
		final MinecraftServer server = world.getServer();
		if (server == null) {
			return;
		}
		final Holder<WorldClock> clock = server.registryAccess().getOrThrow(WorldClocks.OVERWORLD);
		final ServerClockManager clocks = serverLevel.clockManager();
		final ClockState currentState = clocks.packState().clocks().get(clock);
		if (enabled) {
			if (previousState == null) {
				// A saved real-time rate on restart has no earlier in-memory owner state.
				previousState = currentState.rate() == REAL_TIME_RATE && !currentState.paused() ? new ClockState(0, 0, 1, false) : currentState;
			}
			final GameRules globalRules = server.getGlobalGameRules();
			globalRules.set(GameRules.ADVANCE_TIME, true, server);
			if (serverLevel.getGameRules() != globalRules) {
				serverLevel.getGameRules().set(GameRules.ADVANCE_TIME, true, server);
			}
			clocks.setRate(clock, REAL_TIME_RATE);
			clocks.setPaused(clock, false);
			clocks.setTotalTicks(clock, ticksAt(LocalTime.now()));
		} else {
			// Do not overwrite a rate/pause another administrator/mod changed after enabling MTR sync.
			if (currentState.rate() == REAL_TIME_RATE && !currentState.paused()) {
				clocks.setRate(clock, previousState.rate());
				clocks.setPaused(clock, previousState.paused());
			}
			previousState = null;
		}
	}

	static long ticksAt(LocalTime time) {
		// Keep the legacy whole-second rounding and 06:00 -> 0 tick convention.
		return Math.round((time.getHour() + 18) * 1000 + time.getMinute() / 0.06 + time.getSecond() / 3.6) % 24000;
	}
}

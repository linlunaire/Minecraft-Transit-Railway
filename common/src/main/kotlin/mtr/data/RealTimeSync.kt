package mtr.data

import net.minecraft.server.level.ServerLevel
import net.minecraft.world.clock.ClockState
import net.minecraft.world.clock.WorldClocks
import net.minecraft.world.level.Level
import net.minecraft.world.level.gamerules.GameRules
import java.time.LocalTime

/** Native 26.2 replacement for the optional Time and Wind command integration. */
internal class RealTimeSync {

	private var previousState: ClockState? = null

	fun apply(world: Level?, enabled: Boolean) {
		// Vanilla shares its clocks; hybrid servers may instead keep a manager per level.
		if (world !is ServerLevel || Level.OVERWORLD != world.dimension() || (!enabled && previousState == null)) {
			return
		}
		val server = world.server ?: return
		val clock = server.registryAccess().getOrThrow(WorldClocks.OVERWORLD)
		val clocks = world.clockManager()
		val currentState = clocks.packState().clocks()[clock]
		if (enabled) {
			if (previousState == null) {
				// A saved real-time rate on restart has no earlier in-memory owner state.
				previousState = if (currentState!!.rate() == REAL_TIME_RATE && !currentState.paused()) {
					ClockState(0, 0F, 1F, false)
				} else {
					currentState
				}
			}
			val globalRules = server.globalGameRules
			globalRules.set(GameRules.ADVANCE_TIME, true, server)
			if (world.gameRules !== globalRules) {
				world.gameRules.set(GameRules.ADVANCE_TIME, true, server)
			}
			clocks.setRate(clock, REAL_TIME_RATE)
			clocks.setPaused(clock, false)
			clocks.setTotalTicks(clock, ticksAt(LocalTime.now()))
		} else {
			// Do not overwrite a rate/pause another administrator/mod changed after enabling MTR sync.
			if (currentState!!.rate() == REAL_TIME_RATE && !currentState.paused()) {
				clocks.setRate(clock, previousState!!.rate())
				clocks.setPaused(clock, previousState!!.paused())
			}
			previousState = null
		}
	}

	companion object {
		const val REAL_TIME_RATE: Float = 1F / 72

		@JvmStatic
		fun ticksAt(time: LocalTime): Long {
			// Keep the legacy whole-second rounding and 06:00 -> 0 tick convention.
			return Math.round((time.hour + 18) * 1000 + time.minute / 0.06 + time.second / 3.6) % 24000
		}
	}
}

package mtr.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;

/** Native 1.21.1 wall-clock sync, without optional Time and Wind or command dispatch. */
final class RealTimeSync {

	private static final String KEY_PREVIOUS_DAYLIGHT = "real_time_previous_daylight_cycle";
	private final Clock clock;
	private Boolean previousDaylightCycle;
	private long lastEpochSecond = Long.MIN_VALUE;

	RealTimeSync() {
		this(Clock.systemDefaultZone());
	}

	RealTimeSync(Clock clock) {
		this.clock = clock;
	}

	void load(CompoundTag tag) {
		previousDaylightCycle = tag.contains(KEY_PREVIOUS_DAYLIGHT, Tag.TAG_BYTE) ? tag.getBoolean(KEY_PREVIOUS_DAYLIGHT) : null;
		lastEpochSecond = Long.MIN_VALUE;
	}

	void save(CompoundTag tag) {
		if (previousDaylightCycle == null) tag.remove(KEY_PREVIOUS_DAYLIGHT);
		else tag.putBoolean(KEY_PREVIOUS_DAYLIGHT, previousDaylightCycle);
	}

	void apply(Level world, boolean enabled) {
		if (!(world instanceof ServerLevel level) || !Level.OVERWORLD.equals(world.dimension()) || world.getServer() == null) return;
		final GameRules.BooleanValue daylight = level.getGameRules().getRule(GameRules.RULE_DAYLIGHT);
		if (enabled) {
			if (previousDaylightCycle == null) previousDaylightCycle = daylight.get();
			// 1.21.1 has no native fractional clock rate. Pause automatic day advance,
			// then update only day time; game time and scheduled ticks remain untouched.
			daylight.set(false, level.getServer());
			lastEpochSecond = Long.MIN_VALUE;
			updateTime(level);
		} else if (previousDaylightCycle != null) {
			// Do not undo a later administrator change that resumed the daylight cycle.
			if (!daylight.get()) daylight.set(previousDaylightCycle, level.getServer());
			previousDaylightCycle = null;
			lastEpochSecond = Long.MIN_VALUE;
		}
	}

	void tick(Level world, boolean enabled) {
		if (enabled && previousDaylightCycle != null && world instanceof ServerLevel level
				&& Level.OVERWORLD.equals(world.dimension()) && world.getServer() != null
				&& !level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
			updateTime(level);
		}
	}

	private void updateTime(ServerLevel level) {
		final long epochSecond = Math.floorDiv(clock.millis(), 1000);
		if (epochSecond == lastEpochSecond) return;
		lastEpochSecond = epochSecond;
		final long ticks = ticksAt(LocalTime.ofInstant(Instant.ofEpochSecond(epochSecond), clock.getZone()));
		if (level.getDayTime() != ticks) level.setDayTime(ticks);
	}

	static long ticksAt(LocalTime time) {
		// Preserve the previous whole-second rounding and 06:00 -> 0 tick convention.
		return Math.round((time.getHour() + 18) * 1000 + time.getMinute() / 0.06 + time.getSecond() / 3.6) % 24000;
	}
}

package mtr.mappings;

import net.minecraft.world.phys.Vec3;

/** MTR's tick-based linear interpolation, independent of world collision adjustment. */
public final class EntityPositionInterpolator {

	private Vec3 target = Vec3.ZERO;
	private int steps;

	public void moveTo(Vec3 target, int steps) {
		this.target = target;
		this.steps = Math.max(0, steps);
	}

	public boolean isActive() {
		return steps > 0;
	}

	public Vec3 targetOr(Vec3 current) {
		return isActive() ? target : current;
	}

	public Vec3 advance(Vec3 current) {
		if (!isActive()) {
			return current;
		}
		final Vec3 next = new Vec3(current.x + (target.x - current.x) / steps, current.y + (target.y - current.y) / steps, current.z + (target.z - current.z) / steps);
		steps--;
		return next;
	}

	public void cancel() {
		steps = 0;
	}
}

package mtr.mappings;

import net.minecraft.world.phys.Vec3;

import java.util.Random;

/** Checks the old MTR movement formula without constructing a game world. */
public final class EntityCompatibilityCheck {

	public static void main(String[] args) {
		final EntityPositionInterpolator interpolation = new EntityPositionInterpolator();
		Vec3 current = new Vec3(3, -6, 9);
		require(!interpolation.isActive(), "New interpolator must be idle");
		require(interpolation.advance(current) == current, "Idle interpolation must preserve the position");
		final Vec3 target = new Vec3(12, 3, -9);
		interpolation.moveTo(target, 3);
		require(interpolation.targetOr(current).equals(target), "Active interpolation must expose the network target");
		current = interpolation.advance(current);
		require(current.equals(new Vec3(6, -3, 3)), "First tick did not move one third of the distance");
		current = interpolation.advance(current);
		require(current.equals(new Vec3(9, 0, -3)), "Second tick did not preserve linear speed");
		current = interpolation.advance(current);
		require(current.equals(target) && !interpolation.isActive(), "Third tick must reach the target and stop");
		require(interpolation.targetOr(current) == current, "Idle target must follow the current entity position");

		interpolation.moveTo(new Vec3(99, 9, 9), 3);
		current = interpolation.advance(current);
		final Vec3 replacement = new Vec3(-6, 12, 0);
		interpolation.moveTo(replacement, 2);
		current = interpolation.advance(current);
		require(current.equals(new Vec3(17.5, 8.5, -1.5)), "A new packet must interpolate from the current position");
		current = interpolation.advance(current);
		require(current.equals(replacement), "Replacement target must be reached on time");

		interpolation.moveTo(target, 1);
		require(interpolation.advance(current).equals(target), "Train-driven one-tick movement must reach its target");
		interpolation.moveTo(replacement, 8);
		interpolation.cancel();
		require(!interpolation.isActive() && interpolation.advance(current) == current, "Cancellation must stop movement immediately");
		for (int steps : new int[]{0, -1, Integer.MIN_VALUE}) {
			interpolation.moveTo(target, steps);
			require(!interpolation.isActive() && interpolation.advance(current) == current, "Non-positive step counts must preserve legacy no-movement behavior");
		}

		// Compare each tick with the original EntityBase formula, including external
		// position changes between ticks and a new packet arriving before completion.
		final Random random = new Random(262);
		for (int sequence = 0; sequence < 500; sequence++) {
			current = randomPosition(random);
			Vec3 expected = current;
			Vec3 expectedTarget = randomPosition(random);
			int remaining = 1 + random.nextInt(20);
			interpolation.moveTo(expectedTarget, remaining);
			for (int tick = 0; tick < 40; tick++) {
				if (tick == 2) {
					expectedTarget = randomPosition(random);
					remaining = 1 + random.nextInt(20);
					interpolation.moveTo(expectedTarget, remaining);
				}
				if (tick == 4) {
					current = current.add(0.125, -0.25, 1);
					expected = expected.add(0.125, -0.25, 1);
				}
				if (remaining > 0) {
					expected = new Vec3(expected.x + (expectedTarget.x - expected.x) / remaining, expected.y + (expectedTarget.y - expected.y) / remaining, expected.z + (expectedTarget.z - expected.z) / remaining);
					remaining--;
				}
				current = interpolation.advance(current);
				require(current.equals(expected), "Movement diverged from the old formula at sequence " + sequence + ", tick " + tick);
				require(interpolation.isActive() == (remaining > 0), "Active state diverged from the old step counter");
			}
		}
		System.out.println("PASS: entity interpolation ticks, replacement targets, one-tick movement, cancellation, non-positive steps and 20,000 legacy-formula comparisons");
	}

	private static Vec3 randomPosition(Random random) {
		return new Vec3((random.nextDouble() - 0.5) * 60_000_000, (random.nextDouble() - 0.5) * 4096, (random.nextDouble() - 0.5) * 60_000_000);
	}

	private static void require(boolean condition, String message) {
		if (!condition) {
			throw new AssertionError(message);
		}
	}
}

package mtr.entity;

import mtr.Registry;
import mtr.mappings.EntityMapper;
import mtr.mappings.EntityPositionInterpolator;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.InterpolationHandler;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public abstract class EntityBase extends EntityMapper {

	private final EntityPositionInterpolator positions = new EntityPositionInterpolator();
	private Vec3 speed = Vec3.ZERO;
	private final InterpolationHandler interpolation = new InterpolationHandler(this) {
		private int length = DEFAULT_INTERPOLATION_STEPS;

		@Override
		public void interpolateTo(Vec3 position, float yaw, float pitch) {
			// Dispatch through the seat override, so remote updates do not replace
			// the local passenger's train-driven position.
			EntityBase.this.lerpTo(position.x, position.y, position.z, yaw, pitch, length, true);
		}

		@Override
		public Vec3 position() {
			return positions.targetOr(EntityBase.this.position());
		}

		@Override
		public float yRot() {
			return getYRot();
		}

		@Override
		public float xRot() {
			return getXRot();
		}

		@Override
		public boolean hasActiveInterpolation() {
			return positions.isActive();
		}

		@Override
		public void setInterpolationLength(int length) {
			this.length = Math.max(0, length);
		}

		@Override
		public void interpolate() {
			setClientPosition();
		}

		@Override
		public void cancel() {
			positions.cancel();
		}
	};

	public EntityBase(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public final InterpolationHandler getInterpolation() {
		return interpolation;
	}

	public void lerpTo(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
		positions.moveTo(new Vec3(x, y, z), interpolationSteps);
		setDeltaMovement(speed);
	}

	@Override
	public void lerpMotion(Vec3 speed) {
		this.speed = speed;
		setDeltaMovement(speed);
	}

	@Override
	public final Packet<?> getAddEntityPacket2() {
		return Registry.createAddEntityPacket(this);
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
		if (!isInvulnerableToBase(source)) {
			markHurt();
		}
		return false;
	}

	@Override
	protected final void readAdditionalSaveData(ValueInput input) {
		// Seats and legacy conversion lifts had no additional persistent fields.
	}

	@Override
	protected final void addAdditionalSaveData(ValueOutput output) {
		// Their train/lift state is stored separately in RailwayData.
	}

	protected final void setClientPosition() {
		if (positions.isActive()) {
			setPos(positions.advance(position()));
		} else {
			reapplyPosition();
		}
	}
}

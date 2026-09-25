package mtr.mappings;

import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/** MTR seats and lifts use the vanilla spawn payload with data=0. */
public final class EntitySpawnPacketMapper {

	private EntitySpawnPacketMapper() {
	}

	public static ClientboundAddEntityPacket create(Entity entity) {
		// Match a freshly constructed ServerEntity's base/movement/angles, without
		// constructing a tracker with a discarded packet-broadcast callback.
		final Vec3 position = entity.trackingPosition();
		return new ClientboundAddEntityPacket(entity.getId(), entity.getUUID(), position.x, position.y, position.z,
			entity.getXRot(), entity.getYRot(), entity.getType(), 0, entity.getDeltaMovement(), entity.getYHeadRot());
	}
}

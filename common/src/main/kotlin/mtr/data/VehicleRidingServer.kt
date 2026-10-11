package mtr.data

import io.netty.buffer.Unpooled
import mtr.Registry
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.Identifier
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import java.util.UUID
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate

@JvmSuppressWildcards
open class VehicleRidingServer {
    companion object {
        private const val INNER_PADDING = 0.5F
        private const val BOX_PADDING = 3

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun mountRider(world: Level?, ridingEntities: MutableSet<UUID?>?, id: Long, routeId: Long,
                            carX: Double, carY: Double, carZ: Double, length: Double, width: Double,
                            carYaw: Float, carPitch: Float, doorOpen: Boolean, canMount: Boolean,
                            percentageOffset: Int, packetId: Identifier?, canRide: Function<Player?, Boolean?>?,
                            ridingCallback: Consumer<Player?>?) {
            val railwayData = RailwayData.getInstance(world) ?: return
            val halfLength = length / 2
            val halfWidth = width / 2
            if (canMount) {
                val margin = halfLength + BOX_PADDING
                world!!.getEntitiesOfClass(Player::class.java, AABB(carX + margin, carY + margin, carZ + margin, carX - margin, carY - margin, carZ - margin), Predicate { player ->
                    !player.isSpectator && !containsRider(ridingEntities, player.uuid) && railwayData.railwayDataCoolDownModule.canRide(player) && canRide!!.apply(player)!!
                }).forEach(Consumer { player ->
                    val positionRotated: Vec3 = player.position().subtract(carX, carY, carZ).yRot(-carYaw).xRot(-carPitch)
                    if (Math.abs(positionRotated.x) < halfWidth + INNER_PADDING && Math.abs(positionRotated.y) < 2.5 && Math.abs(positionRotated.z) <= halfLength && !railwayData.railwayDataCoolDownModule.shouldDismount(player)) {
                        addRider(ridingEntities, player.uuid)
                        val percentageX = (positionRotated.x / width + 0.5).toFloat()
                        val percentageZ = (if (length == 0.0) 0.0 else positionRotated.z / length + 0.5).toFloat() + percentageOffset
                        val packet = FriendlyByteBuf(Unpooled.buffer())
                        packet.writeLong(id)
                        packet.writeFloat(percentageX)
                        packet.writeFloat(percentageZ)
                        packet.writeUUID(player.uuid)
                        Registry.sendToPlayers(world, packetId, packet)
                    }
                })
            }
            if (ridingEntities!!.isEmpty()) return
            val ridingEntitiesIterator = ridingEntities.iterator()
            while (ridingEntitiesIterator.hasNext()) {
                val uuid = ridingEntitiesIterator.next()
                val player = world!!.getPlayerByUUID(javaReference(uuid))
                if (player != null) {
                    val remove = if (player.isSpectator || railwayData.railwayDataCoolDownModule.shouldDismount(player)) {
                        true
                    } else if (doorOpen) {
                        val positionRotated: Vec3 = player.position().subtract(carX, carY, carZ).yRot(-carYaw).xRot(-carPitch)
                        Math.abs(positionRotated.z) <= halfLength && (Math.abs(positionRotated.x) > halfWidth + INNER_PADDING || Math.abs(positionRotated.y) > 10)
                    } else false
                    railwayData.railwayDataCoolDownModule.updatePlayerRiding(player, routeId)
                    ridingCallback!!.accept(player)
                    if (remove) ridingEntitiesIterator.remove()
                }
            }
        }

        // Java evaluates UUID access before invoking a possibly null set receiver.
        private fun containsRider(riders: MutableSet<UUID?>?, uuid: UUID?): Boolean = riders!!.contains(uuid)
        private fun addRider(riders: MutableSet<UUID?>?, uuid: UUID?) { riders!!.add(uuid) }

        // Nullable UUID entries must still reach the Java lookup unchanged.
        @Suppress("UNCHECKED_CAST")
        private fun <T> javaReference(value: T?): T = value as T
    }
}

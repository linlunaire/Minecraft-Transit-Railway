package mtr.data

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import org.msgpack.value.Value
import java.util.HashSet

@JvmSuppressWildcards
open class LiftServer : Lift {
    constructor(pos: BlockPos?, facing: Direction?) : super(pos, facing)
    constructor(map: MutableMap<String?, Value?>?) : super(map)

    open fun tickServer(world: Level?, liftsInPlayerRange: MutableMap<Player, MutableSet<LiftServer>?>?, liftsToSync: MutableSet<LiftServer>?) {
        if (floors.isNotEmpty()) {
            for (player in world!!.players()) {
                var inRange = ridingEntities.contains(player.uuid)
                if (!inRange) {
                    val playerPos = player.blockPosition()
                    for (floor in floors) {
                        if (playerPos.distManhattan(floor) < LIFT_UPDATE_DISTANCE) {
                            inRange = true
                            break
                        }
                    }
                }
                if (inRange) {
                    var lifts = liftsInPlayerRange!![player]
                    if (lifts == null) {
                        lifts = HashSet()
                        liftsInPlayerRange[player] = lifts
                    }
                    lifts.add(this)
                }
            }
        }

        tick(world, 1F)

        val ridingEntitiesCount = ridingEntities.size
        VehicleRidingServer.mountRider(world, ridingEntities, id, 1,
            currentPositionX + liftOffsetX / 2F, currentPositionY + liftOffsetY, currentPositionZ + liftOffsetZ / 2F,
            (liftWidth - 1).toDouble(), (liftDepth - 1).toDouble(), getYaw(), 0F, doorValue > 0, true, 0,
            mtr.packet.IPacket.PACKET_UPDATE_LIFT_PASSENGERS, { true }, {})

        if (liftInstructions.isDirty() || ridingEntitiesCount != ridingEntities.size) {
            liftsToSync!!.add(this)
        }
    }

    private companion object {
        const val LIFT_UPDATE_DISTANCE = 64
    }
}

package mtr.data

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import java.util.UUID

@JvmSuppressWildcards
open class RailwayDataDriveTrainModule(
    railwayData: RailwayData?, world: Level?, rails: MutableMap<BlockPos, MutableMap<BlockPos, Rail>>?
) : RailwayDataModuleBase(railwayData, world, rails) {
    private val acceleratePlayers = HashSet<UUID?>()
    private val brakePlayers = HashSet<UUID?>()
    private val doorsPlayers = HashSet<UUID?>()

    open fun tick() {
        acceleratePlayers.clear()
        brakePlayers.clear()
        doorsPlayers.clear()
    }

    open fun drive(player: ServerPlayer?, pressingAccelerate: Boolean, pressingBrake: Boolean, pressingDoors: Boolean) {
        if (pressingAccelerate) acceleratePlayers.add(player!!.uuid)
        if (pressingBrake) brakePlayers.add(player!!.uuid)
        if (pressingDoors) doorsPlayers.add(player!!.uuid)
    }

    open fun drive(trainServer: TrainServer?): Boolean {
        var dirty = false
        for (ridingEntity in trainServer!!.getRidingEntities()) {
            if (acceleratePlayers.contains(ridingEntity) && trainServer.changeManualSpeed(true)) {
                dirty = true
            } else if (brakePlayers.contains(ridingEntity) && trainServer.changeManualSpeed(false)) {
                dirty = true
            }
            if (doorsPlayers.contains(ridingEntity) && trainServer.toggleDoors()) dirty = true
        }
        return dirty
    }
}

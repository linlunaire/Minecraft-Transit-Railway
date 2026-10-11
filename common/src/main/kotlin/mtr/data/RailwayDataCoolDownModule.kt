package mtr.data

import mtr.Registry
import mtr.entity.EntitySeat
import mtr.mappings.Utilities
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import java.util.HashMap
import java.util.function.Consumer

@JvmSuppressWildcards
open class RailwayDataCoolDownModule(railwayData: RailwayData?, world: Level?, rails: MutableMap<BlockPos, MutableMap<BlockPos, Rail>>?) :
    RailwayDataModuleBase(railwayData, world, rails) {
    private val playerRidingCoolDown: MutableMap<Player?, Int?> = HashMap()
    private val playerRidingRoute: MutableMap<Player?, Long?> = HashMap()
    private val playerSeats: MutableMap<Player?, EntitySeat?> = HashMap()
    private val playerSeatCoolDowns: MutableMap<Player?, Int?> = HashMap()
    private val playerShiftCoolDowns: MutableMap<Player?, Int?> = HashMap()

    open fun tick() {
        world!!.players().forEach(Consumer<Player?> { player ->
            val seatCoolDownOld = playerSeatCoolDowns[player]
            val seatOld = playerSeats[player]
            val seat: EntitySeat?
            if (seatCoolDownOld == null || seatCoolDownOld <= 0 || Utilities.entityRemoved(seatOld)) {
                seat = EntitySeat(world, player!!.x, player.y, player.z)
                world.addFreshEntity(seat)
                seat.initialize(player)
                playerSeats[player] = seat
                playerSeatCoolDowns[player] = 3
            } else {
                seat = seatOld
                playerSeatCoolDowns[player] = seatCoolDownOld - 1
            }
            seat!!.updateSeatByRailwayData(player)
            val oldShiftCoolDown = playerShiftCoolDowns.getOrDefault(player, 0)!!
            val shiftCoolDown = if (player!!.isShiftKeyDown) Math.min(SHIFT_ACTIVATE_TICKS, oldShiftCoolDown + 1) else 0
            if (shiftCoolDown != oldShiftCoolDown) playerShiftCoolDowns[player] = shiftCoolDown
        })

        val playerRidingCoolDownIterator = playerRidingCoolDown.entries.iterator()
        while (playerRidingCoolDownIterator.hasNext()) {
            val entry = playerRidingCoolDownIterator.next()
            val player = entry.key
            val coolDown = entry.value!!
            if (coolDown <= 0) {
                updatePlayerRiding(player, 0)
                player!!.stopRiding()
                playerRidingCoolDownIterator.remove()
                playerRidingRoute.remove(player)
            } else entry.setValue(coolDown - 1)
        }
    }

    open fun onPlayerJoin(serverPlayer: ServerPlayer?) {
        playerRidingCoolDown[serverPlayer] = 2
        playerShiftCoolDowns[serverPlayer] = 0
    }

    open fun onPlayerDisconnect(player: Player?) {
        playerSeats.remove(player)
        playerSeatCoolDowns.remove(player)
        playerShiftCoolDowns.remove(player)
    }

    open fun updatePlayerRiding(player: Player?, routeId: Long) {
        val isRiding = routeId != 0L
        player!!.fallDistance = 0.0
        player.setNoGravity(isRiding)
        player.noPhysics = isRiding
        if (isRiding) {
            Utilities.getAbilities(player).mayfly = true
            playerRidingCoolDown[player] = 2
            playerRidingRoute[player] = routeId
        } else {
            (player as ServerPlayer).gameMode.gameModeForPlayer.updatePlayerAbilities(Utilities.getAbilities(player))
        }
        Registry.setInTeleportationState(player, isRiding)
    }

    open fun updatePlayerSeatCoolDown(player: Player?) { playerSeatCoolDowns[player] = 3 }
    open fun canRide(player: Player?): Boolean = !playerRidingCoolDown.containsKey(player)

    open fun getRidingRoute(player: Player?): Route? {
        val routeId = playerRidingRoute[player]
        return if (routeId == null) null else railwayData!!.dataCache.routeIdMap[routeId]
    }

    open fun moveSeat(player: Player?, x: Double, y: Double, z: Double) {
        val entitySeat = playerSeats[player]
        if (entitySeat != null) {
            player!!.startRiding(entitySeat)
            entitySeat.setPos(x, y, z)
        }
    }

    open fun shouldDismount(player: Player?): Boolean = playerShiftCoolDowns.getOrDefault(player, 0)!! == SHIFT_ACTIVATE_TICKS

    companion object {
        const val SHIFT_ACTIVATE_TICKS = 30
    }
}

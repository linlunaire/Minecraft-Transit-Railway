package mtr.data

import mtr.packet.PacketTrainDataGuiServer
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

@JvmSuppressWildcards
open class RailwayDataRailActionsModule(
    railwayData: RailwayData?, world: Level?, rails: MutableMap<BlockPos, MutableMap<BlockPos, Rail>>?
) : RailwayDataModuleBase(railwayData, world, rails) {
    private val railActions: MutableList<Rail.RailActions?> = ArrayList()

    open fun tick() {
        if (railActions.isNotEmpty() && railActions[0]!!.build()) {
            railActions.removeAt(0)
            PacketTrainDataGuiServer.updateRailActionsS2C(world, railActions)
        }
    }

    open fun markRailForBridge(player: Player?, pos1: BlockPos?, pos2: BlockPos?, radius: Int, state: BlockState?): Boolean {
        if (!railwayData!!.containsRail(pos1, pos2)) return false
        railActions.add(Rail.RailActions(world, player, Rail.RailActionType.BRIDGE, rails!![pos1]!![pos2], radius, 0, state))
        PacketTrainDataGuiServer.updateRailActionsS2C(world, railActions)
        return true
    }

    open fun markRailForTunnel(player: Player?, pos1: BlockPos?, pos2: BlockPos?, radius: Int, height: Int): Boolean {
        if (!railwayData!!.containsRail(pos1, pos2)) return false
        railActions.add(Rail.RailActions(world, player, Rail.RailActionType.TUNNEL, rails!![pos1]!![pos2], radius, height, null))
        PacketTrainDataGuiServer.updateRailActionsS2C(world, railActions)
        return true
    }

    open fun markRailForTunnelWall(player: Player?, pos1: BlockPos?, pos2: BlockPos?, radius: Int, height: Int, state: BlockState?): Boolean {
        if (!railwayData!!.containsRail(pos1, pos2)) return false
        railActions.add(Rail.RailActions(world, player, Rail.RailActionType.TUNNEL_WALL, rails!![pos1]!![pos2], radius + 1, height + 1, state))
        PacketTrainDataGuiServer.updateRailActionsS2C(world, railActions)
        return true
    }

    open fun removeRailAction(id: Long) {
        railActions.removeIf { it!!.id == id }
        PacketTrainDataGuiServer.updateRailActionsS2C(world, railActions)
    }
}

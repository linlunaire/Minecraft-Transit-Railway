package mtr.item

import mtr.data.RailwayData
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

open class ItemTunnelCreator(height: Int, width: Int) :
    ItemNodeModifierSelectableBlockBase(false, height, width) {
    protected override fun onConnect(
        player: Player,
        stack: ItemStack,
        railwayData: RailwayData,
        posStart: BlockPos,
        posEnd: BlockPos,
        radius: Int,
        height: Int
    ): Boolean {
        return railwayData.railwayDataRailActionsModule.markRailForTunnel(player, posStart, posEnd, radius, height)
    }
}

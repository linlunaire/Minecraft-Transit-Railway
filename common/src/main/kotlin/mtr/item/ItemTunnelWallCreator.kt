package mtr.item

import mtr.data.RailwayData
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

open class ItemTunnelWallCreator(height: Int, width: Int) :
    ItemNodeModifierSelectableBlockBase(true, height, width) {
    protected override fun onConnect(
        player: Player,
        stack: ItemStack,
        railwayData: RailwayData,
        posStart: BlockPos,
        posEnd: BlockPos,
        radius: Int,
        height: Int
    ): Boolean {
        val state = getSavedState(stack)
        return state == null || railwayData.railwayDataRailActionsModule.markRailForTunnelWall(
            player,
            posStart,
            posEnd,
            radius,
            height,
            state
        )
    }
}

package mtr.item

import mtr.data.RailwayData
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

open class ItemBridgeCreator(width: Int) : ItemNodeModifierSelectableBlockBase(true, 0, width) {
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
        return state == null || railwayData.railwayDataRailActionsModule.markRailForBridge(
            player,
            posStart,
            posEnd,
            radius,
            state
        )
    }
}

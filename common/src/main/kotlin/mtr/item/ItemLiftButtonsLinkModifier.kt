package mtr.item

import java.util.function.Function

import mtr.CreativeModeTabs
import mtr.block.BlockLiftButtons
import mtr.block.BlockLiftButtons.TileEntityLiftButtons
import mtr.block.BlockLiftPanelBase
import mtr.block.BlockLiftPanelBase.TileEntityLiftPanel1Base
import mtr.block.BlockLiftTrackFloor
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.context.UseOnContext

open class ItemLiftButtonsLinkModifier(private val isConnector: Boolean) : ItemBlockClickingBase(
    CreativeModeTabs.ESCALATORS_LIFTS, Function { properties -> properties.stacksTo(1) }) {
    protected override fun onStartClick(context: UseOnContext, compoundTag: CompoundTag) {
    }

    protected override fun onEndClick(context: UseOnContext, posEnd: BlockPos, compoundTag: CompoundTag) {
        val world = context.getLevel()
        val posStart = context.getClickedPos()
        val blockStart = world.getBlockState(posStart).getBlock()
        val blockEnd = world.getBlockState(posEnd).getBlock()

        if (blockStart is BlockLiftTrackFloor && blockEnd is BlockLiftButtons || blockStart is BlockLiftButtons && blockEnd is BlockLiftTrackFloor || blockStart is BlockLiftTrackFloor && blockEnd is BlockLiftPanelBase || blockStart is BlockLiftPanelBase && blockEnd is BlockLiftTrackFloor) {
            var posFloor: BlockPos
            var posButtons: BlockPos
            if (blockStart is BlockLiftTrackFloor) {
                posFloor = posStart
                posButtons = posEnd
            } else {
                posFloor = posEnd
                posButtons = posStart
            }

            val blockEntity = world.getBlockEntity(posButtons)
            if (blockEntity is TileEntityLiftButtons) {
                blockEntity.registerFloor(posFloor, isConnector)
            }

            if (blockEntity is TileEntityLiftPanel1Base) {
                blockEntity.registerFloor(posFloor, isConnector)
            }
        }
    }

    protected override fun clickCondition(context: UseOnContext): Boolean {
        val block = context.getLevel().getBlockState(context.getClickedPos()).getBlock()
        return block is BlockLiftTrackFloor || block is BlockLiftButtons || block is BlockLiftPanelBase
    }
}

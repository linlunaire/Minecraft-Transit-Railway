package mtr.block

import mtr.mappings.BlockMapper
import mtr.mappings.BlockTooltip
import mtr.mappings.Text
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.item.Item.TooltipContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

open class BlockStationColorPole(settings: Properties, private val showTooltip: Boolean) :
    BlockMapper(settings), BlockTooltip {
    public override fun getShape(
        state: BlockState,
        blockGetter: BlockGetter,
        pos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        return getStationPoleShape()
    }

    override fun appendHoverText(
        itemStack: ItemStack?,
        tooltipContext: TooltipContext?,
        tooltip: MutableList<Component?>,
        tooltipFlag: TooltipFlag?
    ) {
        if (showTooltip) {
            tooltip.add(
                Text.translatable("tooltip.mtr.station_color").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
            )
        }
    }

    companion object {
        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun getStationPoleShape(): VoxelShape = box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0)
    }
}

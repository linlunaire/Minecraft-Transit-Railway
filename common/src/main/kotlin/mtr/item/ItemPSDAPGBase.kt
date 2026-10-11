package mtr.item

import mtr.Blocks
import mtr.CreativeModeTabs
import mtr.block.BlockPSDAPGBase
import mtr.block.BlockPSDTop
import mtr.block.IBlock
import mtr.block.IBlock.EnumSide
import mtr.block.ITripleBlock
import mtr.mappings.Text
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.util.StringRepresentable
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.component.TooltipDisplay
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf

open class ItemPSDAPGBase(private val item: EnumPSDAPGItem, private val type: EnumPSDAPGType) :
    ItemWithCreativeTabBase(if (type.isLift) CreativeModeTabs.ESCALATORS_LIFTS else CreativeModeTabs.RAILWAY_FACILITIES),
    IBlock {
    override fun useOn(context: UseOnContext): InteractionResult {
        val horizontalBlocks = if (item.isDoor) if (type.isOdd) 3 else 2 else 1
        if (blocksNotReplaceable(
                context,
                horizontalBlocks,
                if (type.isPSD) 3 else 2,
                this.blockStateFromItem.getBlock()
            )
        ) {
            return InteractionResult.FAIL
        }

        val world = context.getLevel()
        val playerFacing = context.getHorizontalDirection()
        val pos = context.getClickedPos().relative(context.getClickedFace())

        for (x in 0..<horizontalBlocks) {
            val newPos = pos.relative(playerFacing.getClockWise(), x)

            for (y in 0..1) {
                val state = this.blockStateFromItem.setValue(BlockPSDAPGBase.FACING, playerFacing)
                    .setValue(IBlock.HALF, if (y == 1) DoubleBlockHalf.UPPER else DoubleBlockHalf.LOWER)
                if (item.isDoor) {
                    var newState = state.setValue(IBlock.SIDE, if (x == 0) EnumSide.LEFT else EnumSide.RIGHT)
                    if (type.isOdd) {
                        newState = newState.setValue(ITripleBlock.ODD, x > 0 && x < horizontalBlocks - 1)
                    }
                    world.setBlockAndUpdate(newPos.above(y), newState)
                } else {
                    world.setBlockAndUpdate(
                        newPos.above(y),
                        state.setValue(IBlock.SIDE_EXTENDED, EnumSide.SINGLE)
                    )
                }
            }

            if (type.isPSD) {
                world.setBlockAndUpdate(newPos.above(2), BlockPSDTop.getActualState(world, newPos.above(2)))
            }
        }

        context.getItemInHand().shrink(1)
        return InteractionResult.SUCCESS
    }

    override fun appendHoverText(
        itemStack: ItemStack,
        tooltipContext: TooltipContext,
        tooltipDisplay: TooltipDisplay,
        tooltip: java.util.function.Consumer<Component>,
        tooltipFlag: TooltipFlag
    ) {
        tooltip.accept(
            Text.translatable(if (type.isLift) if (type.isOdd) "tooltip.mtr.railway_sign_odd" else "tooltip.mtr.railway_sign_even" else "tooltip.mtr." + item.getSerializedName())
                .setStyle(
                    Style.EMPTY.withColor(ChatFormatting.GRAY)
                )
        )
    }

    private val blockStateFromItem: BlockState
        get() {
            when (type) {
                EnumPSDAPGType.PSD_1 -> {
                    when (item) {
                        EnumPSDAPGItem.PSD_APG_DOOR -> return Blocks.PSD_DOOR_1.get()
                            .defaultBlockState()

                        EnumPSDAPGItem.PSD_APG_GLASS -> return Blocks.PSD_GLASS_1.get()
                            .defaultBlockState()

                        EnumPSDAPGItem.PSD_APG_GLASS_END -> return Blocks.PSD_GLASS_END_1.get()
                            .defaultBlockState()

                        else -> {}
                    }
                    when (item) {
                        EnumPSDAPGItem.PSD_APG_DOOR -> return Blocks.PSD_DOOR_2.get()
                            .defaultBlockState()

                        EnumPSDAPGItem.PSD_APG_GLASS -> return Blocks.PSD_GLASS_2.get()
                            .defaultBlockState()

                        EnumPSDAPGItem.PSD_APG_GLASS_END -> return Blocks.PSD_GLASS_END_2.get()
                            .defaultBlockState()

                        else -> {}
                    }
                    when (item) {
                        EnumPSDAPGItem.PSD_APG_DOOR -> return Blocks.APG_DOOR.get()
                            .defaultBlockState()

                        EnumPSDAPGItem.PSD_APG_GLASS -> return Blocks.APG_GLASS.get()
                            .defaultBlockState()

                        EnumPSDAPGItem.PSD_APG_GLASS_END -> return Blocks.APG_GLASS_END.get()
                            .defaultBlockState()

                        else -> {}
                    }
                    return Blocks.LIFT_DOOR_EVEN_1.get().defaultBlockState()
                }

                EnumPSDAPGType.PSD_2 -> {
                    when (item) {
                        EnumPSDAPGItem.PSD_APG_DOOR -> return Blocks.PSD_DOOR_2.get()
                            .defaultBlockState()

                        EnumPSDAPGItem.PSD_APG_GLASS -> return Blocks.PSD_GLASS_2.get()
                            .defaultBlockState()

                        EnumPSDAPGItem.PSD_APG_GLASS_END -> return Blocks.PSD_GLASS_END_2.get()
                            .defaultBlockState()

                        else -> {}
                    }
                    when (item) {
                        EnumPSDAPGItem.PSD_APG_DOOR -> return Blocks.APG_DOOR.get()
                            .defaultBlockState()

                        EnumPSDAPGItem.PSD_APG_GLASS -> return Blocks.APG_GLASS.get()
                            .defaultBlockState()

                        EnumPSDAPGItem.PSD_APG_GLASS_END -> return Blocks.APG_GLASS_END.get()
                            .defaultBlockState()

                        else -> {}
                    }
                    return Blocks.LIFT_DOOR_EVEN_1.get().defaultBlockState()
                }

                EnumPSDAPGType.APG -> {
                    when (item) {
                        EnumPSDAPGItem.PSD_APG_DOOR -> return Blocks.APG_DOOR.get()
                            .defaultBlockState()

                        EnumPSDAPGItem.PSD_APG_GLASS -> return Blocks.APG_GLASS.get()
                            .defaultBlockState()

                        EnumPSDAPGItem.PSD_APG_GLASS_END -> return Blocks.APG_GLASS_END.get()
                            .defaultBlockState()

                        else -> {}
                    }
                    return Blocks.LIFT_DOOR_EVEN_1.get().defaultBlockState()
                }

                EnumPSDAPGType.LIFT_DOOR_1 -> return Blocks.LIFT_DOOR_EVEN_1.get()
                    .defaultBlockState()

                EnumPSDAPGType.LIFT_DOOR_ODD_1 -> return Blocks.LIFT_DOOR_ODD_1.get()
                    .defaultBlockState()

                else -> return net.minecraft.world.level.block.Blocks.AIR.defaultBlockState()
            }
        }

    enum class EnumPSDAPGType(internal val isPSD: Boolean, internal val isOdd: Boolean, internal val isLift: Boolean) {
        PSD_1(true, false, false),
        PSD_2(true, false, false),
        APG(false, false, false),
        LIFT_DOOR_1(false, false, true),
        LIFT_DOOR_ODD_1(false, true, true)
    }

    enum class EnumPSDAPGItem(private val serializedName: String, internal val isDoor: Boolean) : StringRepresentable {
        PSD_APG_DOOR("psd_apg_door", true),
        PSD_APG_GLASS("psd_apg_glass", false),
        PSD_APG_GLASS_END("psd_apg_glass_end", false);

        override fun getSerializedName(): String {
            return serializedName
        }
    }

    companion object {
        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun blocksNotReplaceable(context: UseOnContext, width: Int, height: Int, blacklistBlock: Block?): Boolean {
            val facing = context.getHorizontalDirection()
            val world = context.getLevel()
            val startingPos = context.getClickedPos().relative(context.getClickedFace())

            for (x in 0..<width) {
                val offsetPos = startingPos.relative(facing.getClockWise(), x)

                if (blacklistBlock != null) {
                    val isBlacklistedBelow = world.getBlockState(offsetPos.below()).`is`(blacklistBlock)
                    val isBlacklistedAbove = world.getBlockState(offsetPos.above(height)).`is`(blacklistBlock)
                    if (isBlacklistedBelow || isBlacklistedAbove) {
                        return true
                    }
                }

                for (y in 0..<height) {
                    if (!world.getBlockState(offsetPos.above(y)).canBeReplaced()) {
                        return true
                    }
                }
            }

            return false
        }
    }
}

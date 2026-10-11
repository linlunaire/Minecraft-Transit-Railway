package mtr.item

import mtr.CreativeModeTabs
import mtr.mappings.CompoundTagMapper
import mtr.mappings.ItemStackUtilities
import mtr.mappings.Text
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.component.TooltipDisplay
import net.minecraft.world.item.context.UseOnContext
import java.util.function.Consumer
import java.util.function.Function

abstract class ItemBlockClickingBase(
    creativeModeTab: CreativeModeTabs.Wrapper,
    propertiesConsumer: Function<Properties, Properties>
) : ItemWithCreativeTabBase(creativeModeTab, propertiesConsumer) {
    override fun useOn(context: UseOnContext): InteractionResult {
        if (!context.level.isClientSide()) {
            if (clickCondition(context)) {
                val itemStack = context.itemInHand
                val compoundTag = ItemStackUtilities.getCustomData(itemStack)

                if (compoundTag.contains(TAG_POS)) {
                    val posEnd = BlockPos.of(CompoundTagMapper.getLong(compoundTag, TAG_POS))
                    onEndClick(context, posEnd, compoundTag)
                    compoundTag.remove(TAG_POS)
                } else {
                    compoundTag.putLong(TAG_POS, context.clickedPos.asLong())
                    onStartClick(context, compoundTag)
                }
                ItemStackUtilities.setCustomData(itemStack, compoundTag)
                return InteractionResult.SUCCESS
            } else {
                return InteractionResult.FAIL
            }
        } else {
            return super.useOn(context)
        }
    }

    override fun appendHoverText(
        stack: ItemStack,
        tooltipContext: TooltipContext,
        tooltipDisplay: TooltipDisplay,
        tooltip: Consumer<Component>,
        tooltipFlag: TooltipFlag
    ) {
        val compoundTag = ItemStackUtilities.getCustomData(stack)
        val posLong = CompoundTagMapper.getLong(compoundTag, TAG_POS)
        if (posLong != 0L) {
            tooltip.accept(
                Text.translatable("tooltip.mtr.selected_block", BlockPos.of(posLong).toShortString())
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))
            )
        }
    }

    protected abstract fun onStartClick(context: UseOnContext, compoundTag: CompoundTag)
    protected abstract fun onEndClick(context: UseOnContext, posEnd: BlockPos, compoundTag: CompoundTag)
    protected abstract fun clickCondition(context: UseOnContext): Boolean

    companion object {
        const val TAG_POS: String = "pos"
    }
}

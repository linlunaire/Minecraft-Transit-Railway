package mtr.item;

import mtr.CreativeModeTabs;
import mtr.mappings.Text;
import mtr.mappings.ItemStackUtilities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Function;

public abstract class ItemBlockClickingBase extends ItemWithCreativeTabBase {

	public static final String TAG_POS = "pos";

	public ItemBlockClickingBase(CreativeModeTabs.Wrapper creativeModeTab, Function<Properties, Properties> propertiesConsumer) {
		super(creativeModeTab, propertiesConsumer);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (!context.getLevel().isClientSide()) {
			if (clickCondition(context)) {
				final ItemStack itemStack = context.getItemInHand();
				final CompoundTag compoundTag = ItemStackUtilities.getCustomData(itemStack);

				if (compoundTag.contains(TAG_POS)) {
					final BlockPos posEnd = BlockPos.of(mtr.mappings.CompoundTagMapper.getLong(compoundTag, TAG_POS));
					onEndClick(context, posEnd, compoundTag);
					compoundTag.remove(TAG_POS);
				} else {
					compoundTag.putLong(TAG_POS, context.getClickedPos().asLong());
					onStartClick(context, compoundTag);
				}
				ItemStackUtilities.setCustomData(itemStack, compoundTag);

				return InteractionResult.SUCCESS;
			} else {
				return InteractionResult.FAIL;
			}
		} else {
			return super.useOn(context);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<Component> tooltip, TooltipFlag tooltipFlag) {
		final CompoundTag compoundTag = ItemStackUtilities.getCustomData(stack);
		final long posLong = mtr.mappings.CompoundTagMapper.getLong(compoundTag, TAG_POS);
		if (posLong != 0) {
			tooltip.accept(Text.translatable("tooltip.mtr.selected_block", BlockPos.of(posLong).toShortString()).setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)));
		}
	}

	protected abstract void onStartClick(UseOnContext context, CompoundTag compoundTag);

	protected abstract void onEndClick(UseOnContext context, BlockPos posEnd, CompoundTag compoundTag);

	protected abstract boolean clickCondition(UseOnContext context);
}

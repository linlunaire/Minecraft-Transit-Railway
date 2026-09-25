package mtr.mappings;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TooltipBlockItem extends BlockItem {

	public TooltipBlockItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, display, tooltip, flag);
		if (getBlock() instanceof BlockTooltip blockTooltip) {
			final List<Component> lines = new ArrayList<>();
			blockTooltip.appendHoverText(stack, context, lines, flag);
			lines.forEach(tooltip);
		}
	}
}

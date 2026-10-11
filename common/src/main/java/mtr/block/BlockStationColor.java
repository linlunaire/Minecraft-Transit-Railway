package mtr.block;

import mtr.mappings.BlockMapper;
import mtr.mappings.Text;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;

import java.util.List;

public class BlockStationColor extends BlockMapper implements mtr.mappings.BlockTooltip {

	public BlockStationColor(Properties settings) {
		super(mtr.mappings.RegistrationContext.stationColorProperties(settings));
	}


	@Override
	public void appendHoverText(ItemStack itemStack, net.minecraft.world.item.Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
		tooltip.add(Text.translatable("tooltip.mtr.station_color").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
	}
}

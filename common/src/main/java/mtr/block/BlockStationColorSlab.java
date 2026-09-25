package mtr.block;

import com.mojang.serialization.MapCodec;
import mtr.mappings.Text;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SlabBlock;

import java.util.List;

public class BlockStationColorSlab extends SlabBlock implements mtr.mappings.BlockTooltip {

	public BlockStationColorSlab(Properties settings) {
		super(mtr.mappings.RegistrationContext.stationColorProperties(settings));
	}

	@Override
	public MapCodec<? extends SlabBlock> codec() {
		return simpleCodec(properties -> this);
	}


	@Override
	public void appendHoverText(ItemStack itemStack, net.minecraft.world.item.Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
		tooltip.add(Text.translatable("tooltip.mtr.station_color").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
	}
}

package mtr.mappings;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

/** The legacy selected predicate checks key presence, including pos=0 and wrong-typed tags. */
public final class SelectedItemModelProperty implements ConditionalItemModelProperty {

	public static final Identifier ID = Identifier.parse("mtr:selected");
	public static final SelectedItemModelProperty INSTANCE = new SelectedItemModelProperty();
	public static final MapCodec<SelectedItemModelProperty> CODEC = MapCodec.unit(INSTANCE);

	private SelectedItemModelProperty() {
	}

	@Override
	public boolean get(ItemStack stack, ClientLevel level, LivingEntity entity, int seed, ItemDisplayContext context) {
		return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().contains("pos");
	}

	@Override
	public MapCodec<? extends ConditionalItemModelProperty> type() { return CODEC; }
}

package mtr.mappings;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public interface ItemStackUtilities {

	static CompoundTag getCustomData(ItemStack itemStack) {
		return itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
	}

	static void setCustomData(ItemStack itemStack, CompoundTag tag) {
		if (tag.isEmpty()) {
			itemStack.remove(DataComponents.CUSTOM_DATA);
		} else {
			itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
		}
	}
}

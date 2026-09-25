package mtr.mappings;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Objects;
import java.util.function.Supplier;

/** Supplies the registered identifier before the vanilla block/item constructor runs. */
public final class RegistrationContext {

	private static final ThreadLocal<Identifier> CURRENT_ID = new ThreadLocal<>();

	private RegistrationContext() {
	}

	public static <T> T construct(Identifier id, Supplier<T> supplier) {
		final Identifier previous = CURRENT_ID.get();
		CURRENT_ID.set(Objects.requireNonNull(id));
		try {
			return supplier.get();
		} finally {
			if (previous == null) {
				CURRENT_ID.remove();
			} else {
				CURRENT_ID.set(previous);
			}
		}
	}

	public static BlockBehaviour.Properties blockProperties(BlockBehaviour.Properties properties) {
		return properties.setId(ResourceKey.create(Registries.BLOCK, requireId()));
	}

	public static Item.Properties itemProperties() {
		return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, requireId()));
	}

	public static BlockBehaviour.Properties stationColorProperties(BlockBehaviour.Properties properties) {
		return blockProperties(properties).overrideDescription(requireId().toLanguageKey("block").replace("block.mtr.station_color_", "block.minecraft."));
	}

	public static Item.Properties blockItemProperties(Identifier id, Block block) {
		return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).overrideDescription(block.getDescriptionId());
	}

	private static Identifier requireId() {
		return Objects.requireNonNull(CURRENT_ID.get(), "MTR block/item constructed without its registration identifier");
	}
}

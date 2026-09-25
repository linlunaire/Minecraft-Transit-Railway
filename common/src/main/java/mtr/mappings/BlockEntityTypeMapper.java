package mtr.mappings;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collections;

/** Keeps inherited addon blocks valid without accepting blocks with a different entity factory. */
final class BlockEntityTypeMapper<T extends BlockEntityMapper> extends BlockEntityType<T> {

	private final ClassValue<Boolean> inheritedFactories;

	BlockEntityTypeMapper(BlockEntitySupplier<? extends T> supplier, Block block) {
		super(supplier, Collections.singleton(block));
		final Class<?> originalClass = block.getClass();
		inheritedFactories = new ClassValue<>() {
			@Override
			protected Boolean computeValue(Class<?> candidateClass) {
				if (candidateClass == originalClass || !originalClass.isAssignableFrom(candidateClass) || !(block instanceof EntityBlockMapper)) {
					return false;
				}
				try {
					return originalClass.getMethod("createBlockEntity", BlockPos.class, BlockState.class).equals(candidateClass.getMethod("createBlockEntity", BlockPos.class, BlockState.class))
							&& originalClass.getMethod("newBlockEntity", BlockPos.class, BlockState.class).equals(candidateClass.getMethod("newBlockEntity", BlockPos.class, BlockState.class))
							&& originalClass.getMethod("getType").equals(candidateClass.getMethod("getType"));
				} catch (NoSuchMethodException ignored) {
					return false;
				}
			}
		};
	}

	@Override
	public boolean isValid(BlockState state) {
		// 26.2 validates in the entity constructor, during NBT loading and on state
		// changes. Fix the registered type, not just one construction call site.
		return super.isValid(state) || inheritedFactories.get(state.getBlock().getClass());
	}
}

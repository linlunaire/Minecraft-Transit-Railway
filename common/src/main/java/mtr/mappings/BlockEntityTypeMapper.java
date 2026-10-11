package mtr.mappings;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;

/** Accepts unchanged inherited factories without weakening validation for unrelated blocks. */
final class BlockEntityTypeMapper<T extends BlockEntityMapper> extends BlockEntityType<T> {

	// Fabric 1.21.1 remaps Minecraft method names. Resolve the vanilla entry point
	// by its unique signature instead of relying on the development name string.
	private static final Method ENTITY_FACTORY = Arrays.stream(EntityBlock.class.getMethods())
			.filter(method -> method.getReturnType() == BlockEntity.class
					&& Arrays.equals(method.getParameterTypes(), new Class<?>[]{BlockPos.class, BlockState.class}))
			.findFirst().orElseThrow(() -> new IllegalStateException("Missing block entity factory"));

	private final ClassValue<Boolean> inheritedFactories;

	BlockEntityTypeMapper(BlockEntitySupplier<? extends T> supplier, Block block) {
		super(supplier, Collections.singleton(block), null);
		final Class<?> originalClass = block.getClass();
		inheritedFactories = new ClassValue<>() {
			@Override
			protected Boolean computeValue(Class<?> candidateClass) {
				if (candidateClass == originalClass || !originalClass.isAssignableFrom(candidateClass) || !(block instanceof EntityBlockMapper)) {
					return false;
				}
				try {
					return originalClass.getMethod("createBlockEntity", BlockPos.class, BlockState.class).equals(candidateClass.getMethod("createBlockEntity", BlockPos.class, BlockState.class))
							&& originalClass.getMethod(ENTITY_FACTORY.getName(), BlockPos.class, BlockState.class).equals(candidateClass.getMethod(ENTITY_FACTORY.getName(), BlockPos.class, BlockState.class))
							&& originalClass.getMethod("getType").equals(candidateClass.getMethod("getType"));
				} catch (NoSuchMethodException ignored) {
					return false;
				}
			}
		};
	}

	@Override
	public boolean isValid(BlockState state) {
		// Used by construction, NBT loading and state changes, not just FAWE's probe.
		return super.isValid(state) || inheritedFactories.get(state.getBlock().getClass());
	}
}

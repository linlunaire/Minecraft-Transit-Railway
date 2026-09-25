package mtr.mappings;

import mtr.BlockEntityTypes;
import mtr.block.BlockAPGGlass;
import mtr.block.BlockPSDDoor;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.lang.reflect.Field;
import java.util.IdentityHashMap;

/** Real 26.2 block-entity construction, including the inheritance pattern used by JCM's APGGlassDRL. */
public final class BlockEntityExtensionCompatibilityCheck {

	private static int assertions;

	public static void main(String[] args) throws Exception {
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		// Vanilla bootstrap freezes registries. Reopen only these two registries in this
		// standalone test process to emulate loader registration, never in production.
		reopen(BuiltInRegistries.BLOCK);
		reopen(BuiltInRegistries.BLOCK_ENTITY_TYPE);
		final BlockAPGGlass original = (BlockAPGGlass) mtr.Blocks.APG_GLASS.get();
		Registry.register(BuiltInRegistries.BLOCK, id("mtr:apg_glass"), original);
		final BlockEntityType<?> type = BlockEntityTypes.APG_GLASS_TILE_ENTITY.get();
		Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id("mtr:apg_glass"), type);
		final AddonGlass addon = register("jsblock:apg_glass_drl", AddonGlass::new);
		final OtherFactoryGlass otherFactory = register("test:other_factory", OtherFactoryGlass::new);
		final OtherEntryPointGlass otherEntryPoint = register("test:other_entry_point", OtherEntryPointGlass::new);
		final OtherTypeGlass otherType = register("test:other_type", OtherTypeGlass::new);
		final GrandchildGlass grandchild = register("test:grandchild", GrandchildGlass::new);
		final BlockAPGGlass sameClass = register("test:same_class", BlockAPGGlass::new);
		final Block originalDoor = mtr.Blocks.PSD_DOOR_1.get();
		Registry.register(BuiltInRegistries.BLOCK, id("mtr:psd_door"), originalDoor);
		final BlockEntityType<?> originalDoorType = BlockEntityTypes.PSD_DOOR_1_TILE_ENTITY.get();
		Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id("mtr:psd_door"), originalDoorType);
		final DifferentStyleDoor styleDoor = register("test:different_door_style", DifferentStyleDoor::new);
		finishRegistration(BuiltInRegistries.BLOCK);
		finishRegistration(BuiltInRegistries.BLOCK_ENTITY_TYPE);

		final BlockPos pos = new BlockPos(19, 64, -27);
		final BlockState state = addon.defaultBlockState().setValue(AddonGlass.EXTRA, 2).setValue(BlockAPGGlass.ARROW_DIRECTION, 3);
		final BlockEntity entity = addon.newBlockEntity(pos, state);
		require(entity instanceof BlockAPGGlass.TileEntityAPGGlass, "Inherited APG factory no longer returns its real MTR entity");
		require(entity.getType() == type, "Inherited entity lost its registered MTR type");
		require(entity.getBlockState() == state, "Addon block state was substituted or its custom property was lost");
		require(entity.getBlockPos().equals(pos), "Addon entity position changed");
		require(type.isValid(state), "Type-level validation rejects the inherited addon state");
		require(entity.isValidBlockState(state), "Entity-level validation rejects the inherited addon state");
		final BlockState changed = state.setValue(AddonGlass.EXTRA, 1);
		entity.setBlockState(changed);
		require(entity.getBlockState() == changed, "Updating addon state lost its identity");
		require(type.isValid(original.defaultBlockState()), "Original MTR block became invalid");
		require(original.newBlockEntity(pos, original.defaultBlockState()).getType() == type, "Original MTR constructor changed");
		require(!type.isValid(net.minecraft.world.level.block.Blocks.STONE.defaultBlockState()), "Unrelated vanilla block accepted");
		require(!type.isValid(otherFactory.defaultBlockState()), "Addon overriding its factory was silently accepted");
		require(!type.isValid(otherEntryPoint.defaultBlockState()), "Addon overriding the vanilla entry point was silently accepted");
		require(!type.isValid(otherType.defaultBlockState()), "Addon overriding its ticker type was silently accepted");
		require(!type.isValid(sameClass.defaultBlockState()), "Unregistered same-class block was silently accepted");
		require(type.isValid(grandchild.defaultBlockState()), "A second level of unchanged inheritance was rejected");
		require(originalDoorType.isValid(originalDoor.defaultBlockState()), "Unmodified configurable type rejected its original block");
		require(!originalDoorType.isValid(styleDoor.defaultBlockState()), "Type factories depending on instance settings must not opt into inherited blocks");
		for (BlockState invalid : new BlockState[]{net.minecraft.world.level.block.Blocks.STONE.defaultBlockState(), otherFactory.defaultBlockState(), otherEntryPoint.defaultBlockState(), otherType.defaultBlockState(), sameClass.defaultBlockState()}) {
			expectInvalid(() -> entity.setBlockState(invalid));
			require(entity.getBlockState() == changed, "Rejected update replaced the valid addon state");
			expectInvalid(() -> new BlockAPGGlass.TileEntityAPGGlass(pos, invalid));
		}

		final var registries = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
		final CompoundTag saved = entity.saveWithFullMetadata(registries);
		require(saved.getString("id").orElseThrow().equals("mtr:apg_glass"), "Addon entity save ID changed");
		final Tag savedState = BlockState.CODEC.encodeStart(NbtOps.INSTANCE, changed).getOrThrow();
		require(((CompoundTag) savedState).getString("Name").orElseThrow().equals("jsblock:apg_glass_drl"), "Addon block save ID changed");
		final BlockState restoredState = BlockState.CODEC.parse(NbtOps.INSTANCE, savedState).getOrThrow();
		require(restoredState == changed && restoredState.getValue(AddonGlass.EXTRA) == 1, "Addon properties changed during state serialization");
		final BlockEntity restored = BlockEntity.loadStatic(pos, restoredState, saved, registries);
		require(restored instanceof BlockAPGGlass.TileEntityAPGGlass, "Real NBT loading dropped the inherited addon entity");
		require(restored.getType() == type && restored.getBlockState() == changed, "NBT loading substituted the type or addon state");
		require(restored.saveWithFullMetadata(registries).equals(saved), "Block entity metadata changed on save/load/save");
		for (BlockState variant : addon.getStateDefinition().getPossibleStates()) {
			require(type.isValid(variant), "An addon property combination is invalid: " + variant);
			require(addon.newBlockEntity(pos, variant).getBlockState() == variant, "An addon property combination was substituted");
			restored.setBlockState(variant);
			require(restored.getBlockState() == variant, "An addon property combination could not be updated");
		}
		System.out.println("PASS: inherited addon APG construction/type validation, exact state/position retention, original block, invalid-state rejection and real NBT save/load; " + assertions + " assertions");
	}

	private static <T extends Block> T register(String name, java.util.function.Supplier<T> factory) {
		final Identifier id = id(name);
		return Registry.register(BuiltInRegistries.BLOCK, id, RegistrationContext.construct(id, factory));
	}

	private static void reopen(Registry<?> registry) throws Exception {
		final Field frozen = MappedRegistry.class.getDeclaredField("frozen");
		frozen.setAccessible(true);
		frozen.set(registry, false);
		final Field intrusive = MappedRegistry.class.getDeclaredField("unregisteredIntrusiveHolders");
		intrusive.setAccessible(true);
		intrusive.set(registry, new IdentityHashMap<>());
	}

	private static Identifier id(String name) {
		return Identifier.parse(name);
	}

	private static void finishRegistration(Registry<?> registry) throws Exception {
		final Field frozen = MappedRegistry.class.getDeclaredField("frozen");
		frozen.setAccessible(true);
		frozen.set(registry, true);
		final Field intrusive = MappedRegistry.class.getDeclaredField("unregisteredIntrusiveHolders");
		intrusive.setAccessible(true);
		require(((java.util.Map<?, ?>) intrusive.get(registry)).isEmpty(), "Fixture left unregistered intrusive holders");
		intrusive.set(registry, null);
	}

	private static void require(boolean condition, String message) {
		assertions++;
		if (!condition) throw new AssertionError(message);
	}

	private static void expectInvalid(Runnable action) {
		try {
			action.run();
			throw new AssertionError("Invalid block entity state was accepted");
		} catch (IllegalStateException expected) {
			require(expected.getMessage().contains("Invalid block entity"), "Wrong invalid-state failure: " + expected);
		}
	}

	public static class AddonGlass extends BlockAPGGlass {
		private static final IntegerProperty EXTRA = IntegerProperty.create("addon_value", 0, 2);

		@Override
		protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
			super.createBlockStateDefinition(builder);
			builder.add(EXTRA);
		}
	}

	public static final class OtherFactoryGlass extends AddonGlass {
		@Override
		public BlockEntityMapper createBlockEntity(BlockPos pos, BlockState state) {
			return super.createBlockEntity(pos, state);
		}
	}

	public static final class OtherEntryPointGlass extends AddonGlass {
		@Override
		public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
			return super.newBlockEntity(pos, state);
		}
	}

	public static final class OtherTypeGlass extends AddonGlass {
		@Override
		public BlockEntityType<? extends BlockEntityMapper> getType() {
			return BlockEntityTypes.APG_GLASS_TILE_ENTITY.get();
		}
	}

	public static final class GrandchildGlass extends AddonGlass {
	}

	public static final class DifferentStyleDoor extends BlockPSDDoor {
		public DifferentStyleDoor() {
			super(1);
		}
	}
}

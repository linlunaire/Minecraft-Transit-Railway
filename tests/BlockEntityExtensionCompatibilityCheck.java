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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.lang.reflect.Field;
import java.util.IdentityHashMap;

/** Exercises the actual factory/validation path used by FAWE when inspecting addon blocks. */
public final class BlockEntityExtensionCompatibilityCheck {

	private static int assertions;

	public static void main(String[] args) throws Exception {
		// Match the loader inventory boundary used by the existing NeoForge snapshot check.
		try {
			Class.forName("net.neoforged.fml.loading.LoadingModList")
					.getMethod("of", java.util.List.class, java.util.List.class, java.util.List.class, java.util.List.class, java.util.Map.class)
					.invoke(null, java.util.List.of(), java.util.List.of(), java.util.List.of(), java.util.List.of(), java.util.Map.of());
		} catch (ClassNotFoundException ignored) {
			// No NeoForge loader on the common/Fabric classpath.
		}
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		// Emulate loader registration only in this standalone process; no world is opened.
		reopen(BuiltInRegistries.BLOCK);
		reopen(BuiltInRegistries.BLOCK_ENTITY_TYPE);
		final BlockAPGGlass original = (BlockAPGGlass) mtr.Blocks.APG_GLASS.get();
		Registry.register(BuiltInRegistries.BLOCK, id("mtr:apg_glass"), original);
		final BlockEntityType<?> type = BlockEntityTypes.APG_GLASS_TILE_ENTITY.get();
		Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id("mtr:apg_glass"), type);
		final AddonGlass addon = register("jsblock:apg_glass_drl", new AddonGlass());
		final OtherFactoryGlass otherFactory = register("test:other_factory", new OtherFactoryGlass());
		final OtherEntryPointGlass otherEntryPoint = register("test:other_entry_point", new OtherEntryPointGlass());
		final OtherTypeGlass otherType = register("test:other_type", new OtherTypeGlass());
		final GrandchildGlass grandchild = register("test:grandchild", new GrandchildGlass());
		final BlockAPGGlass sameClass = register("test:same_class", new BlockAPGGlass());
		final Block originalDoor = mtr.Blocks.PSD_DOOR_1.get();
		Registry.register(BuiltInRegistries.BLOCK, id("mtr:psd_door"), originalDoor);
		final BlockEntityType<?> originalDoorType = BlockEntityTypes.PSD_DOOR_1_TILE_ENTITY.get();
		Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id("mtr:psd_door"), originalDoorType);
		final DifferentStyleDoor styleDoor = register("test:different_door_style", new DifferentStyleDoor());
		final java.util.List<BlockAPGGlass> actualAddons = new java.util.ArrayList<>();
		for (String addonClass : args) {
			actualAddons.add(register("test:actual_addon_" + actualAddons.size(), (BlockAPGGlass) Class.forName(addonClass).getConstructor().newInstance()));
		}
		finishRegistration(BuiltInRegistries.BLOCK);
		finishRegistration(BuiltInRegistries.BLOCK_ENTITY_TYPE);

		final BlockPos pos = BlockPos.ZERO;
		final BlockState state = addon.defaultBlockState().setValue(AddonGlass.EXTRA, 2).setValue(BlockAPGGlass.ARROW_DIRECTION, 3);
		final BlockEntity entity = addon.newBlockEntity(pos, state);
		require(entity instanceof BlockAPGGlass.TileEntityAPGGlass, "Inherited APG factory changed its entity class");
		require(entity.getType() == type, "Inherited APG factory changed its registered type");
		require(entity.getBlockState() == state && entity.getBlockPos().equals(pos), "Addon state/properties or position were substituted");
		require(type.isValid(state) && entity.isValidBlockState(state), "Inherited addon state is not valid");
		final BlockState changed = state.setValue(AddonGlass.EXTRA, 1);
		entity.setBlockState(changed);
		require(entity.getBlockState() == changed, "Updating addon state lost its identity");
		require(type.isValid(original.defaultBlockState()), "Original MTR block became invalid");
		require(original.newBlockEntity(pos, original.defaultBlockState()).getType() == type, "Original MTR constructor changed");
		require(type.isValid(grandchild.defaultBlockState()), "Unchanged second-level inheritance was rejected");
		require(originalDoorType.isValid(originalDoor.defaultBlockState()), "Original configurable door was rejected");
		require(!originalDoorType.isValid(styleDoor.defaultBlockState()), "Instance-dependent factories must not accept arbitrary subclasses");
		for (BlockState invalid : new BlockState[]{net.minecraft.world.level.block.Blocks.STONE.defaultBlockState(), otherFactory.defaultBlockState(), otherEntryPoint.defaultBlockState(), otherType.defaultBlockState(), sameClass.defaultBlockState()}) {
			require(!type.isValid(invalid), "Unrelated or factory-overriding block was accepted: " + invalid);
			expectInvalid(() -> entity.setBlockState(invalid));
			require(entity.getBlockState() == changed, "Rejected update replaced valid state");
			expectInvalid(() -> new BlockAPGGlass.TileEntityAPGGlass(pos, invalid));
		}

		final var registries = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
		final CompoundTag saved = entity.saveWithFullMetadata(registries);
		require(saved.getString("id").equals("mtr:apg_glass"), "Addon entity save ID changed");
		final Tag savedState = BlockState.CODEC.encodeStart(NbtOps.INSTANCE, changed).getOrThrow();
		require(((CompoundTag) savedState).getString("Name").equals("jsblock:apg_glass_drl"), "Addon block save ID changed");
		final BlockState restoredState = BlockState.CODEC.parse(NbtOps.INSTANCE, savedState).getOrThrow();
		require(restoredState == changed && restoredState.getValue(AddonGlass.EXTRA) == 1, "Addon properties changed during serialization");
		final BlockEntity restored = BlockEntity.loadStatic(pos, restoredState, saved, registries);
		require(restored instanceof BlockAPGGlass.TileEntityAPGGlass, "NBT loading dropped the inherited addon entity");
		require(restored.getType() == type && restored.getBlockState() == changed, "NBT loading substituted type/state");
		require(restored.saveWithFullMetadata(registries).equals(saved), "Metadata changed on save/load/save");
		for (BlockState variant : addon.getStateDefinition().getPossibleStates()) {
			require(type.isValid(variant), "Invalid addon property combination: " + variant);
			require(addon.newBlockEntity(pos, variant).getBlockState() == variant, "Addon property combination was substituted");
			restored.setBlockState(variant);
			require(restored.getBlockState() == variant, "Addon property combination could not be updated");
		}
		for (BlockAPGGlass actualAddon : actualAddons) {
			for (BlockState variant : actualAddon.getStateDefinition().getPossibleStates()) {
				final BlockEntity actualEntity = actualAddon.newBlockEntity(pos, variant);
				require(type.isValid(variant) && actualEntity.getType() == type && actualEntity.getBlockState() == variant, "Real addon constructor failed: " + variant);
				final BlockEntity actualRestored = BlockEntity.loadStatic(pos, variant, actualEntity.saveWithFullMetadata(registries), registries);
				require(actualRestored != null && actualRestored.getType() == type && actualRestored.getBlockState() == variant, "Real addon NBT round trip failed: " + variant);
			}
		}
		System.out.println("PASS: real 1.21.1 addon APG construction, state validation, NBT save/load and invalid-factory rejection; " + assertions + " assertions");
	}

	private static <T extends Block> T register(String name, T block) {
		return Registry.register(BuiltInRegistries.BLOCK, id(name), block);
	}

	private static ResourceLocation id(String name) {
		return ResourceLocation.parse(name);
	}

	private static void reopen(Registry<?> registry) throws Exception {
		final Field frozen = MappedRegistry.class.getDeclaredField("frozen");
		frozen.setAccessible(true);
		frozen.set(registry, false);
		final Field intrusive = MappedRegistry.class.getDeclaredField("unregisteredIntrusiveHolders");
		intrusive.setAccessible(true);
		intrusive.set(registry, new IdentityHashMap<>());
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

	private static void expectInvalid(Runnable action) {
		try {
			action.run();
			throw new AssertionError("Invalid block entity state was accepted");
		} catch (IllegalStateException expected) {
			require(expected.getMessage().contains("Invalid block entity"), "Wrong invalid-state failure: " + expected);
		}
	}

	private static void require(boolean condition, String message) {
		assertions++;
		if (!condition) throw new AssertionError(message);
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

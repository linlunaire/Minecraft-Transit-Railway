package mtr.block;

import mtr.BlockEntityTypes;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/** Actual door entities and loader snapshot serialization. Only the in-memory world boundary is replaced. */
public final class DoorSnapshotCompatibilityCheck {

	private static final BlockPos POS = new BlockPos(10, 70, -20);
	private static final String FIXTURE = Type.getInternalName(DoorSnapshotCompatibilityCheck.class);
	private static RegistryAccess registries;
	private static Level world;
	private static BlockState state;
	private static BlockPSDAPGDoorBase.TileEntityPSDAPGDoorBase door;
	private static Method snapshotFactory;
	private static int mutations;
	private static int updateFlags;
	private static int captureDepth;
	private static int assertions;
	private static boolean client;

	public static void main(String[] args) throws Exception {
		// NeoForge's feature flag bootstrap normally receives this list from the loader.
		try {
			// The standalone test has no launch service; supply only the FML environment queried by bootstrap.
			Class<?> loaderType = Class.forName("net.neoforged.fml.loading.FMLLoader");
			Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
			unsafeField.setAccessible(true);
			Object loader = ((Unsafe) unsafeField.get(null)).allocateInstance(loaderType);
			Field current = loaderType.getDeclaredField("current");
			current.setAccessible(true);
			((java.util.concurrent.atomic.AtomicReference) current.get(null)).set(loader);
			Field dist = loaderType.getDeclaredField("dist");
			dist.setAccessible(true);
			dist.set(loader, Class.forName("net.neoforged.api.distmarker.Dist").getField("CLIENT").get(null));
			Object modList = Class.forName("net.neoforged.fml.loading.LoadingModList")
					.getMethod("of", List.class, List.class, List.class, List.class, List.class, Map.class)
					.invoke(null, List.of(), List.of(), List.of(), List.of(), List.of(), Map.of());
			Field loadingMods = loaderType.getDeclaredField("loadingModList");
			loadingMods.setAccessible(true);
			loadingMods.set(loader, modList);
		} catch (ClassNotFoundException ignored) {
			// Fabric/vanilla bootstrap has no NeoForge loader.
		}
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		reopen(BuiltInRegistries.BLOCK);
		reopen(BuiltInRegistries.BLOCK_ENTITY_TYPE);
		BlockPSDAPGDoorBase[] blocks = {
				register("psd_door_1", mtr.Blocks.PSD_DOOR_1.get(), BlockEntityTypes.PSD_DOOR_1_TILE_ENTITY.get()),
				register("psd_door_2", mtr.Blocks.PSD_DOOR_2.get(), BlockEntityTypes.PSD_DOOR_2_TILE_ENTITY.get()),
				register("apg_door", mtr.Blocks.APG_DOOR.get(), BlockEntityTypes.APG_DOOR_TILE_ENTITY.get()),
				register("lift_door_even_1", mtr.Blocks.LIFT_DOOR_EVEN_1.get(), BlockEntityTypes.LIFT_DOOR_EVEN_1_TILE_ENTITY.get()),
				register("lift_door_odd_1", mtr.Blocks.LIFT_DOOR_ODD_1.get(), BlockEntityTypes.LIFT_DOOR_ODD_1_TILE_ENTITY.get())
		};
		finish(BuiltInRegistries.BLOCK);
		finish(BuiltInRegistries.BLOCK_ENTITY_TYPE);
		registries = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
		world = createWorld();
		try {
			snapshotFactory = Class.forName("net.neoforged.neoforge.common.util.BlockSnapshot")
					.getMethod("create", net.minecraft.resources.ResourceKey.class, LevelAccessor.class, BlockPos.class);
		} catch (ClassNotFoundException ignored) {
			// Fabric captures the same native NBT payload without NeoForge's wrapper.
		}
		for (BlockPSDAPGDoorBase block : blocks) {
			for (boolean isClient : new boolean[]{true, false}) {
				for (boolean legacyTemp : new boolean[]{true, false}) {
					reset(block, block.defaultBlockState().setValue(BlockPSDAPGDoorBase.TEMP, legacyTemp), isClient);
					CompoundTag first = capture();
					require(mutations == 0, "Snapshot serialization changed the world: " + state);
					require(first.equals(capture()), "Repeated snapshot serialization changed door data");
					CompoundTag update = door.getUpdateTag(registries);
					require(update.equals(door.getUpdateTag(registries)), "Repeated update tags changed door data");
					door.getUpdatePacket();
					require(mutations == 0, "Packet serialization changed the world");
					BlockEntity restored = BlockEntity.loadStatic(POS, state, first, registries);
					require(restored != null && restored.getClass() == door.getClass(), "Door failed native NBT round trip");
					require(first.equals(restored.saveWithFullMetadata(registries)), "Door data changed during save/load/save");
				}
			}
			checkLifecycle(block);
		}
		System.out.println("PASS: five door types, pure snapshot/save/update serialization, legacy migration, open/close and collision; " + assertions + " assertions; "
				+ (snapshotFactory == null ? "native NBT" : "real NeoForge BlockSnapshot.create"));
	}

	@SuppressWarnings("unchecked")
	private static void checkLifecycle(BlockPSDAPGDoorBase block) throws Exception {
		require(!block.defaultBlockState().getValue(BlockPSDAPGDoorBase.TEMP), "New doors still require save-time migration");
		for (BlockState variant : block.getStateDefinition().getPossibleStates()) {
			for (boolean isClient : new boolean[]{true, false}) {
				reset(block, variant, isClient);
				// Old saves can have temp=false in NBT but temp=true in the block state.
				// The persisted block state, not this obsolete marker, determines migration.
				CompoundTag legacy = new CompoundTag();
				legacy.putInt("open", 9);
				legacy.putBoolean("temp", false);
				door.readCompoundTag(legacy);
				require(mutations == 0 && door.isOpen(), "Loading legacy NBT mutated the world or lost door state");
				BlockEntityType<BlockPSDAPGDoorBase.TileEntityPSDAPGDoorBase> type =
						(BlockEntityType<BlockPSDAPGDoorBase.TileEntityPSDAPGDoorBase>) door.getType();
				BlockEntityTicker<BlockPSDAPGDoorBase.TileEntityPSDAPGDoorBase> ticker = block.getTicker(world, state, type);
				if (isClient || !variant.getValue(BlockPSDAPGDoorBase.TEMP)) {
					require(ticker == null, "Client/modern door installed an unnecessary ticker");
				} else {
					require(ticker != null, "Legacy door has no migration ticker");
					ticker.tick(world, POS, state, door);
					require(mutations == 1 && updateFlags == Block.UPDATE_CLIENTS, "Migration repeated or emitted neighbour updates");
					require(state == variant.setValue(BlockPSDAPGDoorBase.TEMP, false), "Migration changed facing/half/side/lock/end properties");
					require(block.getTicker(world, state, type) == null, "Migrated door kept its ticker");
					ticker.tick(world, POS, state, door);
					require(mutations == 1, "A stale ticker repeated migration");
				}
				int writesBefore = mutations;
				for (int opening : new int[]{17, 32, 0, 9, 0}) {
					door.setOpen(opening); // Deliberately skip 1: opening updates need not arrive one step at a time.
					require(door.isOpen() == (opening > 0), "Open/closed state changed");
					require(block.getCollisionShape(state, world, POS, CollisionContext.empty()).isEmpty() == (opening > 0), "Door collision no longer matches opening");
					CompoundTag saved = capture();
					BlockEntity restored = BlockEntity.loadStatic(POS, state, saved, registries);
					require(restored instanceof BlockPSDAPGDoorBase.TileEntityPSDAPGDoorBase
							&& saved.equals(restored.saveWithFullMetadata(registries)), "Opening did not survive native save/load");
					require(door.getUpdateTag(registries).equals(((BlockPSDAPGDoorBase.TileEntityPSDAPGDoorBase) restored).getUpdateTag(registries)), "Network update lost opening data");
				}
				require(mutations == writesBefore, "Opening or serializing a door modified block state");
				// Reproduce a normal neighbour-state change with the loader's pre-change snapshot.
				world.setBlockAndUpdate(POS, state.cycle(BlockPSDAPGDoorBase.END));
				require(mutations == writesBefore + 1, "External door update recursively modified the world");
			}
		}
	}

	private static void reset(BlockPSDAPGDoorBase block, BlockState initial, boolean isClient) {
		state = initial;
		client = isClient;
		mutations = captureDepth = 0;
		updateFlags = 0;
		door = (BlockPSDAPGDoorBase.TileEntityPSDAPGDoorBase) block.newBlockEntity(POS, state);
		door.setLevel(world);
	}

	private static CompoundTag capture() {
		// Abort before overflowing the test JVM; this is the recursive chain in the crash report.
		if (++captureDepth > 8) throw new AssertionError("Recursive door snapshot: writeCompoundTag -> setBlock -> BlockSnapshot.create -> writeCompoundTag");
		try {
			if (snapshotFactory == null) return door.saveWithFullMetadata(registries);
			Object snapshot = snapshotFactory.invoke(null, Level.OVERWORLD, world, POS);
			return (CompoundTag) snapshot.getClass().getMethod("getTag").invoke(snapshot);
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof Error error) throw error;
			throw new RuntimeException(e.getCause());
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		} finally {
			captureDepth--;
		}
	}

	// World boundary: before a mutation, capture the existing entity just as ClientLevel.setBlock does.
	public static boolean setBlock(BlockPos pos, BlockState replacement, int flags) {
		require(pos.equals(POS), "Unexpected position");
		mutations++;
		updateFlags = flags;
		capture();
		state = replacement;
		door.setBlockState(state);
		return true;
	}
	public static BlockState getBlockState(BlockPos pos) { return state; }
	public static BlockEntity getBlockEntity(BlockPos pos) { return door; }
	public static RegistryAccess registryAccess() { return registries; }
	public static boolean isClientSide() { return client; }
	public static void blockEntityChanged(BlockPos pos) { }
	public static void updateNeighbourForOutputSignal(BlockPos pos, Block block) { }

	private static Level createWorld() throws Exception {
		// Avoid a disk, server, window or chunk manager. Unused abstract methods fail loudly if reached.
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		writer.visit(Opcodes.V21, Opcodes.ACC_PUBLIC, "mtr/block/DoorSnapshotWorld", null, Type.getInternalName(Level.class), null);
		for (Method method : DoorSnapshotCompatibilityCheck.class.getDeclaredMethods()) {
			if (!java.lang.reflect.Modifier.isPublic(method.getModifiers()) || method.getName().equals("main")) continue;
			Level.class.getMethod(method.getName(), method.getParameterTypes());
			String descriptor = Type.getMethodDescriptor(method);
			var visitor = writer.visitMethod(Opcodes.ACC_PUBLIC, method.getName(), descriptor, null, null);
			visitor.visitCode();
			int slot = 1;
			for (Type argument : Type.getArgumentTypes(descriptor)) {
				visitor.visitVarInsn(argument.getOpcode(Opcodes.ILOAD), slot);
				slot += argument.getSize();
			}
			visitor.visitMethodInsn(Opcodes.INVOKESTATIC, FIXTURE, method.getName(), descriptor, false);
			visitor.visitInsn(Type.getReturnType(descriptor).getOpcode(Opcodes.IRETURN));
			visitor.visitMaxs(0, 0);
			visitor.visitEnd();
		}
		writer.visitEnd();
		Class<?> type = java.lang.invoke.MethodHandles.lookup().defineClass(writer.toByteArray());
		Field unsafe = Unsafe.class.getDeclaredField("theUnsafe");
		unsafe.setAccessible(true);
		return (Level) ((Unsafe) unsafe.get(null)).allocateInstance(type);
	}

	private static BlockPSDAPGDoorBase register(String name, Block block, BlockEntityType<?> type) {
		Identifier id = Identifier.fromNamespaceAndPath("mtr", name);
		Registry.register(BuiltInRegistries.BLOCK, id, block);
		Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, type);
		return (BlockPSDAPGDoorBase) block;
	}
	private static void reopen(Registry<?> registry) throws Exception {
		Field frozen = MappedRegistry.class.getDeclaredField("frozen");
		frozen.setAccessible(true);
		frozen.set(registry, false);
		Field intrusive = MappedRegistry.class.getDeclaredField("unregisteredIntrusiveHolders");
		intrusive.setAccessible(true);
		intrusive.set(registry, new IdentityHashMap<>());
	}
	private static void finish(Registry<?> registry) throws Exception {
		Field frozen = MappedRegistry.class.getDeclaredField("frozen");
		frozen.setAccessible(true);
		frozen.set(registry, true);
		Field intrusive = MappedRegistry.class.getDeclaredField("unregisteredIntrusiveHolders");
		intrusive.setAccessible(true);
		require(((Map<?, ?>) intrusive.get(registry)).isEmpty(), "Unregistered fixture holders");
		intrusive.set(registry, null);
	}
	private static void require(boolean condition, String message) {
		assertions++;
		if (!condition) throw new AssertionError(message);
	}
}

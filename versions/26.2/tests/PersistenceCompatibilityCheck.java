package mtr.mappings;

import com.mojang.serialization.Codec;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

/** Standalone compatibility check; run with Java 25 and the official 26.2 game libraries. */
public final class PersistenceCompatibilityCheck {

	public static void main(String[] args) throws Exception {
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		final HolderLookup.Provider registries = HolderLookup.Provider.create(Stream.empty());
		final CompoundTag legacy = new CompoundTag();
		legacy.putString("platform_name", "中環 Central");
		legacy.putLong("platform_id", Long.MAX_VALUE);
		legacy.putByteArray("raw_message_pack", new byte[]{0, 1, -1, 127, -128});
		legacy.putIntArray("selected_ids", new int[]{Integer.MIN_VALUE, 1, Integer.MAX_VALUE});
		legacy.putLongArray("train_ids", new long[]{Long.MIN_VALUE, Long.MAX_VALUE});
		final CompoundTag nested = new CompoundTag();
		nested.putBoolean("enabled", true);
		final ListTag signs = new ListTag();
		signs.add(StringTag.valueOf("出口 Exit"));
		nested.put("signs", signs);
		legacy.put("settings", nested);
		checkLegacyGetters(legacy);

		final TestBlockEntity entity = new TestBlockEntity();
		entity.loadAdditional(TagValueInput.create(ProblemReporter.DISCARDING, registries, legacy));
		require(legacy.equals(entity.tag), "Block entity read changed the legacy root keys");
		final TagValueOutput output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, registries);
		entity.saveAdditional(output);
		require(legacy.equals(output.buildResult()), "Block entity round trip changed keys, arrays or nested tags");
		entity.loadAdditional(TagValueInput.create(ProblemReporter.DISCARDING, registries, new CompoundTag()));
		require(entity.tag.isEmpty(), "New block entities must accept an empty compound");

		final Codec<TestSavedData> codec = CompoundTag.CODEC.xmap(tag -> {
			final TestSavedData data = new TestSavedData();
			data.load(tag);
			return data;
		}, data -> data.save(new CompoundTag(), registries));
		final TestSavedData data = codec.parse(NbtOps.INSTANCE, legacy).getOrThrow();
		require(legacy.equals(codec.encodeStart(NbtOps.INSTANCE, data).getOrThrow()), "Saved data codec changed the legacy payload");
		require(codec.parse(NbtOps.INSTANCE, StringTag.valueOf("invalid")).isError(), "Malformed saved data must fail decoding");
		final SavedDataType<TestSavedData> first = new SavedDataType<>(Identifier.parse("mtr:mtr_train_data"), TestSavedData::new, codec, DataFixTypes.SAVED_DATA_COMMAND_STORAGE);
		final SavedDataType<TestSavedData> second = new SavedDataType<>(Identifier.parse("mtr:mtr_train_data"), TestSavedData::new, codec, DataFixTypes.SAVED_DATA_COMMAND_STORAGE);
		require(first.equals(second) && first.hashCode() == second.hashCode(), "Repeated lookups must reuse the same saved data cache entry");

		final Path file = Files.createTempFile("mtr-26.2-legacy-nbt-", ".dat");
		try {
			final CompoundTag wrapper = new CompoundTag();
			wrapper.put("data", legacy);
			NbtIo.writeCompressed(wrapper, file);
			require(legacy.equals(NbtIo.readCompressed(file, NbtAccounter.unlimitedHeap()).getCompound("data").orElseThrow()), "Legacy compressed .dat read changed its payload");
		} finally {
			Files.delete(file);
		}
		System.out.println("PASS: legacy getter defaults/UUIDs, root NBT keys, arrays, nested tags, empty block entity, saved-data codec, corrupt payload rejection, cache identity and legacy .dat");
	}

	private static void checkLegacyGetters(CompoundTag data) {
		require(CompoundTagMapper.getLong(data, "platform_id") == Long.MAX_VALUE, "Long identifier was truncated");
		require(CompoundTagMapper.getString(data, "platform_name").equals("中環 Central"), "Unicode name was changed");
		require(Arrays.equals(CompoundTagMapper.getByteArray(data, "raw_message_pack"), new byte[]{0, 1, -1, 127, -128}), "MessagePack bytes were changed");
		require(Arrays.equals(CompoundTagMapper.getLongArray(data, "train_ids"), new long[]{Long.MIN_VALUE, Long.MAX_VALUE}), "Long array was changed");
		for (String key : new String[]{"missing", "settings"}) {
			require(CompoundTagMapper.getInt(data, key) == 0 && CompoundTagMapper.getLong(data, key) == 0L, "Missing/wrong-type integer default changed");
			require(CompoundTagMapper.getFloat(data, key) == 0F && CompoundTagMapper.getDouble(data, key) == 0D, "Missing/wrong-type floating default changed");
			require(!CompoundTagMapper.getBoolean(data, key) && CompoundTagMapper.getString(data, key).isEmpty(), "Missing/wrong-type boolean/string default changed");
			require(CompoundTagMapper.getByteArray(data, key).length == 0 && CompoundTagMapper.getLongArray(data, key).length == 0, "Missing/wrong-type array default changed");
		}
		final CompoundTag numbers = new CompoundTag();
		numbers.putInt("numeric", 42);
		require(CompoundTagMapper.getLong(numbers, "numeric") == 42L && CompoundTagMapper.getDouble(numbers, "numeric") == 42D, "Numeric tag coercion changed");
		require(CompoundTagMapper.getBoolean(numbers, "numeric"), "Numeric boolean coercion changed");
		final UUID uuid = UUID.fromString("01234567-89ab-cdef-fedc-ba9876543210");
		numbers.putIntArray("uuid", UUIDUtil.uuidToIntArray(uuid));
		require(uuid.equals(CompoundTagMapper.getUUID(numbers, "uuid")), "Legacy four-int UUID changed");
		numbers.putIntArray("uuid", new int[]{1, 2, 3});
		try {
			CompoundTagMapper.getUUID(numbers, "uuid");
			throw new AssertionError("Malformed UUID accepted");
		} catch (IllegalArgumentException expected) {
			// Preserve fail-fast behavior: corrupt UUIDs must not become a default ID.
		}
	}

	private static void require(boolean condition, String message) {
		if (!condition) {
			throw new AssertionError(message);
		}
	}

	private static final class TestBlockEntity extends BlockEntityMapper {
		private CompoundTag tag;

		private TestBlockEntity() {
			super(null, BlockPos.ZERO, null);
		}

		@Override
		public boolean isValidBlockState(BlockState state) {
			return true;
		}

		@Override
		public void readCompoundTag(CompoundTag tag) {
			this.tag = tag.copy();
		}

		@Override
		public void writeCompoundTag(CompoundTag tag) {
			tag.merge(this.tag);
		}
	}

	private static final class TestSavedData extends PersistentStateMapper {
		private CompoundTag tag;

		private TestSavedData() {
			super("mtr_train_data");
		}

		@Override
		public void load(CompoundTag tag) {
			this.tag = tag.copy();
		}

		@Override
		public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
			return tag.merge(this.tag);
		}
	}
}

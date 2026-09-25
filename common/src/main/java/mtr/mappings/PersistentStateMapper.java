package mtr.mappings;

import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.SavedDataStorage;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

public abstract class PersistentStateMapper extends SavedData {

	public PersistentStateMapper(String name) {
		super();
	}

	public abstract void load(CompoundTag compoundTag);

	public abstract CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider);

	protected static <T extends PersistentStateMapper> T getInstance(Level world, Supplier<T> supplier, String name) {
		if (!(world instanceof ServerLevel serverLevel)) {
			return null;
		}

		final Identifier id = Identifier.fromNamespaceAndPath("mtr", name);
		final Codec<T> codec = CompoundTag.CODEC.xmap(compoundTag -> {
			final T data = supplier.get();
			data.load(compoundTag);
			return data;
		}, data -> data.save(new CompoundTag(), serverLevel.registryAccess()));
		final SavedDataType<T> type = new SavedDataType<>(id, supplier, codec, DataFixTypes.SAVED_DATA_COMMAND_STORAGE);
		final SavedDataStorage storage = serverLevel.getDataStorage();
		final T existing = storage.get(type);
		if (existing != null) {
			return existing;
		}

		final Path worldPath = serverLevel.getServer().getWorldPath(LevelResource.ROOT);
		final Path dataFolder = DimensionType.getStorageFolder(serverLevel.dimension(), worldPath).resolve("data");
		final Path dataFile = id.withSuffix(".dat").resolveAgainst(dataFolder);
		if (Files.exists(dataFile)) {
			// SavedDataStorage logs decode failures and returns null. Do not replace a broken save with empty data.
			throw new IllegalStateException("Unable to load MTR saved data: " + dataFile);
		}

		final Path legacyDataFile = getLegacyDataFolder(serverLevel, worldPath).resolve(name + ".dat");
		final CompoundTag compoundTag;
		try {
			// 26.2 does not relocate mod-owned .dat files from the old dimension directories.
			// Keep the original file as a backup; SavedDataStorage writes the new namespaced location.
			compoundTag = Files.exists(legacyDataFile)
					? NbtIo.readCompressed(legacyDataFile, NbtAccounter.unlimitedHeap()).getCompound("data").orElseThrow(() -> new IOException("Missing data compound in " + legacyDataFile))
					: new CompoundTag();
		} catch (IOException e) {
			throw new UncheckedIOException("Unable to load legacy MTR saved data: " + legacyDataFile, e);
		}
		final T data = supplier.get();
		// RailwayData also loads its external MessagePack files from this callback, even without a .dat file.
		data.load(compoundTag);
		storage.set(type, data);
		return data;
	}

	private static Path getLegacyDataFolder(ServerLevel serverLevel, Path worldPath) {
		if (serverLevel.dimension().equals(Level.OVERWORLD)) {
			return worldPath.resolve("data");
		} else if (serverLevel.dimension().equals(Level.NETHER)) {
			return worldPath.resolve("DIM-1").resolve("data");
		} else if (serverLevel.dimension().equals(Level.END)) {
			return worldPath.resolve("DIM1").resolve("data");
		} else {
			return DimensionType.getStorageFolder(serverLevel.dimension(), worldPath).resolve("data");
		}
	}
}

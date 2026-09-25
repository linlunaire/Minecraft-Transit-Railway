package mtr.mappings;

import dev.architectury.utils.GameInstance;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;

/** Registry-aware cargo serialization, including both historical MTR envelopes. */
public final class TrainCargoMapper {

	private TrainCargoMapper() {
	}

	public static void load(CompoundTag tag, NonNullList<ItemStack> stacks) {
		load(tag, stacks, serverRegistries());
	}

	public static CompoundTag save(NonNullList<ItemStack> stacks) {
		return save(stacks, serverRegistries());
	}

	public static void load(CompoundTag tag, NonNullList<ItemStack> stacks, HolderLookup.Provider registries) {
		// MessagePack cargo contains the Items compound directly; legacy train NBT
		// nests it under cargo. Do not read an absent child and silently empty a train.
		final CompoundTag cargo = tag.contains("Items") ? tag : tag.contains("cargo")
				? tag.getCompound("cargo").orElseThrow(() -> new IllegalStateException("Train cargo must be a compound")) : new CompoundTag();
		final ProblemReporter.Collector problems = new ProblemReporter.Collector();
		final NonNullList<ItemStack> decoded = NonNullList.withSize(stacks.size(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(TagValueInput.create(problems, registries, cargo), decoded);
		check(problems);
		for (int i = 0; i < stacks.size(); i++) {
			stacks.set(i, decoded.get(i));
		}
	}

	public static CompoundTag save(NonNullList<ItemStack> stacks, HolderLookup.Provider registries) {
		final ProblemReporter.Collector problems = new ProblemReporter.Collector();
		final TagValueOutput output = TagValueOutput.createWithContext(problems, registries);
		ContainerHelper.saveAllItems(output, stacks);
		check(problems);
		return output.buildResult();
	}

	private static HolderLookup.Provider serverRegistries() {
		final MinecraftServer server = GameInstance.getServer();
		if (server == null) {
			throw new IllegalStateException("Train cargo serialization requires an active server registry lookup");
		}
		return server.registryAccess();
	}

	private static void check(ProblemReporter.Collector problems) {
		if (!problems.isEmpty()) {
			throw new IllegalStateException("Unable to serialize train cargo: " + problems.getReport());
		}
	}
}

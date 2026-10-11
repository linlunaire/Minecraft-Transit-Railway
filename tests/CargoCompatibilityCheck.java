package mtr.mappings;

import net.minecraft.SharedConstants;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.chat.Component;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public final class CargoCompatibilityCheck {

	public static void main(String[] args) throws Exception {
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		final HolderLookup.Provider registries = VanillaRegistries.createLookup();
		BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.build(registries).forEach(pending -> pending.apply());
		final NonNullList<ItemStack> original = NonNullList.withSize(4, ItemStack.EMPTY);
		original.set(1, new ItemStack(Items.DIAMOND, 37));
		original.get(1).set(DataComponents.CUSTOM_NAME, Component.literal("货物 Cargo"));
		final CompoundTag custom = new CompoundTag();
		custom.putLongArray("ids", new long[]{Long.MIN_VALUE, Long.MAX_VALUE});
		original.get(1).set(DataComponents.CUSTOM_DATA, CustomData.of(custom));
		original.set(3, new ItemStack(Items.IRON_INGOT, 64));
		final CompoundTag saved = TrainCargoMapper.save(original, registries);
		final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		NbtIo.write(saved, new DataOutputStream(bytes));
		final CompoundTag binary = NbtIo.read(new DataInputStream(new ByteArrayInputStream(bytes.toByteArray())));
		check(original, binary, registries);
		final CompoundTag legacyEnvelope = new CompoundTag();
		legacyEnvelope.put("cargo", binary);
		check(original, legacyEnvelope, registries);

		// The 1.21.1 layout used a byte Slot and a flat item id/count/components.
		final CompoundTag legacyItem = new CompoundTag();
		legacyItem.putByte("Slot", (byte) 2);
		legacyItem.putString("id", "minecraft:diamond");
		legacyItem.putInt("count", 19);
		final ListTag legacyItems = new ListTag();
		legacyItems.add(legacyItem);
		final CompoundTag legacyCargo = new CompoundTag();
		legacyCargo.put("Items", legacyItems);
		final NonNullList<ItemStack> expectedLegacy = NonNullList.withSize(4, ItemStack.EMPTY);
		expectedLegacy.set(2, new ItemStack(Items.DIAMOND, 19));
		check(expectedLegacy, legacyCargo, registries);

		legacyItem.putString("id", "mtr:missing_cargo_item");
		final NonNullList<ItemStack> unchanged = NonNullList.withSize(4, ItemStack.EMPTY);
		unchanged.set(0, new ItemStack(Items.GOLD_INGOT, 2));
		try {
			TrainCargoMapper.load(legacyCargo, unchanged, registries);
			throw new AssertionError("Unknown item was silently discarded");
		} catch (IllegalStateException expected) {
			if (!unchanged.get(0).is(Items.GOLD_INGOT) || unchanged.get(0).getCount() != 2) {
				throw new AssertionError("Failed decode mutated the target inventory");
			}
		}
		check(NonNullList.withSize(4, ItemStack.EMPTY), new CompoundTag(), registries);
		checkSavePublication();
		System.out.println("PASS: cargo slots/counts/components, binary NBT, root/nested envelopes, legacy byte Slot, empty cargo, unknown-item rejection and save-file replacement/failure preservation");
	}

	private static void checkSavePublication() throws Exception {
		final Path directory = Files.createTempDirectory("mtr-save-test-");
		final Path target = directory.resolve("record");
		final Path blocked = directory.resolve("blocked");
		final Path child = blocked.resolve("keep");
		try {
			SaveFileMapper.write(target, new byte[]{1, 2, 3, 4, 5});
			SaveFileMapper.write(target, new byte[]{9, 8});
			if (!Arrays.equals(Files.readAllBytes(target), new byte[]{9, 8})) {
				throw new AssertionError("Replacing a save retained trailing bytes");
			}
			Files.createDirectory(blocked);
			Files.write(child, new byte[]{7});
			try {
				SaveFileMapper.write(blocked, new byte[]{0});
				throw new AssertionError("Expected replacement of a nonempty directory to fail");
			} catch (IOException expected) {
				if (!Arrays.equals(Files.readAllBytes(child), new byte[]{7}) || !Arrays.equals(Files.readAllBytes(target), new byte[]{9, 8})) {
					throw new AssertionError("Failed publication changed existing data");
				}
			}
			try (var files = Files.list(directory)) {
				if (files.count() != 2) {
					throw new AssertionError("Failed publication leaked a temporary file");
				}
			}
		} finally {
			Files.deleteIfExists(child);
			Files.deleteIfExists(blocked);
			Files.deleteIfExists(target);
			Files.delete(directory);
		}
	}

	private static void check(NonNullList<ItemStack> expected, CompoundTag data, HolderLookup.Provider registries) {
		final NonNullList<ItemStack> actual = NonNullList.withSize(expected.size(), ItemStack.EMPTY);
		TrainCargoMapper.load(data, actual, registries);
		for (int i = 0; i < expected.size(); i++) {
			if (expected.get(i).getCount() != actual.get(i).getCount() || !ItemStack.isSameItemSameComponents(expected.get(i), actual.get(i))) {
				throw new AssertionError("Cargo changed at slot " + i + ": " + data);
			}
		}
	}
}

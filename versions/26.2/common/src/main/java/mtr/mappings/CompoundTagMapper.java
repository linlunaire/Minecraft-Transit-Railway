package mtr.mappings;

import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;

import java.util.UUID;
import java.util.Collection;

/** Preserve pre-Optional NBT getter defaults when reading existing MTR data. */
public final class CompoundTagMapper {

	private CompoundTagMapper() {
	}

	public static int getInt(CompoundTag tag, String key) {
		return tag.getIntOr(key, 0);
	}

	public static long getLong(CompoundTag tag, String key) {
		return tag.getLongOr(key, 0L);
	}

	public static float getFloat(CompoundTag tag, String key) {
		return tag.getFloatOr(key, 0F);
	}

	public static double getDouble(CompoundTag tag, String key) {
		return tag.getDoubleOr(key, 0D);
	}

	public static boolean getBoolean(CompoundTag tag, String key) {
		return tag.getBooleanOr(key, false);
	}

	public static String getString(CompoundTag tag, String key) {
		return tag.getStringOr(key, "");
	}

	public static byte[] getByteArray(CompoundTag tag, String key) {
		return tag.getByteArray(key).orElseGet(() -> new byte[0]);
	}

	public static long[] getLongArray(CompoundTag tag, String key) {
		return tag.getLongArray(key).orElseGet(() -> new long[0]);
	}

	public static void putLongArray(CompoundTag tag, String key, Collection<Long> values) {
		tag.putLongArray(key, values.stream().mapToLong(Long::longValue).toArray());
	}

	public static UUID getUUID(CompoundTag tag, String key) {
		final int[] data = tag.getIntArray(key).orElseThrow(() -> new IllegalArgumentException("Missing UUID: " + key));
		if (data.length != 4) {
			throw new IllegalArgumentException("UUID must contain four integers: " + key);
		}
		return UUIDUtil.uuidFromIntArray(data);
	}
}

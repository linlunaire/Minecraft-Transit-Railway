package dev.architectury.platform;

import java.util.HashSet;
import java.util.Set;

/** Test-only loader inventory; never included in the mod's sources or JAR. */
public final class Platform {
	public static final Set<String> MODS = new HashSet<>();

	public static boolean isModLoaded(String id) {
		return MODS.contains(id);
	}
}

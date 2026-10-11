package mtr.servlet;

import dev.architectury.platform.Platform;

/** Optional map presence, including enabled Bukkit plugins on hybrid servers. */
public final class WebMapSupport {

	private WebMapSupport() {
	}

	public static boolean isAvailable() {
		return isLoaded("dynmap") || isLoaded("bluemap") || isLoaded("squaremap");
	}

	public static boolean isLoaded(String modId) {
		final String pluginName = switch (modId) {
			case "dynmap" -> "dynmap";
			case "bluemap" -> "BlueMap";
			case "squaremap" -> "squaremap";
			default -> null;
		};
		return pluginName != null && (Platform.isModLoaded(modId) || isPluginEnabled(pluginName));
	}

	private static boolean isPluginEnabled(String name) {
		try {
			// Do not link Bukkit or map APIs into normal Fabric/NeoForge installations.
			final ClassLoader loader = WebMapSupport.class.getClassLoader();
			final Class<?> bukkit = Class.forName("org.bukkit.Bukkit", false, loader);
			final Object manager = bukkit.getMethod("getPluginManager").invoke(null);
			if (manager == null) return false;
			final Class<?> pluginManager = Class.forName("org.bukkit.plugin.PluginManager", false, loader);
			return Boolean.TRUE.equals(pluginManager.getMethod("isPluginEnabled", String.class).invoke(manager, name));
		} catch (ReflectiveOperationException | LinkageError | SecurityException ignored) {
			return false;
		}
	}
}

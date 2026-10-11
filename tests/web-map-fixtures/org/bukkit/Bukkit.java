package org.bukkit;

import org.bukkit.plugin.PluginManager;
import java.util.HashSet;
import java.util.Set;

/** Test-only enabled-plugin inventory; present APIs do not imply an enabled map plugin. */
public final class Bukkit {
	public static final Set<String> ENABLED_PLUGINS = new HashSet<>();
	public static boolean ready = true;

	public static PluginManager getPluginManager() {
		return ready ? ENABLED_PLUGINS::contains : null;
	}
}

package org.bukkit.plugin;

/** Minimal reflective boundary for the hybrid-server detection regression. */
public interface PluginManager {
	boolean isPluginEnabled(String name);
}

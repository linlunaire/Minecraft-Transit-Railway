package mtr.servlet

import dev.architectury.platform.Platform

/** Optional map presence, including enabled Bukkit plugins on hybrid servers. */
object WebMapSupport {
    @JvmStatic
    fun isAvailable(): Boolean = isLoaded("dynmap") || isLoaded("bluemap") || isLoaded("squaremap")

    @JvmStatic
    fun isLoaded(modId: String): Boolean {
        val pluginName = when (modId) {
            "dynmap" -> "dynmap"
            "bluemap" -> "BlueMap"
            "squaremap" -> "squaremap"
            else -> return false
        }
        return Platform.isModLoaded(modId) || isPluginEnabled(pluginName)
    }

    private fun isPluginEnabled(name: String): Boolean {
        return try {
            // Do not link Bukkit or map APIs into normal Fabric/NeoForge installations.
            val loader = WebMapSupport::class.java.classLoader
            val bukkit = Class.forName("org.bukkit.Bukkit", false, loader)
            val manager = bukkit.getMethod("getPluginManager").invoke(null) ?: return false
            val pluginManager = Class.forName("org.bukkit.plugin.PluginManager", false, loader)
            pluginManager.getMethod("isPluginEnabled", String::class.java).invoke(manager, name) == true
        } catch (_: ReflectiveOperationException) {
            false
        } catch (_: LinkageError) {
            false
        } catch (_: SecurityException) {
            false
        }
    }
}

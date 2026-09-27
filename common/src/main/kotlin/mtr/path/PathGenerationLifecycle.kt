package mtr.path

import dev.architectury.event.events.common.LifecycleEvent
import mtr.data.RailwayDataPathGenerationModule
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import java.lang.ref.WeakReference
import java.util.WeakHashMap

/** Tracks only instantiated world modules; shutdown must never load or create saved data. */
object PathGenerationLifecycle {
    // Both keys and module values are weak: a module itself owns its Level through RailwayData.
    private class WorldTasks(var module: WeakReference<RailwayDataPathGenerationModule>?, var closed: Boolean = false)
    private val worlds = WeakHashMap<ServerLevel, WorldTasks>()
    private val stoppedServers = WeakHashMap<MinecraftServer, Boolean>()
    private var installed = false

    @JvmStatic
    @Synchronized
    fun install() {
        if (installed) return
        LifecycleEvent.SERVER_LEVEL_UNLOAD.register(::unload)
        LifecycleEvent.SERVER_STOPPING.register(::stop)
        installed = true
    }

    /** Construction and lifecycle events are on the server owner thread. */
    internal fun track(world: Level?, module: RailwayDataPathGenerationModule) {
        if (world !is ServerLevel) return
        val state = worlds.getOrPut(world) { WorldTasks(null) }
        if (state.closed || stoppedServers.containsKey(world.server)) {
            state.closed = true
            module.close()
        } else {
            state.module?.get()?.close()
            state.module = WeakReference(module)
        }
    }

    private fun unload(world: ServerLevel) {
        val state = worlds.getOrPut(world) { WorldTasks(null) }
        state.closed = true
        val module = state.module?.get()
        state.module = null
        module?.close()
    }

    private fun stop(server: MinecraftServer) {
        stoppedServers[server] = true
        // Snapshot before closing: no data lookup, disk I/O, or iteration over a changing registry.
        worlds.keys.filter { it.server === server }.forEach(::unload)
    }
}

package mtr.data

import mtr.packet.PacketTrainDataGuiServer
import mtr.path.PathGenerationTask
import mtr.path.PathGenerationLifecycle
import net.minecraft.core.BlockPos
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.Level

@JvmSuppressWildcards
open class RailwayDataPathGenerationModule(
    railwayData: RailwayData?, world: Level?, rails: MutableMap<BlockPos, MutableMap<BlockPos, Rail>>?
) : RailwayDataModuleBase(railwayData, world, rails) {
    private val generatingPathThreads = HashMap<Long, Thread?>()
    private val requests = HashMap<Long, PathGenerationTask.Request>()
    private val workers = HashSet<Thread>()
    private var closed = false

    init { PathGenerationLifecycle.track(world, this) }

    /** Terminal, owner-thread close. Never join workers while the server is unloading a world. */
    fun close() {
        if (closed) return
        closed = true
        requests.values.forEach { it.cancel() }
        requests.clear()
        workers.forEach { it.interrupt() }
        workers.clear()
        generatingPathThreads.clear()
    }

    open fun generatePath(minecraftServer: MinecraftServer?, depotId: Long) {
        if (closed) return
        workers.removeIf { it.state == Thread.State.TERMINATED }
        generatingPathThreads.keys.removeIf { !generatingPathThreads[it]!!.isAlive }
        requests.remove(depotId)?.cancel()
        val depot = railwayData!!.dataCache.depotIdMap[depotId]
        if (depot != null) {
            if (generatingPathThreads.containsKey(depotId)) {
                generatingPathThreads[depotId]!!.interrupt()
                System.out.println("Restarting path generation" + if (depot.name!!.isEmpty()) "" else " for " + depot.name)
            } else {
                System.out.println("Starting path generation" + if (depot.name!!.isEmpty()) "" else " for " + depot.name)
            }
            val request = PathGenerationTask.Request(depot)
            requests[depotId] = request
            try {
                depot.generateMainRoute(minecraftServer, world, railwayData.dataCache, rails, railwayData.sidings) { thread: Thread? ->
                    PathGenerationTask.register(thread, request)
                    if (closed) {
                        request.cancel()
                        thread?.interrupt()
                    } else {
                        if (thread != null) workers.add(thread)
                        generatingPathThreads[depotId] = thread
                    }
                }
            } catch (error: Exception) {
                request.cancel()
                throw error
            }
            if (!closed) railwayData.resetTrainDelays(depot)
        } else {
            generatingPathThreads.remove(depotId)?.interrupt()
            PacketTrainDataGuiServer.generatePathS2C(world, depotId, 0)
            System.out.println("Failed to generate path, depot is null")
        }
    }
}

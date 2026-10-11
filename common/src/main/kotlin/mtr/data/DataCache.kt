package mtr.data

import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
import net.minecraft.core.BlockPos
import java.util.function.Function

@JvmSuppressWildcards
open class DataCache(
    @JvmField protected val stations: MutableSet<Station?>?,
    @JvmField protected val platforms: MutableSet<Platform?>?,
    @JvmField protected val sidings: MutableSet<Siding?>?,
    @JvmField protected val routes: MutableSet<Route?>?,
    @JvmField protected val depots: MutableSet<Depot?>?,
    private val liftsServer: MutableSet<LiftServer?>?
) {
    private var lastRefreshedTime = 0L

    @JvmField val stationIdMap: MutableMap<Long?, Station?> = HashMap()
    @JvmField val platformIdMap: MutableMap<Long?, Platform?> = HashMap()
    @JvmField val sidingIdMap: MutableMap<Long?, Siding?> = HashMap()
    @JvmField val routeIdMap: MutableMap<Long?, Route?> = HashMap()
    @JvmField val depotIdMap: MutableMap<Long?, Depot?> = HashMap()
    @JvmField val liftsServerIdMap: MutableMap<Long?, LiftServer?> = HashMap()
    @JvmField val platformIdToStation: MutableMap<Long?, Station?> = HashMap()
    @JvmField val sidingIdToDepot: MutableMap<Long?, Depot?> = HashMap()
    @JvmField val routeIdToOneDepot: MutableMap<Long?, Depot?> = HashMap()
    @JvmField val stationIdToConnectingStations: MutableMap<Station?, MutableSet<Station?>?> = HashMap()
    @JvmField val blockPosToStation: MutableMap<BlockPos?, Station?> = HashMap()
    @JvmField val blockPosToPlatformId = Long2LongOpenHashMap()
    @JvmField val platformConnections = Long2ObjectOpenHashMap<Long2ObjectOpenHashMap<RailwayDataRouteFinderModule.ConnectionDetails>>()

    fun sync() {
        try {
            mapIds(stationIdMap, stations)
            mapIds(platformIdMap, platforms)
            mapIds(sidingIdMap, sidings)
            mapIds(routeIdMap, routes)
            mapIds(depotIdMap, depots)
            mapIds(liftsServerIdMap, liftsServer)
            routeIdToOneDepot.clear()
            routes!!.forEach { route -> route!!.platformIds.removeIf { !platformIdMap.containsKey(it!!.platformId) } }
            depots!!.forEach { depot ->
                depot!!.routeIds.removeIf { routeIdMap[it] == null }
                depot.routeIds.forEach { routeIdToOneDepot[it] = depot }
            }
            platformConnections.clear()
            routes.forEach { route ->
                val depot = routeIdToOneDepot[route!!.id]
                if (depot != null) {
                    for (i in 1 until route.platformIds.size) {
                        val previousId = route.platformIds[i - 1]!!.platformId
                        val currentId = route.platformIds[i]!!.platformId
                        val previous = platformIdMap[previousId]
                        val current = platformIdMap[currentId]
                        if (previous != null && current != null) {
                            val duration = tryGet(depot.platformTimes, previousId, currentId, 0F)!!
                            if (duration > 0) {
                                val currentPos = current.getMidPos().asLong()
                                put(platformConnections, previous.getMidPos().asLong(), currentPos) { old ->
                                    val value = Math.round(duration)
                                    val details = old ?: RailwayDataRouteFinderModule.ConnectionDetails(previous)
                                    details.addDurationInfo(route.id, value)
                                    details
                                }
                                if (i == route.platformIds.size - 1 && !platformConnections.containsKey(currentPos)) {
                                    platformConnections.put(currentPos, Long2ObjectOpenHashMap())
                                }
                            }
                        }
                    }
                }
            }
            stationIdToConnectingStations.clear()
            stations!!.forEach { station1 ->
                stationIdToConnectingStations[station1] = HashSet()
                stations.forEach { station2 ->
                    if (station1 !== station2 && station1!!.intersecting(station2)) stationIdToConnectingStations[station1]!!.add(station2)
                }
            }
            mapSavedRailIdToStation(platformIdToStation, platforms, stations)
            mapSavedRailIdToStation(sidingIdToDepot, sidings, depots)
            blockPosToPlatformId.clear()
            blockPosToStation.clear()
            syncAdditional()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        lastRefreshedTime = System.currentTimeMillis()
    }

    open fun needsRefresh(cachedRefreshTime: Long): Boolean = lastRefreshedTime > cachedRefreshTime
    protected open fun syncAdditional() {}

    companion object {
        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun <T, U> tryGet(map: Map<T, Map<T, U>?>?, key1: T?, key2: T?, defaultValue: U): U =
            tryGet(map, key1, key2) ?: defaultValue

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun <T, U> tryGet(map: Map<T, Map<T, U>?>?, key1: T?, key2: T?): U? = map!![key1]?.get(key2)

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun <U> tryGet(map: Long2ObjectOpenHashMap<Long2ObjectOpenHashMap<U>>?, key1: Long, key2: Long): U? =
            map!!.get(key1)?.get(key2)

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun tryGet2(map: Long2ObjectOpenHashMap<Long2IntOpenHashMap>?, key1: Long, key2: Long, defaultValue: Int): Int =
            map!!.get(key1)?.getOrDefault(key2, defaultValue) ?: defaultValue

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun <U> put(map: Long2ObjectOpenHashMap<Long2ObjectOpenHashMap<U>>?, key1: Long, key2: Long, putValue: Function<U?, U>?) {
            val old = map!!.get(key1)
            val inner = old ?: Long2ObjectOpenHashMap<U>().also { map.put(key1, it) }
            inner.put(key2, putValue!!.apply(inner.get(key2)))
        }

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun put2(map: Long2ObjectOpenHashMap<Long2IntOpenHashMap>?, key1: Long, key2: Long, putValue: Function<Int, Int?>?) {
            val old = map!!.get(key1)
            val inner = old ?: Long2IntOpenHashMap().also { map.put(key1, it) }
            inner.put(key2, putValue!!.apply(inner.get(key2))!!)
        }

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        protected open fun <U : NameColorDataBase?> mapIds(map: MutableMap<Long?, U>?, source: MutableSet<U>?) {
            map!!.clear()
            source!!.forEach { map[it!!.id] = it }
        }

        private fun <U : SavedRailBase?, V : AreaBase?> mapSavedRailIdToStation(map: MutableMap<Long?, V>, savedRails: MutableSet<U>?, areas: MutableSet<V>?) {
            map.clear()
            savedRails!!.forEach { savedRail ->
                val pos = savedRail!!.getMidPos()
                for (area in areas!!) {
                    if (area!!.isTransportMode(savedRail.transportMode) && area.inArea(pos.x, pos.z)) {
                        map[savedRail.id] = area
                        break
                    }
                }
            }
        }
    }
}

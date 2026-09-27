package mtr.data

import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
import it.unimi.dsi.fastutil.longs.LongAVLTreeSet
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import java.util.Random
import java.util.function.BiConsumer

@JvmSuppressWildcards
open class RailwayDataRouteFinderModule(
    railwayData: RailwayData?, world: Level?, rails: MutableMap<BlockPos, MutableMap<BlockPos, Rail>>?
) : RailwayDataModuleBase(railwayData, world, rails) {
    private var connectionDensityOld = Long2ObjectOpenHashMap<Long2IntOpenHashMap>()
    private var connectionDensity = Long2ObjectOpenHashMap<Long2IntOpenHashMap>()
    private var startPos: BlockPos? = null
    private var endPos: BlockPos? = null
    private var currentRouteFinderRequest: RouteFinderRequest? = null
    private var totalTime = 0
    private var tempDataDuration = 0
    private var count = 0
    private var startMillis = 0L
    private var tickStage = TickStage.GET_POS
    private val globalBlacklist = Long2IntOpenHashMap()
    private val localBlacklist = Long2IntOpenHashMap()
    private val tempData = ArrayList<RouteFinderData>()
    private val platformPositions = LongAVLTreeSet()
    private val data = ArrayList<RouteFinderData>()
    private val routeFinderQueue = ArrayList<RouteFinderRequest>()

    open fun tick() {
        val platformCount = railwayData!!.dataCache.platformConnections.size
        if (platformCount < 4) return
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < (currentRouteFinderRequest?.maxTickTime ?: 2)) {
            when (tickStage) {
                TickStage.GET_POS -> {
                    if (routeFinderQueue.isEmpty()) {
                        val random = Random()
                        var shortestDistance = Int.MAX_VALUE
                        var tempStartPos: BlockPos? = null
                        var tempEndPos: BlockPos? = null
                        currentRouteFinderRequest = null
                        // Preserve map iteration order without boxing every packed block position.
                        val platformConnectionKeys = railwayData.dataCache.platformConnections.keys.toLongArray()
                        for (i in 0..minOf(200, platformCount * platformCount / 10000)) {
                            while (true) {
                                val startIndex = random.nextInt(platformCount)
                                val endIndex = random.nextInt(platformCount)
                                if (startIndex != endIndex) {
                                    val candidateStart = BlockPos.of(platformConnectionKeys[startIndex])
                                    val candidateEnd = BlockPos.of(platformConnectionKeys[endIndex])
                                    val distance = candidateStart.distManhattan(candidateEnd)
                                    if (distance < shortestDistance) {
                                        shortestDistance = distance
                                        tempStartPos = candidateStart
                                        tempEndPos = candidateEnd
                                    }
                                    break
                                }
                            }
                        }
                        startPos = tempStartPos
                        endPos = tempEndPos
                    } else {
                        val request = routeFinderQueue.removeAt(0)
                        currentRouteFinderRequest = request
                        startPos = request.startPos
                        endPos = request.endPos
                    }
                    globalBlacklist.clear()
                    startMillis = System.currentTimeMillis()
                    totalTime = Int.MAX_VALUE
                    tickStage = TickStage.START_FIND_ROUTE
                }
                TickStage.START_FIND_ROUTE -> {
                    tempData.clear()
                    tempDataDuration = 0
                    platformPositions.clear()
                    platformPositions.addAll(railwayData.dataCache.platformConnections.keys)
                    platformPositions.add(endPos!!.asLong())
                    localBlacklist.clear()
                    tickStage = TickStage.FIND_ROUTE
                }
                TickStage.FIND_ROUTE -> {
                    if (startPos == null || endPos == null) tickStage = TickStage.GET_POS
                    else if (findRoutePart()) tickStage = TickStage.END_FIND_ROUTE
                }
                TickStage.END_FIND_ROUTE -> {
                    if (tempData.isEmpty()) {
                        for (i in 0 until data.size - 1) {
                            DataCache.put2(connectionDensity, data[i].pos!!.asLong(), data[i + 1].pos!!.asLong()) { old -> old + 1 }
                        }
                        count++
                        if (count >= getMaxCount()) {
                            connectionDensityOld = connectionDensity
                            connectionDensity = Long2ObjectOpenHashMap()
                            count = 0
                        }
                        val request = currentRouteFinderRequest
                        if (request != null) {
                            val newDataList = ArrayList<RouteFinderData>()
                            for (part in data) RouteFinderData.append(newDataList, part, railwayData)
                            request.callback!!.accept(newDataList, (System.currentTimeMillis() - startMillis).toInt())
                        }
                        tickStage = TickStage.GET_POS
                    } else {
                        val elapsedTime = tempDataDuration
                        if (elapsedTime > 0 && elapsedTime < totalTime) {
                            totalTime = elapsedTime
                            data.clear()
                            data.addAll(tempData)
                        }
                        tickStage = TickStage.START_FIND_ROUTE
                    }
                }
            }
        }
    }

    open fun findRoute(posStart: BlockPos?, posEnd: BlockPos?, maxTickTime: Int, callback: BiConsumer<MutableList<RouteFinderData>, Int>?): Boolean {
        if (routeFinderQueue.size >= MAX_REQUESTS) return false
        routeFinderQueue.add(RouteFinderRequest(posStart, posEnd, maxTickTime, callback))
        tickStage = TickStage.GET_POS
        return true
    }

    open fun getConnectionDensity(posStart: BlockPos?, posEnd: BlockPos?): Int =
        DataCache.tryGet2(connectionDensityOld, posStart!!.asLong(), posEnd!!.asLong(),
            if (count == 0) 0 else DataCache.tryGet2(connectionDensity, posStart.asLong(), posEnd.asLong(), 0) * getMaxCount() / count)

    private fun findRoutePart(): Boolean {
        val elapsedTime = tempDataDuration
        val previous = (if (tempData.isEmpty()) startPos else tempData[tempData.size - 1].pos)!!
        val previousLong = previous.asLong()
        val previousDistanceToEnd = previous.distManhattan(endPos!!)
        var bestPosition: BlockPos? = null
        var bestIncrease = -Float.MAX_VALUE
        var bestDuration = 0
        var bestRouteId = 0L
        var bestWaitingTime = 0

        val positions = platformPositions.iterator()
        while (positions.hasNext()) {
            val positionLong = positions.nextLong()
            val details = DataCache.tryGet(railwayData!!.dataCache.platformConnections, previousLong, positionLong)
            val position = BlockPos.of(positionLong)
            var duration = previous.distManhattan(position) * WALKING_SPEED_TICKS_PER_METER
            var routeId = 0L
            var waitingTime = 0
            if (details != null && verifyTime(positionLong, elapsedTime + details.shortestDuration)) {
                val schedules = railwayData.getSchedulesAtPlatform(details.platformStart!!.id)
                if (schedules != null) {
                    var lastDeparture = Int.MIN_VALUE
                    for (schedule in schedules) {
                        if (details.durationInfo.containsKey(schedule.routeId)) {
                            val delay = ((schedule.arrivalMillis - startMillis) / Depot.MILLIS_PER_TICK - elapsedTime).toInt()
                            val newDuration = details.durationInfo[schedule.routeId]!! + delay
                            lastDeparture = maxOf(lastDeparture, delay)
                            if (delay > -100 && newDuration < duration) {
                                duration = newDuration
                                routeId = schedule.routeId
                                waitingTime = maxOf(0, delay)
                                lastDeparture = 0
                            }
                        }
                    }
                    if (lastDeparture > Int.MIN_VALUE && lastDeparture < 0) {
                        for ((id, scheduledDuration) in details.durationInfo) {
                            val depot = railwayData.dataCache.routeIdToOneDepot[id]
                            if (depot != null) {
                                val delay = depot.getMillisUntilDeploy(1, -lastDeparture * Depot.MILLIS_PER_TICK - 100) / Depot.MILLIS_PER_TICK
                                val newDuration = scheduledDuration + delay
                                if (delay >= 0 && newDuration < duration) {
                                    duration = newDuration
                                    routeId = id
                                    waitingTime = maxOf(0, delay - 100)
                                }
                            }
                        }
                    }
                }
            }
            if (verifyTime(positionLong, elapsedTime + duration)) {
                val increase = (previousDistanceToEnd - position.distManhattan(endPos!!)).toFloat() / duration
                globalBlacklist.put(positionLong, elapsedTime + duration)
                if (increase > bestIncrease) {
                    bestPosition = position
                    bestIncrease = increase
                    bestDuration = duration
                    bestRouteId = routeId
                    bestWaitingTime = waitingTime
                }
            }
        }
        if (bestPosition == null || bestDuration == 0) {
            if (tempData.isNotEmpty()) tempDataDuration -= tempData.removeAt(tempData.size - 1).duration
            else return true
        } else {
            localBlacklist.put(bestPosition.asLong(), elapsedTime + bestDuration)
            tempData.add(RouteFinderData.create(bestPosition, bestDuration, bestRouteId, bestWaitingTime))
            tempDataDuration += bestDuration
        }
        return tempData.isNotEmpty() && tempData[tempData.size - 1].pos!!.equals(endPos)
    }

    private fun verifyTime(position: Long, time: Int): Boolean = time < totalTime &&
        compareBlacklist(localBlacklist, position, time, false) && compareBlacklist(globalBlacklist, position, time, true)

    private fun getMaxCount(): Int = railwayData!!.dataCache.platformConnections.size * 100

    open class ConnectionDetails(@get:JvmSynthetic internal val platformStart: Platform?) {
        @get:JvmSynthetic internal var shortestDuration = Int.MAX_VALUE
            private set
        @get:JvmSynthetic internal val durationInfo: MutableMap<Long, Int> = HashMap()

        open fun addDurationInfo(routeId: Long, duration: Int) {
            durationInfo[routeId] = duration
            shortestDuration = minOf(duration, shortestDuration)
        }
    }

    open class RouteFinderData private constructor(
        @JvmField val pos: BlockPos?, @JvmField val duration: Int,
        @JvmField val routeId: Long, @JvmField val waitingTime: Int
    ) {
        @JvmField val stationIds: MutableList<Long?> = ArrayList()

        companion object {
            @JvmSynthetic internal fun create(pos: BlockPos?, duration: Int, routeId: Long, waitingTime: Int): RouteFinderData =
                RouteFinderData(pos, duration, routeId, waitingTime)

            @JvmSynthetic internal fun append(output: MutableList<RouteFinderData>, next: RouteFinderData, railway: RailwayData) {
                val station = RailwayData.getStation(railway.stations, railway.dataCache, next.pos)
                val stationId = station?.id ?: 0L
                if (output.isEmpty()) {
                    next.stationIds.add(stationId)
                    output.add(next)
                } else {
                    val lastIndex = output.size - 1
                    val last = output[lastIndex]
                    if (last.routeId == next.routeId) {
                        output.removeAt(lastIndex)
                        val combined = RouteFinderData(next.pos, last.duration + next.duration, last.routeId, last.waitingTime)
                        combined.stationIds.addAll(last.stationIds)
                        combined.stationIds.add(stationId)
                        output.add(combined)
                    } else {
                        next.stationIds.add(stationId)
                        output.add(next)
                    }
                }
            }
        }
    }

    private class RouteFinderRequest(val startPos: BlockPos?, val endPos: BlockPos?, maxTickTime: Int,
                                     val callback: BiConsumer<MutableList<RouteFinderData>, Int>?) {
        val maxTickTime = maxOf(2, maxTickTime)
    }

    private enum class TickStage { GET_POS, START_FIND_ROUTE, FIND_ROUTE, END_FIND_ROUTE }

    companion object {
        private const val MAX_REQUESTS = 10
        private const val WALKING_SPEED_TICKS_PER_METER = 5

        private fun compareBlacklist(blacklist: Long2IntOpenHashMap, position: Long, time: Int, inclusive: Boolean): Boolean =
            !blacklist.containsKey(position) || (if (inclusive) time <= blacklist.get(position) else time < blacklist.get(position))
    }
}

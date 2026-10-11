package mtr.servlet

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import mtr.data.*
import javax.servlet.AsyncContext
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.*

open class ArrivalsServletHandler : HttpServlet() {
    protected override fun doGet(request: HttpServletRequest?, response: HttpServletResponse?) {
        var stationId = 0L
        var worldIndex = 0L
        try {
            stationId = java.lang.Long.parseLong(request!!.getParameter("stationId"))
        } catch (ignored: Exception) {
        }
        try {
            worldIndex = Integer.parseInt(request!!.getParameter("worldIndex")).toLong()
        } catch (ignored: Exception) {
        }

        val asyncContext: AsyncContext? = request!!.startAsync()
        val stationIdFinal = stationId
        val worldIndexFinal = worldIndex
        Webserver.callback!!.accept {
            val dataArray = JsonArray()
            var worldIndexCounter = 0
            Webserver.getWorlds!!.get()!!.forEach(java.util.function.Consumer { world ->
                if (worldIndexCounter.toLong() == worldIndexFinal) {
                    val railwayData = RailwayData.getInstance(world)
                    if (railwayData != null) {
                        val schedulesForStation = HashMap<Long, MutableList<ScheduleEntry>>()
                        railwayData.getSchedulesForStation(schedulesForStation, stationIdFinal)
                        val scheduleEntries = ArrayList<ScheduleEntry>()
                        schedulesForStation.values.forEach(java.util.function.Consumer { scheduleEntries.addAll(it) })
                        Collections.sort(scheduleEntries)
                        for (scheduleEntry in scheduleEntries) {
                            val dataCache = railwayData.dataCache
                            val route = dataCache.routeIdMap[scheduleEntry.routeId]
                            if (route != null && scheduleEntry.currentStationIndex < route.platformIds.size - 1) {
                                val scheduleObject = JsonObject()
                                scheduleObject.addProperty("arrival", scheduleEntry.arrivalMillis)
                                scheduleObject.addProperty("name", route.name)
                                var destination = route.getDestination(scheduleEntry.currentStationIndex)
                                if (destination == null) {
                                    val station = railwayData.dataCache.platformIdToStation[route.getLastPlatformId()]
                                    destination = if (station == null) "" else station.name
                                }
                                scheduleObject.addProperty("destination", destination)
                                scheduleObject.addProperty("circular", if (route.circularState == Route.CircularState.NONE) "" else if (route.circularState == Route.CircularState.CLOCKWISE) "cw" else "ccw")
                                scheduleObject.addProperty("route", if (route.isLightRailRoute) route.lightRailRouteNumber else "")
                                val platform = railwayData.dataCache.platformIdMap[route.platformIds[scheduleEntry.currentStationIndex]!!.platformId]
                                scheduleObject.addProperty("platform", if (platform == null) "" else platform.name)
                                scheduleObject.addProperty("color", route.color)
                                dataArray.add(scheduleObject)
                            }
                        }
                    }
                }
                worldIndexCounter++
            })
            IServletHandler.sendResponse(response, asyncContext, dataArray.toString())
        }
    }
}

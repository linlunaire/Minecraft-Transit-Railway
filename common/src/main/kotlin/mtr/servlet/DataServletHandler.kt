package mtr.servlet

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import mtr.data.*
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import javax.servlet.AsyncContext
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.HashSet

open class DataServletHandler : HttpServlet() {
    protected override fun doGet(request: HttpServletRequest?, response: HttpServletResponse?) {
        val asyncContext: AsyncContext? = request!!.startAsync()
        Webserver.callback!!.accept {
            val dataArray = JsonArray()
            Webserver.getWorlds!!.get()!!.forEach(java.util.function.Consumer { world ->
                val railwayData = RailwayData.getInstance(world)
                val routesArray = JsonArray()
                val stationPositionsObject = JsonObject()
                val stationsObject = JsonObject()
                val typesObject = JsonArray()
                val types: MutableSet<String> = HashSet()
                val dataCache = Webserver.getDataCache!!.apply(railwayData)
                if (dataCache != null) {
                    Webserver.getRoutes!!.apply(railwayData)!!.forEach(java.util.function.Consumer routeLoop@ { nullableRoute ->
                        val route = nullableRoute!!
                        if (route.isHidden) return@routeLoop
                        val routeObject = JsonObject()
                        routeObject.addProperty("color", route.color)
                        routeObject.addProperty("name", route.name)
                        routeObject.addProperty("number", if (route.isLightRailRoute) route.lightRailRouteNumber else "")
                        val type = IServletHandler.createRouteKey(route.transportMode, route.routeType)
                        routeObject.addProperty("type", type)
                        types.add(type)
                        val routeStationsArray = JsonArray()
                        routeObject.add("stations", routeStationsArray)
                        val routeDurationsArray = JsonArray()
                        routeObject.add("durations", routeDurationsArray)
                        val routeDensityArray = JsonArray()
                        routeObject.add("densities", routeDensityArray)
                        routeObject.addProperty("circular", if (route.circularState == Route.CircularState.NONE) "" else if (route.circularState == Route.CircularState.CLOCKWISE) "cw" else "ccw")

                        val depot = dataCache.routeIdToOneDepot[route.id]
                        var accumulatedTime = 0F
                        var prevPlatformPos: BlockPos? = null
                        var i = 0
                        while (i < route.platformIds.size) {
                            val platformId = route.platformIds[i]!!.platformId
                            var time = 0F
                            if (i > 0 && depot != null) {
                                val prevPlatformId = route.platformIds[i - 1]!!.platformId
                                if (depot.platformTimes.containsKey(prevPlatformId) && depot.platformTimes[prevPlatformId]!!.containsKey(platformId)) {
                                    time = depot.platformTimes[prevPlatformId]!![platformId]!!
                                }
                            }

                            val station = dataCache.platformIdToStation[platformId]
                            var addedStation = false
                            var thisPlatformPos: BlockPos? = null
                            if (station != null) {
                                val platform = dataCache.platformIdMap[platformId]
                                if (platform != null) {
                                    try {
                                        val newId = station.id.toString() + "_" + route.color
                                        routeStationsArray.add(newId)
                                        thisPlatformPos = platform.getMidPos()
                                        if (stationPositionsObject.has(newId)) {
                                            val stationPositionObject = stationPositionsObject.getAsJsonObject(newId)
                                            val existingX = stationPositionObject.get("x").asInt
                                            val existingZ = stationPositionObject.get("y").asInt
                                            stationPositionObject.addProperty("x", (existingX + thisPlatformPos.x) / 2)
                                            stationPositionObject.addProperty("y", (existingZ + thisPlatformPos.z) / 2)
                                        } else {
                                            val stationPositionObject = JsonObject()
                                            stationPositionObject.addProperty("x", thisPlatformPos.x)
                                            stationPositionObject.addProperty("y", thisPlatformPos.z)
                                            stationPositionObject.addProperty("vertical", platform.getAxis() == Direction.Axis.Z)
                                            stationPositionsObject.add(newId, stationPositionObject)
                                        }

                                        val stationObject = JsonObject()
                                        stationObject.addProperty("name", station.name)
                                        stationObject.addProperty("color", station.color)
                                        stationObject.addProperty("zone", station.zone)
                                        val stationCenter = station.getCenter()
                                        stationObject.addProperty("x", if (stationCenter == null) 0 else stationCenter.x)
                                        stationObject.addProperty("z", if (stationCenter == null) 0 else stationCenter.z)
                                        val stationConnectionsArray = JsonArray()
                                        dataCache.stationIdToConnectingStations[station]!!.forEach(java.util.function.Consumer { stationConnectionsArray.add(it!!.id.toString()) })
                                        stationObject.add("connections", stationConnectionsArray)
                                        stationsObject.add(station.id.toString(), stationObject)
                                        addedStation = true
                                    } catch (ignored: Exception) {
                                    }
                                }
                            }

                            if (prevPlatformPos != null && thisPlatformPos != null && railwayData != null) {
                                routeDensityArray.add(railwayData.railwayDataRouteFinderModule.getConnectionDensity(prevPlatformPos, thisPlatformPos))
                            } else {
                                routeDensityArray.add(0)
                            }
                            prevPlatformPos = thisPlatformPos
                            accumulatedTime += time
                            if (i > 0 && addedStation) {
                                routeDurationsArray.add(accumulatedTime)
                                accumulatedTime = 0F
                            }
                            i++
                        }
                        routesArray.add(routeObject)
                    })
                }

                for (transportMode in TransportMode.entries) {
                    for (routeType in RouteType.entries) {
                        val type = IServletHandler.createRouteKey(transportMode, routeType)
                        if (types.contains(type)) typesObject.add(type)
                    }
                }
                val dataObject = JsonObject()
                dataObject.add("routes", routesArray)
                dataObject.add("positions", stationPositionsObject)
                dataObject.add("stations", stationsObject)
                dataObject.add("types", typesObject)
                dataArray.add(dataObject)
            })
            IServletHandler.sendResponse(response, asyncContext, dataArray.toString())
        }
    }
}

package mtr.servlet

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import mtr.data.*
import net.minecraft.core.BlockPos
import net.minecraft.resources.Identifier
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import javax.servlet.AsyncContext
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.ArrayList
import java.util.Arrays
import java.util.Locale

open class RouteFinderServletHandler : HttpServlet() {
    protected override fun doGet(request: HttpServletRequest?, response: HttpServletResponse?) {
        val asyncContext: AsyncContext? = request!!.startAsync()
        asyncContext!!.timeout = -1
        Webserver.callback!!.accept {
            val errors: MutableList<String> = ArrayList()
            val parameterStartPlayer = request.getParameter("startPlayer")
            val parameterEndPlayer = request.getParameter("endPlayer")
            val parameterStartStation = request.getParameter("startStation")
            val parameterEndStation = request.getParameter("endStation")
            val parameterStartPos = request.getParameter("startPos")
            val parameterEndPos = request.getParameter("endPos")
            var maxTickTime = 40
            try {
                maxTickTime = Integer.parseInt(request.getParameter("maxTickTime"))
            } catch (ignored: Exception) {
            }
            val world = getWorldFromParameter(request.getParameter("dimension"), errors)
            if (world != null) {
                val railwayData = RailwayData.getInstance(world)
                if (railwayData != null) {
                    val startPositionInfo = getPosition(world, railwayData, parameterStartPlayer, parameterStartStation, parameterStartPos, true, errors)
                    val endPositionInfo = getPosition(world, railwayData, parameterEndPlayer, parameterEndStation, parameterEndPos, false, errors)
                    if (startPositionInfo != null && endPositionInfo != null && errors.isEmpty()) {
                        if (!railwayData.railwayDataRouteFinderModule.findRoute(startPositionInfo.pos, endPositionInfo.pos, maxTickTime) { dataList, duration ->
                            val jsonObject = JsonObject()
                            jsonObject.add("start", startPositionInfo.toJsonObject())
                            jsonObject.add("end", endPositionInfo.toJsonObject())
                            jsonObject.addProperty("response", duration)
                            val jsonArrayData = JsonArray()
                            for (data in dataList) {
                                val positionInfo = PositionInfo(data.pos, railwayData, null, null, false)
                                val jsonObjectData = positionInfo.toJsonObject()
                                jsonObjectData.addProperty("duration", data.duration)
                                val route = railwayData.dataCache.routeIdMap[data.routeId]
                                if (route != null) {
                                    val jsonObjectRoute = JsonObject()
                                    jsonObjectRoute.addProperty("wait", data.waitingTime)
                                    jsonObjectRoute.addProperty("color", route.color)
                                    jsonObjectRoute.addProperty("name", route.name)
                                    jsonObjectRoute.addProperty("number", if (route.isLightRailRoute) route.lightRailRouteNumber else "")
                                    jsonObjectRoute.addProperty("type", IServletHandler.createRouteKey(route.transportMode, route.routeType))
                                    jsonObjectRoute.addProperty("circular", if (route.circularState == Route.CircularState.NONE) "" else if (route.circularState == Route.CircularState.CLOCKWISE) "cw" else "ccw")
                                    val jsonArrayRouteStations = JsonArray()
                                    data.stationIds.forEach(java.util.function.Consumer { stationId -> jsonArrayRouteStations.add(java.lang.String.valueOf(stationId)) })
                                    jsonObjectRoute.add("stations", jsonArrayRouteStations)
                                    jsonObjectData.add("route", jsonObjectRoute)
                                }
                                jsonArrayData.add(jsonObjectData)
                                if (endPositionInfo.isStationParameter && positionInfo.station === endPositionInfo.station) break
                            }
                            jsonObject.add("directions", jsonArrayData)
                            IServletHandler.sendResponse(response, asyncContext, jsonObject.toString())
                        }) {
                            errors.add("Too many requests! Please try again.")
                        }
                    }
                }
            }
            if (errors.isNotEmpty()) {
                val jsonObject = JsonObject()
                val jsonArray = JsonArray()
                errors.forEach(java.util.function.Consumer { jsonArray.add(it) })
                jsonObject.add("errors", jsonArray)
                IServletHandler.sendResponse(response, asyncContext, jsonObject.toString())
            }
        }
    }

    companion object {
        private fun getWorldFromParameter(parameterDimension: String?, errors: MutableList<String>): Level? {
            if (parameterDimension == null) {
                errors.add("The 'dimension' parameter must be defined.")
            } else {
                val worlds = Webserver.getWorlds!!.get()!!
                try {
                    return worlds[Integer.parseInt(parameterDimension)]
                } catch (ignored: Exception) {
                }
                for (world in worlds) {
                    val dimensionLocation: Identifier = world!!.dimension().identifier()
                    if (parameterDimension.equals(dimensionLocation.toString(), ignoreCase = true) || parameterDimension.equals(dimensionLocation.path, ignoreCase = true)) return world
                }
                if (worlds.size > 1) {
                    errors.add(String.format("The 'dimension' parameter must be a world index (0-%s) or a valid world ID (such as %s).", worlds.size - 1, worlds[0]!!.dimension().identifier().toString()))
                }
            }
            return null
        }

        private fun getPosition(world: Level, railwayData: RailwayData, parameterPlayer: String?, parameterStation: String?, parameterPos: String?, isStart: Boolean, errors: MutableList<String>): PositionInfo? {
            if (parameterPlayer != null) {
                for (player: Player in world.players()) {
                    val playerName = player.name.string
                    if (playerName.equals(parameterPlayer, ignoreCase = true)) return PositionInfo(player.blockPosition(), railwayData, null, playerName, false)
                }
                errors.add(String.format("The player '%s' is not online or in the specified dimension.", parameterPlayer))
            } else if (parameterStation != null) {
                try {
                    val station = railwayData.dataCache.stationIdMap[java.lang.Long.parseLong(parameterStation)]
                    return PositionInfo(station!!.getCenter(), railwayData, station, null, true)
                } catch (ignored: Exception) {
                }
                try {
                    for (station in railwayData.stations) {
                        val stationNameSplit = splitJava(station.name!!.lowercase(Locale.ENGLISH), "\\|").asList()
                        if (Arrays.stream(splitJava(parameterStation.lowercase(Locale.ENGLISH), "\\|")).allMatch(stationNameSplit::contains)) {
                            return PositionInfo(station.getCenter(), railwayData, station, null, true)
                        }
                    }
                } catch (ignored: Exception) {
                }
                errors.add(String.format("The station '%s' does not exist in the specified dimension.", parameterStation))
            } else if (parameterPos != null) {
                try {
                    val coordinates = splitJava(parameterPos, ",")
                    return PositionInfo(RailwayData.newBlockPos(java.lang.Double.parseDouble(coordinates[0]), java.lang.Double.parseDouble(coordinates[1]), java.lang.Double.parseDouble(coordinates[2])), railwayData, null, null, false)
                } catch (ignored: Exception) {
                }
                errors.add(String.format("The block position '%s' is not formatted correctly.", parameterPos))
            } else {
                val key = if (isStart) "start" else "end"
                errors.add(String.format("One of '%sPlayer', '%sStation', or '%sPos' must be defined.", key, key, key))
            }
            return null
        }

        // Keep Java's trailing-empty removal and single-character split fast path.
        @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
        private fun splitJava(value: String, regex: String): Array<String> = (value as java.lang.String).split(regex)
    }

    private class PositionInfo(val pos: BlockPos?, railwayData: RailwayData, station: Station?, private val playerName: String?, val isStationParameter: Boolean) {
        val station: Station? = station ?: RailwayData.getStation(railwayData.stations, railwayData.dataCache, pos)
        private val platform: Platform? = railwayData.dataCache.platformIdMap[RailwayData.getClosePlatformId(railwayData.platforms, railwayData.dataCache, pos)]

        fun toJsonObject(): JsonObject {
            val jsonObject = JsonObject()
            val jsonArrayPos = JsonArray()
            jsonArrayPos.add(pos!!.x)
            jsonArrayPos.add(pos.y)
            jsonArrayPos.add(pos.z)
            jsonObject.add("pos", jsonArrayPos)
            if (station != null) {
                val jsonObjectStation = JsonObject()
                jsonObjectStation.addProperty("name", station.name)
                jsonObjectStation.addProperty("id", station.id.toString())
                jsonObject.add("station", jsonObjectStation)
            }
            if (platform != null) jsonObject.addProperty("platform", platform.name)
            if (playerName != null) jsonObject.addProperty("player", playerName)
            return jsonObject
        }
    }
}

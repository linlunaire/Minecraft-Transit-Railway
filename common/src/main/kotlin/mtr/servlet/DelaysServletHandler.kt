package mtr.servlet

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import mtr.data.DataCache
import mtr.data.RailwayData
import mtr.data.Route
import mtr.data.Station
import javax.servlet.AsyncContext
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class DelaysServletHandler : HttpServlet() {
    protected override fun doGet(request: HttpServletRequest?, response: HttpServletResponse?) {
        val asyncContext: AsyncContext? = request!!.startAsync()
        Webserver.callback!!.accept {
            val dataArray = JsonArray()
            Webserver.getWorlds!!.get()!!.forEach(java.util.function.Consumer { world ->
                val railwayData = RailwayData.getInstance(world)
                val delayArray = JsonArray()
                if (railwayData != null) {
                    val dataCache: DataCache? = railwayData.dataCache
                    railwayData.getTrainDelays().forEach(java.util.function.BiConsumer { routeId, trainDelaysForRoute ->
                        trainDelaysForRoute.forEach(java.util.function.BiConsumer { pos, trainDelay ->
                            val routeName: String?
                            val routeNumber: String?
                            val destination: String?
                            val circular: String
                            val color: Int
                            val route = dataCache!!.routeIdMap[routeId]
                            if (route == null) {
                                routeName = ""
                                routeNumber = ""
                                destination = ""
                                circular = ""
                                color = 0
                            } else {
                                routeName = route.name
                                routeNumber = if (route.isLightRailRoute) route.lightRailRouteNumber else ""
                                val station: Station? = dataCache.platformIdToStation[route.getLastPlatformId()]
                                destination = if (station == null) "" else station.name
                                circular = if (route.circularState == Route.CircularState.NONE) "" else if (route.circularState == Route.CircularState.CLOCKWISE) "cw" else "ccw"
                                color = route.color
                            }
                            val delayObject = JsonObject()
                            delayObject.addProperty("name", routeName)
                            delayObject.addProperty("number", routeNumber)
                            delayObject.addProperty("destination", destination)
                            delayObject.addProperty("circular", circular)
                            delayObject.addProperty("color", color)
                            delayObject.addProperty("delay", trainDelay.getDelayTicks())
                            delayObject.addProperty("time", trainDelay.getLastDelayTime())
                            delayObject.addProperty("x", pos.x)
                            delayObject.addProperty("y", pos.y)
                            delayObject.addProperty("z", pos.z)
                            delayArray.add(delayObject)
                        })
                    })
                }
                dataArray.add(delayArray)
            })
            IServletHandler.sendResponse(response, asyncContext, dataArray.toString())
        }
    }
}

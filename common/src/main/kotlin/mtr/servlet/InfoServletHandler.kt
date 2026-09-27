package mtr.servlet

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import mtr.data.RailwayData
import mtr.data.Route
import mtr.data.Station
import javax.servlet.AsyncContext
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class InfoServletHandler : HttpServlet() {
    protected override fun doGet(request: HttpServletRequest?, response: HttpServletResponse?) {
        val asyncContext: AsyncContext? = request!!.startAsync()
        Webserver.callback!!.accept {
            val dataArray = JsonArray()
            Webserver.getWorlds!!.get()!!.forEach(java.util.function.Consumer { world ->
                val railwayData = RailwayData.getInstance(world)
                val playersArray = JsonArray()
                if (railwayData != null) {
                    world!!.players().forEach(java.util.function.Consumer { player ->
                        val dataObject = JsonObject()
                        dataObject.addProperty("player", player.name.string)
                        val routeName: String?
                        val routeNumber: String?
                        val destination: String?
                        val circular: String
                        val color: Int
                        val route = railwayData.railwayDataCoolDownModule.getRidingRoute(player)
                        if (route == null) {
                            routeName = ""
                            routeNumber = ""
                            destination = ""
                            circular = ""
                            color = 0
                        } else {
                            routeName = route.name
                            routeNumber = if (route.isLightRailRoute) route.lightRailRouteNumber else ""
                            val station: Station? = railwayData.dataCache.platformIdToStation[route.getLastPlatformId()]
                            destination = if (station == null) "" else station.name
                            circular = if (route.circularState == Route.CircularState.NONE) "" else if (route.circularState == Route.CircularState.CLOCKWISE) "cw" else "ccw"
                            color = route.color
                        }
                        dataObject.addProperty("name", routeName)
                        dataObject.addProperty("number", routeNumber)
                        dataObject.addProperty("destination", destination)
                        dataObject.addProperty("circular", circular)
                        dataObject.addProperty("color", color)
                        playersArray.add(dataObject)
                    })
                }
                dataArray.add(playersArray)
            })
            IServletHandler.sendResponse(response, asyncContext, dataArray.toString())
        }
    }
}

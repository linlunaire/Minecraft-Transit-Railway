package mtr.servlet

import mtr.MTR
import mtr.data.DataCache
import mtr.data.RailwayData
import mtr.data.Route
import net.minecraft.world.level.Level
import org.eclipse.jetty.server.Connector
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.util.resource.Resource
import org.eclipse.jetty.util.thread.QueuedThreadPool
import org.slf4j.LoggerFactory
import java.net.BindException
import java.nio.file.Files
import java.nio.file.Path
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Supplier

@JvmSuppressWildcards
abstract class Webserver {
    companion object {
        @JvmField var callback: Consumer<Runnable?>? = Consumer { it!!.run() }
        @JvmField var getWorlds: Supplier<MutableList<Level?>?>? = Supplier { ArrayList() }
        @JvmField var getRoutes: Function<RailwayData?, MutableSet<Route?>?>? = Function { HashSet() }
        @JvmField var getDataCache: Function<RailwayData?, DataCache?>? = Function { null }

        private var webServer: Server? = null
        private var serverConnector: ServerConnector? = null
        private val LOGGER = LoggerFactory.getLogger("MTR Web Map")

        // Explicitly share the class monitor across Java bridges and Kotlin Companion
        // calls. @Synchronized would additionally lock Companion and split the contract.
        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun init(): Unit = synchronized(Webserver::class.java) {
            stop()
            webServer = Server(QueuedThreadPool(100, 10, 120))
            serverConnector = ServerConnector(webServer)
            webServer!!.connectors = arrayOf<Connector>(serverConnector!!)
            val context = ServletContextHandler()
            webServer!!.handler = context
            val url = MTR::class.java.getResource("/assets/mtr/website/")
            if (url != null) {
                try {
                    context.baseResource = Resource.newResource(url.toURI())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            val servletHolder = ServletHolder("default", DefaultServlet::class.java)
            servletHolder.setInitParameter("dirAllowed", "true")
            servletHolder.setInitParameter("cacheControl", "max-age=0,public")
            context.addServlet(servletHolder, "/")
            context.addServlet(DataServletHandler::class.java, "/data")
            context.addServlet(InfoServletHandler::class.java, "/info")
            context.addServlet(ArrivalsServletHandler::class.java, "/arrivals")
            context.addServlet(DelaysServletHandler::class.java, "/delays")
            context.addServlet(RouteFinderServletHandler::class.java, "/route")
        }

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun start(path: Path?): Unit = synchronized(Webserver::class.java) {
            if (webServer == null) init()
            if (webServer!!.isStarted) return@synchronized
            val port: Int
            try {
                if (!Files.exists(path)) {
                    Files.createDirectories(path!!.toAbsolutePath().parent)
                    Files.writeString(path, "8888\n")
                }
                port = Integer.parseInt(Files.readString(path).trim { it <= ' ' })
                if (port != 0 && (port < 1025 || port > 65535)) {
                    throw IllegalArgumentException("Expected 0 or a port from 1025 to 65535")
                }
            } catch (e: Exception) {
                LOGGER.error("Web map not started: invalid or unreadable port file {}. Use 0 to disable it or an integer from 1025 to 65535. File left unchanged.", path!!.toAbsolutePath(), e)
                return@synchronized
            }
            if (port == 0) {
                LOGGER.info("Web map disabled by {} (port 0).", path!!.toAbsolutePath())
                return@synchronized
            }
            serverConnector!!.port = port
            try {
                // Bind before starting the servlet context/thread pool. Checking first
                // whether a port is free would introduce a race with the actual bind.
                serverConnector!!.open()
                webServer!!.start()
            } catch (e: Exception) {
                stop()
                var cause: Throwable = e
                while (cause.cause != null && cause !is BindException) cause = cause.cause!!
                if (cause is BindException) {
                    LOGGER.warn("Web map could not bind port {}: {}. Minecraft can continue, but the web map is unavailable. Set a free port in {} (or 0 to disable) and restart. No alternate port was opened.", port, cause.message, path!!.toAbsolutePath())
                } else {
                    LOGGER.error("Web map startup failed on port {}. Check {}; Minecraft can continue without the web map.", port, path!!.toAbsolutePath(), e)
                }
            }
        }

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun stop(): Unit = synchronized(Webserver::class.java) {
            if (webServer == null) return@synchronized
            try {
                webServer!!.stop()
                // A connector opened before Server.start() also needs closing on failure.
                serverConnector!!.close()
            } catch (e: Exception) {
                LOGGER.error("Could not completely stop the web map", e)
            }
        }
    }
}

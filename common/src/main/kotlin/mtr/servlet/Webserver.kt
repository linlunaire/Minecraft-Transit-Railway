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
        private val LOGGER = LoggerFactory.getLogger("YLM Web Map")

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
            if (!WebMapSupport.isAvailable()) {
                stop()
                LOGGER.info("未检测到 Dynmap、BlueMap 或 Squaremap 模组／已启用插件，已停用 YLM 网页地图，不监听端口。")
                return@synchronized
            }
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
                LOGGER.error("网页地图未启动：端口文件 {} 无效或无法读取。请设为 0（关闭）或 1025～65535 的整数；原文件未修改。", path!!.toAbsolutePath(), e)
                return@synchronized
            }
            if (port == 0) {
                LOGGER.info("网页地图已关闭：{} 中的端口设为 0。", path!!.toAbsolutePath())
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
                    LOGGER.warn("网页地图无法监听端口 {}：{}。游戏可继续运行，但网页地图不可用；请在 {} 中设置空闲端口（或设为 0 关闭）并重启。未自动改用其他端口。", port, cause.message, path!!.toAbsolutePath())
                } else {
                    LOGGER.error("网页地图在端口 {} 启动失败，请检查 {}；游戏可在不启用网页地图的情况下继续运行。", port, path!!.toAbsolutePath(), e)
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
                LOGGER.error("网页地图未能完全停止", e)
            }
        }
    }
}

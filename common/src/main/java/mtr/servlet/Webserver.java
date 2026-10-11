package mtr.servlet;

import mtr.MTR;
import mtr.data.DataCache;
import mtr.data.RailwayData;
import mtr.data.Route;
import net.minecraft.world.level.Level;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.BindException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Webserver {

	public static Consumer<Runnable> callback = Runnable::run;
	public static Supplier<List<Level>> getWorlds = ArrayList::new;
	public static Function<RailwayData, Set<Route>> getRoutes = railwayData -> new HashSet<>();
	public static Function<RailwayData, DataCache> getDataCache = railwayData -> null;

	private static Server webServer;
	private static ServerConnector serverConnector;
	private static final Logger LOGGER = LoggerFactory.getLogger("MTR Web Map");

	public static synchronized void init() {
		stop();
		webServer = new Server(new QueuedThreadPool(100, 10, 120));
		serverConnector = new ServerConnector(webServer);
		webServer.setConnectors(new Connector[]{serverConnector});
		final ServletContextHandler context = new ServletContextHandler();
		webServer.setHandler(context);
		final URL url = MTR.class.getResource("/assets/mtr/website/");
		if (url != null) {
			try {
				context.setBaseResource(Resource.newResource(url.toURI()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		final ServletHolder servletHolder = new ServletHolder("default", DefaultServlet.class);
		servletHolder.setInitParameter("dirAllowed", "true");
		servletHolder.setInitParameter("cacheControl", "max-age=0,public");
		context.addServlet(servletHolder, "/");
		context.addServlet(DataServletHandler.class, "/data");
		context.addServlet(InfoServletHandler.class, "/info");
		context.addServlet(ArrivalsServletHandler.class, "/arrivals");
		context.addServlet(DelaysServletHandler.class, "/delays");
		context.addServlet(RouteFinderServletHandler.class, "/route");
	}

	public static synchronized void start(Path path) {
		if (!WebMapSupport.isAvailable()) {
			stop();
			LOGGER.info("未检测到 Dynmap、BlueMap 或 Squaremap 模组／已启用插件，已停用 MTR 网页地图，不监听端口。");
			return;
		}
		if (webServer == null) init();
		if (webServer.isStarted()) return;
		final int port;
		try {
			if (!Files.exists(path)) {
				Files.createDirectories(path.toAbsolutePath().getParent());
				Files.writeString(path, "8888\n");
			}
			port = Integer.parseInt(Files.readString(path).trim());
			if (port != 0 && (port < 1025 || port > 65535)) throw new IllegalArgumentException("Expected 0 or a port from 1025 to 65535");
		} catch (Exception e) {
			LOGGER.error("网页地图未启动：端口配置 {} 无效或无法读取。填写 0 可停用，或填写 1025–65535 的端口号；原文件未修改。", path.toAbsolutePath(), e);
			return;
		}
		if (port == 0) {
			LOGGER.info("网页地图已停用：{} 中的端口设置为 0。", path.toAbsolutePath());
			return;
		}
		serverConnector.setPort(port);
		try {
			// Bind before starting the servlet context/thread pool. The OS remains the
			// authority; checking whether a port is free first would introduce a race.
			serverConnector.open();
			webServer.start();
		} catch (Exception e) {
			stop();
			Throwable cause = e;
			while (cause.getCause() != null && !(cause instanceof BindException)) cause = cause.getCause();
			if (cause instanceof BindException) {
				LOGGER.warn("网页地图端口 {} 被占用：{}。已停止地图服务，不影响 Minecraft 运行。请在 {} 设置空闲端口或填 0 停用；不会自动改用其他端口。", port, cause.getMessage(), path.toAbsolutePath());
			} else {
				LOGGER.error("网页地图在端口 {} 启动失败，请检查 {}；Minecraft 可继续运行。", port, path.toAbsolutePath(), e);
			}
		}
	}

	public static synchronized void stop() {
		if (webServer == null) return;
		try {
			webServer.stop();
			// A connector opened before Server.start() also needs closing if startup fails.
			serverConnector.close();
		} catch (Exception e) {
			LOGGER.error("未能完全停止网页地图服务", e);
		}
	}
}

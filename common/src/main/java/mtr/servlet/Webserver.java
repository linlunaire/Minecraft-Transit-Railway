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
			LOGGER.error("Web map not started: invalid or unreadable port file {}. Use 0 to disable it or an integer from 1025 to 65535. File left unchanged.", path.toAbsolutePath(), e);
			return;
		}
		if (port == 0) {
			LOGGER.info("Web map disabled by {} (port 0).", path.toAbsolutePath());
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
				LOGGER.warn("Web map could not bind port {}: {}. Minecraft can continue, but the web map is unavailable. Set a free port in {} (or 0 to disable) and restart. No alternate port was opened.", port, cause.getMessage(), path.toAbsolutePath());
			} else {
				LOGGER.error("Web map startup failed on port {}. Check {}; Minecraft can continue without the web map.", port, path.toAbsolutePath(), e);
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
			LOGGER.error("Could not completely stop the web map", e);
		}
	}
}

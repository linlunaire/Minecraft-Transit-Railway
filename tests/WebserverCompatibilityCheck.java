package mtr.servlet;

import dev.architectury.platform.Platform;
import org.bukkit.Bukkit;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;

/** Real Jetty socket lifecycle on loopback; no Minecraft server or public listener. */
public final class WebserverCompatibilityCheck {

	public static void main(String[] args) throws Exception {
		checkProviderDetection();
		final Path directory = Files.createTempDirectory("mtr-webserver-check-");
		final Path config = directory.resolve("mtr_webserver_port.txt");
		try {
			Webserver.init();
			connector().setHost("127.0.0.1");
			Files.writeString(config, "0");
			Webserver.start(config);
			require(Files.readString(config).equals("0"), "Missing map providers must not rewrite the config");
			require(server().isStopped() && connector().getLocalPort() < 0, "Missing map providers must leave Jetty stopped");
			Files.delete(config);
			Webserver.start(config);
			require(!Files.exists(config), "Missing map providers created a default port file");
			require(server().isStopped() && connector().getLocalPort() < 0, "Missing map providers opened a listener");
			Platform.MODS.add("dynmap");
			try (ServerSocket occupied = new ServerSocket(0, 1, InetAddress.getLoopbackAddress())) {
				Files.writeString(config, Integer.toString(occupied.getLocalPort()));
				Webserver.start(config);
				require(server().isStopped(), "Port conflict must stop the failed web server, not leave Jetty in FAILED state");
				require(((QueuedThreadPool) server().getThreadPool()).isStopped(), "Port conflict leaked the Jetty thread pool");
				require(connector().getLocalPort() < 0, "Port conflict unexpectedly opened another port");
				require(Files.readString(config).equals(Integer.toString(occupied.getLocalPort())), "Port conflict rewrote the configured port");
			}
			Webserver.start(config); // The same instance can recover when its configured port becomes free.
			require(server().isStarted(), "Web map could not recover after a port conflict");
			final Server running = server();
			final int port = connector().getLocalPort();
			try (HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build()) {
				final HttpResponse<String> response = client.send(HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + "/info")).timeout(Duration.ofSeconds(3)).build(), HttpResponse.BodyHandlers.ofString());
				require(response.statusCode() == 200 && response.body().equals("[]"), "Real web map info endpoint did not respond after recovery");
			}
			Webserver.start(config);
			require(server() == running && connector().getLocalPort() == port, "Repeated start replaced the live listener");
			Platform.MODS.clear();
			Webserver.start(config);
			require(running.isStopped() && connector().getLocalPort() < 0, "Losing the map provider left a listener running");
			Bukkit.ENABLED_PLUGINS.add("BlueMap");
			Webserver.start(config);
			require(server().isStarted() && connector().getLocalPort() == port, "Enabled hybrid-server map plugin did not permit startup");
			Bukkit.ENABLED_PLUGINS.clear();
			Platform.MODS.add("dynmap");
			Webserver.init();
			require(running.isStopped() && ((QueuedThreadPool) running.getThreadPool()).isStopped(), "Repeated init leaked the previous server");
			connector().setHost("127.0.0.1");
			for (String invalid : new String[] {"0", "-1", "65536", "port=8888", ""}) {
				Files.writeString(config, invalid);
				Webserver.start(config);
				require(server().isStopped() && connector().getLocalPort() < 0, "Disabled or invalid config opened a listener: " + invalid);
				require(Files.readString(config).equals(invalid), "Invalid config was silently overwritten");
			}
			Files.writeString(config, Integer.toString(port));
			server().setHandler(new AbstractHandler() {
				@Override protected void doStart() throws Exception { throw new IllegalStateException("fixture handler startup failure"); }
				@Override public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) { }
			});
			Webserver.start(config);
			require(server().isStopped() && ((QueuedThreadPool) server().getThreadPool()).isStopped(), "Partial startup leaked resources");
			try (ServerSocket released = new ServerSocket(port, 1, InetAddress.getLoopbackAddress())) {
				require(released.getLocalPort() == port, "Partial startup did not release the pre-bound socket");
			}
			Webserver.stop();
			Webserver.stop();
			System.out.println("PASS: three mod/plugin provider inventories, absent Bukkit/API-only negative controls, real Jetty disabled/occupied-port cleanup, HTTP response, repeat lifecycle and partial-start cleanup (loopback only)");
		} finally {
			Webserver.stop();
			Files.deleteIfExists(config);
			Files.deleteIfExists(directory);
			Platform.MODS.clear();
			Bukkit.ENABLED_PLUGINS.clear();
		}
	}

	@SuppressWarnings("unchecked")
	private static void checkProviderDetection() throws Exception {
		require(!WebMapSupport.isAvailable(), "API dependencies on the test classpath were mistaken for installed map providers");
		for (String id : new String[]{"dynmap", "bluemap", "squaremap"}) {
			Platform.MODS.add(id);
			require(WebMapSupport.isLoaded(id) && WebMapSupport.isAvailable(), "Loaded map mod was not detected: " + id);
			Platform.MODS.clear();
			require(!WebMapSupport.isAvailable(), "Map availability was stale after loader inventory changed");
		}
		for (String[] plugin : new String[][]{{"dynmap", "dynmap"}, {"bluemap", "BlueMap"}, {"squaremap", "squaremap"}}) {
			Bukkit.ENABLED_PLUGINS.add(plugin[1]);
			require(WebMapSupport.isLoaded(plugin[0]) && WebMapSupport.isAvailable(), "Enabled map plugin was not detected: " + plugin[1]);
			Bukkit.ready = false;
			require(!WebMapSupport.isAvailable(), "An uninitialized plugin manager enabled the map");
			Bukkit.ready = true;
			Bukkit.ENABLED_PLUGINS.clear();
			require(!WebMapSupport.isAvailable(), "Disabled map plugin was still detected");
		}
		require(!WebMapSupport.isLoaded("unrelated"), "An unrelated mod was accepted as a provider");
		final URL[] classpath = Arrays.stream(System.getProperty("java.class.path").split(Pattern.quote(java.io.File.pathSeparator)))
				.map(path -> { try { return Path.of(path).toUri().toURL(); } catch (Exception e) { throw new IllegalStateException(e); } }).toArray(URL[]::new);
		try (URLClassLoader withoutBukkit = new URLClassLoader(classpath, ClassLoader.getPlatformClassLoader()) {
			@Override protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
				if (name.startsWith("org.bukkit.")) throw new ClassNotFoundException(name);
				return super.loadClass(name, resolve);
			}
		}) {
			final Class<?> support = Class.forName("mtr.servlet.WebMapSupport", true, withoutBukkit);
			require(Boolean.FALSE.equals(support.getMethod("isAvailable").invoke(null)), "A normal loader without Bukkit failed safe absence detection");
			((Set<String>) Class.forName("dev.architectury.platform.Platform", true, withoutBukkit).getField("MODS").get(null)).add("squaremap");
			require(Boolean.TRUE.equals(support.getMethod("isAvailable").invoke(null)), "A native map mod incorrectly required Bukkit");
		}
	}

	private static Server server() throws Exception { return (Server) field("webServer"); }
	private static ServerConnector connector() throws Exception { return (ServerConnector) field("serverConnector"); }
	private static Object field(String name) throws Exception {
		final Field field = Webserver.class.getDeclaredField(name);
		field.setAccessible(true);
		return field.get(null);
	}
	private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}

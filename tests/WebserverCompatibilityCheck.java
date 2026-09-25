package mtr.servlet;

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
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

/** Real Jetty socket lifecycle on loopback; no Minecraft server or public listener. */
public final class WebserverCompatibilityCheck {

	public static void main(String[] args) throws Exception {
		final Path directory = Files.createTempDirectory("mtr-webserver-check-");
		final Path config = directory.resolve("mtr_webserver_port.txt");
		try {
			Webserver.init();
			connector().setHost("127.0.0.1");
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
			System.out.println("PASS: real Jetty occupied-port cleanup/recovery, HTTP response, repeat lifecycle, disabled/invalid configuration and partial-start failure cleanup (loopback only)");
		} finally {
			Webserver.stop();
			Files.deleteIfExists(config);
			Files.deleteIfExists(directory);
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

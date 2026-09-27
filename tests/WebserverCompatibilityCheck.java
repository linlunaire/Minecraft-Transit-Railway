package mtr.servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/** Real Jetty loopback lifecycle and Java interoperability; no Minecraft server. */
public final class WebserverCompatibilityCheck {

    private static int assertions;
    private static int hiddenCalls;

    public static void main(String[] args) throws Exception {
        final boolean kotlinImplementation = java.util.Arrays.stream(Webserver.class.getDeclaredAnnotations()).anyMatch(annotation -> annotation.annotationType().getName().equals("kotlin.Metadata"));
        final boolean locksOnly = java.util.Arrays.asList(args).contains("--locks-only");
        if (args.length > 0 && !args[0].startsWith("--")) {
            final Path expected = Path.of(args[0]).toRealPath();
            final Path actual = Path.of(Webserver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toRealPath();
            require(actual.equals(expected), "Webserver was not loaded from the selected Kotlin production output");
            require(kotlinImplementation, "Webserver is still a Java implementation");
        }
        verifyContract(Webserver.class);
        try {
            verifyContract(BrokenStaticContract.class);
            throw new AssertionError("Negative control accepted final static entrypoints");
        } catch (ContractFailure expected) {
            assertions++;
        }
        require(new HidingSubclass() instanceof Webserver, "Public constructor/subclass contract changed");
        HidingSubclass.init();
        HidingSubclass.start(null);
        HidingSubclass.stop();
        require(hiddenCalls == 3, "Java subclasses cannot hide the static lifecycle entrypoints");
        verifyCallbacks();

        final Path directory = Files.createTempDirectory("mtr-webserver-check-");
        final Path config = directory.resolve("mtr_webserver_port.txt");
        try {
            Webserver.stop(); // Uninitialized stop remains a no-op.
            Files.writeString(config, "0");
            verifyClassMonitor(Webserver::init);
            verifyClassMonitor(() -> Webserver.start(config));
            verifyClassMonitor(Webserver::stop);
            verifyCompanionOnlyNegativeControl();
            if (kotlinImplementation) verifyCompanionMonitors(config);
            if (locksOnly) {
                System.out.println("PASS: " + assertions + " Webserver Java/Companion shared class-monitor checks, including the Companion-only negative control");
                return;
            }
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
            Webserver.start(config);
            require(server().isStarted(), "Web map could not recover after a port conflict");
            final Server running = server();
            final int port = connector().getLocalPort();
            try (HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build()) {
                final HttpResponse<String> response = client.send(HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + "/info")).timeout(Duration.ofSeconds(3)).build(), HttpResponse.BodyHandlers.ofString());
                require(response.statusCode() == 200 && response.body().equals("[]"), "Real web map info endpoint did not respond after recovery");
            }
            Webserver.start(config);
            Webserver.start(null); // Already-started Java code does not inspect the path.
            require(server() == running && connector().getLocalPort() == port, "Repeated start replaced the live listener");
            Webserver.init();
            require(running.isStopped() && ((QueuedThreadPool) running.getThreadPool()).isStopped(), "Repeated init leaked the previous server");
            connector().setHost("127.0.0.1");
            for (String invalid : new String[] {"0", "+0", "0000", "\u00000\u0020", "-1", "1024", "65536", "port=8888", "", "\u00A00\u00A0"}) {
                Files.writeString(config, invalid);
                Webserver.start(config);
                require(server().isStopped() && connector().getLocalPort() < 0, "Disabled or invalid config opened a listener: " + invalid);
                require(Files.readString(config).equals(invalid), "Invalid config was silently overwritten");
            }
            Webserver.start(directory);
            require(Files.isDirectory(directory) && server().isStopped() && connector().getLocalPort() < 0, "Unreadable port file was replaced or opened a listener");
            try {
                Webserver.start(null);
                throw new AssertionError("Stopped-server null path no longer raises NullPointerException");
            } catch (NullPointerException expected) {
                assertions++;
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
            System.out.println("PASS: " + assertions + " Webserver checks: Java ABI/class-monitor/static hiding, callback ownership, real loopback HTTP, occupied-port recovery, disabled/invalid config and partial-start cleanup");
        } finally {
            Webserver.stop();
            Files.deleteIfExists(config);
            Files.deleteIfExists(directory);
        }
    }

    private static void verifyContract(Class<?> type) throws Exception {
        contract(Modifier.isPublic(type.getModifiers()) && Modifier.isAbstract(type.getModifiers()) && !Modifier.isFinal(type.getModifiers()), "Webserver must stay public, abstract and subclassable");
        contract(Modifier.isPublic(type.getDeclaredConstructor().getModifiers()), "Webserver constructor must stay public");
        for (String name : new String[] {"init", "start", "stop"}) {
            final var method = name.equals("start") ? type.getDeclaredMethod(name, Path.class) : type.getDeclaredMethod(name);
            final int modifiers = method.getModifiers();
            contract(Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers), "Lifecycle entrypoint changed its public/static/hiding contract: " + name);
            contract(method.getReturnType() == void.class && method.getExceptionTypes().length == 0, "Lifecycle descriptor or declared exceptions changed");
        }
        for (String name : new String[] {"callback", "getWorlds", "getRoutes", "getDataCache"}) {
            final Field field = type.getDeclaredField(name);
            contract(Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers()), "Callback fields must remain mutable public statics: " + name);
        }
    }

    private static void verifyCallbacks() throws Exception {
        final int[] calls = {0};
        Webserver.callback.accept(() -> calls[0]++);
        require(calls[0] == 1, "Default callback must execute synchronously");
        try { Webserver.callback.accept(null); throw new AssertionError("Null callback runnable was accepted"); }
        catch (NullPointerException expected) { assertions++; }
        final List<?> worlds = Webserver.getWorlds.get();
        require(worlds.isEmpty() && worlds != Webserver.getWorlds.get(), "Default worlds supplier must allocate independent mutable lists");
        worlds.add(null);
        require(Webserver.getWorlds.get().isEmpty(), "World supplier leaked a previous list");
        final Set<?> routes = Webserver.getRoutes.apply(null);
        require(routes.isEmpty() && routes != Webserver.getRoutes.apply(null), "Default routes function must ignore nullable input and return independent sets");
        routes.add(null);
        require(Webserver.getRoutes.apply(null).isEmpty() && Webserver.getDataCache.apply(null) == null, "Default route/cache callbacks changed");
        for (String name : new String[] {"callback", "getWorlds", "getRoutes", "getDataCache"}) {
            final Field field = Webserver.class.getDeclaredField(name);
            final Object previous = field.get(null);
            try { field.set(null, null); require(field.get(null) == null, "Java callers can no longer replace a callback with null"); }
            finally { field.set(null, previous); }
        }
    }

    private static void verifyClassMonitor(Runnable operation) throws Exception {
        verifyClassMonitor(Webserver.class, operation);
    }

    private static void verifyClassMonitor(Class<?> monitor, Runnable operation) throws Exception {
        final CountDownLatch entering = new CountDownLatch(1);
        final AtomicReference<Throwable> failure = new AtomicReference<>();
        final Thread worker = new Thread(() -> {
            entering.countDown();
            try { operation.run(); } catch (Throwable thrown) { failure.set(thrown); }
        }, "webserver-class-monitor-check");
        try {
            synchronized (monitor) {
                worker.start();
                require(entering.await(3, TimeUnit.SECONDS), "Monitor worker did not start");
                final long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(3);
                ThreadInfo info;
                do {
                    info = ManagementFactory.getThreadMXBean().getThreadInfo(worker.threadId());
                    if (info == null || info.getThreadState() == Thread.State.BLOCKED) break;
                    Thread.onSpinWait();
                } while (System.nanoTime() < deadline);
                contract(info != null && info.getThreadState() == Thread.State.BLOCKED && info.getLockInfo() != null
                    && info.getLockInfo().getIdentityHashCode() == System.identityHashCode(monitor), "Lifecycle entrypoint must acquire the shared class monitor, not just Companion: " + monitor.getName());
            }
        } finally {
            worker.join(5000);
        }
        require(!worker.isAlive() && failure.get() == null, "Lifecycle call failed or remained blocked after releasing the class monitor: " + failure.get());
    }

    private static void verifyCompanionMonitors(Path config) throws Exception {
        final Object companion = Webserver.class.getField("Companion").get(null);
        for (String name : new String[] {"init", "start", "stop"}) {
            final var method = name.equals("start") ? companion.getClass().getMethod(name, Path.class) : companion.getClass().getMethod(name);
            final Runnable operation = () -> {
                try {
                    if (name.equals("start")) method.invoke(companion, config); else method.invoke(companion);
                } catch (ReflectiveOperationException exception) {
                    throw new AssertionError("Actual Companion entrypoint failed: " + name, exception);
                }
            };
            // Exercise the actual Kotlin entrypoint before inspecting implementation flags,
            // so the old @JvmStatic/@Synchronized implementation fails behaviorally.
            verifyClassMonitor(operation);
            require(!Modifier.isSynchronized(method.getModifiers()), "Companion must not introduce a second implicit monitor: " + name);
            final CountDownLatch finished = new CountDownLatch(1);
            final AtomicReference<Throwable> failure = new AtomicReference<>();
            final Thread worker = new Thread(() -> {
                try { operation.run(); } catch (Throwable thrown) { failure.set(thrown); }
                finally { finished.countDown(); }
            }, "webserver-no-companion-monitor-check");
            try {
                synchronized (companion) {
                    worker.start();
                    require(finished.await(3, TimeUnit.SECONDS), "Lifecycle entrypoint incorrectly locks Companion: " + name);
                }
            } finally {
                worker.join(5000);
            }
            require(!worker.isAlive() && failure.get() == null, "Companion lifecycle call failed: " + failure.get());
        }
    }

    private static void verifyCompanionOnlyNegativeControl() throws Exception {
        // Reproduce the old generated bridge: Java locks the class and delegates to
        // a synchronized companion; direct Kotlin calls bypass the class monitor.
        verifyClassMonitor(CompanionOnlyContract.class, CompanionOnlyContract::stop);
        try {
            verifyClassMonitor(CompanionOnlyContract.class, CompanionOnlyContract.companion::stop);
            throw new AssertionError("Negative control accepted Companion-only synchronization");
        } catch (ContractFailure expected) {
            require(expected.getMessage().contains("shared class monitor"), "Negative control failed for an unrelated reason");
        }
    }

    public static final class CompanionOnlyContract {
        private static final Companion companion = new Companion();
        public static synchronized void stop() { companion.stop(); }
        private static final class Companion {
            public synchronized void stop() { }
        }
    }

    public static final class HidingSubclass extends Webserver {
        public static void init() { hiddenCalls++; }
        public static void start(Path path) { hiddenCalls++; }
        public static void stop() { hiddenCalls++; }
    }

    public abstract static class BrokenStaticContract {
        public BrokenStaticContract() { }
        public static final void init() { }
        public static final void start(Path path) { }
        public static final void stop() { }
    }

    private static final class ContractFailure extends AssertionError {
        ContractFailure(String message) { super(message); }
    }
    private static void contract(boolean condition, String message) { assertions++; if (!condition) throw new ContractFailure(message); }
    private static Server server() throws Exception { return (Server) field("webServer"); }
    private static ServerConnector connector() throws Exception { return (ServerConnector) field("serverConnector"); }
    private static Object field(String name) throws Exception {
        final Field field = Webserver.class.getDeclaredField(name);
        field.setAccessible(true);
        return field.get(null);
    }
    private static void require(boolean condition, String message) { assertions++; if (!condition) throw new AssertionError(message); }
}

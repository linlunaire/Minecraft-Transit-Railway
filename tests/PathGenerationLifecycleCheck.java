package mtr.path;

import dev.architectury.event.events.common.LifecycleEvent;
import mtr.data.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.objectweb.asm.*;
import sun.misc.Unsafe;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/** Real event invokers and request registration; constructor-free worlds never open a save. */
public final class PathGenerationLifecycleCheck {
    private static int assertions;
    private static Unsafe unsafe;
    private static Class<?> serverType, worldType;

    public static void main(String[] args) throws Exception {
        net.minecraft.SharedConstants.tryDetectVersion(); net.minecraft.server.Bootstrap.bootStrap();
        verifyEntryPoint();
        if (args.length > 0 && !args[0].startsWith("--")) for (Class<?> type : List.of(PathGenerationLifecycle.class, PathGenerationTask.class, RailwayDataPathGenerationModule.class)) {
            require(Path.of(type.getProtectionDomain().getCodeSource().getLocation().toURI()).toRealPath().equals(Path.of(args[0]).toRealPath()), "Lifecycle dependency escaped selected artifact: " + type);
        }
        if (!Arrays.asList(args).contains("--without-events")) {
            PathGenerationLifecycle.install(); PathGenerationLifecycle.install();
        }
        var field = Unsafe.class.getDeclaredField("theUnsafe"); field.setAccessible(true);
        unsafe = (Unsafe) field.get(null);
        ShellLoader loader = new ShellLoader();
        serverType = loader.server(); worldType = loader.world();
        MinecraftServer server = server(), otherServer = server();
        Context first = new Context(world(server)), otherWorld = new Context(world(server)), unrelated = new Context(world(otherServer));
        first.generate(); otherWorld.generate(); unrelated.generate();
        require(first.queue.size() == 1 && first.request.get().isCurrent(), "Fixture did not queue a live managed request");
        LifecycleEvent.SERVER_LEVEL_UNLOAD.invoker().act(first.world);
        first.queue.forEach(Runnable::run);
        require(first.delivered.isEmpty(), "World unload allowed completed worker's queued notification");
        require(!first.request.get().isCurrent(), "Unloaded request stayed current");
        require(otherWorld.request.get().isCurrent() && unrelated.request.get().isCurrent(), "Unload cancelled another world/server");
        first.generate(); require(first.depot.calls == 1 && first.data.resets == 1, "Closed module admitted work/reset train delays");
        Context late = new Context(first.world); late.generate();
        require(late.depot.calls == 0, "New module reopened the same unloaded world object");
        Context reloaded = new Context(world(server)); reloaded.generate();
        require(reloaded.request.get().isCurrent(), "A new world instance inherited an old world's tombstone");
        Context replacement = new Context(reloaded.world);
        require(!reloaded.request.get().isCurrent(), "Replacing a world's data module retained its old request");
        replacement.generate();

        activeWorkers(server);
        lateRegistration(server);
        LifecycleEvent.SERVER_STOPPING.invoker().stateChanged(server);
        otherWorld.queue.forEach(Runnable::run); replacement.queue.forEach(Runnable::run);
        require(otherWorld.delivered.isEmpty() && replacement.delivered.isEmpty(), "Server stop allowed pending status");
        require(unrelated.request.get().isCurrent(), "Stopping one server cancelled a different server instance");
        unrelated.queue.forEach(Runnable::run); require(unrelated.delivered.size() == 1, "Unrelated server failed to publish");
        Context afterStop = new Context(world(server)); afterStop.generate();
        require(afterStop.depot.calls == 0, "New world admitted generation after its server stopped");
        // Unknown worlds/servers also acquire closed state without loading RailwayData.
        ServerLevel empty = world(otherServer); LifecycleEvent.SERVER_LEVEL_UNLOAD.invoker().act(empty);
        Context afterEmptyUnload = new Context(empty); afterEmptyUnload.generate();
        require(afterEmptyUnload.depot.calls == 0, "Unloaded empty world reopened");
        MinecraftServer neverLoaded = server(); LifecycleEvent.SERVER_STOPPING.invoker().stateChanged(neverLoaded);
        Context afterEmptyStop = new Context(world(neverLoaded)); afterEmptyStop.generate();
        require(afterEmptyStop.depot.calls == 0, "Stopped empty server reopened");
        LifecycleEvent.SERVER_STOPPING.invoker().stateChanged(server);
        LifecycleEvent.SERVER_LEVEL_UNLOAD.invoker().act(first.world);
        LifecycleEvent.SERVER_STOPPING.invoker().stateChanged(otherServer);
        System.out.println("PASS: real unload/stop events, completed/active/superseded workers, late registration, terminal admission, world/server isolation; assertions=" + assertions);
    }

    private static void activeWorkers(MinecraftServer server) throws Exception {
        Context context = new Context(world(server));
        WaitingWorker old = new WaitingWorker(), current = new WaitingWorker();
        try {
            context.depot.next = old; context.generate(); old.awaitStart();
            context.depot.next = current; context.generate(); current.awaitStart();
            require(old.interrupts == 1 && old.isAlive(), "Restart fixture did not retain an old live worker");
            LifecycleEvent.SERVER_LEVEL_UNLOAD.invoker().act(context.world);
            require(old.interrupts == 2 && current.interrupts == 1, "Unload did not interrupt every outstanding worker");
            require(!old.request.isCurrent() && !current.request.isCurrent(), "Unload left a worker's request current");
            require(old.isAlive() && current.isAlive(), "Close must not join workers; fixture waits for explicit release");
            LifecycleEvent.SERVER_LEVEL_UNLOAD.invoker().act(context.world);
            require(old.interrupts == 2 && current.interrupts == 1, "Repeated close re-interrupted workers");
        } finally { old.finish(); current.finish(); }
    }

    private static void lateRegistration(MinecraftServer server) throws Exception {
        Context context = new Context(world(server));
        context.depot.beforeRegister = () -> LifecycleEvent.SERVER_LEVEL_UNLOAD.invoker().act(context.world);
        context.generate();
        require(context.queue.isEmpty() && !context.request.get().isCurrent(), "Callback registered a live request after close");
        require(context.data.resets == 0, "Late callback reset train delays on an unloaded world");
    }

    private static MinecraftServer server() throws Exception { return (MinecraftServer) unsafe.allocateInstance(serverType); }
    private static ServerLevel world(MinecraftServer server) throws Exception {
        ServerLevel world = (ServerLevel) unsafe.allocateInstance(worldType); worldType.getField("server").set(world, server); return world;
    }

    private static void verifyEntryPoint() throws Exception {
        List<String> calls = new ArrayList<>();
        try (var input = PathGenerationLifecycleCheck.class.getClassLoader().getResourceAsStream("mtr/MTR.class")) {
            require(input != null, "Missing MTR initialization");
            new ClassReader(input).accept(new ClassVisitor(Opcodes.ASM9) {
                @Override public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                    if (!name.equals("init")) return null;
                    return new MethodVisitor(Opcodes.ASM9) {
                        @Override public void visitMethodInsn(int opcode, String owner, String method, String descriptor, boolean isInterface) { calls.add(owner + "." + method); }
                        @Override public void visitJumpInsn(int opcode, Label label) { calls.add("branch"); }
                        @Override public void visitFieldInsn(int opcode, String owner, String field, String descriptor) { calls.add("field"); }
                    };
                }
            }, ClassReader.SKIP_DEBUG);
        }
        require(!calls.isEmpty() && calls.getFirst().equals("mtr/path/PathGenerationLifecycle.install"), "Lifecycle registration is missing or conditional in MTR.init");
    }

    private static final class Context {
        final ServerLevel world; final RailwayProbe data; final DepotProbe depot = new DepotProbe();
        final RailwayDataPathGenerationModule module;
        final List<Runnable> queue = new ArrayList<>(); final List<String> delivered = new ArrayList<>();
        final AtomicReference<PathGenerationTask.Request> request = new AtomicReference<>();
        Context(ServerLevel world) throws Exception {
            this.world = world; data = (RailwayProbe) unsafe.allocateInstance(RailwayProbe.class);
            DataCache cache = new DataCache(Set.of(), Set.of(), Set.of(), Set.of(), Set.of(), Set.of());
            var field = RailwayData.class.getDeclaredField("dataCache"); field.setAccessible(true); field.set(data, cache);
            cache.depotIdMap.put(depot.id, depot);
            module = new RailwayDataPathGenerationModule(data, world, new HashMap<>());
            depot.work = () -> {
                request.set(PathGenerationTask.current());
                PathGenerationTask.publish(request.get(), queue::add, () -> delivered.add("done"));
            };
        }
        void generate() { module.generatePath(world.getServer(), depot.id); }
    }

    public static final class RailwayProbe extends RailwayData {
        int resets;
        private RailwayProbe() { super(null); }
        @Override public void resetTrainDelays(Depot depot) { resets++; }
    }
    public static final class DepotProbe extends Depot {
        Runnable work, beforeRegister; WaitingWorker next; int calls;
        DepotProbe() { super(1, TransportMode.TRAIN); }
        @Override public void generateMainRoute(MinecraftServer server, Level world, DataCache cache, Map<BlockPos, Map<BlockPos, Rail>> rails, Set<Siding> sidings, Consumer<Thread> callback) {
            calls++;
            if (beforeRegister != null) beforeRegister.run();
            if (next != null) { callback.accept(next); next.start(); return; }
            AtomicReference<Throwable> failure = new AtomicReference<>();
            Thread thread = new Thread(work); thread.setUncaughtExceptionHandler((ignored, error) -> failure.set(error));
            callback.accept(thread); thread.start();
            try { thread.join(5000); } catch (InterruptedException error) { throw new AssertionError(error); }
            require(!thread.isAlive() && failure.get() == null, "Fixture worker failed: " + failure.get());
        }
    }

    public static final class WaitingWorker extends Thread {
        volatile int interrupts; volatile PathGenerationTask.Request request;
        final CountDownLatch started = new CountDownLatch(1), release = new CountDownLatch(1);
        WaitingWorker() { setDaemon(true); }
        @Override public void run() {
            request = PathGenerationTask.current(); started.countDown();
            while (release.getCount() != 0) try { release.await(); } catch (InterruptedException ignored) { }
        }
        @Override public void interrupt() { interrupts++; super.interrupt(); }
        void awaitStart() throws InterruptedException { require(started.await(5, TimeUnit.SECONDS), "Worker failed to start"); }
        void finish() throws InterruptedException { release.countDown(); join(5000); require(!isAlive(), "Fixture worker leaked"); }
    }

    private static final class ShellLoader extends ClassLoader {
        ShellLoader() { super(PathGenerationLifecycleCheck.class.getClassLoader()); }
        Class<?> server() {
            ClassWriter writer = new ClassWriter(0);
            writer.visit(Opcodes.V25, Opcodes.ACC_PUBLIC, "mtr/path/LifecycleServerShell", null, "net/minecraft/server/MinecraftServer", null);
            writer.visitEnd(); byte[] bytes = writer.toByteArray(); return defineClass(null, bytes, 0, bytes.length);
        }
        Class<?> world() {
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            writer.visit(Opcodes.V25, Opcodes.ACC_PUBLIC, "mtr/path/LifecycleWorldShell", null, "net/minecraft/server/level/ServerLevel", null);
            writer.visitField(Opcodes.ACC_PUBLIC, "server", "Lnet/minecraft/server/MinecraftServer;", null, null).visitEnd();
            MethodVisitor method = writer.visitMethod(Opcodes.ACC_PUBLIC, "getServer", "()Lnet/minecraft/server/MinecraftServer;", null, null);
            method.visitCode(); method.visitVarInsn(Opcodes.ALOAD, 0);
            method.visitFieldInsn(Opcodes.GETFIELD, "mtr/path/LifecycleWorldShell", "server", "Lnet/minecraft/server/MinecraftServer;");
            method.visitInsn(Opcodes.ARETURN); method.visitMaxs(0, 0); method.visitEnd(); writer.visitEnd();
            byte[] bytes = writer.toByteArray(); return defineClass(null, bytes, 0, bytes.length);
        }
    }
    private static void require(boolean value, String message) { assertions++; if (!value) throw new AssertionError(message); }
}

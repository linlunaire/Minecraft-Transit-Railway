package mtr.path;

import mtr.data.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import org.msgpack.core.MessagePack;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.*;
import java.util.jar.JarFile;

/** Original Siding algorithm oracle plus the extracted production planning interface. */
public final class SidingRoutePlanCheck {
    private static final String SCENARIO = "mtr.path.SidingRoutePlanCheck$Scenario";
    private static final String BASELINE_SHA = "15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec";

    public static void main(String[] args) throws Exception {
        if (args.length < 2) throw new IllegalArgumentException("golden source [--java-baseline] [--record] [--module-source=classes]");
        Path source = Path.of(args[1]).toRealPath(), moduleSource = source;
        boolean baseline = Arrays.asList(args).contains("--java-baseline"), record = Arrays.asList(args).contains("--record");
        boolean negativeControl = Arrays.asList(args).contains("--negative-control");
        boolean publicationCheck = Arrays.asList(args).contains("--publication-check");
        for (String arg : args) if (arg.startsWith("--module-source=")) moduleSource = Path.of(arg.substring(16)).toRealPath();
        if (baseline) {
            require(Files.isRegularFile(source), "Java baseline must be the frozen release JAR");
            String hash = HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(source)));
            require(hash.equals(BASELINE_SHA), "Wrong original Java artifact: " + hash);
        }
        require(!record || baseline, "Only pinned original Java may record the oracle");
        require(!negativeControl || !baseline, "Negative control must never alter the original Java oracle");
        Map<String, byte[]> selected = new HashMap<>();
        select(source, "mtr/data/Siding", selected);
        select(baseline ? source : moduleSource, "mtr/path/PathFinder", selected);
        select(baseline ? source : moduleSource, "mtr/path/PathData", selected);
        if (!baseline) {
            select(moduleSource, "mtr/path/SidingRoutePlan", selected);
            for (Class<?> dependency : List.of(Rail.class, SavedRailBase.class, Platform.class, RailAngle.class, RailType.class, TransportMode.class, PathGenerationTask.class)) {
                Path actual = Path.of(dependency.getProtectionDomain().getCodeSource().getLocation().toURI()).toRealPath();
                require(actual.equals(source) || Files.isDirectory(source) && actual.equals(moduleSource), "Domain dependency escaped selected artifact: " + dependency.getName() + " at " + actual);
            }
        }
        require(selected.containsKey("mtr.data.Siding"), "Missing selected Siding");
        require(selected.containsKey(baseline ? "mtr.path.PathFinder" : "mtr.path.SidingRoutePlan"), "Missing selected planner/search implementation");
        if (!baseline) {
            boolean[] kotlin = {false};
            new ClassReader(selected.get("mtr.path.SidingRoutePlan")).accept(new ClassVisitor(Opcodes.ASM9) {
                @Override public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) { if (descriptor.equals("Lkotlin/Metadata;")) kotlin[0] = true; return null; }
            }, ClassReader.SKIP_CODE);
            require(kotlin[0], "Planning module is not the Kotlin production implementation");
        }
        int[] timeSites = {0}, queueSites = {0}, closureSites = {0}, mutations = {0};
        for (var entry : selected.entrySet()) {
            ClassWriter relocated = new ClassWriter(0);
            new ClassReader(entry.getValue()).accept(new ClassRemapper(relocated, new Remapper(Opcodes.ASM9) {
                @Override public String map(String name) { return name.startsWith("mtr/libraries/") ? name.substring(14) : name; }
            }), 0);
            ClassWriter writer = new ClassWriter(0);
            new ClassReader(relocated.toByteArray()).accept(new ClassVisitor(Opcodes.ASM9, writer) {
                @Override public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    MethodVisitor delegate = super.visitMethod(access, name, descriptor, signature, exceptions);
                    if (negativeControl && entry.getKey().equals("mtr.path.SidingRoutePlan") && name.equals("compute")) return new MethodVisitor(Opcodes.ASM9, delegate) {
                        @Override public void visitInsn(int opcode) {
                            if (opcode == Opcodes.IADD) { mutations[0]++; super.visitInsn(Opcodes.ISUB); }
                            else super.visitInsn(opcode);
                        }
                    };
                    if (!entry.getKey().equals("mtr.data.Siding") || !name.equals("generateRoute")) return delegate;
                    return new MethodVisitor(Opcodes.ASM9, delegate) {
                        @Override public void visitMethodInsn(int opcode, String owner, String method, String desc, boolean isInterface) {
                            if (owner.equals("mtr/data/Siding") && method.equals("generateTimeSegments")) {
                                require(desc.equals("(Ljava/util/List;Ljava/util/List;Ljava/util/Map;)V"), "Time-stage seam changed");
                                timeSites[0]++;
                                super.visitMethodInsn(Opcodes.INVOKESTATIC, SCENARIO.replace('.', '/'), "timeStage", "(Lmtr/data/Siding;Ljava/util/List;Ljava/util/List;Ljava/util/Map;)V", false);
                            } else if (owner.equals("net/minecraft/server/MinecraftServer") && method.equals("execute")) {
                                require(desc.equals("(Ljava/lang/Runnable;)V"), "Server queue seam changed");
                                queueSites[0]++;
                                super.visitMethodInsn(Opcodes.INVOKESTATIC, SCENARIO.replace('.', '/'), "enqueue", "(Lnet/minecraft/server/MinecraftServer;Ljava/lang/Runnable;)V", false);
                            } else super.visitMethodInsn(opcode, owner, method, desc, isInterface);
                        }
                        @Override public void visitInvokeDynamicInsn(String name, String desc, Handle bootstrap, Object... arguments) {
                            if (name.equals("run") && !publicationCheck) {
                                String guard = baseline ? "" : "Ljava/util/function/BooleanSupplier;";
                                require(desc.equals("(Lmtr/data/Siding;" + guard + "Ljava/util/List;Ljava/util/Map;Ljava/util/List;Ljava/util/Map;II)Ljava/lang/Runnable;"), "Publication closure seam changed: " + desc);
                                closureSites[0]++;
                                super.visitMethodInsn(Opcodes.INVOKESTATIC, SCENARIO.replace('.', '/'), "publication", desc, false);
                            } else super.visitInvokeDynamicInsn(name, desc, bootstrap, arguments);
                        }
                    };
                }
            }, 0);
            entry.setValue(writer.toByteArray());
        }
        require(timeSites[0] == 1 && queueSites[0] == 1 && closureSites[0] == (publicationCheck ? 0 : 1), "Expected external stage/publication seams");
        require(!negativeControl || mutations[0] > 0, "Negative control did not modify any result arithmetic");
        ClassLoader loader = new ClassLoader(SidingRoutePlanCheck.class.getClassLoader()) {
            @Override protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                byte[] bytes = selected.get(name);
                if (bytes == null && (name.equals(SCENARIO) || name.startsWith(SCENARIO + "$"))) {
                    try (InputStream input = getParent().getResourceAsStream(name.replace('.', '/') + ".class")) {
                        if (input == null) throw new ClassNotFoundException(name); bytes = input.readAllBytes();
                    } catch (java.io.IOException error) { throw new ClassNotFoundException(name, error); }
                }
                if (bytes == null) return super.loadClass(name, resolve);
                synchronized (getClassLoadingLock(name)) {
                    Class<?> result = findLoadedClass(name); if (result == null) result = defineClass(name, bytes, 0, bytes.length);
                    if (resolve) resolveClass(result); return result;
                }
            }
        };
        String actual;
        if (publicationCheck) {
            try { Class.forName(SCENARIO, true, loader).getMethod("checkPublication").invoke(null); }
            catch (InvocationTargetException error) { throw new AssertionError("Selected Siding publication failed", error.getCause()); }
            return;
        }
        try { actual = (String) Class.forName(SCENARIO, true, loader).getMethod("run", boolean.class, Path.class).invoke(null, baseline, Path.of(args[0])); }
        catch (InvocationTargetException error) { throw new AssertionError("Selected Siding planning failed", error.getCause()); }
        if (record) Files.writeString(Path.of(args[0]), actual);
        else require(Files.readString(Path.of(args[0])).replace("\r\n", "\n").equals(actual), "Siding planning differs from original Java oracle\n" + actual);
        System.out.println("PASS: " + (baseline ? "original Java" : "Kotlin planning interface + Siding adapter") + "; real search/append, ordered time/queue boundary, retained aliases; no server thread or train publication executed");
    }

    private static void select(Path source, String prefix, Map<String, byte[]> selected) throws Exception {
        if (Files.isDirectory(source)) {
            Path directory = source.resolve(prefix.substring(0, prefix.lastIndexOf('/')));
            String simple = prefix.substring(prefix.lastIndexOf('/') + 1);
            try (var paths = Files.list(directory)) {
                for (Path file : paths.filter(path -> path.getFileName().toString().equals(simple + ".class") || path.getFileName().toString().startsWith(simple + "$") && path.toString().endsWith(".class")).toList()) {
                    String name = prefix.substring(0, prefix.lastIndexOf('/') + 1) + file.getFileName();
                    selected.put(name.replace('/', '.').replace(".class", ""), Files.readAllBytes(file));
                }
            }
        } else try (JarFile jar = new JarFile(source.toFile())) {
            for (var entries = jar.entries(); entries.hasMoreElements();) {
                var entry = entries.nextElement();
                if (entry.getName().equals(prefix + ".class") || entry.getName().startsWith(prefix + "$") && entry.getName().endsWith(".class")) {
                    try (InputStream input = jar.getInputStream(entry)) { selected.put(entry.getName().replace('/', '.').replace(".class", ""), input.readAllBytes()); }
                }
            }
        }
    }
    private static void require(boolean value, String message) { if (!value) throw new AssertionError(message); }

    /** Shares the selected Siding's runtime package/loader, while retaining real domain objects. */
    public static final class Scenario {
        private static final RuntimeException FAILURE = new IllegalStateException("external stage failure");
        private static final List<String> events = new ArrayList<>();
        private static List<PathData> capturedPath;
        private static List<?> capturedTimes;
        private static Map<?, ?> capturedPlatformTimes;
        private static int repeat1, repeat2, assertions;
        private static String fault;
        private static Runnable pending;
        private static boolean realPublication;

        public static void checkPublication() throws Exception {
            realPublication = true;
            Fixture input = fixture(new Case("publication", "same", 4, false));
            var field = Siding.class.getDeclaredField("path"); field.setAccessible(true);
            @SuppressWarnings("unchecked") List<PathData> live = (List<PathData>) field.get(input.siding);
            live.addAll(input.mainPath);
            Depot depot = new Depot(TransportMode.TRAIN);
            input.siding.setSidingData(null, depot, input.rails);
            reset(null);
            input.siding.generateRoute(null, input.mainPath, 4, input.rails, input.first, input.last, false, 256, true);
            check(pending != null && !capturedPath.isEmpty(), "No real publication queued");
            input.siding.setSidingData(null, depot, input.rails);
            pending.run();
            check(describe(live).equals(describe(capturedPath)), "Routine same-binding refresh discarded valid result");
            check(field.get(input.siding) == live, "Publication replaced live path container");
            for (String change : List.of("detach", "reattach", "replace")) {
                input.siding.generateRoute(null, input.mainPath, 4, input.rails, input.first, input.last, false, 256, true);
                Runnable stale = pending;
                if (change.equals("replace")) input.siding.setSidingData(null, new Depot(depot.id, TransportMode.TRAIN), input.rails);
                else {
                    input.siding.setSidingData(null, null, input.rails);
                    check(live.isEmpty(), "Detach did not clear path");
                    if (change.equals("reattach")) {
                        live.addAll(input.mainPath); // Fixture avoids unrelated default-train creation.
                        input.siding.setSidingData(null, depot, input.rails);
                    }
                }
                String before = describe(live);
                stale.run();
                check(before.equals(describe(live)), "Stale queued publication changed binding after " + change);
                live.clear(); live.addAll(input.mainPath);
                input.siding.setSidingData(null, depot, input.rails);
            }

            // Drive real request registration/restart, with a controllable generator adapter and real Siding commit.
            var unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe"); unsafeField.setAccessible(true);
            var unsafe = (sun.misc.Unsafe) unsafeField.get(null);
            RailwayProbe railway = (RailwayProbe) unsafe.allocateInstance(RailwayProbe.class);
            DataCache cache = new DataCache(Set.of(), Set.of(), Set.of(), Set.of(), Set.of(), Set.of());
            for (String name : List.of("dataCache", "sidings")) {
                var member = RailwayData.class.getDeclaredField(name); member.setAccessible(true);
                member.set(railway, name.equals("dataCache") ? cache : new HashSet<>(List.of(input.siding)));
            }
            DepotProbe managed = new DepotProbe();
            cache.depotIdMap.put(managed.id, managed);
            input.siding.setSidingData(null, managed, input.rails);
            RailwayDataPathGenerationModule module = new RailwayDataPathGenerationModule(railway, null, input.rails);
            List<Runnable> notifications = new ArrayList<>();
            List<String> delivered = new ArrayList<>();
            managed.work = () -> {
                input.siding.generateRoute(null, input.mainPath, 4, input.rails, input.first, input.last, false, 256, true);
                PathGenerationTask.publish(PathGenerationTask.current(), notifications::add, () -> delivered.add("done"));
            };
            module.generatePath(null, managed.id);
            Runnable old = pending;
            check(notifications.size() == 1 && delivered.isEmpty(), "Managed status did not cross owner queue");
            input.mainPath.set(1, part(input.rails, 3, 4, 333));
            module.generatePath(null, managed.id); // Old worker has exited; cancellation must still invalidate its queue.
            pending.run();
            String fresh = describe(live);
            check(fresh.equals(describe(capturedPath)), "Latest managed result did not publish");
            old.run();
            check(fresh.equals(describe(live)), "Finished old worker overwrote a newer request");
            notifications.forEach(Runnable::run);
            check(delivered.equals(List.of("done")), "Stale request delivered completion status");
            input.mainPath.set(1, part(input.rails, 3, 4, 444));
            module.generatePath(null, managed.id);
            old = pending;
            managed.fail = true;
            try { module.generatePath(null, managed.id); throw new AssertionError("Expected rejected replacement"); }
            catch (IllegalStateException expected) { check(expected.getMessage().equals("fixture launch failure"), "Unexpected launch failure"); }
            old.run();
            check(fresh.equals(describe(live)), "Failed replacement revived the previous request");

            // Ownership changed before this old depot's worker even reached the siding.
            managed.fail = false;
            input.siding.setSidingData(null, new Depot(999, TransportMode.TRAIN), input.rails);
            reset(null);
            managed.work = () -> {
                try {
                    input.siding.generateRoute(null, input.mainPath, 4, input.rails, input.first, input.last, false, 256, true);
                    throw new AssertionError("Old depot began a route for an already rebound siding");
                } catch (java.util.concurrent.CancellationException expected) { }
            };
            module.generatePath(null, managed.id);
            check(pending == null && capturedPath == null, "Wrong-depot worker entered calculation/publication");

            // The hot-loop contract must preserve the interrupt flag and never return an empty-path failure.
            Thread.currentThread().interrupt();
            try {
                PathFinder.findPath(new ArrayList<>(), input.rails, new ArrayList<>(List.of(input.first, input.last)), 0, 256, true);
                throw new AssertionError("Interrupted search returned a route result");
            } catch (java.util.concurrent.CancellationException expected) {
                check(Thread.currentThread().isInterrupted(), "Search cleared the interrupt flag");
            } finally { Thread.interrupted(); }
            var integrate = Siding.class.getDeclaredMethod("generateTimeSegments", List.class, List.class, Map.class);
            integrate.setAccessible(true);
            InterruptingRail rail = new InterruptingRail();
            try {
                integrate.invoke(input.siding, List.of(new PathData(rail, 1, 0, pos(0), pos(1), 0)), new ArrayList<>(), new HashMap<>());
                throw new AssertionError("Interrupted timetable integration returned normally");
            } catch (InvocationTargetException expected) {
                check(expected.getCause() instanceof java.util.concurrent.CancellationException && rail.reads == 2 && Thread.currentThread().isInterrupted(), "Timetable did not cancel at integration loop");
            } finally { Thread.interrupted(); }
            System.out.println("PASS: real Siding publication, binding epochs, managed restart after worker exit, guarded owner notification, cancellation; assertions=" + assertions);
        }

        public static final class InterruptingRail extends Rail {
            public int reads;
            InterruptingRail() { super(new BlockPos(0, 64, 0), RailAngle.E, new BlockPos(10, 64, 0), RailAngle.W, RailType.IRON, TransportMode.TRAIN); }
            @Override public double getLength() { if (++reads == 2) Thread.currentThread().interrupt(); return 1_000_000; }
        }

        public static final class RailwayProbe extends RailwayData {
            private RailwayProbe() { super(null); }
            @Override public void resetTrainDelays(Depot ignored) { }
        }
        public static final class DepotProbe extends Depot {
            Runnable work; boolean fail;
            DepotProbe() { super(345, TransportMode.TRAIN); }
            @Override public void generateMainRoute(MinecraftServer server, net.minecraft.world.level.Level world, DataCache cache, Map<BlockPos, Map<BlockPos, Rail>> rails, Set<Siding> sidings, java.util.function.Consumer<Thread> callback) {
                if (fail) throw new IllegalStateException("fixture launch failure");
                var error = new java.util.concurrent.atomic.AtomicReference<Throwable>();
                Thread thread = new Thread(work);
                thread.setUncaughtExceptionHandler((worker, failure) -> error.set(failure));
                callback.accept(thread); thread.start();
                try { thread.join(5000); } catch (InterruptedException interrupted) { throw new AssertionError(interrupted); }
                check(!thread.isAlive() && error.get() == null, "Managed worker failed: " + error.get());
            }
        }

        public static String run(boolean baseline, Path fixture) throws Exception {
            List<String> records = new ArrayList<>();
            List<Case> cases = cases();
            for (Case scenario : cases) {
                Fixture input = fixture(scenario); reset(null);
                String before = describe(input.mainPath);
                String result;
                try {
                    int status = input.siding.generateRoute(null, input.mainPath, scenario.segments, input.rails, input.first, input.last, scenario.repeat, 256, true);
                    result = plan(status, capturedPath, repeat1, repeat2, input.mainPath);
                    check(events.equals(List.of("time", "publication", "enqueue")), "Compute/time/queue order: " + events);
                    check(capturedPath != input.mainPath && capturedTimes != null && capturedPlatformTimes != null && pending != null, "Fresh container or stage outputs lost");
                } catch (RuntimeException error) {
                    result = "throws:" + error.getClass().getSimpleName();
                    check(events.isEmpty(), "Failed planning entered external stages: " + events);
                }
                check(describe(input.mainPath).equals(before), "Input main path modified: " + scenario.label);
                records.add(scenario.label + "\t" + result + "\t" + events);
            }
            // These boundaries are deliberately not part of the pure computation contract.
            for (String stage : List.of("time", "enqueue")) {
                Fixture input = fixture(new Case("failure", "same", 4, true)); reset(stage);
                try { input.siding.generateRoute(null, input.mainPath, 4, input.rails, input.first, input.last, true, 256, true); throw new AssertionError("Expected stage failure"); }
                catch (RuntimeException error) { check(error == FAILURE, "Stage failure identity changed"); }
                check(events.equals(stage.equals("time") ? List.of("time") : List.of("time", "publication", "enqueue")), "Failure ordering changed");
                records.add("failure-" + stage + "\t" + events);
            }
            if (!baseline) interfaceChecks(cases, Files.readAllLines(fixture));
            System.out.println("Siding planning assertions=" + assertions + ", records=" + records.size());
            return String.join("\n", records) + "\n";
        }

        private static void interfaceChecks(List<Case> cases, List<String> oracle) throws Exception {
            Class<?> module = Class.forName("mtr.path.SidingRoutePlan", true, Scenario.class.getClassLoader());
            check(module.getClassLoader() == Scenario.class.getClassLoader(), "Planning module escaped selected artifact");
            var compute = module.getMethod("compute", SavedRailBase.class, List.class, int.class, Map.class, SavedRailBase.class, SavedRailBase.class, boolean.class, int.class, boolean.class);
            for (int i = 0; i < cases.size(); i++) {
                Case scenario = cases.get(i); Fixture input = fixture(scenario); reset(null);
                String before = describe(input.mainPath), actual;
                try {
                    Object result = compute.invoke(null, input.siding, input.mainPath, scenario.segments, input.rails, input.first, input.last, scenario.repeat, 256, true);
                    Class<?> type = result.getClass();
                    @SuppressWarnings("unchecked") List<PathData> path = (List<PathData>) type.getField("path").get(result);
                    actual = plan(type.getField("successfulSegments").getInt(result), path, type.getField("repeatIndex1").getInt(result), type.getField("repeatIndex2").getInt(result), input.mainPath);
                    check(path != input.mainPath, "Planner retained main path container");
                    for (PathData part : path) if (part != null && (input.mainPath == null || !input.mainPath.contains(part))) {
                        check(input.rails.values().stream().flatMap(map -> map.values().stream()).anyMatch(rail -> rail == part.rail), "Computed path cloned a borrowed rail");
                    }
                    path.clear(); check(describe(input.mainPath).equals(before), "Clearing result changed input path");
                } catch (InvocationTargetException error) { actual = "throws:" + error.getCause().getClass().getSimpleName(); }
                check(actual.equals(oracle.get(i).split("\t", 3)[1]), "Pure interface differs at " + scenario.label + ": " + actual);
                check(describe(input.mainPath).equals(before) && events.isEmpty(), "Pure planning mutated input or crossed external stage boundary");
            }
            Object unused = compute.invoke(null, null, null, 4, null, null, null, false, 256, true);
            check(unused.getClass().getField("successfulSegments").getInt(unused) == 0, "Unused nullable siding was eagerly dereferenced");
            Fixture required = fixture(new Case("null-siding", "same", 4, false));
            try { compute.invoke(null, null, required.mainPath, 4, required.rails, required.first, required.last, false, 256, true); throw new AssertionError("Required null siding accepted"); }
            catch (InvocationTargetException error) { check(error.getCause() instanceof NullPointerException, "Required null siding failure changed"); }
        }

        public static void timeStage(Siding siding, List<PathData> path, List<?> times, Map<?, ?> platformTimes) {
            events.add("time"); capturedPath = path; capturedTimes = times; capturedPlatformTimes = platformTimes;
            if ("time".equals(fault)) throw FAILURE;
        }
        public static Runnable publication(Siding siding, List<PathData> path, Map<?, ?> rails, List<?> times, Map<?, ?> platformTimes, int first, int second) {
            events.add("publication");
            check(path == capturedPath && times == capturedTimes && platformTimes == capturedPlatformTimes, "Publication copied/replaced earlier stage outputs");
            repeat1 = first; repeat2 = second;
            return pending = () -> { throw new AssertionError("Headless fixture must not execute train publication"); };
        }
        public static Runnable publication(Siding siding, java.util.function.BooleanSupplier guard, List<PathData> path, Map<?, ?> rails, List<?> times, Map<?, ?> platformTimes, int first, int second) {
            check(guard.getAsBoolean(), "Fresh publication was rejected");
            return publication(siding, path, rails, times, platformTimes, first, second);
        }
        public static void enqueue(MinecraftServer ignored, Runnable task) {
            events.add("enqueue");
            if (realPublication) pending = task;
            else check(task == pending, "Wrong publication task");
            if ("enqueue".equals(fault)) throw FAILURE;
        }
        private static void reset(String failure) { events.clear(); capturedPath = null; capturedTimes = null; capturedPlatformTimes = null; pending = null; repeat1 = repeat2 = -999; fault = failure; }

        private static List<Case> cases() {
            List<Case> result = new ArrayList<>();
            for (String kind : List.of("same", "opposite", "unrelated", "empty", "entry-unreachable", "exit-unreachable", "null-element", "null-tail"))
                for (boolean repeat : List.of(false, true)) for (int segments : new int[]{4, Integer.MAX_VALUE, Integer.MIN_VALUE})
                    result.add(new Case(kind + ":" + repeat + ":" + segments, kind, segments, repeat));
            for (String kind : List.of("null-first", "null-last", "both-missing", "null-main", "unused-null-main", "unreachable-null-main", "null-rails"))
                for (boolean repeat : List.of(false, true)) result.add(new Case(kind + ":" + repeat, kind, 4, repeat));
            return result;
        }
        private static Fixture fixture(Case scenario) {
            Map<BlockPos, Map<BlockPos, Rail>> rails = new LinkedHashMap<>();
            for (int i = 0; i <= 6; i++) rails.put(pos(i), new LinkedHashMap<>());
            for (int i = 0; i < 6; i++) connect(rails, i, i + 1);
            Siding siding = new Siding(99, TransportMode.TRAIN, pos(0), pos(1), 10);
            Platform first = new Platform(100, TransportMode.TRAIN, pos(2), pos(3));
            Platform last = new Platform(200, TransportMode.TRAIN, pos(5), pos(6));
            List<PathData> main = new ArrayList<>();
            if (!scenario.kind.equals("empty")) {
                if (scenario.kind.equals("opposite")) main.add(part(rails, 3, 2, 13));
                else if (scenario.kind.equals("unrelated")) main.add(new PathData(new Rail(pos(20), RailAngle.E, pos(21), RailAngle.W, RailType.IRON, TransportMode.TRAIN), 777, -3, pos(20), pos(21), -5));
                else main.add(part(rails, 2, 3, 13));
                main.add(part(rails, 3, 4, 14)); main.add(part(rails, 4, 5, 15)); main.add(part(rails, 5, 6, 16));
            }
            switch (scenario.kind) {
                case "entry-unreachable", "unreachable-null-main" -> { rails.get(pos(1)).remove(pos(2)); rails.get(pos(2)).remove(pos(1)); }
                case "exit-unreachable" -> { rails.get(pos(4)).remove(pos(5)); rails.get(pos(5)).remove(pos(4)); }
                case "null-element" -> main.set(0, null);
                case "null-tail" -> main.set(2, null);
                case "null-first" -> first = null;
                case "null-last" -> last = null;
                case "both-missing", "unused-null-main" -> { first = null; last = null; rails = null; }
                case "null-rails" -> rails = null;
            }
            if (scenario.kind.endsWith("null-main") || scenario.kind.equals("null-main")) main = null;
            return new Fixture(siding, main, rails, first, last);
        }
        private static PathData part(Map<BlockPos, Map<BlockPos, Rail>> rails, int a, int b, int tag) { return new PathData(rails.get(pos(a)).get(pos(b)), 1000 + tag, tag, pos(a), pos(b), -tag); }
        private static void connect(Map<BlockPos, Map<BlockPos, Rail>> rails, int a, int b) {
            rails.get(pos(a)).put(pos(b), new Rail(pos(a), RailAngle.E, pos(b), RailAngle.W, RailType.IRON, TransportMode.TRAIN));
            rails.get(pos(b)).put(pos(a), new Rail(pos(b), RailAngle.W, pos(a), RailAngle.E, RailType.IRON, TransportMode.TRAIN));
        }
        private static BlockPos pos(int value) { return new BlockPos(value * 10, 64, 0); }
        private static String plan(int status, List<PathData> path, int first, int second, List<PathData> main) throws Exception {
            check(path != null, "No computed path reached time stage");
            List<Integer> aliases = new ArrayList<>();
            for (PathData part : path) {
                int alias = -1;
                if (main != null) for (int i = 0; i < main.size(); i++) if (main.get(i) == part) { alias = i; break; }
                aliases.add(alias);
            }
            return status + ":" + first + ":" + second + ":" + describe(path) + ":aliases=" + aliases;
        }
        private static String describe(List<PathData> path) throws Exception {
            if (path == null) return "null";
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            for (PathData data : path) {
                if (data == null) digest.update((byte) 0);
                else {
                    digest.update((byte) 1);
                    try (var packer = MessagePack.newDefaultBufferPacker()) { data.toMessagePack(packer); digest.update(packer.toByteArray()); }
                }
            }
            return path.size() + ":" + HexFormat.of().formatHex(digest.digest());
        }
        public static void check(boolean value, String message) { assertions++; if (!value) throw new AssertionError(message); }
        public static final class Case {
            public final String label, kind;
            public final int segments;
            public final boolean repeat;
            public Case(String label, String kind, int segments, boolean repeat) { this.label = label; this.kind = kind; this.segments = segments; this.repeat = repeat; }
        }
        public static final class Fixture {
            public final Siding siding;
            public final List<PathData> mainPath;
            public final Map<BlockPos, Map<BlockPos, Rail>> rails;
            public final Platform first, last;
            public Fixture(Siding siding, List<PathData> mainPath, Map<BlockPos, Map<BlockPos, Rail>> rails, Platform first, Platform last) {
                this.siding = siding; this.mainPath = mainPath; this.rails = rails; this.first = first; this.last = last;
            }
        }
    }
}

import java.io.DataInputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.IdentityHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/** Invokes Transit Core using release JARs and the runtime nested in its shipped artifact. */
public final class KotlinCoreArtifactCheck {
    public static void main(String[] args) throws Exception {
        checkDependencyFloorRegression();
        if (args.length == 1 && args[0].equals("--check-dependency-floor")) return;
        require(args.length == 4 || args.length == 5, "Expected: releaseJar mtr|ante kotlinStdlibJar transitCoreLoaderJar [mtrReleaseJar]");
        Path artifact = Path.of(args[0]).toRealPath(), sharedArtifact = Path.of(args[3]).toRealPath();
        Path stdlib = Files.createTempFile("mtr-core-runtime-", ".jar");
        try {
        boolean mtr = args[1].equals("mtr");
        require(mtr || args[1].equals("ante"), "Unknown core mode: " + args[1]);
        require(mtr ? args.length == 4 : args.length == 5, "ANTE must be checked with its actual MTR dependency");
        try (JarFile jar = new JarFile(artifact.toFile()); JarFile transitCore = new JarFile(sharedArtifact.toFile())) {
            var nested = transitCore.stream().filter(entry -> entry.getName().startsWith("META-INF/jars/kotlin-stdlib-") && entry.getName().endsWith(".jar")).toList();
            require(nested.size() == 1, "Transit Core must supply exactly one stdlib");
            try (var input = transitCore.getInputStream(nested.getFirst())) {
                Files.copy(input, stdlib, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
            checkContents(jar, false);
            checkContents(transitCore, true);
            if (mtr) checkKotlinFixes(jar);
            checkClassVersion(transitCore, "io.github.linlunaire.transitcore.collection.FrameGeometryCache");
            checkClassVersion(transitCore, "io.github.linlunaire.transitcore.concurrent.BoundedTaskDispatcher");
            checkClassVersion(transitCore, "io.github.linlunaire.transitcore.collection.FrameMembership");
            checkMetadata(jar, mtr ? "mtr" : "mtrsteamloco");
            checkMetadata(transitCore, "transit_core");
            checkTransitCoreDependency(jar, transitCore, mtr ? "mtr" : "mtrsteamloco");
            if (!mtr) {
                Path mtrArtifact = Path.of(args[4]).toRealPath();
                checkMtrDependency(jar, mtrArtifact);
                try (JarFile mtrJar = new JarFile(mtrArtifact.toFile())) {
                    checkContents(mtrJar, false);
                    checkKotlinFixes(mtrJar);
                    checkTransitCoreDependency(mtrJar, transitCore, "mtr");
                }
            }
        }
        var urls = mtr ? new java.net.URL[]{artifact.toUri().toURL(), sharedArtifact.toUri().toURL(), stdlib.toUri().toURL()}
                : new java.net.URL[]{artifact.toUri().toURL(), sharedArtifact.toUri().toURL(), Path.of(args[4]).toRealPath().toUri().toURL(), stdlib.toUri().toURL()};
        try (URLClassLoader loader = new URLClassLoader(urls, ClassLoader.getPlatformClassLoader())) {
            Class<?> runtime = loader.loadClass("kotlin.jvm.internal.Intrinsics");
            require(Path.of(runtime.getProtectionDomain().getCodeSource().getLocation().toURI()).equals(stdlib), "Kotlin runtime did not come from the shared stdlib");
            checkCache(loadCore(loader, sharedArtifact, "io.github.linlunaire.transitcore.collection.FrameGeometryCache"));
            checkMembership(loadCore(loader, sharedArtifact, "io.github.linlunaire.transitcore.collection.FrameMembership"));
            checkScheduler(loadCore(loader, sharedArtifact, "io.github.linlunaire.transitcore.concurrent.BoundedTaskDispatcher"), loader);
        }
        System.out.println("PASS: " + artifact.getFileName() + " calls Java-25 Kotlin from its correct release owner, requires Transit Core and a shared runtime, and bundles neither shared core nor stdlib (not a loader/game startup)");
        } finally {
            Files.deleteIfExists(stdlib);
        }
    }

    private static Class<?> loadCore(ClassLoader loader, Path artifact, String className) throws Exception {
        Class<?> core = loader.loadClass(className);
        require(Path.of(core.getProtectionDomain().getCodeSource().getLocation().toURI()).equals(artifact), className + " must come from the prerequisite JAR");
        boolean kotlinMetadata = false;
        for (var annotation : core.getDeclaredAnnotations()) kotlinMetadata |= annotation.annotationType().getName().equals("kotlin.Metadata");
        require(kotlinMetadata, "Published core is not Kotlin-compiled (@Metadata missing): " + className);
        return core;
    }

    private static void checkContents(JarFile jar, boolean transitCore) {
        for (var entries = jar.entries(); entries.hasMoreElements();) {
            String name = entries.nextElement().getName();
            require(!(name.startsWith("kotlin/") && name.endsWith(".class")), "Bundled shared runtime class: " + name);
            require(transitCore || !(name.contains("kotlin-stdlib") && name.endsWith(".jar")), "Consumer bundles shared stdlib: " + name);
            require(!name.startsWith("mtr/core/"), "Obsolete embedded core package: " + name);
            require(!name.startsWith("cn/zbx1425/mtrsteamloco/core/"), "Obsolete embedded ANTE core package: " + name);
            require(!name.startsWith("cn/zbx1425/mtrsteamloco/render/rail/RailBuildScheduler"), "Obsolete Java scheduler in release JAR: " + name);
            require(transitCore || !name.startsWith("io/github/linlunaire/transitcore/"), "Consumer must not embed Transit Core: " + name);
            require(!name.equals("mtr/mappings/FrameGeometryCache.class"), "Obsolete Java cache in release JAR");
            require(!name.startsWith("legacy/"), "Historical compatibility sources must not enter 26.2 runtime: " + name);
            require(!name.startsWith("org/bukkit/") && !name.startsWith("dev/architectury/platform/Platform"), "Test-only map provider fixture in release JAR: " + name);
            require(!name.startsWith("mtr/data/LiftScenario") && !name.startsWith("mtr/data/LiftCompatibilityCheck"), "Lift regression fixture in release JAR: " + name);
        }
    }

    private static void checkClassVersion(JarFile jar, String className) throws Exception {
        String classPath = className.replace('.', '/') + ".class";
        require(jar.getJarEntry(classPath) != null, "Published JAR is missing " + classPath);
        try (DataInputStream input = new DataInputStream(jar.getInputStream(jar.getJarEntry(classPath)))) {
            require(input.readInt() == 0xCAFEBABE, "Invalid core class file");
            input.readUnsignedShort();
            require(input.readUnsignedShort() == 69, "Core bytecode must target Java 25 (major 69)");
        }
    }

    private static void checkKotlinFixes(JarFile jar) throws Exception {
        for (String name : new String[]{"mtr.servlet.Webserver", "mtr.servlet.WebMapSupport", "mtr.data.RealTimeSync", "mtr.mappings.BlockEntityTypeMapper", "mtr.mappings.TerrainMapGeometry"}) {
            checkClassVersion(jar, name);
            try (var input = jar.getInputStream(jar.getJarEntry(name.replace('.', '/') + ".class"))) {
                var model = java.lang.classfile.ClassFile.of().parse(input.readAllBytes());
                require(model.findAttribute(java.lang.classfile.Attributes.runtimeVisibleAnnotations())
                        .map(attribute -> attribute.annotations().stream().anyMatch(annotation -> annotation.className().equalsString("Lkotlin/Metadata;")))
                        .orElse(false), "Ported fix must be packaged as Kotlin, not a stale Java class: " + name);
            }
        }
    }

    private static void checkTransitCoreDependency(JarFile consumer, JarFile transitCore, String modId) throws Exception {
        boolean fabric = consumer.getJarEntry("fabric.mod.json") != null;
        String metadata = fabric ? "fabric.mod.json" : "META-INF/neoforge.mods.toml";
        require(transitCore.getJarEntry(metadata) != null, "Consumer and Transit Core must use the same loader");
        String provided = read(transitCore, metadata);
        require(Pattern.compile(fabric ? "\"id\"\\s*:\\s*\"transit_core\"" : "(?m)^modId\\s*=\\s*\"transit_core\"\\s*$").matcher(provided).find(), "Wrong prerequisite mod ID");
        var version = Pattern.compile(fabric ? "\"version\"\\s*:\\s*\"([^\"]+)\"" : "(?m)^version\\s*=\\s*\"([^\"]+)\"").matcher(provided);
        require(version.find() && version.group(1).matches("0\\.\\d+\\.\\d+"), "Missing or unsupported Transit Core version");
        String coreVersion = version.group(1), declared = read(consumer, metadata);
        if (fabric) {
            var depends = Pattern.compile("\"depends\"\\s*:\\s*\\{([^}]*)}", Pattern.DOTALL).matcher(declared);
            require(depends.find() && Pattern.compile("\"transit_core\"\\s*:\\s*\"" + Pattern.quote(">=" + coreVersion + " <1") + "\"").matcher(depends.group(1)).find(), "Transit Core dependency floor does not match the installed prerequisite");
        } else {
            boolean found = false;
            for (String section : declared.split("(?m)(?=^\\[\\[)")) {
                if (!section.startsWith("[[dependencies." + modId + "]]")) continue;
                if (!Pattern.compile("(?m)^modId\\s*=\\s*\"transit_core\"\\s*$").matcher(section).find()) continue;
                require(Pattern.compile("(?m)^type\\s*=\\s*\"required\"\\s*$").matcher(section).find(), "Transit Core must be required");
                require(Pattern.compile("(?m)^versionRange\\s*=\\s*\"" + Pattern.quote("[" + coreVersion + ",1)") + "\"\\s*$").matcher(section).find(), "Transit Core dependency floor does not match the installed prerequisite");
                require(Pattern.compile("(?m)^side\\s*=\\s*\"BOTH\"\\s*$").matcher(section).find(), "Transit Core must be required on both sides");
                found = true;
            }
            require(found, "Missing Transit Core dependency");
        }
    }

    private static void checkMetadata(JarFile jar, String modId) throws Exception {
        boolean fabric = jar.getJarEntry("fabric.mod.json") != null;
        boolean neo = jar.getJarEntry("META-INF/neoforge.mods.toml") != null;
        require(fabric || neo, "Missing loader metadata");
        String metadata = read(jar, fabric ? "fabric.mod.json" : "META-INF/neoforge.mods.toml");
        var identity = Pattern.compile(fabric ? "\"id\"\\s*:\\s*\"([^\"]+)\"" : "(?m)^modId\\s*=\\s*\"([^\"]+)\"").matcher(metadata);
        require(identity.find() && identity.group(1).equals(modId), "Branding must not change the compatibility mod ID: " + modId);
        if (!modId.equals("transit_core")) {
            String displayName = modId.equals("mtr") ? "YanlingMTR" : "YanlingMTR-ANTE";
            var name = Pattern.compile(fabric ? "\"name\"\\s*:\\s*\"([^\"]+)\"" : "(?m)^displayName\\s*=\\s*\"([^\"]+)\"").matcher(metadata);
            require(name.find() && name.group(1).equals(displayName), "Wrong branded mod display name: " + displayName);
            String prefix = modId.equals("mtr") ? "YanlingMTR-" : "YanlingMTR-ANTE-";
            require(Path.of(jar.getName()).getFileName().toString().startsWith(prefix + (fabric ? "fabric-" : "neoforge-")), "Wrong branded release filename");
        }
        if (modId.equals("mtr")) {
            for (String locale : new String[]{"en_us", "zh_cn"}) {
                String translations = read(jar, "assets/mtr/lang/" + locale + ".json");
                for (String key : new String[]{"gui.mtr.mtr_options", "gui.mtr.mismatched_versions", "key.category.mtr.keybinding", "itemGroup.mtr.core"}) {
                    require(Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"[^\"]*YanlingMTR")
                            .matcher(translations).find(), "Missing branded translation: " + locale + "/" + key);
                }
            }
            require(read(jar, "assets/mtr/website/index.html").contains("<title>YanlingMTR"), "Web map title uses the old brand");
            require(read(jar, "pack.mcmeta").contains("YanlingMTR resources"), "Resource pack description uses the old brand");
            System.out.println("PASS: YanlingMTR display name, artifact prefix, English/Chinese UI and web map; legacy mtr identity retained");
        }
        if (fabric) {
            String json = read(jar, "fabric.mod.json");
            var depends = Pattern.compile("\"depends\"\\s*:\\s*\\{([^}]*)}", Pattern.DOTALL).matcher(json);
            require(depends.find(), "Missing required Fabric dependencies");
            require(!depends.group(1).contains("fabric-language-kotlin"), "External Fabric Kotlin runtime is still required");
        }
        if (neo) {
            String toml = read(jar, "META-INF/neoforge.mods.toml");
            // Split at table headers: versionRange contains '[' and is not a section delimiter.
            for (String section : toml.split("(?m)(?=^\\[\\[)")) {
                if (!section.startsWith("[[dependencies." + modId + "]]")) continue;
                require(!Pattern.compile("(?m)^modId\\s*=\\s*\"kotlinforforge\"\\s*$").matcher(section).find(), "External NeoForge Kotlin runtime is still required");
            }
        }
    }

    private static String read(JarFile jar, String entry) throws Exception {
        try (var input = jar.getInputStream(jar.getJarEntry(entry))) { return new String(input.readAllBytes(), StandardCharsets.UTF_8); }
    }

    private static void checkDependencyFloorRegression() throws Exception {
        Path directory = Files.createTempDirectory("mtr-dependency-check-");
        Path mtr = directory.resolve("mtr.jar"), ante = directory.resolve("ante.jar");
        try {
            for (boolean fabric : new boolean[]{true, false}) {
                String entry = fabric ? "fabric.mod.json" : "META-INF/neoforge.mods.toml";
                for (String version : new String[]{"26.2-3.4.0-kotlin.1", "26.2-3.4.0-kotlin.2", "26.2-3.4.1"}) {
                    writeMetadataFixture(mtr, entry, fabric ? "{\"version\":\"" + version + "\"}" : "version=\"" + version + "\"\n");
                    for (boolean matches : new boolean[]{true, false}) {
                        String floor = matches ? version : "26.2-3.3.3";
                        String metadata = fabric ? "{\"depends\":{\"mtr\":\">=" + floor + " <26.3\"}}"
                                : "[[dependencies.mtrsteamloco]]\nmodId=\"mtr\"\nversionRange=\"[" + floor + ",26.3)\"\n";
                        writeMetadataFixture(ante, entry, metadata);
                        try (JarFile jar = new JarFile(ante.toFile())) {
                            try {
                                checkMtrDependency(jar, mtr);
                                require(matches, "Incorrect dependency floor was accepted");
                            } catch (AssertionError failure) {
                                if (matches || !failure.getMessage().startsWith("ANTE dependency floor must match")) throw failure;
                            }
                        }
                    }
                }
                writeMetadataFixture(mtr, fabric ? "META-INF/neoforge.mods.toml" : "fabric.mod.json", "wrong loader");
                try (JarFile jar = new JarFile(ante.toFile())) {
                    try {
                        checkMtrDependency(jar, mtr);
                        throw new AssertionError("Mixed loaders were accepted");
                    } catch (AssertionError failure) {
                        require(failure.getMessage().equals("ANTE and MTR must use the same loader"), "Wrong mixed-loader failure");
                    }
                }
            }
            System.out.println("PASS: 14 dependency metadata cases accept matching preview/stable floors and reject stale floors or mixed loaders");
        } finally {
            Files.deleteIfExists(mtr);
            Files.deleteIfExists(ante);
            Files.deleteIfExists(directory);
        }
    }

    private static void writeMetadataFixture(Path artifact, String entry, String metadata) throws Exception {
        try (var zip = new java.util.zip.ZipOutputStream(Files.newOutputStream(artifact))) {
            zip.putNextEntry(new java.util.zip.ZipEntry(entry));
            zip.write(metadata.getBytes(StandardCharsets.UTF_8));
            zip.closeEntry();
        }
    }

    private static void checkMtrDependency(JarFile ante, Path mtrArtifact) throws Exception {
        boolean fabric = ante.getJarEntry("fabric.mod.json") != null;
        String metadata = fabric ? "fabric.mod.json" : "META-INF/neoforge.mods.toml";
        try (JarFile mtr = new JarFile(mtrArtifact.toFile())) {
            require(mtr.getJarEntry(metadata) != null, "ANTE and MTR must use the same loader");
            var version = Pattern.compile(fabric ? "\"version\"\\s*:\\s*\"([^\"]+)\"" : "(?m)^version\\s*=\\s*\"([^\"]+)\"")
                    .matcher(read(mtr, metadata));
            require(version.find(), "MTR version is missing");
            String mtrVersion = version.group(1);
            var parts = Pattern.compile("26\\.2-(\\d+)\\.(\\d+)\\.(\\d+)(?:[-+].*)?").matcher(mtrVersion);
            require(parts.matches(), "Unexpected MTR version: " + mtrVersion);
            int major = Integer.parseInt(parts.group(1)), minor = Integer.parseInt(parts.group(2)), patch = Integer.parseInt(parts.group(3));
            require(major > 3 || major == 3 && minor >= 4,
                    "This Kotlin ANTE integration requires MTR 26.2-3.4.0-kotlin.1 or newer");
            String declared = read(ante, metadata);
            if (fabric) {
                var depends = Pattern.compile("\"depends\"\\s*:\\s*\\{([^}]*)}", Pattern.DOTALL).matcher(declared);
                require(depends.find() && Pattern.compile("\"mtr\"\\s*:\\s*\"" + Pattern.quote(">=" + mtrVersion + " <26.3") + "\"")
                        .matcher(depends.group(1)).find(), "ANTE dependency floor must match the tested MTR artifact: " + mtrVersion);
            } else {
                boolean found = false;
                for (String section : declared.split("(?m)(?=^\\[\\[)")) {
                    if (!section.startsWith("[[dependencies.mtrsteamloco]]")) continue;
                    if (!Pattern.compile("(?m)^modId\\s*=\\s*\"mtr\"\\s*$").matcher(section).find()) continue;
                    require(Pattern.compile("(?m)^versionRange\\s*=\\s*\"" + Pattern.quote("[" + mtrVersion + ",26.3)") + "\"\\s*$")
                            .matcher(section).find(), "ANTE dependency floor must match the tested MTR artifact: " + mtrVersion);
                    found = true;
                }
                require(found, "ANTE is missing its MTR dependency");
            }
        }
    }

    private static void checkCache(Class<?> core) throws Exception {
        IdentityHashMap<Object, Boolean> disposed = new IdentityHashMap<>();
        Consumer<Object> close = value -> require(disposed.put(value, Boolean.TRUE) == null, "Cache disposed one value twice");
        Object cache = core.getConstructor(long.class, int.class, ToLongFunction.class, Consumer.class)
                .newInstance(100L, 120, (ToLongFunction<Object>) value -> 80, close);
        Method begin = core.getMethod("beginFrame"), get = core.getMethod("get", Object.class, Function.class);
        Method finish = core.getMethod("finishFrame"), dispose = core.getMethod("close");
        Object firstKey = new String("equal"), secondKey = new String("equal");
        Function<Object, Object> create = ignored -> new Object();
        begin.invoke(cache);
        Object first = get.invoke(cache, firstKey, create), second = get.invoke(cache, secondKey, create);
        require(first != second && get.invoke(cache, firstKey, create) == first, "Cache lost identity-key semantics");
        finish.invoke(cache);
        require(disposed.isEmpty(), "Cache retired active geometry over its soft budget");
        begin.invoke(cache);
        require(get.invoke(cache, firstKey, create) == first, "Warm cache recreated geometry");
        finish.invoke(cache);
        require(disposed.size() == 1 && disposed.containsKey(second), "Inactive cache entry was not retired");
        dispose.invoke(cache);
        dispose.invoke(cache);
        require(disposed.size() == 2 && disposed.containsKey(first), "Cache close leaked geometry");
    }

    private static void checkMembership(Class<?> core) throws Exception {
        Object membership = core.getConstructor().newInstance();
        Method mark = core.getMethod("mark", Object.class);
        Method reconcile = core.getMethod("reconcile", Consumer.class, Consumer.class);
        AtomicInteger added = new AtomicInteger(), removed = new AtomicInteger();
        Consumer<Object> onAdd = ignored -> added.incrementAndGet(), onRemove = ignored -> removed.incrementAndGet();
        mark.invoke(membership, new String("equal"));
        mark.invoke(membership, new String("equal"));
        reconcile.invoke(membership, onAdd, onRemove);
        require(added.get() == 1 && removed.get() == 0, "Shared membership must retain equality semantics");
        mark.invoke(membership, new String("equal"));
        reconcile.invoke(membership, onAdd, onRemove);
        require(added.get() == 1 && removed.get() == 0, "Stable frame repeated a transition");
        reconcile.invoke(membership, onAdd, onRemove);
        require(removed.get() == 1, "Shared membership failed to retire an invisible key");
        core.getMethod("clear").invoke(membership);
    }

    private static void checkScheduler(Class<?> core, ClassLoader loader) throws Exception {
        ArrayDeque<Runnable> workers = new ArrayDeque<>();
        Object scheduler = core.getConstructor(Executor.class, int.class).newInstance((Executor) workers::add, 4);
        Class<?> upload = loader.loadClass(core.getName() + "$Upload");
        require(upload.isInterface() && upload.getMethod("close").isDefault(), "Upload must retain its Java SAM/default-close contract");
        Method schedule = core.getMethod("trySchedule", Runnable.class, Supplier.class, BooleanSupplier.class, Runnable.class, Consumer.class);
        Method pump = core.getMethod("uploadOne"), discard = core.getMethod("discardStale");
        AtomicInteger prepared = new AtomicInteger(), uploaded = new AtomicInteger(), closed = new AtomicInteger(), finished = new AtomicInteger();
        AtomicBoolean current = new AtomicBoolean(true);
        Supplier<Object> build = () -> {
            AtomicBoolean released = new AtomicBoolean();
            return Proxy.newProxyInstance(loader, new Class<?>[]{upload}, (proxy, method, values) -> {
                if (method.getName().equals("upload")) { require(!released.get(), "Uploaded released staging"); uploaded.incrementAndGet(); return null; }
                if (method.getName().equals("close")) { require(released.compareAndSet(false, true), "Staging closed twice"); closed.incrementAndGet(); return null; }
                throw new AssertionError("Unexpected Upload call: " + method);
            });
        };
        Object[] job = {(Runnable) prepared::incrementAndGet, build, (BooleanSupplier) current::get,
                (Runnable) finished::incrementAndGet, (Consumer<Throwable>) error -> { throw new AssertionError("Build failed", error); }};
        for (int i = 0; i < 4; i++) require((boolean) schedule.invoke(scheduler, job), "Available build slot rejected");
        require(!(boolean) schedule.invoke(scheduler, job) && prepared.get() == 4, "Saturation mutated work or exceeded admission");
        while (!workers.isEmpty()) workers.remove().run();
        require(!(boolean) schedule.invoke(scheduler, job), "Completed upload released admission too early");
        for (int i = 1; i <= 4; i++) {
            require((boolean) pump.invoke(scheduler), "Completed build disappeared");
            require(uploaded.get() == i && closed.get() == i && finished.get() == i, "Pump did not upload/close/finish exactly once");
        }
        require(!(boolean) pump.invoke(scheduler), "Empty completion queue was not empty");
        require((boolean) schedule.invoke(scheduler, job), "Consumed upload did not release admission");
        workers.remove().run();
        current.set(false);
        discard.invoke(scheduler);
        require(uploaded.get() == 4 && closed.get() == 5 && finished.get() == 5 && !(boolean) pump.invoke(scheduler), "Stale completion uploaded or leaked");
        current.set(true);
        for (int i = 0; i < 4; i++) require((boolean) schedule.invoke(scheduler, job), "Discard leaked admission");
        while (!workers.isEmpty()) workers.remove().run();
        while ((boolean) pump.invoke(scheduler)) { }
        require(uploaded.get() == 8 && closed.get() == 9 && finished.get() == 9, "Recovered scheduler lost staging ownership");
    }

    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}

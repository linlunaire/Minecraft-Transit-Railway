package mtr.data;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarFile;
import org.objectweb.asm.*;

/** Loads the selected production modules. Adapters replace external Date and packet effects only. */
public final class KotlinRailwayModulesCompatibilityCheck {
    private static final String SCENARIO = "mtr.data.KotlinRailwayModulesScenario";
    private static final List<String> MODULES = List.of("RailwayDataPathGenerationModule", "RailwayDataDriveTrainModule", "RailwayDataLoggingModule");
    public static final class FixedDate extends Date { public FixedDate() { super(1_800_000_000_123L); } }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) throw new IllegalArgumentException("golden source [--record]");
        Path source = Path.of(args[1]).toRealPath();
        Map<String, byte[]> definitions = new HashMap<>();
        if (Files.isDirectory(source)) {
            try (var entries = Files.list(source.resolve("mtr/data"))) {
                for (Path entry : entries.filter(path -> moduleEntry("mtr/data/" + path.getFileName())).toList())
                    definitions.put("mtr.data." + entry.getFileName().toString().replace(".class", ""), Files.readAllBytes(entry));
            }
        } else try (JarFile jar = new JarFile(source.toFile())) {
            for (var entries = jar.entries(); entries.hasMoreElements();) {
                var entry = entries.nextElement();
                if (moduleEntry(entry.getName())) try (var input = jar.getInputStream(entry)) { definitions.put(entry.getName().replace('/', '.').replace(".class", ""), input.readAllBytes()); }
            }
        }
        boolean kotlin = Files.isDirectory(source);
        if (kotlin) {
            // The lifecycle Interface contains the selected module type; share its defining loader.
            try (var entries = Files.list(source.resolve("mtr/path"))) {
                for (Path entry : entries.filter(path -> path.getFileName().toString().equals("PathGenerationLifecycle.class") || path.getFileName().toString().startsWith("PathGenerationLifecycle$") && path.toString().endsWith(".class")).toList()) {
                    definitions.put("mtr.path." + entry.getFileName().toString().replace(".class", ""), Files.readAllBytes(entry));
                }
            }
            require(definitions.containsKey("mtr.path.PathGenerationLifecycle"), "Missing selected lifecycle Module");
        }
        for (String module : MODULES) {
            byte[] selected = definitions.get("mtr.data." + module); require(selected != null, "Missing selected module " + module);
            boolean[] metadata = {false};
            new ClassReader(selected).accept(new ClassVisitor(Opcodes.ASM9) {
                @Override public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                    if (descriptor.equals("Lkotlin/Metadata;")) metadata[0] = true;
                    return null;
                }
            }, ClassReader.SKIP_CODE);
            require(metadata[0] == kotlin, "Expected Kotlin compiler output or original Java JAR: " + module);
        }
        if (args.length == 3) require(args[2].equals("--record") && !kotlin, "Record only original Java JARs");
        int[] dates = {0};
        for (var entry : definitions.entrySet()) {
            ClassWriter writer = new ClassWriter(0);
            new ClassReader(entry.getValue()).accept(new ClassVisitor(Opcodes.ASM9, writer) {
                @Override public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        @Override public void visitTypeInsn(int opcode, String type) { super.visitTypeInsn(opcode, opcode == Opcodes.NEW && type.equals("java/util/Date") ? "mtr/data/KotlinRailwayModulesCompatibilityCheck$FixedDate" : type); }
                        @Override public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            if (opcode == Opcodes.INVOKESPECIAL && owner.equals("java/util/Date") && name.equals("<init>") && descriptor.equals("()V")) {
                                owner = "mtr/data/KotlinRailwayModulesCompatibilityCheck$FixedDate"; dates[0]++;
                            }
                            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        }
                    };
                }
            }, 0);
            entry.setValue(writer.toByteArray());
        }
        require(dates[0] == 2, "Logging date sites changed");
        // Java's protected same-package field access requires Train and the old driver
        // to share a runtime package (package name AND defining class loader).
        Path trainSource = Path.of(Train.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        if (Files.isDirectory(trainSource)) {
            try (var entries = Files.list(trainSource.resolve("mtr/data"))) {
                for (Path entry : entries.filter(path -> trainEntry("mtr/data/" + path.getFileName())).toList())
                    definitions.put("mtr.data." + entry.getFileName().toString().replace(".class", ""), Files.readAllBytes(entry));
            }
        } else try (JarFile jar = new JarFile(trainSource.toFile())) {
            for (var entries = jar.entries(); entries.hasMoreElements();) {
                var entry = entries.nextElement();
                if (trainEntry(entry.getName())) try (var input = jar.getInputStream(entry)) { definitions.put(entry.getName().replace('/', '.').replace(".class", ""), input.readAllBytes()); }
            }
        }
        definitions.put("mtr.packet.PacketTrainDataGuiServer", packetAdapter());
        ClassLoader loader = new ClassLoader(KotlinRailwayModulesCompatibilityCheck.class.getClassLoader()) {
            @Override protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                byte[] bytes = definitions.get(name);
                if (bytes == null && (name.equals(SCENARIO) || name.startsWith(SCENARIO + "$"))) {
                    try (InputStream input = getParent().getResourceAsStream(name.replace('.', '/') + ".class")) {
                        if (input == null) throw new ClassNotFoundException(name); bytes = input.readAllBytes();
                    } catch (java.io.IOException error) { throw new ClassNotFoundException(name, error); }
                }
                if (bytes == null) return super.loadClass(name, resolve);
                synchronized (getClassLoadingLock(name)) {
                    Class<?> type = findLoadedClass(name);
                    if (type == null) type = defineClass(name, bytes, 0, bytes.length);
                    if (resolve) resolveClass(type); return type;
                }
            }
        };
        Locale previousLocale = Locale.getDefault(); TimeZone previousZone = TimeZone.getDefault();
        String actual;
        try {
            Locale.setDefault(Locale.US); TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
            actual = (String) Class.forName(SCENARIO, true, loader).getMethod("run").invoke(null);
        } catch (InvocationTargetException error) { throw new AssertionError("Selected railway modules failed", error.getCause()); }
        finally { Locale.setDefault(previousLocale); TimeZone.setDefault(previousZone); }
        if (args.length == 3) Files.writeString(Path.of(args[0]), actual);
        else require(Files.readString(Path.of(args[0])).replace("\r\n", "\n").equals(actual), "Railway modules differ from frozen Java behavior\n" + actual);
        System.out.println("PASS: " + (kotlin ? "Kotlin" : "original Java") + " railway modules, thread restart/cleanup, driving input order, reduced data and real CSV create/append/failure recovery");
    }
    private static boolean moduleEntry(String name) {
        return MODULES.stream().anyMatch(module -> name.equals("mtr/data/" + module + ".class") || name.startsWith("mtr/data/" + module + "$") && name.endsWith(".class"));
    }
    private static boolean trainEntry(String name) { return List.of("Train", "TrainServer").stream().anyMatch(type -> name.equals("mtr/data/" + type + ".class") || name.startsWith("mtr/data/" + type + "$") && name.endsWith(".class")); }
    private static byte[] packetAdapter() {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        writer.visit(Opcodes.V25, Opcodes.ACC_PUBLIC, "mtr/packet/PacketTrainDataGuiServer", null, "java/lang/Object", null);
        String signature = "(Lnet/minecraft/world/level/Level;JI)V";
        var method = writer.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "generatePathS2C", signature, null, null);
        method.visitCode(); method.visitVarInsn(Opcodes.ALOAD, 0); method.visitVarInsn(Opcodes.LLOAD, 1); method.visitVarInsn(Opcodes.ILOAD, 3);
        method.visitMethodInsn(Opcodes.INVOKESTATIC, SCENARIO.replace('.', '/'), "packet", signature, false);
        method.visitInsn(Opcodes.RETURN); method.visitMaxs(0, 0); method.visitEnd(); writer.visitEnd(); return writer.toByteArray();
    }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}

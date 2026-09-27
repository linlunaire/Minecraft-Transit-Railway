package mtr.data;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarFile;
import org.objectweb.asm.*;

/** Only the external clock and world clock are adapted; Depot's scheduling code is unchanged. */
public final class KotlinDepotCompatibilityCheck {
    public static long now = 1_800_000_000_000L;
    public static long worldTime;
    public static long currentTimeMillis() { return now; }
    public static long overworldTime() { return worldTime; }
    private static final String SCENARIO = "mtr.data.KotlinDepotScenario";

    public static void main(String[] args) throws Exception {
        if (args.length < 2) throw new IllegalArgumentException("golden source [--record]");
        Path source = Path.of(args[1]).toRealPath();
        Map<String, byte[]> classes = new HashMap<>();
        if (Files.isDirectory(source)) {
            try (var entries = Files.list(source.resolve("mtr/data"))) {
                for (Path entry : entries.filter(path -> depotEntry(path.getFileName().toString())).toList())
                    classes.put("mtr.data." + entry.getFileName().toString().replace(".class", ""), Files.readAllBytes(entry));
            }
        } else try (JarFile jar = new JarFile(source.toFile())) {
            for (var entries = jar.entries(); entries.hasMoreElements();) {
                var entry = entries.nextElement();
                if (entry.getName().startsWith("mtr/data/") && depotEntry(entry.getName().substring(9)))
                    try (var input = jar.getInputStream(entry)) { classes.put(entry.getName().replace('/', '.').replace(".class", ""), input.readAllBytes()); }
            }
        }
        require(classes.containsKey("mtr.data.Depot"), "Missing selected Depot");
        boolean[] kotlin = {false};
        new ClassReader(classes.get("mtr.data.Depot")).accept(new ClassVisitor(Opcodes.ASM9) {
            @Override public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                if (descriptor.equals("Lkotlin/Metadata;")) kotlin[0] = true;
                return null;
            }
        }, ClassReader.SKIP_CODE);
        if (args.length == 3) require(args[2].equals("--record") && !kotlin[0] && Files.isRegularFile(source), "Record only original Java JARs");
        int[] clockCalls = {0};
        for (var entry : classes.entrySet()) {
            ClassWriter writer = new ClassWriter(0);
            new ClassReader(entry.getValue()).accept(new ClassVisitor(Opcodes.ASM9, writer) {
                @Override public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        @Override public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            if (owner.equals("java/lang/System") && name.equals("currentTimeMillis") && descriptor.equals("()J")) {
                                owner = "mtr/data/KotlinDepotCompatibilityCheck"; clockCalls[0]++;
                            }
                            if (owner.equals("mtr/path/PathGenerationTask") && name.equals("publish")) owner = SCENARIO.replace('.', '/');
                            if (owner.equals("mtr/packet/PacketTrainDataGuiServer") && name.equals("generatePathS2C")) {
                                owner = SCENARIO.replace('.', '/'); name = "packet";
                            }
                            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        }
                    };
                }
            }, 0);
            entry.setValue(writer.toByteArray());
        }
        require(clockCalls[0] == 5, "Depot clock call sites changed: " + clockCalls[0]);
        classes.put(SCENARIO + "$ClockLevel", clockLevel());
        ClassLoader loader = new ClassLoader(KotlinDepotCompatibilityCheck.class.getClassLoader()) {
            @Override protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                byte[] bytes = classes.get(name);
                if (bytes == null && (name.equals(SCENARIO) || name.startsWith(SCENARIO + "$"))) {
                    try (InputStream input = getParent().getResourceAsStream(name.replace('.', '/') + ".class")) {
                        if (input == null) throw new ClassNotFoundException(name);
                        bytes = input.readAllBytes();
                    } catch (java.io.IOException error) { throw new ClassNotFoundException(name, error); }
                }
                if (bytes == null) return super.loadClass(name, resolve);
                synchronized (getClassLoadingLock(name)) {
                    Class<?> type = findLoadedClass(name);
                    if (type == null) type = defineClass(name, bytes, 0, bytes.length);
                    if (resolve) resolveClass(type);
                    return type;
                }
            }
        };
        String actual;
        try { actual = (String) Class.forName(SCENARIO, true, loader).getMethod("run").invoke(null); }
        catch (InvocationTargetException error) { throw new AssertionError("Selected Depot failed", error.getCause()); }
        if (args.length == 3) Files.writeString(Path.of(args[0]), actual);
        else require(Files.readString(Path.of(args[0])).replace("\r\n", "\n").equals(actual), "Depot behavior differs from Java golden\n" + actual);
        if (kotlin[0]) {
            try { Class.forName(SCENARIO, true, loader).getMethod("workerContracts").invoke(null); }
            catch (InvocationTargetException error) { throw new AssertionError("Depot worker lifecycle failed", error.getCause()); }
        }
        System.out.println("PASS: " + (kotlin[0] ? "Kotlin" : "original Java") + " Depot, deterministic clock, save/wire bytes, partial mutation, daily scheduling and virtual dispatch");
    }

    private static boolean depotEntry(String name) { return name.equals("Depot.class") || name.startsWith("Depot$") && name.endsWith(".class"); }
    private static byte[] clockLevel() {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        writer.visit(Opcodes.V25, Opcodes.ACC_PUBLIC, SCENARIO.replace('.', '/') + "$ClockLevel", null, "net/minecraft/world/level/Level", null);
        var method = writer.visitMethod(Opcodes.ACC_PUBLIC, "getOverworldClockTime", "()J", null, null);
        method.visitCode(); method.visitMethodInsn(Opcodes.INVOKESTATIC, "mtr/data/KotlinDepotCompatibilityCheck", "overworldTime", "()J", false);
        method.visitInsn(Opcodes.LRETURN); method.visitMaxs(0, 0); method.visitEnd();
        method = writer.visitMethod(Opcodes.ACC_PUBLIC, "getMaxY", "()I", null, null);
        method.visitCode(); method.visitLdcInsn(319); method.visitInsn(Opcodes.IRETURN); method.visitMaxs(0, 0); method.visitEnd();
        writer.visitEnd();
        return writer.toByteArray();
    }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}

package mtr.data;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarFile;
import org.objectweb.asm.*;

/** Selects the real incremental search; adapters control time/randomness, not graph decisions. */
public final class RouteFinderCompatibilityCheck {
    public static final long EPOCH = 1_800_000_000_000L;
    public static long now = EPOCH;
    public static long clock() { return now++; }
    public static final class SeededRandom extends Random { public SeededRandom() { super(262332L); } }
    private static final String TARGET = "mtr.data.RailwayDataRouteFinderModule";
    private static final String SCENARIO = "mtr.data.RouteFinderScenario";

    public static void main(String[] args) throws Exception {
        Path source = Path.of(args[1]).toRealPath();
        boolean kotlin = Files.isDirectory(source), allocation = Arrays.asList(args).contains("--allocation");
        boolean record = Arrays.asList(args).contains("--record"), baseline = Arrays.asList(args).contains("--baseline");
        if (record && kotlin) throw new AssertionError("Record only frozen Java releases");
        Map<String, byte[]> definitions = new HashMap<>();
        if (kotlin) {
            try (var paths = Files.list(source.resolve("mtr/data"))) {
                for (Path path : paths.filter(path -> selected("mtr.data." + path.getFileName().toString().replace(".class", ""))).toList())
                    definitions.put("mtr.data." + path.getFileName().toString().replace(".class", ""), Files.readAllBytes(path));
            }
        } else try (JarFile jar = new JarFile(source.toFile())) {
            for (var entries = jar.entries(); entries.hasMoreElements();) {
                var entry = entries.nextElement(); String name = entry.getName().replace('/', '.').replace(".class", "");
                if (entry.getName().endsWith(".class") && selected(name)) try (var input = jar.getInputStream(entry)) { definitions.put(name, input.readAllBytes()); }
            }
        }
        for (String name : List.of(TARGET, TARGET + "$ConnectionDetails", TARGET + "$RouteFinderData")) {
            byte[] bytes = definitions.get(name); if (bytes == null) throw new AssertionError("Missing type " + name);
            boolean[] metadata = {false};
            new ClassReader(bytes).accept(new ClassVisitor(Opcodes.ASM9) {
                @Override public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) { if (descriptor.equals("Lkotlin/Metadata;")) metadata[0] = true; return null; }
            }, ClassReader.SKIP_CODE);
            if (metadata[0] != kotlin) throw new AssertionError("Wrong source language " + name);
        }
        int[] clocks = {0}, randoms = {0};
        for (var definition : definitions.entrySet()) {
            ClassWriter writer = new ClassWriter(0);
            new ClassReader(definition.getValue()).accept(new ClassVisitor(Opcodes.ASM9, writer) {
                @Override public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        @Override public void visitTypeInsn(int opcode, String type) { super.visitTypeInsn(opcode, opcode == Opcodes.NEW && type.equals("java/util/Random") ? support() + "$SeededRandom" : type); }
                        @Override public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            if (owner.equals("java/lang/System") && name.equals("currentTimeMillis") && descriptor.equals("()J")) { owner = support(); name = "clock"; clocks[0]++; }
                            if (owner.equals("java/util/Random") && name.equals("<init>") && descriptor.equals("()V")) { owner = support() + "$SeededRandom"; randoms[0]++; }
                            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        }
                    };
                }
            }, 0);
            definition.setValue(writer.toByteArray());
        }
        if (clocks[0] != 4 || randoms[0] != 1) throw new AssertionError("Clock/random sites changed: " + clocks[0] + "/" + randoms[0]);
        ClassLoader loader = new ClassLoader(RouteFinderCompatibilityCheck.class.getClassLoader()) {
            @Override protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                byte[] bytes = definitions.get(name);
                if (bytes == null && (name.equals(SCENARIO) || name.startsWith(SCENARIO + "$"))) try (InputStream input = getParent().getResourceAsStream(name.replace('.', '/') + ".class")) {
                    if (input == null) throw new ClassNotFoundException(name); bytes = input.readAllBytes();
                } catch (IOException error) { throw new ClassNotFoundException(name, error); }
                if (bytes == null) return super.loadClass(name, resolve);
                synchronized (getClassLoadingLock(name)) {
                    Class<?> type = findLoadedClass(name); if (type == null) type = defineClass(name, bytes, 0, bytes.length);
                    if (resolve) resolveClass(type); return type;
                }
            }
        };
        String actual;
        try { actual = (String) Class.forName(SCENARIO, true, loader).getMethod("run", boolean.class, boolean.class).invoke(null, allocation, baseline); }
        catch (InvocationTargetException error) { throw new AssertionError("Selected route finder failed", error.getCause()); }
        if (!allocation) {
            if (record) Files.writeString(Path.of(args[0]), actual);
            else {
                List<String> expected = Files.readAllLines(Path.of(args[0])), got = actual.lines().toList();
                if (expected.size() != got.size()) throw new AssertionError("Route finder record count changed " + expected.size() + " -> " + got.size());
                for (int i = 0; i < got.size(); i++) if (!expected.get(i).equals(got.get(i))) throw new AssertionError("Route finder record " + i + " differs\nJava: " + expected.get(i) + "\nActual: " + got.get(i));
            }
        }
        System.out.println("PASS: " + (kotlin ? "Kotlin" : "original Java") + " route finder " + (allocation ? (baseline ? "allocation baseline measured" : "primitive snapshot/candidate allocation gates") : "fixed-clock graph, schedule, queue and density behavior"));
    }
    private static boolean selected(String name) { return name.equals(TARGET) || name.startsWith(TARGET + "$"); }
    private static String support() { return RouteFinderCompatibilityCheck.class.getName().replace('.', '/'); }
}

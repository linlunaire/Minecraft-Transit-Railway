package mtr.sound.bve;

import org.objectweb.asm.*;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.*;
import java.util.jar.JarFile;

/** Executes the selected production parsers; only external resource and sound factories are adapted. */
public final class BveSoundCompatibilityCheck {
    private static final String SCENARIO = "mtr.sound.bve.BveSoundScenario";
    private static final List<String> CLASSES = List.of("MotorDataBase", "MotorData4", "MotorData5", "BveTrainSoundConfig", "ConfigFile");
    private static final String BASELINE_SHA256 = "15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec";

    public static void main(String[] args) throws Exception {
        if (args.length < 2 || args.length > 3) throw new IllegalArgumentException("golden classesOrJar [--record]");
        Path source = Path.of(args[1]).toRealPath();
        Map<String, byte[]> definitions = new HashMap<>();
        if (Files.isDirectory(source)) {
            try (var paths = Files.list(source.resolve("mtr/sound/bve"))) {
                for (Path path : paths.filter(path -> selected("mtr/sound/bve/" + path.getFileName())).toList())
                    definitions.put("mtr.sound.bve." + path.getFileName().toString().replace(".class", ""), Files.readAllBytes(path));
            }
        } else try (JarFile jar = new JarFile(source.toFile())) {
            for (var entries = jar.entries(); entries.hasMoreElements();) {
                var entry = entries.nextElement();
                if (selected(entry.getName())) try (InputStream input = jar.getInputStream(entry)) {
                    definitions.put(entry.getName().replace('/', '.').replace(".class", ""), input.readAllBytes());
                }
            }
        }
        boolean kotlin = metadata(definitions.get("mtr.sound.bve.ConfigFile"));
        for (String type : CLASSES) require(metadata(definitions.get("mtr.sound.bve." + type)) == kotlin, "Mixed Java/Kotlin BVE batch: " + type);
        if (Files.isDirectory(source)) require(kotlin, "Expected Kotlin production output");
        if (args.length == 3) {
            require(args[2].equals("--record") && !kotlin && Files.isRegularFile(source), "Record only the frozen Java JAR");
            require(HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(source))).equals(BASELINE_SHA256), "Wrong Java baseline JAR");
        }
        int[] adapted = {0, 0, 0};
        for (var entry : definitions.entrySet()) {
            ClassWriter writer = new ClassWriter(0);
            new ClassReader(entry.getValue()).accept(new ClassVisitor(Opcodes.ASM9, writer) {
                @Override public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        @Override public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            int adapter = owner.equals("mtr/mappings/UtilitiesClient") && name.equals("getResources") ? 0
                                    : owner.equals("mtr/mappings/Utilities") && name.equals("getInputStream") ? 1
                                    : owner.equals("mtr/mappings/RegistryUtilities") && name.equals("createSoundEvent") ? 2 : -1;
                            if (adapter >= 0) { adapted[adapter]++; owner = SCENARIO.replace('.', '/'); isInterface = false; }
                            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        }
                    };
                }
            }, 0);
            entry.setValue(writer.toByteArray());
        }
        require(Arrays.equals(adapted, new int[]{1, 1, 1}), "Unexpected external adapter sites " + Arrays.toString(adapted));
        ClassLoader loader = new ClassLoader(BveSoundCompatibilityCheck.class.getClassLoader()) {
            @Override protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                byte[] bytes = definitions.get(name);
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
        catch (InvocationTargetException error) { throw new AssertionError("Selected BVE classes failed", error.getCause()); }
        if (args.length == 3) System.out.print(actual);
        else {
            String expected = Files.readString(Path.of(args[0])).replace("\r\n", "\n");
            if (!expected.equals(actual)) {
                List<String> left = expected.lines().toList(), right = actual.lines().toList();
                for (int index = 0; index < Math.max(left.size(), right.size()); index++) {
                    String before = index < left.size() ? left.get(index) : "<missing>", after = index < right.size() ? right.get(index) : "<missing>";
                    if (!before.equals(after)) throw new AssertionError("BVE differs from frozen Java at record " + (index + 1) + "\nexpected: " + before + "\nactual: " + after);
                }
            }
            System.out.println("PASS: " + (kotlin ? "Kotlin" : "original Java") + " BVE sound data, " + actual.lines().count() + " records; parser boundaries, float bits, array aliases and virtual dispatch");
        }
    }

    private static boolean selected(String name) {
        return CLASSES.stream().anyMatch(type -> name.equals("mtr/sound/bve/" + type + ".class") || name.startsWith("mtr/sound/bve/" + type + "$") && name.endsWith(".class"));
    }
    private static boolean metadata(byte[] bytes) {
        require(bytes != null, "Missing BVE production class");
        boolean[] result = {false};
        new ClassReader(bytes).accept(new ClassVisitor(Opcodes.ASM9) {
            @Override public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) { if (descriptor.equals("Lkotlin/Metadata;")) result[0] = true; return null; }
        }, ClassReader.SKIP_CODE);
        return result[0];
    }
    private static void require(boolean value, String message) { if (!value) throw new AssertionError(message); }
}

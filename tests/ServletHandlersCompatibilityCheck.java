package mtr.servlet;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;
import java.util.jar.JarFile;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;

/** Runs the selected servlet bytecode; adapters replace only game-world access. */
public final class ServletHandlersCompatibilityCheck {
    private static final String SCENARIO = "mtr.servlet.ServletHandlersScenario";
    private static final List<String> TYPES = List.of("IServletHandler", "DataServletHandler", "InfoServletHandler", "DelaysServletHandler", "ArrivalsServletHandler", "RouteFinderServletHandler");

    public static void main(String[] args) throws Exception {
        Path source = Path.of(args[1]).toRealPath();
        boolean java = Arrays.asList(args).contains("--java"), record = Arrays.asList(args).contains("--record");
        if (record && !java) throw new AssertionError("Only original Java may record the reference");
        if (record) {
            if (!Files.isRegularFile(source)) throw new AssertionError("Record only the frozen Java release JAR");
            String hash = HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(source)));
            if (!hash.equals("15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec")) throw new AssertionError("Java release hash mismatch: " + hash);
        }
        Map<String, byte[]> definitions = new HashMap<>();
        if (Files.isDirectory(source)) {
            try (var paths = Files.list(source.resolve("mtr/servlet"))) {
                for (Path path : paths.filter(path -> selected("mtr.servlet." + path.getFileName().toString().replace(".class", ""))).toList())
                    definitions.put("mtr.servlet." + path.getFileName().toString().replace(".class", ""), Files.readAllBytes(path));
            }
        } else try (JarFile jar = new JarFile(source.toFile())) {
            for (var entries = jar.entries(); entries.hasMoreElements();) {
                var entry = entries.nextElement(); String name = entry.getName().replace('/', '.').replace(".class", "");
                if (entry.getName().endsWith(".class") && selected(name)) try (var input = jar.getInputStream(entry)) { definitions.put(name, input.readAllBytes()); }
            }
        }
        for (String simple : TYPES) {
            String name = "mtr.servlet." + simple; byte[] bytes = definitions.get(name);
            if (bytes == null) throw new AssertionError("Missing selected class " + name);
            boolean[] metadata = {false};
            new ClassReader(bytes).accept(new ClassVisitor(Opcodes.ASM9) {
                @Override public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) { if (descriptor.equals("Lkotlin/Metadata;")) metadata[0] = true; return null; }
            }, ClassReader.SKIP_CODE);
            if (metadata[0] == java) throw new AssertionError("Wrong source language " + name);
        }
        Set<String> adapted = new TreeSet<>();
        for (var definition : definitions.entrySet()) {
            // Published Java releases shade servlet/JSON dependencies; test against
            // the unshaded development classpath without replacing handler logic.
            ClassWriter normalized = new ClassWriter(0);
            new ClassReader(definition.getValue()).accept(new ClassRemapper(normalized, new Remapper(Opcodes.ASM9) {
                @Override public String map(String internalName) { return internalName.startsWith("mtr/libraries/") ? internalName.substring("mtr/libraries/".length()) : internalName; }
            }), 0);
            ClassWriter writer = new ClassWriter(0);
            new ClassReader(normalized.toByteArray()).accept(new ClassVisitor(Opcodes.ASM9, writer) {
                @Override public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        @Override public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            String replacement = switch (owner + "." + name) {
                                case "mtr/data/RailwayData.getInstance" -> "railway";
                                case "net/minecraft/world/level/Level.players" -> "players";
                                case "net/minecraft/world/level/Level.dimension" -> "dimension";
                                case "net/minecraft/world/entity/player/Player.getName" -> "playerName";
                                case "net/minecraft/world/entity/player/Player.blockPosition" -> "playerPosition";
                                case "mtr/data/RailwayDataCoolDownModule.getRidingRoute" -> "ridingRoute";
                                default -> null;
                            };
                            if (replacement != null) {
                                adapted.add(replacement);
                                if (opcode != Opcodes.INVOKESTATIC) descriptor = "(L" + owner + ";" + descriptor.substring(1);
                                owner = SCENARIO.replace('.', '/'); name = replacement; opcode = Opcodes.INVOKESTATIC; isInterface = false;
                            }
                            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        }
                    };
                }
            }, 0);
            definition.setValue(writer.toByteArray());
        }
        if (!adapted.equals(new TreeSet<>(List.of("railway", "players", "dimension", "playerName", "playerPosition", "ridingRoute")))) throw new AssertionError("Game-access sites changed: " + adapted);
        ClassLoader loader = new ClassLoader(ServletHandlersCompatibilityCheck.class.getClassLoader()) {
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
        try { actual = (String) Class.forName(SCENARIO, true, loader).getMethod("run").invoke(null); }
        catch (InvocationTargetException error) { throw new AssertionError("Selected servlet implementation failed", error.getCause()); }
        if (record) Files.writeString(Path.of(args[0]), actual);
        else {
            List<String> expected = Files.readAllLines(Path.of(args[0])), got = actual.lines().toList();
            if (!expected.equals(got)) {
                for (int i = 0; i < Math.min(expected.size(), got.size()); i++) if (!expected.get(i).equals(got.get(i))) throw new AssertionError("Servlet record " + i + " differs\nJava: " + expected.get(i) + "\nActual: " + got.get(i));
                throw new AssertionError("Servlet record count changed " + expected.size() + " -> " + got.size());
            }
        }
        System.out.println("PASS: " + (java ? "original Java" : "Kotlin") + " servlet HTTP/async/JSON reference");
    }
    private static boolean selected(String name) { return TYPES.stream().anyMatch(type -> name.equals("mtr.servlet." + type) || name.startsWith("mtr.servlet." + type + "$")); }
}

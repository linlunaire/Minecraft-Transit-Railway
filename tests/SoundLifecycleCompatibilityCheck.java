package mtr.sound;

import org.objectweb.asm.*;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.*;
import java.util.jar.JarFile;

/** Loads real sound implementations; intercepts only client/audio/clock/random boundaries. */
public final class SoundLifecycleCompatibilityCheck {
    private static final String SCENARIO = "mtr.sound.SoundLifecycleScenario";
    private static final List<String> TYPES = List.of("mtr/sound/TrainSoundBase", "mtr/sound/LoopingSoundInstance", "mtr/sound/TrainLoopingSoundInstance", "mtr/sound/JonTrainSound", "mtr/sound/bve/BveTrainSound");
    private static final String BASELINE_SHA256 = "15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec";

    public static void main(String[] args) throws Exception {
        java.io.PrintStream output = System.out;
        if (args.length < 2 || args.length > 3) throw new IllegalArgumentException("golden classesOrJar [--record]");
        Path source = Path.of(args[1]).toRealPath();
        Map<String, byte[]> definitions = new HashMap<>();
        if (Files.isDirectory(source)) {
            try (var paths = Files.walk(source.resolve("mtr/sound"))) {
                for (Path path : paths.filter(Files::isRegularFile).filter(path -> selected(source.relativize(path).toString().replace('\\', '/'))).toList())
                    definitions.put(source.relativize(path).toString().replace('\\', '.').replace('/', '.').replace(".class", ""), Files.readAllBytes(path));
            }
        } else try (JarFile jar = new JarFile(source.toFile())) {
            for (var entries = jar.entries(); entries.hasMoreElements();) {
                var entry = entries.nextElement();
                if (selected(entry.getName())) try (InputStream input = jar.getInputStream(entry)) {
                    definitions.put(entry.getName().replace('/', '.').replace(".class", ""), input.readAllBytes());
                }
            }
        }
        boolean kotlin = metadata(definitions.get("mtr.sound.TrainSoundBase"));
        for (String type : TYPES) require(metadata(definitions.get(type.replace('/', '.'))) == kotlin, "Mixed language sound batch " + type);
        if (Files.isDirectory(source)) require(kotlin, "Expected Kotlin output");
        if (args.length == 3) {
            require(args[2].equals("--record") && !kotlin && Files.isRegularFile(source), "Record only original Java JAR");
            require(HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(source))).equals(BASELINE_SHA256), "Wrong baseline Java JAR");
        }
        Map<String, Integer> sites = new TreeMap<>();
        for (var definition : definitions.entrySet()) {
            ClassWriter writer = new ClassWriter(0);
            new ClassReader(definition.getValue()).accept(new ClassVisitor(Opcodes.ASM9, writer) {
                @Override public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        @Override public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            String adapter = switch (owner + "." + name) {
                                case "net/minecraft/client/Minecraft.getInstance" -> "minecraft";
                                case "net/minecraft/client/Minecraft.getSoundManager" -> "sounds";
                                case "net/minecraft/client/player/LocalPlayer.blockPosition" -> "playerPosition";
                                case "net/minecraft/client/sounds/SoundManager.isActive" -> "active";
                                case "net/minecraft/client/sounds/SoundManager.play" -> "play";
                                case "mtr/MTRClient.getLastFrameDuration" -> "frameDuration";
                                case "mtr/MTRClient.canPlaySound" -> "canPlay";
                                case "net/minecraft/client/multiplayer/ClientLevel.playLocalSound" -> "playLocal";
                                case "java/util/concurrent/ThreadLocalRandom.nextInt" -> "randomInt";
                                default -> null;
                            };
                            if (adapter != null) {
                                sites.merge(adapter, 1, Integer::sum);
                                if (opcode != Opcodes.INVOKESTATIC) descriptor = "(L" + owner + ";" + descriptor.substring(1);
                                owner = SCENARIO.replace('.', '/'); name = adapter; opcode = Opcodes.INVOKESTATIC; isInterface = false;
                            }
                            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        }
                    };
                }
            }, 0);
            definition.setValue(writer.toByteArray());
        }
        Map<String, Integer> expected = Map.of("minecraft", 3, "sounds", 2, "playerPosition", 1, "active", 2, "play", 2, "frameDuration", 1, "canPlay", 1, "playLocal", 5, "randomInt", 2);
        require(sites.equals(expected), "Sound boundary sites changed: " + sites);
        ClassLoader loader = new ClassLoader(SoundLifecycleCompatibilityCheck.class.getClassLoader()) {
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
        String actual;
        try { actual = (String) Class.forName(SCENARIO, true, loader).getMethod("run").invoke(null); }
        catch (InvocationTargetException error) { throw new AssertionError("Selected sound lifecycle failed", error.getCause()); }
        if (args.length == 3) output.print(actual);
        else {
            String expectedRecords = Files.readString(Path.of(args[0])).replace("\r\n", "\n");
            List<String> left = expectedRecords.lines().toList(), right = actual.lines().toList();
            for (int i = 0; i < Math.max(left.size(), right.size()); i++) {
                String before = i < left.size() ? left.get(i) : "<missing>", after = i < right.size() ? right.get(i) : "<missing>";
                require(before.equals(after), "Sound lifecycle differs at record " + (i + 1) + "\nexpected: " + before + "\nactual: " + after);
            }
            output.println("PASS: " + (kotlin ? "Kotlin" : "original Java") + " sound lifecycle, " + right.size() + " records; real tickable sounds, train state, float timing and dispatch");
        }
    }
    private static boolean selected(String name) { return TYPES.stream().anyMatch(type -> name.equals(type + ".class") || name.startsWith(type + "$") && name.endsWith(".class")); }
    private static boolean metadata(byte[] bytes) {
        require(bytes != null, "Missing production sound class"); boolean[] found = {false};
        new ClassReader(bytes).accept(new ClassVisitor(Opcodes.ASM9) { @Override public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) { if (descriptor.equals("Lkotlin/Metadata;")) found[0] = true; return null; } }, ClassReader.SKIP_CODE);
        return found[0];
    }
    private static void require(boolean value, String message) { if (!value) throw new AssertionError(message); }
}

package mtr.data;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;
import java.util.jar.JarFile;

/** Selects actual lift bytecode, adapting only world, player, mount and client IO. */
public final class LiftCompatibilityCheck {
    private static final String SCENARIO = "mtr.data.LiftScenario";
    private static final List<String> TYPES = List.of("Lift", "LiftServer", "LiftClient");
    private static final String JAVA_SHA256 = "600c54aabce022d3896070eae2b7cada64be029e1b9f4485491c381f957c251f";

    public static void main(String[] args) throws Exception {
        PrintStream output = System.out;
        Path source = Path.of(args[1]).toRealPath();
        boolean original = Arrays.asList(args).contains("--java"), record = Arrays.asList(args).contains("--record");
        if (record && (!original || !Files.isRegularFile(source) || !HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(source))).equals(JAVA_SHA256))) {
            throw new AssertionError("Only the pinned preview2 JAR's Java lift implementation can record goldens");
        }
        Map<String, byte[]> definitions = new HashMap<>();
        if (Files.isDirectory(source)) try (var paths = Files.list(source.resolve("mtr/data"))) {
            for (Path path : paths.filter(p -> selected("mtr.data." + p.getFileName().toString().replace(".class", "")) && p.toString().endsWith(".class")).toList()) {
                definitions.put("mtr.data." + path.getFileName().toString().replace(".class", ""), Files.readAllBytes(path));
            }
        } else try (JarFile jar = new JarFile(source.toFile())) {
            for (var items = jar.entries(); items.hasMoreElements();) {
                var entry = items.nextElement(); String name = entry.getName().replace('/', '.').replace(".class", "");
                if (entry.getName().endsWith(".class") && selected(name)) try (var input = jar.getInputStream(entry)) { definitions.put(name, input.readAllBytes()); }
            }
        }
        for (String name : TYPES) {
            byte[] bytes = definitions.get("mtr.data." + name);
            if (bytes == null) throw new AssertionError("Missing selected lift class " + name);
            boolean[] kotlin = {false};
            new ClassReader(bytes).accept(new ClassVisitor(Opcodes.ASM9) {
                @Override public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) { if (descriptor.equals("Lkotlin/Metadata;")) kotlin[0] = true; return null; }
            }, ClassReader.SKIP_CODE);
            if (kotlin[0] == original) throw new AssertionError("Wrong implementation language for " + name);
        }
        Set<String> sites = new TreeSet<>();
        for (var entry : definitions.entrySet()) {
            ClassWriter plain = new ClassWriter(0);
            new ClassReader(entry.getValue()).accept(new ClassRemapper(plain, new Remapper(Opcodes.ASM9) {
                @Override public String map(String name) { return name.startsWith("mtr/libraries/") ? name.substring(14) : name; }
            }), 0);
            ClassWriter writer = new ClassWriter(0);
            new ClassReader(plain.toByteArray()).accept(new ClassVisitor(Opcodes.ASM9, writer) {
                @Override public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        @Override public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            String adapter = switch (owner + "." + name) {
                                case "net/minecraft/world/level/Level.isClientSide" -> "clientSide";
                                case "net/minecraft/world/level/Level.getBlockEntity" -> "blockEntity";
                                case "net/minecraft/world/level/Level.getBlockState" -> "blockState";
                                case "net/minecraft/world/level/block/state/BlockState.getBlock" -> "block";
                                case "net/minecraft/world/level/Level.getNearestPlayer" -> "nearestPlayer";
                                case "net/minecraft/world/level/Level.playSound" -> "sound";
                                case "net/minecraft/world/level/Level.players" -> "players";
                                case "net/minecraft/world/entity/player/Player.getUUID" -> "uuid";
                                case "net/minecraft/world/entity/player/Player.blockPosition" -> "position";
                                case "mtr/data/RailwayData.chunkLoaded" -> "loaded";
                                case "mtr/block/IBlock.getStatePropertySafe" -> "property";
                                case "mtr/block/BlockLiftTrackFloor$TileEntityLiftTrackFloor.getShouldDing" -> "ding";
                                case "mtr/block/BlockPSDAPGDoorBase$TileEntityPSDAPGDoorBase.setOpen" -> "openDoor";
                                case "mtr/data/VehicleRidingServer.mountRider", "mtr/data/VehicleRidingServer$Companion.mountRider" -> "mount";
                                case "net/minecraft/client/Minecraft.getInstance" -> "minecraft";
                                case "mtr/data/VehicleRidingClient.begin" -> "begin";
                                case "mtr/data/VehicleRidingClient.movePlayer" -> "movePlayer";
                                case "mtr/data/VehicleRidingClient.setOffsets" -> "offsets";
                                case "mtr/data/VehicleRidingClient.moveSelf" -> "moveSelf";
                                case "mtr/data/VehicleRidingClient.end" -> "end";
                                case "mtr/data/VehicleRidingClient.renderPlayerAndGetOffset" -> "renderOffset";
                                case "mtr/data/VehicleRidingClient.startRiding" -> "startRiding";
                                case "mtr/data/VehicleRidingClient.updateRiderPercentages" -> "percentages";
                                case "mtr/data/VehicleRidingClient.getViewOffset" -> "viewOffset";
                                default -> null;
                            };
                            if (adapter != null) {
                                sites.add(adapter);
                                if (opcode != Opcodes.INVOKESTATIC) descriptor = "(L" + owner + ";" + descriptor.substring(1);
                                owner = SCENARIO.replace('.', '/'); name = adapter; opcode = Opcodes.INVOKESTATIC; isInterface = false;
                            }
                            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        }
                    };
                }
            }, 0);
            entry.setValue(writer.toByteArray());
        }
        Set<String> expectedSites = Set.of("clientSide", "blockEntity", "blockState", "block", "nearestPlayer", "sound", "players", "uuid", "position", "loaded", "property", "ding", "openDoor", "mount", "minecraft", "begin", "movePlayer", "offsets", "moveSelf", "end", "renderOffset", "startRiding", "percentages", "viewOffset");
        if (!sites.equals(expectedSites)) throw new AssertionError("World/client adapter sites changed: " + sites);
        ClassLoader loader = new ClassLoader(LiftCompatibilityCheck.class.getClassLoader()) {
            @Override protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                byte[] bytes = definitions.get(name);
                if (bytes == null && selected(name)) throw new ClassNotFoundException("Missing selected implementation (no development fallback): " + name);
                if (bytes == null && (name.equals(SCENARIO) || name.startsWith(SCENARIO + "$"))) try (var input = getParent().getResourceAsStream(name.replace('.', '/') + ".class")) {
                    if (input == null) throw new ClassNotFoundException(name); bytes = input.readAllBytes();
                } catch (IOException error) { throw new ClassNotFoundException(name, error); }
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
        catch (InvocationTargetException error) { throw new AssertionError("Selected lift implementation failed", error.getCause()); }
        if (record) output.print(actual);
        else {
            List<String> expected = Files.readAllLines(Path.of(args[0])), records = actual.lines().toList();
            for (int i = 0; i < Math.min(expected.size(), records.size()); i++) if (!expected.get(i).equals(records.get(i))) throw new AssertionError("Lift record " + i + " differs\nJava: " + expected.get(i) + "\nActual: " + records.get(i));
            if (expected.size() != records.size()) throw new AssertionError("Lift record count changed");
            output.println("PASS: " + records.size() + " original-Java lift records against " + source);
        }
    }
    private static boolean selected(String name) { return TYPES.stream().anyMatch(type -> name.equals("mtr.data." + type) || name.startsWith("mtr.data." + type + "$")); }
}

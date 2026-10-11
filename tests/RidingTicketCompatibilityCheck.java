package mtr.data;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;
import java.util.jar.JarFile;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.*;

/** Selects original Java or current Kotlin; only Minecraft/network boundaries are adapted. */
public final class RidingTicketCompatibilityCheck {
    private static final String SCENARIO = "mtr.data.RidingTicketScenario";
    private static final List<String> TYPES = List.of("TicketSystem", "VehicleRidingServer", "RailwayDataCoolDownModule");
    private static final Set<Integer> TICKET_INTEGRATION_CASES = Set.of(0, 2, 3, 5, 7, 14, 18, 32, 65, 112, 135, 152);
    public static boolean includeTicketCase(int index) { return TICKET_INTEGRATION_CASES.contains(index); }
    public static void main(String[] args) throws Exception {
        Path source = Path.of(args[1]).toRealPath(); boolean original = Arrays.asList(args).contains("--java"), record = Arrays.asList(args).contains("--record");
        if (record) {
            if (!original || !Files.isRegularFile(source)) throw new AssertionError("Only the frozen Java JAR may record expectations");
            String sha = HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(source)));
            if (!sha.equals("15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec")) throw new AssertionError("Original release SHA-256 mismatch: " + sha);
        }
        Map<String, byte[]> definitions = new HashMap<>();
        if (Files.isDirectory(source)) try (var files = Files.list(source.resolve("mtr/data"))) {
            for (Path file : files.filter(p -> p.toString().endsWith(".class") && selected("mtr.data." + p.getFileName().toString().replace(".class", ""))).toList()) definitions.put("mtr.data." + file.getFileName().toString().replace(".class", ""), Files.readAllBytes(file));
        } else try (JarFile jar = new JarFile(source.toFile())) {
            for (var items = jar.entries(); items.hasMoreElements();) { var entry = items.nextElement(); String name = entry.getName().replace('/', '.').replace(".class", ""); if (entry.getName().endsWith(".class") && selected(name)) try (var in = jar.getInputStream(entry)) { definitions.put(name, in.readAllBytes()); } }
        }
        if (!original) {
            String prefix = "mtr/data/TicketTransaction";
            if (Files.isDirectory(source)) try (var files = Files.list(source.resolve("mtr/data"))) {
                for (Path file : files.filter(p -> p.getFileName().toString().startsWith("TicketTransaction") && p.toString().endsWith(".class")).toList()) definitions.put("mtr.data." + file.getFileName().toString().replace(".class", ""), Files.readAllBytes(file));
            } else try (JarFile jar = new JarFile(source.toFile())) {
                for (var items = jar.entries(); items.hasMoreElements();) { var entry = items.nextElement(); if (entry.getName().startsWith(prefix) && entry.getName().endsWith(".class")) try (var in = jar.getInputStream(entry)) { definitions.put(entry.getName().replace('/', '.').replace(".class", ""), in.readAllBytes()); } }
            }
            if (!definitions.containsKey("mtr.data.TicketTransaction")) throw new AssertionError("Missing packaged ticket domain implementation");
        }
        for (String type : TYPES) {
            byte[] bytes = definitions.get("mtr.data." + type); if (bytes == null) throw new AssertionError("Missing selected type " + type);
            boolean[] metadata = {false}; new ClassReader(bytes).accept(new ClassVisitor(Opcodes.ASM9) { @Override public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) { if (descriptor.equals("Lkotlin/Metadata;")) metadata[0] = true; return null; } }, ClassReader.SKIP_CODE);
            if (metadata[0] == original) throw new AssertionError("Wrong implementation language: " + type);
        }
        Set<String> sites = new TreeSet<>();
        for (var entry : definitions.entrySet()) {
            ClassWriter plain = new ClassWriter(0);
            new ClassReader(entry.getValue()).accept(new ClassRemapper(plain, new Remapper(Opcodes.ASM9) { @Override public String map(String name) { return name.startsWith("mtr/libraries/") ? name.substring(14) : name; } }), 0);
            ClassWriter writer = new ClassWriter(0);
            new ClassReader(plain.toByteArray()).accept(new ClassVisitor(Opcodes.ASM9, writer) {
                @Override public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        private boolean seatDup;
                        @Override public void visitTypeInsn(int opcode, String type) { if (opcode == Opcodes.NEW && type.equals("mtr/entity/EntitySeat")) seatDup = true; else super.visitTypeInsn(opcode, type); }
                        @Override public void visitInsn(int opcode) { if (seatDup && opcode == Opcodes.DUP) seatDup = false; else super.visitInsn(opcode); }
                        @Override public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                            if (opcode == Opcodes.GETFIELD && owner.equals("mtr/data/RailwayData") && name.equals("railwayDataCoolDownModule")) { sites.add("cooldown"); super.visitMethodInsn(Opcodes.INVOKESTATIC, SCENARIO.replace('.', '/'), "cooldown", "(Lmtr/data/RailwayData;)Lmtr/data/RailwayDataCoolDownModule;", false); }
                            else super.visitFieldInsn(opcode, owner, name, descriptor);
                        }
                        @Override public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            if (owner.equals("mtr/entity/EntitySeat") && name.equals("<init>") && descriptor.equals("(Lnet/minecraft/world/level/Level;DDD)V")) { sites.add("newSeat"); super.visitMethodInsn(Opcodes.INVOKESTATIC, SCENARIO.replace('.', '/'), "newSeat", "(Lnet/minecraft/world/level/Level;DDD)Lmtr/entity/EntitySeat;", false); return; }
                            String replacement = switch (owner + "." + name) {
                                case "mtr/data/RailwayData.getInstance" -> "railway";
                                case "net/minecraft/world/level/Level.getScoreboard" -> "scoreboard";
                                case "net/minecraft/world/level/Level.playSound" -> "playSound";
                                case "net/minecraft/world/level/Level.getEntitiesOfClass" -> "entities";
                                case "net/minecraft/world/level/Level.getPlayerByUUID" -> "byUuid";
                                case "net/minecraft/world/level/Level.players" -> "players";
                                case "net/minecraft/world/level/Level.addFreshEntity" -> "addEntity";
                                case "net/minecraft/world/entity/player/Player.getGameProfile" -> "profile";
                                case "net/minecraft/world/entity/player/Player.isCreative" -> "creative";
                                case "net/minecraft/world/entity/player/Player.isSpectator" -> "spectator";
                                case "net/minecraft/world/entity/player/Player.isShiftKeyDown" -> "shift";
                                case "net/minecraft/world/entity/player/Player.getUUID" -> "uuid";
                                case "net/minecraft/world/entity/player/Player.position" -> "position";
                                case "net/minecraft/world/entity/player/Player.getX" -> "x";
                                case "net/minecraft/world/entity/player/Player.getY" -> "y";
                                case "net/minecraft/world/entity/player/Player.getZ" -> "z";
                                case "net/minecraft/world/entity/player/Player.setNoGravity" -> "gravity";
                                case "net/minecraft/world/entity/player/Player.startRiding" -> "startRiding";
                                case "net/minecraft/world/entity/player/Player.stopRiding" -> "stopRiding";
                                case "net/minecraft/server/level/ServerPlayerGameMode.getGameModeForPlayer" -> "gameMode";
                                case "mtr/mappings/Utilities.getAbilities" -> "abilities";
                                case "mtr/mappings/Utilities.entityRemoved" -> "removed";
                                case "mtr/mappings/PlayerUtilities.displayClientMessage" -> "message";
                                case "mtr/Registry.sendToPlayers" -> "packet";
                                case "mtr/Registry.setInTeleportationState" -> "teleport";
                                case "mtr/entity/EntitySeat.initialize" -> "initializeSeat";
                                case "mtr/entity/EntitySeat.updateSeatByRailwayData" -> "updateSeat";
                                case "mtr/entity/EntitySeat.setPos" -> "seatPos";
                                default -> null;
                            };
                            if (replacement != null) { sites.add(replacement); if (opcode != Opcodes.INVOKESTATIC) descriptor = "(L" + owner + ";" + descriptor.substring(1); owner = SCENARIO.replace('.', '/'); name = replacement; opcode = Opcodes.INVOKESTATIC; isInterface = false; }
                            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        }
                    };
                }
            }, 0);
            entry.setValue(writer.toByteArray());
        }
        if (!sites.equals(new TreeSet<>(List.of("abilities", "addEntity", "byUuid", "cooldown", "creative", "entities", "gameMode", "gravity", "initializeSeat", "message", "newSeat", "packet", "playSound", "players", "position", "profile", "railway", "removed", "scoreboard", "seatPos", "shift", "spectator", "startRiding", "stopRiding", "teleport", "updateSeat", "uuid", "x", "y", "z")))) throw new AssertionError("Boundary adapter sites changed: " + sites);
        ClassLoader loader = new ClassLoader(RidingTicketCompatibilityCheck.class.getClassLoader()) {
            @Override protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                byte[] bytes = definitions.get(name);
                if (bytes == null && (name.equals(SCENARIO) || name.startsWith(SCENARIO + "$"))) try (var in = getParent().getResourceAsStream(name.replace('.', '/') + ".class")) { if (in == null) throw new ClassNotFoundException(name); bytes = in.readAllBytes(); } catch (IOException e) { throw new ClassNotFoundException(name, e); }
                if (bytes == null) return super.loadClass(name, resolve);
                synchronized (getClassLoadingLock(name)) { Class<?> type = findLoadedClass(name); if (type == null) type = defineClass(name, bytes, 0, bytes.length); if (resolve) resolveClass(type); return type; }
            }
        };
        String result; try { result = (String) Class.forName(SCENARIO, true, loader).getMethod("run", boolean.class, boolean.class).invoke(null, record, !original); } catch (InvocationTargetException e) { throw new AssertionError("Selected riding/ticket implementation failed", e.getCause()); }
        if (record) Files.writeString(Path.of(args[0]), result);
        else { List<String> expected = Files.readAllLines(Path.of(args[0])).stream().filter(line -> !line.startsWith("ticket:") || includeTicketCase(Integer.parseInt(line.substring(7, line.indexOf('\t'))))).toList(), actual = result.lines().toList(); verifyTicketWiringCoverage(expected); if (!expected.equals(actual)) { for (int i = 0; i < Math.min(expected.size(), actual.size()); i++) if (!expected.get(i).equals(actual.get(i))) throw new AssertionError("Record " + i + " differs\nJava: " + expected.get(i) + "\nActual: " + actual.get(i)); throw new AssertionError("Reference record count changed"); } }
        System.out.println("PASS: " + (original ? "original Java" : "Kotlin") + " ticket, vehicle riding and cooldown behavior");
    }
    private static boolean selected(String name) { return TYPES.stream().anyMatch(type -> name.equals("mtr.data." + type) || name.startsWith("mtr.data." + type + "$")); }
    private static void verifyTicketWiringCoverage(List<String> records) {
        List<String> tickets = records.stream().filter(line -> line.startsWith("ticket:")).toList();
        for (String fragment : List.of("gui.mtr.already_entered:", "gui.mtr.already_exited:", "gui.mtr.insufficient_balance:", "gui.mtr.enter_barrier:", "gui.mtr.exit_barrier:", "sound:mtr:enter:", "sound:mtr:enter_concession:", "sound:mtr:exit:", "sound:mtr:exit_concession:")) {
            if (tickets.stream().noneMatch(line -> line.contains(fragment))) throw new AssertionError("Ticket wiring corpus lost coverage: " + fragment);
        }
        for (boolean entering : List.of(false, true)) {
            if (tickets.stream().noneMatch(line -> {
                String[] input = line.substring(line.indexOf('\t') + 1, line.indexOf("->")).split(":");
                return input[3].equals("true") && input[4].equals("true") && (Integer.parseInt(input[1]) == 0) == entering;
            })) throw new AssertionError("Missing automatic gate direction: entering=" + entering);
        }
    }
}

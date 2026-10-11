package mtr.data;

import com.sun.management.ThreadMXBean;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.objectweb.asm.*;
import sun.misc.Unsafe;

import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.*;
import java.util.jar.JarFile;

/** Real scoreboard objective behavior and interpreter allocation accounting; no timing/TPS claim. */
public final class TicketObjectiveRegistrationCheck {
    private static final String OWNER = "mtr.data.TicketSystem";
    private static final String SCENARIO = "mtr.data.TicketObjectiveRegistrationCheck$Scenario";
    private static final String BASELINE_SHA256 = "15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec";

    public static void main(String[] args) throws Exception {
        if (args.length < 1 || args.length > 3) throw new IllegalArgumentException("classesOrJar [--java] [--allocation]");
        Set<String> options = new HashSet<>(Arrays.asList(args).subList(1, args.length));
        if (!Set.of("--java", "--allocation").containsAll(options)) throw new IllegalArgumentException("Unknown option: " + options);
        boolean original = options.contains("--java"), allocation = options.contains("--allocation");
        java.io.PrintStream output = System.out;
        Path source = Path.of(args[0]).toRealPath();
        if (original) {
            require(Files.isRegularFile(source), "Original baseline must be the frozen release JAR");
            String sha = HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(source)));
            require(sha.equals(BASELINE_SHA256), "Original release SHA-256 mismatch: " + sha);
        }
        Map<String, byte[]> definitions = new HashMap<>();
        if (Files.isDirectory(source)) {
            try (var files = Files.list(source.resolve("mtr/data"))) {
                for (Path file : files.filter(path -> selected("mtr.data." + path.getFileName().toString().replace(".class", "")) && path.toString().endsWith(".class")).toList())
                    definitions.put("mtr.data." + file.getFileName().toString().replace(".class", ""), Files.readAllBytes(file));
            }
        } else try (JarFile jar = new JarFile(source.toFile())) {
            for (var entries = jar.entries(); entries.hasMoreElements();) {
                var entry = entries.nextElement(); String name = entry.getName().replace('/', '.').replace(".class", "");
                if (entry.getName().endsWith(".class") && selected(name)) try (InputStream input = jar.getInputStream(entry)) { definitions.put(name, input.readAllBytes()); }
            }
        }
        require(definitions.containsKey(OWNER), "Missing selected TicketSystem");
        boolean[] metadata = {false};
        new ClassReader(definitions.get(OWNER)).accept(new ClassVisitor(Opcodes.ASM9) {
            @Override public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) { if (descriptor.equals("Lkotlin/Metadata;")) metadata[0] = true; return null; }
        }, ClassReader.SKIP_CODE);
        require(metadata[0] != original, "Wrong implementation language: expected " + (original ? "original Java" : "Kotlin"));
        int[] adapted = {0};
        for (var entry : definitions.entrySet()) {
            ClassWriter writer = new ClassWriter(0);
            new ClassReader(entry.getValue()).accept(new ClassVisitor(Opcodes.ASM9, writer) {
                @Override public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        @Override public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            if (owner.equals("net/minecraft/world/level/Level") && name.equals("getScoreboard") && descriptor.equals("()Lnet/minecraft/world/scores/Scoreboard;")) {
                                adapted[0]++;
                                super.visitMethodInsn(Opcodes.INVOKESTATIC, SCENARIO.replace('.', '/'), "scoreboard", "(Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/scores/Scoreboard;", false);
                            } else super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        }
                    };
                }
            }, 0);
            entry.setValue(writer.toByteArray());
        }
        require(adapted[0] > 0, "No real-world scoreboard access found in selected implementation");
        ClassLoader loader = new ClassLoader(TicketObjectiveRegistrationCheck.class.getClassLoader()) {
            @Override protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                byte[] bytes = definitions.get(name);
                if (bytes == null && (name.equals(SCENARIO) || name.startsWith(SCENARIO + "$"))) {
                    try (InputStream input = getParent().getResourceAsStream(name.replace('.', '/') + ".class")) {
                        if (input == null) throw new ClassNotFoundException(name); bytes = input.readAllBytes();
                    } catch (java.io.IOException error) { throw new ClassNotFoundException(name, error); }
                }
                if (bytes == null) return super.loadClass(name, resolve);
                synchronized (getClassLoadingLock(name)) {
                    Class<?> type = findLoadedClass(name); if (type == null) type = defineClass(name, bytes, 0, bytes.length);
                    if (resolve) resolveClass(type); return type;
                }
            }
        };
        try { output.println(Class.forName(SCENARIO, true, loader).getMethod("run", boolean.class, boolean.class).invoke(null, original, allocation)); }
        catch (InvocationTargetException error) { throw new AssertionError("Selected objective registration failed", error.getCause()); }
    }

    private static boolean selected(String name) { return name.equals(OWNER) || name.startsWith(OWNER + "$"); }
    private static void require(boolean value, String message) { if (!value) throw new AssertionError(message); }

    /** Loaded alongside the selected TicketSystem, so the measured loop calls it directly. */
    public static final class Scenario {
        private static final String BALANCE = "mtr_balance", ENTRY = "mtr_entry_zone";
        private static CountingScoreboard current;
        private static Level world;
        private static int assertions;
        private static long scoreboardReads;
        private static boolean failWorld;

        public static Scoreboard scoreboard(Level receiver) {
            Objects.requireNonNull(receiver);
            scoreboardReads++;
            if (failWorld) throw new IllegalStateException("fixture world scoreboard failure");
            return current;
        }

        public static String run(boolean original, boolean allocation) throws Exception {
            net.minecraft.SharedConstants.tryDetectVersion(); net.minecraft.server.Bootstrap.bootStrap();
            var unsafeField = Unsafe.class.getDeclaredField("theUnsafe"); unsafeField.setAccessible(true);
            world = (Level) ((Unsafe) unsafeField.get(null)).allocateInstance(ServerLevel.class);
            current = new CountingScoreboard();
            TicketSystem.addObjectivesIfMissing(null);
            check(current.addCalls == 0 && current.getObjectives().isEmpty(), "Null world must not create objectives");

            TicketSystem.addObjectivesIfMissing(world);
            check(current.addCalls == 2 && current.duplicateFailures == 0, "Fresh scoreboard needs exactly two real creations");
            checkObjective(current.getObjective(BALANCE), "Balance"); checkObjective(current.getObjective(ENTRY), "Entry Zone");
            Objective balance = current.getObjective(BALANCE), entry = current.getObjective(ENTRY);
            current.clearCounters(); scoreboardReads = 0;
            repeat(1000);
            long stableAdds = current.addCalls, stableDuplicates = current.duplicateFailures;
            check(stableAdds == (original ? 2000 : 0), "Stable 1000 calls should attempt " + (original ? 2000 : 0) + " creations, got " + stableAdds);
            check(stableDuplicates == (original ? 2000 : 0), "Stable repeated objective exceptions changed: " + stableDuplicates);
            check(current.getObjective(BALANCE) == balance && current.getObjective(ENTRY) == entry, "Stable registration replaced existing objectives");
            check(scoreboardReads >= 1000, "Scoreboard was cached across calls instead of observing current world state");

            // Existing user-customized objective identity and metadata remain authoritative.
            balance.setDisplayName(Component.literal("Existing balance"));
            TicketSystem.addObjectivesIfMissing(world);
            check(current.getObjective(BALANCE) == balance && balance.getDisplayName().getString().equals("Existing balance"), "Existing objective metadata was overwritten");
            for (String name : List.of(BALANCE, ENTRY)) {
                Objective removed = current.getObjective(name), retained = current.getObjective(name.equals(BALANCE) ? ENTRY : BALANCE);
                current.removeObjective(removed); current.clearCounters();
                TicketSystem.addObjectivesIfMissing(world);
                check(current.getObjective(name) != null && current.getObjective(name) != removed, "Deleted objective was not recreated: " + name);
                check(current.getObjective(name.equals(BALANCE) ? ENTRY : BALANCE) == retained, "Recreating one objective replaced the other");
                check(current.addCalls == (original ? 2 : 1) && current.duplicateFailures == (original ? 1 : 0), "Deletion must create only the missing objective in optimized code");
                checkObjective(current.getObjective(name), name.equals(BALANCE) ? "Balance" : "Entry Zone");
            }

            CountingScoreboard previous = current;
            current = new CountingScoreboard(); TicketSystem.addObjectivesIfMissing(world);
            check(current.addCalls == 2 && current.getObjectives().size() == 2, "Replacing the scoreboard left registration stale");
            current = previous; current.clearCounters(); TicketSystem.addObjectivesIfMissing(world);
            check(current.addCalls == (original ? 2 : 0), "Returning to an existing scoreboard must preserve its objectives");

            // Each creation has its own failure scope: one failure never skips the other.
            for (String failing : List.of(BALANCE, ENTRY)) {
                current = new CountingScoreboard(); current.failName = failing;
                TicketSystem.addObjectivesIfMissing(world);
                String other = failing.equals(BALANCE) ? ENTRY : BALANCE;
                check(current.addCalls == 2 && current.injectedFailures == 1, "Both objective creations must be attempted when one fails");
                check(current.getObjective(failing) == null && current.getObjective(other) != null, "Failed creation prevented the independent objective");
                current.failName = null; current.clearCounters(); TicketSystem.addObjectivesIfMissing(world);
                check(current.getObjectives().size() == 2 && current.addCalls == (original ? 2 : 1), "Failed objective must be retried on the next call");
            }
            current = new CountingScoreboard(); failWorld = true; TicketSystem.addObjectivesIfMissing(world); failWorld = false;
            check(current.addCalls == 0, "Unavailable scoreboard should not create objectives");
            TicketSystem.addObjectivesIfMissing(world); check(current.getObjectives().size() == 2, "World scoreboard recovery was cached as failed");

            // A conflicting type is still an existing objective; no policy replacement is authorized.
            current = new CountingScoreboard(); Objective customized = current.seed(BALANCE);
            TicketSystem.addObjectivesIfMissing(world);
            check(current.getObjective(BALANCE) == customized && customized.getCriteria() == ObjectiveCriteria.TRIGGER, "Preexisting criteria were replaced");
            check(current.getObjectives().size() == 2, "Other objective missing beside customized balance");

            String measured = "";
            if (allocation) {
                check(ManagementFactory.getRuntimeMXBean().getInputArguments().contains("-Xint"), "Allocation gate requires -Xint");
                ThreadMXBean bean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
                check(bean.isThreadAllocatedMemorySupported(), "Thread allocation accounting unavailable"); bean.setThreadAllocatedMemoryEnabled(true);
                current = new CountingScoreboard(); TicketSystem.addObjectivesIfMissing(world); repeat(1024); current.clearCounters();
                long thread = Thread.currentThread().threadId(), before = bean.getThreadAllocatedBytes(thread);
                repeat(4096);
                long bytes = bean.getThreadAllocatedBytes(thread) - before;
                check(current.addCalls == (original ? 8192 : 0) && current.duplicateFailures == (original ? 8192 : 0), "Measured loop skipped the real operation gate");
                // Permit incidental VM bookkeeping; reject even modest per-call component/exception recreation.
                check(original || bytes <= 4096L * 32, "Stable registration allocation exceeded conservative 32 bytes/call gate: " + bytes);
                measured = "; allocation=" + bytes + " bytes/4096 calls (" + bytes / 4096.0 + " bytes/call, -Xint)";
            }
            return "PASS: " + (original ? "original Java" : "Kotlin") + " ticket objective registration; assertions=" + assertions
                    + "; stable 1000 calls=" + stableAdds + " addObjective / " + stableDuplicates + " duplicate exceptions" + measured;
        }

        private static void repeat(int count) { for (int index = 0; index < count; index++) TicketSystem.addObjectivesIfMissing(world); }
        private static void checkObjective(Objective objective, String title) {
            check(objective != null, "Missing objective " + title);
            check(objective.getCriteria() == ObjectiveCriteria.DUMMY && objective.getRenderType() == ObjectiveCriteria.RenderType.INTEGER, "Objective criteria/render type changed");
            check(objective.getDisplayName().getString().equals(title) && !objective.displayAutoUpdate() && objective.numberFormat() == null, "Objective settings changed");
        }
        private static void check(boolean condition, String message) { assertions++; if (!condition) throw new AssertionError(message); }

        public static final class CountingScoreboard extends Scoreboard {
            long addCalls, duplicateFailures, injectedFailures;
            String failName;
            @Override public Objective addObjective(String name, ObjectiveCriteria criteria, Component displayName,
                    ObjectiveCriteria.RenderType renderType, boolean displayAutoUpdate, NumberFormat numberFormat) {
                addCalls++;
                if (name.equals(failName)) { injectedFailures++; throw new IllegalStateException("fixture failed objective " + name); }
                try { return super.addObjective(name, criteria, displayName, renderType, displayAutoUpdate, numberFormat); }
                catch (IllegalArgumentException failure) { duplicateFailures++; throw failure; }
            }
            Objective seed(String name) { return super.addObjective(name, ObjectiveCriteria.TRIGGER, Component.literal("User custom"), ObjectiveCriteria.RenderType.HEARTS, true, null); }
            void clearCounters() { addCalls = duplicateFailures = injectedFailures = 0; }
        }
    }
}

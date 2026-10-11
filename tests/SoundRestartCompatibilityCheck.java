import mtr.data.TrainClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import org.objectweb.asm.*;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarFile;

/** Runs the production sound class, replacing only the client and audio-device boundary. */
public final class SoundRestartCompatibilityCheck {
    private static final String TARGET = "mtr.sound.TrainLoopingSoundInstance";
    private static final Set<SoundInstance> ACTIVE = Collections.newSetFromMap(new IdentityHashMap<>());
    private static final Set<SoundInstance> STOPPING = Collections.newSetFromMap(new IdentityHashMap<>());
    private static Minecraft minecraft;
    private static SoundManager manager;
    private static int starts, stops;

    public static Minecraft minecraft() { return minecraft; }
    public static SoundManager sounds(Minecraft receiver) { require(receiver == minecraft, "Wrong client"); return manager; }
    public static boolean active(SoundManager receiver, SoundInstance sound) { require(receiver == manager, "Wrong manager"); return ACTIVE.contains(sound); }
    public static Object play(SoundManager receiver, SoundInstance sound) {
        require(receiver == manager, "Wrong play manager");
        require(!((TickableSoundInstance) sound).isStopped(), "Replayed a terminally disposed sound");
        require(ACTIVE.add(sound), "Started a duplicate channel before the old channel was released");
        starts++;
        return null;
    }
    public static void stop(SoundManager receiver, SoundInstance sound) {
        require(receiver == manager, "Wrong stop manager");
        stops++;
        // SoundEngine.stop queues a channel stop; isActive stays true until engine cleanup.
        STOPPING.add(sound);
    }

    public static void main(String[] args) throws Exception {
        var output = System.out;
        net.minecraft.SharedConstants.tryDetectVersion();
        net.minecraft.server.Bootstrap.bootStrap();
        Field access = Unsafe.class.getDeclaredField("theUnsafe");
        access.setAccessible(true);
        Unsafe unsafe = (Unsafe) access.get(null);
        minecraft = (Minecraft) unsafe.allocateInstance(Minecraft.class);
        manager = (SoundManager) unsafe.allocateInstance(SoundManager.class);
        Class<?> type = loadProduction(Path.of(args[0]));
        var constructor = type.getConstructor(SoundEvent.class, TrainClient.class);
        var setData = type.getMethod("setData", float.class, float.class, BlockPos.class);
        // This same regression runs on the Java 1.21.1 and Java/Kotlin 26.2 branches.
        Class<?> idType;
        try { idType = Class.forName("net.minecraft.resources.Identifier"); }
        catch (ClassNotFoundException olderGame) { idType = Class.forName("net.minecraft.resources.ResourceLocation"); }
        Object id = idType.getMethod("parse", String.class).invoke(null, "mtr:restart_regression");
        SoundEvent event = (SoundEvent) SoundEvent.class.getMethod("createVariableRangeEvent", idType).invoke(null, id);

        for (float silence : new float[]{0F, -0F, -0.25F}) for (int cleanupDelay : new int[]{1, 20}) {
            ACTIVE.clear(); STOPPING.clear(); starts = stops = 0;
            TrainClient train = (TrainClient) unsafe.allocateInstance(TrainClient.class);
            TickableSoundInstance sound = (TickableSoundInstance) constructor.newInstance(event, train);
            setData.invoke(sound, silence, 0F, BlockPos.ZERO);
            require(starts == 0 && stops == 0, "An initially silent sound used an audio channel");
            for (int cycle = 0; cycle < 3; cycle++) {
                setData.invoke(sound, 1F, 1F, BlockPos.ZERO);
                require(starts == cycle + 1, "Positive volume did not start fresh playback");
                for (int frame = 0; frame < 120; frame++) {
                    setData.invoke(sound, 0.5F, 1.5F, new BlockPos(frame, 64, 0));
                    sound.tick();
                }
                require(starts == cycle + 1, "Continuous playback restarted on a volume/pitch/position update");
                int previousStops = stops;
                setData.invoke(sound, silence, 1F, BlockPos.ZERO);
                sound.tick();
                require(stops > previousStops, "Volume zero only muted the channel; restart would resume the old audio position");
                require(!sound.isStopped(), "Temporary silence permanently disposed the reusable sound");
                // Resume before the engine finishes releasing the channel: do not double-play.
                for (int frame = 0; frame < cleanupDelay; frame++) setData.invoke(sound, 1F, 1F, BlockPos.ZERO);
                require(starts == cycle + 1, "Replayed while the previous channel was still active");
                ACTIVE.removeAll(STOPPING); STOPPING.clear();
            }
            setData.invoke(sound, 1F, 1F, BlockPos.ZERO);
            require(starts == 4, "Repeated silent intervals did not restart playback");
            ACTIVE.clear(); // Resource reload/device reset releases channels externally.
            setData.invoke(sound, 1F, 1F, BlockPos.ZERO);
            require(starts == 5, "Playback did not recover after external channel cleanup");
            train.isRemoved = true; sound.tick();
            require(sound.isStopped(), "Removing the train did not dispose its looping sound");
            ACTIVE.clear();
            setData.invoke(sound, 1F, 1F, BlockPos.ZERO);
            require(starts == 5, "Removed train restarted its sound");
        }
        output.println("PASS: production train audio restarts after silence; 6 scenarios, repeated replay, delayed channel cleanup, continuous playback, reload and removal (no audio device)");
    }

    private static Class<?> loadProduction(Path source) throws Exception {
        String entry = TARGET.replace('.', '/') + ".class";
        byte[] bytes;
        if (Files.isDirectory(source)) bytes = Files.readAllBytes(source.resolve(entry));
        else try (JarFile jar = new JarFile(source.toFile()); var input = jar.getInputStream(jar.getJarEntry(entry))) { bytes = input.readAllBytes(); }
        Set<String> adapters = new HashSet<>();
        ClassWriter writer = new ClassWriter(0);
        new ClassReader(bytes).accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                    @Override public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        String adapter = switch (owner + "." + name) {
                            case "net/minecraft/client/Minecraft.getInstance" -> "minecraft";
                            case "net/minecraft/client/Minecraft.getSoundManager" -> "sounds";
                            case "net/minecraft/client/sounds/SoundManager.isActive" -> "active";
                            case "net/minecraft/client/sounds/SoundManager.play" -> "play";
                            case "net/minecraft/client/sounds/SoundManager.stop" -> "stop";
                            default -> null;
                        };
                        if (adapter == null) { super.visitMethodInsn(opcode, owner, name, descriptor, isInterface); return; }
                        adapters.add(adapter);
                        if (opcode != Opcodes.INVOKESTATIC) descriptor = "(L" + owner + ";" + descriptor.substring(1);
                        boolean voidPlay = adapter.equals("play") && Type.getReturnType(descriptor).equals(Type.VOID_TYPE);
                        if (adapter.equals("play")) descriptor = descriptor.substring(0, descriptor.indexOf(')') + 1) + "Ljava/lang/Object;";
                        super.visitMethodInsn(Opcodes.INVOKESTATIC, "SoundRestartCompatibilityCheck", adapter, descriptor, false);
                        if (voidPlay) super.visitInsn(Opcodes.POP);
                    }
                };
            }
        }, 0);
        require(adapters.containsAll(Set.of("minecraft", "sounds", "active", "play")), "Missing production audio boundaries: " + adapters);
        byte[] adapted = writer.toByteArray();
        ClassLoader loader = new ClassLoader(SoundRestartCompatibilityCheck.class.getClassLoader()) {
            @Override protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                if (!name.equals(TARGET)) return super.loadClass(name, resolve);
                Class<?> type = findLoadedClass(name);
                if (type == null) type = defineClass(name, adapted, 0, adapted.length);
                if (resolve) resolveClass(type);
                return type;
            }
        };
        return Class.forName(TARGET, true, loader);
    }

    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}

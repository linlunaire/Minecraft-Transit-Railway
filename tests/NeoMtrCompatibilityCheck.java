package mtr.data;

import mtr.render.TrainRendererBase;
import mtr.sound.TrainSoundBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.*;
import sun.misc.Unsafe;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.security.ProtectionDomain;
import java.util.*;

/** Executes production methods; only client availability, culling, audio and drawing are fixtures. */
public final class NeoMtrCompatibilityCheck {
    private static final String TRAIN = "mtr.data.TrainClient";
    private static Minecraft minecraft;
    private static int assertions;
    private static boolean trainBoundariesAdapted;

    public static Minecraft minecraft() { return minecraft; }
    public static boolean hideTranslucentParts() { return true; }
    public static boolean isPositionVisible(Vec3 offset, double x, double y, double z) { return true; }

    public static void premain(String options, Instrumentation instrumentation) {
        // Keep Minecraft/MTR in their original loader: package-private/protected accesses must stay valid.
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override public byte[] transform(ClassLoader loader, String name, Class<?> redefining,
                    ProtectionDomain domain, byte[] bytes) {
                return "mtr/data/TrainClient".equals(name) ? adaptTrain(bytes) : null;
            }
        });
    }

    public static void main(String[] args) throws Exception {
        var output = System.out;
        net.minecraft.SharedConstants.tryDetectVersion();
        net.minecraft.server.Bootstrap.bootStrap();
        List<String> failures = new ArrayList<>();
        for (String check : List.of("lift", "disconnect", "connection")) {
            try {
                switch (check) {
                    case "lift" -> checkLift();
                    case "disconnect" -> checkDisconnect();
                    case "connection" -> checkConnection(Path.of(args[0]));
                }
                output.println("PASS: " + check);
            } catch (AssertionError failure) {
                failures.add(check + ": " + failure.getMessage());
                output.println("FAIL: " + failures.getLast());
            }
        }
        if (!failures.isEmpty()) throw new AssertionError(String.join("; ", failures));
        output.println("PASS: NeoMTR regressions, " + assertions + " assertions (no game, GPU or real player connection)");
    }

    private static void checkLift() {
        for (int target : new int[]{-10, 10}) for (boolean doubleSided : new boolean[]{false, true}) {
            Lift lift = new Lift(BlockPos.ZERO, Direction.NORTH) {};
            lift.isDoubleSided = doubleSided;
            lift.doorOpen = false;
            lift.doorValue = 0;
            lift.frontCanOpen = true;
            lift.backCanOpen = true;
            lift.pressButton(target);
            for (int tick = 0; tick < 3; tick++) {
                // The moving branch does not query blocks. A null world catches accidental world access.
                lift.tick(null, 1);
                require(Math.signum(lift.getPositionY()) == Math.signum(target), "Lift did not move towards its target");
                require(!lift.frontCanOpen && !lift.backCanOpen, "Moving lift retains a walkable front/back exit");
                require(!lift.doorOpen && lift.doorValue == 0, "Moving lift changed its closed-door state");
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void checkDisconnect() throws Exception {
        var disconnected = (ServerPlayer) unsafe().allocateInstance(ServerPlayer.class);
        var connected = (ServerPlayer) unsafe().allocateInstance(ServerPlayer.class);
        disconnected.setId(1);
        connected.setId(2);
        var module = new RailwayDataCoolDownModule(null, null, Map.of());
        Map<String, Object> values = new LinkedHashMap<>();
        values.put("playerRidingCoolDown", 2);
        values.put("playerRidingRoute", 123L);
        values.put("playerSeats", null);
        values.put("playerSeatCoolDowns", 3);
        values.put("playerShiftCoolDowns", 5);
        for (var entry : values.entrySet()) {
            Map map = (Map) field(RailwayDataCoolDownModule.class, entry.getKey()).get(module);
            map.put(disconnected, entry.getValue());
            map.put(connected, entry.getValue());
        }
        module.onPlayerDisconnect(disconnected);
        module.onPlayerDisconnect(disconnected);
        for (String name : values.keySet()) {
            Map map = (Map) field(RailwayDataCoolDownModule.class, name).get(module);
            require(!map.containsKey(disconnected), "Disconnected player retained by " + name);
            require(map.size() == 1 && map.containsKey(connected), "Disconnect removed another player's " + name);
        }
        require(module.canRide(disconnected) && !module.canRide(connected), "Disconnect retained riding cooldown");
    }

    private static void checkConnection(Path source) throws Exception {
        minecraft = (Minecraft) unsafe().allocateInstance(Minecraft.class);
        minecraft.player = (LocalPlayer) unsafe().allocateInstance(LocalPlayer.class);
        Class<?> type = Class.forName(TRAIN);
        require(trainBoundariesAdapted, "Client boundary agent did not run");
        require(Path.of(type.getProtectionDomain().getCodeSource().getLocation().toURI()).toRealPath().equals(source.toRealPath()),
                "Test did not load the requested production class source");
        Object train = unsafe().allocateInstance(type);
        var renderer = new RecordingRenderer();
        field(Train.class, "spacing").setInt(train, 20);
        field(Train.class, "width").setInt(train, 3);
        field(type, "trainRenderer").set(train, renderer);
        field(type, "trainSound").set(train, new SilentSound());
        field(type, "vehicleRidingClient").set(train, new VehicleRidingClient(Set.of(), null));
        var simulate = type.getDeclaredMethod("simulateCar", Level.class, int.class, float.class,
                double.class, double.class, double.class, float.class, float.class,
                double.class, double.class, double.class, float.class, float.class,
                boolean.class, boolean.class, double.class);
        simulate.setAccessible(true);
        // x/y/z, yaw/pitch: level, slopes, pure vertical, coincident anchors, turns and non-finite input.
        double[][] cases = {{0, 1, 20, 0, 0}, {0, -1, 20, 0, 0}, {0, 0, 20, 0, 0},
                {0, 2, 18, 0, 0}, {0, 0, 18, 0, 0}, {4, 2, 19, 0.3, -0.2},
                {0, Double.POSITIVE_INFINITY, 20, 0, 0}};
        for (double[] sample : cases) {
            float yaw = (float) sample[3], pitch = (float) sample[4];
            Vec3 first = new Vec3(0, 0, 9);
            Vec3 second = new Vec3(0, 0, -9).xRot(pitch).yRot(yaw).add(sample[0], sample[1], sample[2]);
            Vec3 delta = second.subtract(first);
            double expected = Double.isFinite(delta.length())
                    ? Math.atan2(delta.y, Math.hypot(delta.x, delta.z)) : 0;
            for (double carSpacing : new double[]{20, 40, 0}) {
                renderer.pitches.clear();
                simulate.invoke(train, null, 1, 1F, sample[0], sample[1], sample[2], yaw, pitch,
                        0D, 0D, 0D, 0F, 0F, false, false, carSpacing);
                require(renderer.pitches.size() == 2, "Connection/barrier was not rendered");
                for (float actual : renderer.pitches) {
                    require(Float.isFinite(actual) && Math.abs(actual - expected) < 0.000001,
                            "Connection pitch must use its anchors, not car spacing: expected " + expected + ", got " + actual);
                }
            }
        }
    }

    private static byte[] adaptTrain(byte[] bytes) {
        Set<String> adaptedBoundaries = new HashSet<>();
        var writer = new ClassWriter(0);
        new ClassReader(bytes).accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                var original = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (!name.equals("simulateCar")) return original;
                return new MethodVisitor(Opcodes.ASM9, original) {
                    @Override public void visitMethodInsn(int opcode, String owner, String method, String desc, boolean isInterface) {
                        String adapter = switch (owner + "." + method) {
                            case "net/minecraft/client/Minecraft.getInstance" -> "minecraft";
                            case "mtr/client/Config.hideTranslucentParts" -> "hideTranslucentParts";
                            case "mtr/render/TrainRendererBase.isPositionVisible" -> "isPositionVisible";
                            default -> null;
                        };
                        if (adapter == null) super.visitMethodInsn(opcode, owner, method, desc, isInterface);
                        else {
                            require(opcode == Opcodes.INVOKESTATIC, "Client boundary changed: " + adapter);
                            adaptedBoundaries.add(adapter);
                            super.visitMethodInsn(opcode, "mtr/data/NeoMtrCompatibilityCheck", adapter, desc, false);
                        }
                    }
                };
            }
        }, 0);
        require(adaptedBoundaries.equals(Set.of("minecraft", "hideTranslucentParts", "isPositionVisible")),
                "Missing client fixtures: " + adaptedBoundaries);
        trainBoundariesAdapted = true;
        return writer.toByteArray();
    }

    private static final class RecordingRenderer extends TrainRendererBase {
        private final List<Float> pitches = new ArrayList<>();
        @Override public TrainRendererBase createTrainInstance(TrainClient train) { return this; }
        @Override public void renderCar(int i, double x, double y, double z, float yaw, float pitch, boolean left, boolean right) {}
        @Override public void renderConnection(Vec3 a, Vec3 b, Vec3 c, Vec3 d, Vec3 e, Vec3 f, Vec3 g, Vec3 h,
                double x, double y, double z, float yaw, float pitch) { pitches.add(pitch); }
        @Override public void renderBarrier(Vec3 a, Vec3 b, Vec3 c, Vec3 d, Vec3 e, Vec3 f, Vec3 g, Vec3 h,
                double x, double y, double z, float yaw, float pitch) { pitches.add(pitch); }
    }

    private static final class SilentSound extends TrainSoundBase {
        @Override public TrainSoundBase createTrainInstance(TrainClient train) { return this; }
        @Override public void playNearestCar(Level world, BlockPos pos, int i) {}
        @Override public void playAllCars(Level world, BlockPos pos, int i) {}
        @Override public void playAllCarsDoorOpening(Level world, BlockPos pos, int i) {}
    }

    private static Unsafe unsafe() throws Exception { return (Unsafe) field(Unsafe.class, "theUnsafe").get(null); }
    private static Field field(Class<?> type, String name) throws Exception {
        Field field = type.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }
    private static void require(boolean condition, String message) {
        assertions++;
        if (!condition) throw new AssertionError(message);
    }
}

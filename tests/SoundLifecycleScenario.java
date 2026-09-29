package mtr.sound;

import mtr.client.TrainProperties;
import mtr.data.Train;
import mtr.data.TrainClient;
import mtr.sound.bve.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/** In-memory client/audio adapters with real train getters, sound bases and BVE table lookups. */
public final class SoundLifecycleScenario {
    private static final StringBuilder RECORDS = new StringBuilder();
    private static final List<String> EVENTS = new ArrayList<>();
    private static final Set<SoundInstance> ACTIVE = Collections.newSetFromMap(new IdentityHashMap<>());
    private static Unsafe unsafe;
    private static Minecraft minecraft;
    private static SoundManager manager;
    private static ClientLevel world;
    private static LocalPlayer player;
    private static BlockPos playerPos;
    private static boolean audible, failPlay;
    private static float elapsed;
    private static int pressure, compressor, randomCalls, checks;

    public static Minecraft minecraft() { EVENTS.add("client"); return minecraft; }
    public static SoundManager sounds(Minecraft receiver) { require(receiver == minecraft, "Client receiver"); EVENTS.add("manager"); return manager; }
    public static BlockPos playerPosition(LocalPlayer receiver) { require(receiver == player, "Player receiver"); EVENTS.add("player-position"); return playerPos; }
    public static boolean active(SoundManager receiver, SoundInstance sound) { require(receiver == manager, "Manager receiver"); EVENTS.add("active:" + sound.getIdentifier()); return ACTIVE.contains(sound); }
    public static SoundEngine.PlayResult play(SoundManager receiver, SoundInstance sound) {
        require(receiver == manager, "Play manager receiver"); EVENTS.add("loop:" + sound.getIdentifier() + ":" + state(sound));
        if (failPlay) throw new IllegalStateException("fixture audio failure");
        ACTIVE.add(sound); return null;
    }
    public static void stop(SoundManager receiver, SoundInstance sound) {
        require(receiver == manager, "Stop manager receiver"); EVENTS.add("stop:" + sound.getIdentifier());
        ACTIVE.remove(sound);
    }
    public static float frameDuration() { EVENTS.add("frame:" + bits(elapsed)); return elapsed; }
    public static boolean canPlay() { EVENTS.add("audible:" + audible); return audible; }
    public static void playLocal(ClientLevel receiver, BlockPos pos, SoundEvent event, SoundSource source, float volume, float pitch, boolean delay) {
        Objects.requireNonNull(receiver); // Preserve the virtual-call null receiver boundary.
        EVENTS.add("local:" + event.location() + ":" + position(pos) + ":" + source + ":" + bits(volume) + ":" + bits(pitch) + ":" + delay);
        if (failPlay) throw new IllegalStateException("fixture local audio failure");
    }
    public static int randomInt(ThreadLocalRandom ignored, int minimum, int maximum) {
        EVENTS.add("random:" + minimum + ":" + maximum); randomCalls++;
        int value = minimum == 0 ? compressor : pressure;
        require(value >= minimum && value < maximum, "Random bounds"); return value;
    }

    public static String run() throws Exception {
        net.minecraft.SharedConstants.tryDetectVersion(); net.minecraft.server.Bootstrap.bootStrap();
        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe"); unsafeField.setAccessible(true); unsafe = (Unsafe) unsafeField.get(null);
        minecraft = allocate(Minecraft.class); player = allocate(LocalPlayer.class); world = allocate(ClientLevel.class);
        reset(); looping(); trainLooping(); jon(); bve(); overrides();
        record("checks", Integer.toString(checks)); return RECORDS.toString();
    }

    private static void looping() throws Exception {
        reset(); LoopingSoundInstance sound = new LoopingSoundInstance("test_loop");
        record("loop-initial", state(sound) + "|" + sound.isStopped()); sound.tick();
        BlockPos[] positions = {new BlockPos(20, 64, 0), new BlockPos(10, 64, 0), new BlockPos(10, 64, 0), new BlockPos(32, 64, 0), new BlockPos(33, 64, 0), new BlockPos(-8, 63, 0)};
        for (BlockPos pos : positions) { EVENTS.clear(); sound.setPos(pos, false); record("loop-position", position(pos) + "|" + state(sound) + "|" + EVENTS); }
        EVENTS.clear(); sound.setPos(new BlockPos(0, 0, 0), true); record("loop-remove-other", state(sound) + "|" + EVENTS);
        sound.setPos(new BlockPos(-8, 63, 0), true); record("loop-remove-selected", state(sound));
        ACTIVE.clear(); sound.setPos(new BlockPos(32, 64, 0), false); record("loop-boundary-32", state(sound) + "|" + EVENTS);
        EVENTS.clear(); minecraft.player = null; record("loop-null-player", outcome(() -> { sound.setPos(null, false); return state(sound); }) + "|" + EVENTS);
        record("loop-remove-null", outcome(() -> { sound.setPos(null, true); return null; }));
        minecraft.player = player; manager = null; EVENTS.clear(); sound.setPos(new BlockPos(0, 64, 0), false); record("loop-null-manager", state(sound) + "|" + EVENTS);
        record("loop-null-position", outcome(() -> { sound.setPos(null, false); return null; }));
        record("loop-null-id", outcome(() -> new LoopingSoundInstance(null)));
        record("loop-invalid-id", outcome(() -> new LoopingSoundInstance("BAD?")));
    }

    private static void trainLooping() throws Exception {
        reset(); TrainProbe train = train(3); TrainLoopingSoundInstance sound = new TrainLoopingSoundInstance(event("train_loop"), train);
        record("train-loop-initial", state(sound) + "|" + sound.isStopped() + "|" + sound.canStartSilent() + "|" + sound.canPlaySound());
        float[] values = {0, -0F, 1, -1, Float.MIN_VALUE, Float.MAX_VALUE, Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY};
        for (float volume : values) for (float pitch : values) {
            ACTIVE.clear(); EVENTS.clear(); sound.setData(volume, pitch, new BlockPos(3, -4, 5));
            record("train-loop-values", bits(volume) + "," + bits(pitch) + "|" + state(sound) + "|" + EVENTS);
        }
        EVENTS.clear(); record("train-loop-null-position", outcome(() -> { sound.setData(2, 0, null); return null; }) + "|" + state(sound) + "|" + EVENTS);
        train.isRemoved = true; ACTIVE.clear(); EVENTS.clear(); sound.setData(1, 2, BlockPos.ZERO); sound.tick();
        record("train-loop-removed", state(sound) + "|" + sound.isStopped() + "|" + EVENTS);
        train.isRemoved = false; EVENTS.clear(); sound.setData(1, 3, BlockPos.ZERO); sound.tick();
        record("train-loop-after-stop", state(sound) + "|" + sound.isStopped() + "|" + EVENTS);
        manager = null; TrainLoopingSoundInstance missing = new TrainLoopingSoundInstance(event("missing"), null);
        EVENTS.clear(); missing.setData(0, 0, BlockPos.ZERO); record("train-loop-null-safe", state(missing) + "|" + EVENTS);
        record("train-loop-null-tick", outcome(() -> { missing.tick(); return null; }));
        manager = allocate(SoundManager.class); EVENTS.clear(); record("train-loop-null-train", outcome(() -> { missing.setData(0, 0, BlockPos.ZERO); return null; }) + "|" + EVENTS);
        failPlay = true; ACTIVE.clear(); EVENTS.clear(); record("train-loop-play-failure", outcome(() -> { sound.setData(2, -0F, new BlockPos(9, 10, 11)); return null; }) + "|" + state(sound) + "|" + EVENTS);
        record("train-loop-null-event", outcome(() -> new TrainLoopingSoundInstance(null, train)));
    }

    private static void jon() throws Exception {
        reset(); TrainProbe train = train(2);
        JonTrainSound.JonTrainSoundConfig config = new JonTrainSound.JonTrainSoundConfig("door", 100, 0.5F, false);
        require(!config.constantPlaybackSpeed, "Four-argument config default");
        JonTrainSound prototype = new JonTrainSound("jon", config);
        JonTrainSound sound = (JonTrainSound) prototype.createTrainInstance(train); ((Random) read(sound, "random")).setSeed(0x4a4f4eL);
        require(sound != prototype && sound.config == config && sound.soundId == prototype.soundId, "Jon factory ownership");
        EVENTS.clear(); sound.playNearestCar(null, null, -1); sound.playAllCars(null, null, -1); record("jon-null-world", EVENTS.toString());
        audible = false; sound.playNearestCar(world, null, 0); record("jon-muted", EVENTS.toString()); audible = true;
        float[] speeds = {-1, -0F, 0, Math.nextDown(0.04F), 0.04F, Math.nextUp(0.04F), 0.12F, 1.2F, 1.24F, 4, Float.NaN, Float.POSITIVE_INFINITY};
        for (boolean constant : new boolean[]{false, true}) for (boolean coasting : new boolean[]{false, true}) {
            JonTrainSound candidate = (JonTrainSound) new JonTrainSound("jon", new JonTrainSound.JonTrainSoundConfig("door", 97, 0.5F, coasting, constant)).createTrainInstance(train);
            ((Random) read(candidate, "random")).setSeed(1947);
            for (float speed : speeds) for (float change : new float[]{-0.01F, 0, 0.01F}) {
                update(train, speed, speed - change, true, 0, 0); EVENTS.clear();
                record("jon-speed", constant + ":" + coasting + ":" + bits(speed) + ":" + bits(change) + "|" + outcome(() -> { candidate.playNearestCar(world, BlockPos.ZERO, 0); return null; }) + "|" + EVENTS);
            }
        }
        update(train, 1.5F, 1.4F, true, 0, 0); ((Random) read(sound, "random")).setSeed(1536); MessageDigest digest = MessageDigest.getInstance("SHA-256"); int randomSounds = 0;
        for (int index = 0; index < 2048; index++) { EVENTS.clear(); sound.playNearestCar(world, BlockPos.ZERO, 0); for (String entry : EVENTS) if (entry.contains("jon_random")) randomSounds++; digest.update(EVENTS.toString().getBytes(StandardCharsets.UTF_8)); }
        require(randomSounds > 0, "Jon random sound branch exercised"); record("jon-random", randomSounds + "|" + HexFormat.of().formatHex(digest.digest()));
        for (float[] doors : new float[][]{{0, 0}, {0, 0.1F}, {1, 0.5F}, {0.5F, Math.nextDown(0.5F)}, {0.5F, 0}, {Float.NaN, 0}}) {
            update(train, 0, 0, true, doors[1], doors[0]); EVENTS.clear(); sound.playAllCarsDoorOpening(world, BlockPos.ZERO, 0); record("jon-door", bits(doors[0]) + ":" + bits(doors[1]) + "|" + EVENTS);
        }
        EVENTS.clear(); JonTrainSound nullConfig = new JonTrainSound(null, null); nullConfig.playNearestCar(null, null, 0); nullConfig.playAllCarsDoorOpening(null, null, 0); record("jon-null-config-safe", EVENTS.toString());
        record("jon-null-config-used", outcome(() -> { nullConfig.playNearestCar(world, null, 0); return null; }));
        record("jon-null-train-used", outcome(() -> { prototype.playNearestCar(world, null, 0); return null; }));
        JonTrainSound noId = (JonTrainSound) new JonTrainSound(null, config).createTrainInstance(null); EVENTS.clear(); noId.playNearestCar(world, null, 0); record("jon-null-id-safe", EVENTS.toString());
        update(train, 1, 0, true, 0, 0); EVENTS.clear(); sound.playNearestCar(world, null, 0); record("jon-forward-null-position", EVENTS.toString());
        train.overrideDoors = true; train.opening = true; train.closing = true; EVENTS.clear(); sound.playAllCarsDoorOpening(world, BlockPos.ZERO, 0); record("jon-virtual-doors", EVENTS.toString());
    }

    private static void bve() throws Exception {
        reset(); TrainProbe train = train(3); BveTrainSoundConfig config = config(true);
        BveTrainSound prototype = new BveTrainSound(config); EVENTS.clear(); prototype.playNearestCar(null, null, -1); prototype.playAllCars(null, null, -1); prototype.playAllCarsDoorOpening(null, null, -1); record("bve-prototype", bveState(prototype) + "|" + EVENTS);
        BveTrainSound sound = (BveTrainSound) prototype.createTrainInstance(train); require(sound.config == config, "BVE config identity");
        record("bve-constructor", bveState(sound) + "|" + EVENTS); require(randomCalls == 2, "Two BVE random boundary calls");
        float[][] steps = {{0, 0, 1}, {0.01F, 0, 1}, {0.1F, 0.09F, 1}, {1, 0.9F, 1}, {1, 1, 1}, {1, 1.1F, 1}, {0.2F, 0.3F, 1}, {0.1F, 0.2F, 1}, {0.1F, 0.1F, 1}, {0.01F, 0, 1}, {0, 0, 1}, {1, 0, 0}, {1, 0, -0F}, {1, 0, Float.NaN}, {1, 0, Float.POSITIVE_INFINITY}};
        for (int index = 0; index < steps.length; index++) {
            float[] step = steps[index]; update(train, step[0], step[1], index < 10, 0, 0); elapsed = step[2]; EVENTS.clear();
            record("bve-step", index + "|" + outcome(() -> { sound.playNearestCar(world, new BlockPos(1, 64, -2), 0); return null; }) + "|" + bveState(sound) + "|" + EVENTS);
        }
        // Independent shoe-gain branches, compressor thresholds and int compound casts.
        for (float speed : new float[]{-1F, 0, Math.nextDown(1.39F), 1.39F, Math.nextUp(1.39F), 12.5F, Math.nextUp(12.5F), 30F, Float.NaN}) {
            BveTrainSound candidate = (BveTrainSound) prototype.createTrainInstance(train); update(train, speed / 20, speed / 20 + 0.1F, true, 0, 0); elapsed = 1; EVENTS.clear(); candidate.playNearestCar(world, BlockPos.ZERO, 0); record("bve-shoe", bits(speed) + "|" + bveState(candidate) + "|" + EVENTS);
        }
        for (int reservoir : new int[]{699, 700, 701, 799, 800, 801}) for (float duration : new float[]{1, 4, 20}) {
            BveTrainSound candidate = (BveTrainSound) prototype.createTrainInstance(train); write(candidate, "mrPress", reservoir); write(candidate, "isCompressorActive", reservoir % 2 == 0); write(candidate, "isCompressorActiveLastElapsed", reservoir % 2 != 0);
            elapsed = duration; update(train, 0, 0, true, 0, 0); EVENTS.clear(); candidate.playNearestCar(world, BlockPos.ZERO, 0); record("bve-compressor", reservoir + ":" + bits(duration) + "|" + bveState(candidate) + "|" + EVENTS);
        }
        for (boolean jacobs : new boolean[]{false, true}) {
            write(train.properties, "isJacobsBogie", jacobs); BveTrainSound joints = (BveTrainSound) prototype.createTrainInstance(train);
            for (double progress : new double[]{0, 7, 20, 20, 39.999, 40, 100}) for (int car = 0; car < 3; car++) {
                update(train, 0.2F, 0.2F, true, 0, 0); write(train, "railProgress", progress); EVENTS.clear(); joints.playAllCars(world, BlockPos.ZERO, car); record("bve-joint", jacobs + ":" + progress + ":" + car + "|" + Arrays.deepToString((int[][]) read(joints, "bogieRailId")) + "|" + EVENTS);
            }
        }
        for (float[] doors : new float[][]{{0, 0.1F}, {1, 0.5F}, {0.5F, 0}, {0, 0}}) {
            update(train, 0, 0, true, doors[1], doors[0]); EVENTS.clear(); sound.playAllCarsDoorOpening(world, BlockPos.ZERO, 0); record("bve-door", bits(doors[0]) + ":" + bits(doors[1]) + "|" + EVENTS);
        }
        train.overrideDoors = true; train.opening = true; train.closing = true; EVENTS.clear(); sound.playAllCarsDoorOpening(world, null, 0); record("bve-virtual-doors", EVENTS.toString());
        BveTrainSoundConfig emptyConfig = config(false); BveTrainSound empty = (BveTrainSound) new BveTrainSound(emptyConfig).createTrainInstance(train); train.properties = null;
        record("bve-null-properties-skipped", outcome(() -> { empty.playAllCars(null, null, 0); return null; }));
        record("bve-null-properties-used", outcome(() -> { sound.playAllCars(world, null, 0); return null; }));
        train.properties = properties(); update(train, 1, 1, true, 0, 0); elapsed = 1;
        CountMotor motor = new CountMotor(); write(config, "motorData", motor); BveTrainSound counted = (BveTrainSound) prototype.createTrainInstance(train); motor.calls.clear(); motor.remaining = 3; EVENTS.clear(); counted.playNearestCar(world, BlockPos.ZERO, 0); record("bve-motor-dispatch", motor.calls.toString());
        motor.remaining = 100; motor.count = 41; EVENTS.clear(); record("bve-too-many-motors", outcome(() -> { counted.playNearestCar(world, BlockPos.ZERO, 0); return null; }));
        motor.count = 2; write(config, "motorData", actualMotor());
        failPlay = true; ACTIVE.clear(); EVENTS.clear(); record("bve-output-failure", outcome(() -> { sound.playNearestCar(world, BlockPos.ZERO, 0); return null; }) + "|" + bveState(sound) + "|" + EVENTS); failPlay = false;
        write(sound, "accelLastElapsed", -1F); update(train, 0, 0, true, 0, 0); EVENTS.clear(); record("bve-null-world-used", outcome(() -> { sound.playNearestCar(null, BlockPos.ZERO, 0); return null; }));
        write(sound, "accelLastElapsed", -1F); record("bve-server-world-used", outcome(() -> { sound.playNearestCar(allocate(ServerLevel.class), BlockPos.ZERO, 0); return null; }));
        TrainProbe negativeCars = train(-1); record("bve-negative-cars", outcome(() -> prototype.createTrainInstance(negativeCars)));
        record("bve-null-config-prototype", outcome(() -> new BveTrainSound(null).createTrainInstance(null)));
        record("bve-null-config-used", outcome(() -> new BveTrainSound(null).createTrainInstance(train)));
    }

    private static void overrides() throws Exception {
        reset(); StringBuilder calls = new StringBuilder();
        TrainSoundBase custom = new TrainSoundBase() {
            @Override public TrainSoundBase createTrainInstance(TrainClient train) { calls.append('c'); return null; }
            @Override public void playNearestCar(net.minecraft.world.level.Level world, BlockPos pos, int car) { calls.append('n'); }
            @Override public void playAllCars(net.minecraft.world.level.Level world, BlockPos pos, int car) { calls.append('a'); }
            @Override public void playAllCarsDoorOpening(net.minecraft.world.level.Level world, BlockPos pos, int car) { calls.append('d'); }
        };
        custom.createTrainInstance(null); custom.playNearestCar(null, null, 0); custom.playAllCars(null, null, 0); custom.playAllCarsDoorOpening(null, null, 0);
        LoopingSoundInstance loop = new LoopingSoundInstance("overrides") { @Override public boolean isStopped() { return true; } @Override public void tick() { calls.append('t'); } @Override public void setPos(BlockPos pos, boolean removed) { calls.append('p'); } };
        TrainLoopingSoundInstance trainLoop = new TrainLoopingSoundInstance(event("override_train"), null) { @Override public void setData(float volume, float pitch, BlockPos pos) { calls.append('s'); } @Override public void tick() { calls.append('k'); } @Override public boolean canStartSilent() { return false; } @Override public boolean canPlaySound() { return false; } };
        loop.tick(); loop.setPos(null, true); trainLoop.setData(0, 0, null); trainLoop.tick();
        JonTrainSound.JonTrainSoundConfig config = new JonTrainSound.JonTrainSoundConfig(null, 0, 0, false) {};
        JonTrainSound jon = new JonTrainSound(null, config) { @Override public TrainSoundBase createTrainInstance(TrainClient train) { calls.append('j'); return this; } @Override public void playNearestCar(net.minecraft.world.level.Level world, BlockPos pos, int car) { calls.append('1'); } @Override public void playAllCars(net.minecraft.world.level.Level world, BlockPos pos, int car) { calls.append('2'); } @Override public void playAllCarsDoorOpening(net.minecraft.world.level.Level world, BlockPos pos, int car) { calls.append('3'); } };
        BveTrainSound bve = new BveTrainSound(null) { @Override public TrainSoundBase createTrainInstance(TrainClient train) { calls.append('b'); return this; } @Override public void playNearestCar(net.minecraft.world.level.Level world, BlockPos pos, int car) { calls.append('4'); } @Override public void playAllCars(net.minecraft.world.level.Level world, BlockPos pos, int car) { calls.append('5'); } @Override public void playAllCarsDoorOpening(net.minecraft.world.level.Level world, BlockPos pos, int car) { calls.append('6'); } };
        for (TrainSoundBase sound : List.of(jon, bve)) { sound.createTrainInstance(null); sound.playNearestCar(null, null, 0); sound.playAllCars(null, null, 0); sound.playAllCarsDoorOpening(null, null, 0); }
        record("overrides", calls + "|" + loop.isStopped() + "|" + trainLoop.canStartSilent() + "|" + trainLoop.canPlaySound());
    }

    public static final class TrainProbe extends TrainClient {
        TrainProperties properties; boolean overrideDoors, opening, closing;
        private TrainProbe() { super(null); }
        @Override public TrainProperties getTrainProperties() { EVENTS.add("train-properties"); return properties; }
        @Override public boolean justOpening() { EVENTS.add("opening"); return overrideDoors ? opening : super.justOpening(); }
        @Override public boolean justClosing(float delay) { EVENTS.add("closing:" + bits(delay)); return overrideDoors ? closing : super.justClosing(delay); }
    }
    public static final class CountMotor extends MotorDataBase {
        final List<String> calls = new ArrayList<>(); int count = 2, remaining = 100;
        @Override public int getSoundCount() { calls.add("count"); return remaining-- > 0 ? count : 0; }
        @Override public float getPitch(int index, float speed, float accel) { calls.add("pitch:" + index + ":" + bits(speed) + ":" + bits(accel)); return 1; }
        @Override public float getVolume(int index, float speed, float accel) { calls.add("volume:" + index + ":" + bits(speed) + ":" + bits(accel)); return 0.25F; }
    }
    private static TrainProbe train(int cars) throws Exception {
        TrainProbe train = allocate(TrainProbe.class); write(train, "trainCars", cars); write(train, "spacing", 20); write(train, "accelerationConstant", 0.02F);
        write(train, "path", new ArrayList<>(Collections.nCopies(8, null))); write(train, "distances", new ArrayList<>(List.of(10D, 20D, 30D, 40D, 50D, 60D, 70D, 80D))); train.properties = properties(); return train;
    }
    private static TrainProperties properties() throws Exception { TrainProperties properties = allocate(TrainProperties.class); write(properties, "bogiePosition", 3F); return properties; }
    private static void update(TrainProbe train, float speed, float oldSpeed, boolean onRoute, float door, float oldDoor) throws Exception { write(train, "speed", speed); write(train, "oldSpeed", oldSpeed); write(train, "isOnRoute", onRoute); write(train, "doorValue", door); write(train, "oldDoorValue", oldDoor); }
    private static BveTrainSoundConfig config(boolean populated) throws Exception {
        BveTrainSoundConfig config = allocate(BveTrainSoundConfig.class); write(config, "baseName", "mtr:lifecycle"); write(config, "audioBaseName", "mtr:lifecycle_");
        String text = populated ? "[mtr]\nBreakerDelay=0.1\nRegenerationLimit=72\nMotorVolumeMultiply=0.75\nMotorOutputAtCoast=0.4\nDoorCloseSoundLength=0.5\n[run]\n0=run\n[flange]\n0=flange\n[motor]\n0=motor0\n1=motor1\n[joint]\n0=joint\n[brake]\nBCRelease=air\nBCReleaseFull=airzero\nBCReleaseHigh=airhigh\nEmergency=emergency\n[door]\nOpen=open\nClose=close\n[brakehandle]\nApply=apply\nRelease=release\n[compressor]\nAttack=attack\nLoop=compressor\nRelease=end\n[others]\nNoise=noise\nShoe=shoe\n" : "";
        write(config, "soundCfg", new ConfigFile(text, config)); write(config, "motorData", actualMotor()); return config;
    }
    private static MotorData5 actualMotor() throws Exception {
        MotorData5 motor = allocate(MotorData5.class);
        for (String field : List.of("powerVolume", "powerFrequency", "brakeVolume", "brakeFrequency")) write(motor, field, new MotorData5.FloatSplines("0,0.25,0.5\n36,1,2\n72,2,4\n"));
        write(motor, "soundCount", 2); return motor;
    }
    private static String bveState(BveTrainSound sound) throws Exception {
        StringBuilder state = new StringBuilder();
        for (String field : List.of("accelLastElapsed", "onRouteLastElapsed", "motorCurrentOutput", "motorBreakerTimer", "mrPress", "isCompressorActive", "isCompressorActiveLastElapsed")) { Object value = read(sound, field); state.append(field).append('=').append(value instanceof Float f ? bits(f) : value).append(';'); }
        for (String field : List.of("soundLoopRun", "soundLoopFlange", "soundLoopNoise", "soundLoopShoe", "soundLoopCompressor")) { SoundInstance instance = (SoundInstance) read(sound, field); state.append(field).append('=').append(instance == null ? "null" : state(instance)).append(';'); }
        for (TrainLoopingSoundInstance instance : (TrainLoopingSoundInstance[]) read(sound, "soundLoopMotor")) if (instance != null) state.append(instance.getIdentifier()).append('=').append(state(instance)).append(';');
        return state.toString();
    }
    private static String state(SoundInstance sound) {
        // Inspect raw production parameters before the external audio engine resolves its Sound.
        try { return bits((float) read(sound, "volume")) + ":" + bits((float) read(sound, "pitch")) + ":" + sound.getX() + "," + sound.getY() + "," + sound.getZ() + ":" + sound.isLooping() + ":" + sound.getDelay(); }
        catch (Exception error) { throw new AssertionError(error); }
    }
    private static SoundEvent event(String id) { return SoundEvent.createVariableRangeEvent(Identifier.parse("mtr:" + id)); }
    private static void reset() throws Exception { EVENTS.clear(); ACTIVE.clear(); manager = allocate(SoundManager.class); minecraft.player = player; playerPos = new BlockPos(0, 64, 0); audible = true; failPlay = false; elapsed = 1; pressure = 750; compressor = 1; randomCalls = 0; }
    private static String position(BlockPos pos) { return pos == null ? "null" : pos.getX() + "," + pos.getY() + "," + pos.getZ(); }
    private static String bits(float value) { return Integer.toHexString(Float.floatToRawIntBits(value)); }
    private static String outcome(Action action) { checks++; try { Object value = action.run(); return value == null || value instanceof String ? String.valueOf(value) : value.getClass().getSimpleName(); } catch (Throwable error) { if (error instanceof LinkageError) throw (LinkageError) error; return "!" + error.getClass().getName(); } }
    private static void record(String name, String value) { RECORDS.append(name).append('\t').append(value).append('\n'); }
    private static <T> T allocate(Class<T> type) throws InstantiationException { return type.cast(unsafe.allocateInstance(type)); }
    private static Field field(Object object, String name) throws NoSuchFieldException { for (Class<?> type = object.getClass(); type != null; type = type.getSuperclass()) try { Field result = type.getDeclaredField(name); result.setAccessible(true); return result; } catch (NoSuchFieldException ignored) {} throw new NoSuchFieldException(name); }
    private static Object read(Object object, String name) throws Exception { return field(object, name).get(object); }
    private static void write(Object object, String name, Object value) throws Exception { field(object, name).set(object, value); }
    private static void require(boolean value, String message) { checks++; if (!value) throw new AssertionError(message); }
    @FunctionalInterface private interface Action { Object run() throws Exception; }
}

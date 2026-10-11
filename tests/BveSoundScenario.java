package mtr.sound.bve;

import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvent;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

/** Shared original-Java/current-Kotlin scenarios. Expected records come only from the frozen Java JAR. */
public final class BveSoundScenario {
    private static final Map<String, String> RESOURCES = new HashMap<>();
    private static final List<String> REQUESTS = new ArrayList<>(), SOUNDS = new ArrayList<>();
    private static final StringBuilder RECORDS = new StringBuilder();
    private static String requested;
    private static int mode, opened, closed, checks;

    public static List<Resource> getResources(ResourceManager manager, Identifier location) throws IOException {
        REQUESTS.add(String.valueOf(location)); requested = String.valueOf(location);
        if (mode == 1) throw new IOException("fixture lookup failure");
        if (mode == 2) return null;
        if (mode == 3) throw new AssertionError("fixture lookup error");
        if (mode == 4 || !RESOURCES.containsKey(requested)) return new ArrayList<>();
        return new ArrayList<>(Collections.singletonList(null));
    }
    public static InputStream getInputStream(Resource resource) throws IOException {
        if (mode == 5) throw new IOException("fixture open failure");
        if (mode == 6) return null;
        opened++;
        if (mode == 7) return new InputStream() {
            @Override public int read() throws IOException { throw new IOException("fixture read failure"); }
            @Override public void close() { closed++; }
        };
        return new ByteArrayInputStream(RESOURCES.get(requested).getBytes(StandardCharsets.UTF_8)) {
            @Override public void close() throws IOException { closed++; super.close(); }
        };
    }
    public static SoundEvent createSoundEvent(Identifier location) {
        SOUNDS.add(location.toString());
        if (mode == 8) throw new IllegalStateException("fixture sound failure");
        if (mode == 9) return null;
        return SoundEvent.createVariableRangeEvent(location);
    }

    public static String run() throws Exception {
        Locale previous = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            configScenarios(); resourceScenarios(); motor4Scenarios(); splineScenarios(); motor5Scenarios(); subclassScenarios();
            record("checks", Integer.toString(checks));
            return RECORDS.toString();
        } finally { Locale.setDefault(previous); }
    }

    private static void configScenarios() throws Exception {
        reset(); BveTrainSoundConfig config = new BveTrainSoundConfig(null, "probe");
        record("config-default", describe(config.soundCfg) + "|" + config.baseName + "|" + config.audioBaseName + "|" + config.motorData.getClass().getSimpleName() + "|" + REQUESTS);
        String[] texts = {
                "[MTR]\n Motor Noise Data Type = 4\nMotor Volume Multiply = 0.25\nDoor Close Sound Length = 2.125\nBreakerDelay = 0.125\nRegenerationLimit = 36\nMotorOutputAtCoast = -0.0",
                "[run]\n0=folder\\RUN.wav\n1=ignored.wav\n[rolling]\n0=rolling.wav\n[flange]\n0=flange.wav\n[motor]\n0=motor0.wav\n39=motor39.wav\n40=outside.wav\n[joint]\n0=joint.wav\n[switch]\n0=switch.wav",
                "[brake]\nBCRelease=air.wav\nEmergency=emergency.wav\n[door]\nOpenLeft=left.wav\nOpenRight=right.wav\nClose=close.wav\n[brakehandle]\nApply=apply.wav\nRelease=release.wav\n[compressor]\nAttack=attack.wav\nLoop=loop.wav\nRelease=end.wav\n[others]\nNoise=noise.wav\nShoe=shoe.wav",
                "[door]\nApply=fall_apply.wav\nRelease=fall_release.wav\n[compressor]\nNoise=fall_noise.wav\nShoe=fall_shoe.wav",
                "[brake]\nBCReleaseFull=full.wav\nBCReleaseHigh=high.wav\nBCRelease=air.wav",
                "[ M T R ]\r\nMotorVolumeMultiply=2 ; comment\nMotorOutputAtCoast=0.8 // comment\nBreakerDelay=4 # comment\nDoorCloseSoundLength=3\nDoorCloseSoundLength=\nignored=first=second\nignored2=\nignored3=folder/\nignored4=a.wav\n",
                "[run]\n0=thing.wav.wav\n[unknown]\nx=unused.wav\n[mtr]\nMotorVolumeMultiply=1e-40\n",
                "\u0000[mtr]\u0000\nMotorVolumeMultiply=0x1.0p-3\n\u2003[run]\u2003\n0=unicode.wav\n",
                "[mtr]\nMotorNoiseDataType=6\nRegenerationLimit=-3.6\n",
                "[run]\n0=trailing.wav=\n"
        };
        for (int index = 0; index < texts.length; index++) {
            SOUNDS.clear(); ConfigFile value = new ConfigFile(texts[index], config);
            record("config-" + index, describe(value) + "|events=" + SOUNDS);
        }
        for (String invalid : new String[]{"[run]\n-1=x.wav", "[motor]\nnotnumber=x.wav", "[mtr]\nMotorVolumeMultiply=nan", "[mtr]\nMotorNoiseDataType=4.0", "[run]\n0=x;", "[run]\n0=x#", "[run]\n0=x//", "[unknown]\na=illegal?", "[mtr]\nMotorVolumeMultiply=1e+2"}) {
            SOUNDS.clear(); record("config-invalid", outcome(() -> describe(new ConfigFile(invalid, config))) + "|events=" + SOUNDS);
        }
        record("config-null-content", outcome(() -> new ConfigFile(null, config)));
        record("config-null-empty", describe(new ConfigFile("", null)));
        record("config-null-used", outcome(() -> new ConfigFile("[run]\n0=x", null)));
        ConfigFile mutable = new ConfigFile("[brake]\nBCRelease=air.wav", config);
        require(mutable.airZero == mutable.air && mutable.airHigh == mutable.air, "Air fallback aliases");
        SoundEvent[] alias = mutable.motor; alias[39] = createSoundEvent(Identifier.parse("mtr:mutable"));
        require(mutable.motor == alias && mutable.motor[39] == alias[39], "Public sound arrays remain live");
        record("config-alias", sound(mutable.motor[39]) + "|" + (mutable.run != new ConfigFile("", null).run));
        reset(); RESOURCES.put("pack:sounds/probe/sound.cfg", "[mtr]\nMotorNoiseDataType=4");
        BveTrainSoundConfig four = new BveTrainSoundConfig(null, "pack:probe");
        record("config-bve4", four.baseName + "|" + four.audioBaseName + "|" + four.motorData.getClass().getSimpleName() + "|" + four.motorData.getSoundCount() + "|" + REQUESTS);
        record("config-null-base", outcome(() -> new BveTrainSoundConfig(null, null)));
        record("config-invalid-base", outcome(() -> new BveTrainSoundConfig(null, "Bad:Base")));
        mode = 9; record("config-null-sound", describe(new ConfigFile("[run]\n0=none\n[brake]\nBCRelease=none", config)));
        mode = 8; record("config-factory-failure", outcome(() -> new ConfigFile("[mtr]\nMotorVolumeMultiply=1", config)));
    }

    private static void resourceScenarios() {
        reset(); RESOURCES.put("mtr:read", "é\n列車\u0000");
        for (mode = 0; mode <= 7; mode++) {
            int beforeOpen = opened, beforeClose = closed;
            record("resource-" + mode, outcome(() -> HexFormat.of().formatHex(BveTrainSoundConfig.readResource(null, Identifier.parse("mtr:read")).getBytes(StandardCharsets.UTF_8))) + "|opened=" + (opened - beforeOpen) + "|closed=" + (closed - beforeClose));
        }
        mode = 0;
        record("resource-missing", BveTrainSoundConfig.readResource(null, Identifier.parse("mtr:missing")));
        record("resource-null-location", BveTrainSoundConfig.readResource(null, null));
    }

    private static void motor4Scenarios() throws Exception {
        reset(); RESOURCES.put("mtr:motor/train.dat", "OPENBVE\r\n# MOTOR_P1\n0,100,128\n1,200,64\n1,300,32\n#motor_p2\n2,75,256\n3,125,16\n#motor_b1\n0,80,128\n1,160,64\n#motor_b2\n2,90,32\n2,180,16\n");
        MotorData4 data = new MotorData4(null, "mtr:motor"); require(data.getSoundCount() == 4, "BVE4 maximum sound ID");
        record("motor4-grid", grid(data));
        reset(); MotorData4 empty = new MotorData4(null, "mtr:empty");
        record("motor4-empty", empty.getSoundCount() + "|" + outcome(() -> empty.getPitch(0, 0, 0)) + "|" + outcome(() -> empty.getPitch(0, 0, 1)));
        String[] malformed = {"#motor_p1\n0,100,", "#motor_p1\n0,100", "#motor_p1\n0, 100,128", "#motor_p1\n-1,100,128", "#motor_p1\n2147483647,100,128", "#motor_p1\n0,nan,128", "#motor_p1\n0,Infinity,128", "#motor_p1\n0,1e-40,1e38", "#motor_p1\n0,100,128,", "#motor_p1\n0,100,128 // comment"};
        for (String text : malformed) {
            RESOURCES.put("mtr:invalid/train.dat", text);
            record("motor4-parse", outcome(() -> { MotorData4 value = new MotorData4(null, "mtr:invalid"); return value.getSoundCount() + "|" + outcome(() -> value.getPitch(0, 0, 1)) + "|" + outcome(() -> value.getVolume(0, 0, 1)); }));
        }
        MotorData4.Channel channel = new MotorData4.Channel();
        var ids = channel.soundIds; ids.add(null); ids.add(4);
        channel.pitches.add(null); channel.volumes = null;
        require(channel.soundIds == ids && channel.soundIds.get(1) == 4, "Channel mutable list alias");
        record("motor4-channel", channel.soundIds + "|" + channel.pitches + "|" + channel.volumes + "|" + channel.maxEntryId + "|" + channel.maxSoundId);
        record("motor4-null-base", outcome(() -> new MotorData4(null, null).getSoundCount()));
    }

    private static void splineScenarios() throws Exception {
        reset();
        String[] texts = {"", "# comment\n// comment\nBveTs MotorNoiseTable 1.0\n", "0,1,2,,\n10,3,,4\n20,5,6,8", "0,1\n0,9\n1,10", "-0.0,1\n0.0,2\n1,4", "0, ,2\n1, ,4", "0,,,", " 0 , 1 , 2 \r\n\r\n10,3,4,", "0,1e-40\n1,3.4028235e38", "-3.4028235e38,-3.4028235e38\n3.4028235e38,3.4028235e38", "1", "0,1,\n2,2,"};
        for (String text : texts) {
            MotorData5.FloatSplines spline = new MotorData5.FloatSplines(text);
            record("spline-parse", spline.data.toString() + "|" + splineGrid(spline));
        }
        for (String text : new String[]{null, "nan,1", "0,NaN", "Infinity,1", "0,Infinity", "0,1 // comment", ",1", ",", "\u20030,1", "0,notnumber"}) record("spline-invalid", outcome(() -> new MotorData5.FloatSplines(text)));
        MotorData5.FloatSplines mutable = new MotorData5.FloatSplines("0,1\n1,2");
        var alias = mutable.data; alias.get(0).put(0.5F, 8F); require(mutable.data == alias, "Spline list identity");
        record("spline-alias", bits(mutable.getValue(0, 0.5F)));
        TreeMap<Float, Float> custom = new TreeMap<>(); custom.put(Float.NEGATIVE_INFINITY, -1F); custom.put(-0F, 2F); custom.put(0F, 3F); custom.put(Float.POSITIVE_INFINITY, 4F); custom.put(Float.NaN, 5F);
        mutable.data = new LinkedList<>(List.of(custom)); record("spline-special", splineGrid(mutable));
        mutable.data.get(0).put(0F, null); record("spline-null-value", outcome(() -> mutable.getValue(0, 0F)));
        mutable.data.set(0, null); record("spline-null-map", outcome(() -> mutable.getValue(0, 0F)));
        mutable.data = null; record("spline-null-list", outcome(() -> mutable.getValue(0, 0F)));
    }

    private static void motor5Scenarios() throws Exception {
        reset();
        String[] files = {"powervol.csv", "powerfreq.csv", "brakevol.csv", "brakefreq.csv"};
        String[] values = {"0,0,1\n10,1,0.5\n20,2,0", "0,1,2\n10,2,3\n20,3,4", "0,0.25,0.5\n10,0.5,1\n20,1,2", "0,3,4\n10,4,5\n20,5,6"};
        for (int index = 0; index < files.length; index++) RESOURCES.put("mtr:motor/" + files[index], values[index]);
        MotorData5 data = new MotorData5(null, "mtr:motor"); require(data.getSoundCount() == 2, "BVE5 maximum table width");
        record("motor5-grid", grid(data) + "|" + REQUESTS);
        RESOURCES.put("mtr:motor/powervol.csv", "0,1,2,3\n");
        MotorData5 uneven = new MotorData5(null, "mtr:motor");
        record("motor5-uneven", uneven.getSoundCount() + "|" + outcome(() -> uneven.getVolume(2, 0, 1)) + "|" + outcome(() -> uneven.getPitch(2, 0, 1)));
        reset(); MotorData5 empty = new MotorData5(null, null);
        record("motor5-empty", empty.getSoundCount() + "|" + outcome(() -> empty.getPitch(-1, Float.NaN, -0F)) + "|" + outcome(() -> empty.getVolume(0, 0, 1)) + "|" + REQUESTS);
    }

    private static void subclassScenarios() {
        reset();
        MotorDataBase custom = new MotorDataBase() {
            @Override public int getSoundCount() { return 7; }
            @Override public float getPitch(int index, float speed, float accel) { return speed + accel; }
            @Override public float getVolume(int index, float speed, float accel) { return index - accel; }
        };
        MotorDataBase four = new MotorData4(null, "mtr:empty") {
            @Override public int getSoundCount() { return 8; }
            @Override public float getPitch(int index, float speed, float accel) { return 9; }
            @Override public float getVolume(int index, float speed, float accel) { return 10; }
        };
        MotorDataBase five = new MotorData5(null, "mtr:empty") {
            @Override public int getSoundCount() { return 11; }
            @Override public float getPitch(int index, float speed, float accel) { return 12; }
            @Override public float getVolume(int index, float speed, float accel) { return 13; }
        };
        MotorData5.FloatSplines spline = new MotorData5.FloatSplines("") { @Override public float getValue(int index, float key) { return 14; } };
        ConfigFile config = new ConfigFile("", null) {};
        BveTrainSoundConfig hidden = new HiddenConfig();
        MotorData4.Channel channel = new MotorData4.Channel() {};
        record("subclasses", custom.getSoundCount() + "|" + bits(custom.getPitch(1, 2, 3)) + "|" + bits(custom.getVolume(1, 2, 3)) + "|" + four.getSoundCount() + "|" + bits(four.getPitch(0, 0, 0)) + "|" + bits(four.getVolume(0, 0, 0)) + "|" + five.getSoundCount() + "|" + bits(five.getPitch(0, 0, 0)) + "|" + bits(five.getVolume(0, 0, 0)) + "|" + bits(spline.getValue(0, 0)) + "|" + config.motor.length + "|" + hidden.baseName + "|" + HiddenConfig.readResource(null, null) + "|" + channel.maxEntryId);
    }
    public static class HiddenConfig extends BveTrainSoundConfig {
        public HiddenConfig() { super(null, "probe"); }
        public static String readResource(ResourceManager manager, Identifier location) { return "hidden"; }
    }

    private static String grid(MotorDataBase data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        float[] powers = {0, -0F, 1, -1, 0.3F, -0.7F, Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.MIN_VALUE};
        float[] speeds = {Float.NEGATIVE_INFINITY, -Float.MAX_VALUE, -1, -0.2F, -Float.MIN_VALUE, -0F, 0, Float.MIN_VALUE, Math.nextDown(0.2F), 0.2F, Math.nextUp(0.2F), 0.4F, 1, 10, 20, Float.MAX_VALUE, Float.POSITIVE_INFINITY, Float.NaN};
        for (int index = -1; index < 5; index++) for (float speed : speeds) for (float power : powers) {
            final int selected = index;
            digest.update((outcome(() -> data.getPitch(selected, speed, power)) + "/" + outcome(() -> data.getVolume(selected, speed, power)) + "\n").getBytes(StandardCharsets.UTF_8));
        }
        Random random = new Random(0x42564535L);
        for (int index = 0; index < 1024; index++) {
            float speed = Float.intBitsToFloat(random.nextInt()), power = Float.intBitsToFloat(random.nextInt()); int selected = random.nextInt(6) - 1;
            digest.update((outcome(() -> data.getPitch(selected, speed, power)) + "/" + outcome(() -> data.getVolume(selected, speed, power)) + "\n").getBytes(StandardCharsets.UTF_8));
        }
        return HexFormat.of().formatHex(digest.digest());
    }
    private static String splineGrid(MotorData5.FloatSplines spline) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        float[] keys = {Float.NEGATIVE_INFINITY, -Float.MAX_VALUE, -1, -Float.MIN_VALUE, -0F, 0, Float.MIN_VALUE, 0.5F, 1, 5, 10, 15, 20, Float.MAX_VALUE, Float.POSITIVE_INFINITY, Float.NaN};
        for (int index = -1; index <= spline.data.size(); index++) for (float key : keys) {
            final int selected = index;
            digest.update((outcome(() -> spline.getValue(selected, key)) + "\n").getBytes(StandardCharsets.UTF_8));
        }
        return HexFormat.of().formatHex(digest.digest());
    }
    private static String describe(ConfigFile config) throws Exception {
        List<String> fields = new ArrayList<>();
        for (var field : ConfigFile.class.getFields()) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) continue;
            Object value = field.get(config);
            String encoded = value instanceof Float number ? bits(number) : value instanceof SoundEvent event ? sound(event) : value instanceof SoundEvent[] events ? Arrays.toString(Arrays.stream(events).map(BveSoundScenario::sound).toArray(String[]::new)) : String.valueOf(value);
            fields.add(field.getName() + "=" + encoded);
        }
        Collections.sort(fields); return String.join(";", fields);
    }
    private static String sound(SoundEvent event) { return event == null ? "null" : event.location().toString(); }
    private static String bits(float value) { return Integer.toHexString(Float.floatToRawIntBits(value)); }
    private static String outcome(Action action) {
        checks++;
        try { Object result = action.run(); return result instanceof Float value ? bits(value) : result == null || result instanceof String || result instanceof Integer ? String.valueOf(result).replace("\n", "\\n").replace("\u0000", "\\0") : result.getClass().getSimpleName(); }
        catch (Throwable error) { if (error instanceof LinkageError) throw (LinkageError) error; return "!" + error.getClass().getName(); }
    }
    private static void reset() { RESOURCES.clear(); REQUESTS.clear(); SOUNDS.clear(); mode = 0; opened = 0; closed = 0; }
    private static void record(String key, String value) { RECORDS.append(key).append('\t').append(value).append('\n'); }
    private static void require(boolean value, String message) { checks++; if (!value) throw new AssertionError(message); }
    @FunctionalInterface private interface Action { Object run() throws Exception; }
}

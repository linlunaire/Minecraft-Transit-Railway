package mtr.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mtr.client.DoorAnimationType;
import mtr.mappings.ModelMapper;
import mtr.mappings.RenderBufferSource;
import net.minecraft.client.gui.Font;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/** Java extension contract plus headless base dispatch/text behavior from the original Java JAR. */
public final class KotlinModelBasesCompatibilityCheck {
    public static void main(String[] args) throws Exception {
        if (args.length == 2 && args[0].equals("--print-baseline")) {
            try (BaselineLoader loader = new BaselineLoader(Path.of(args[1]))) {
                Map<String, String> original = snapshot(loader);
                compare(original, snapshot(loader));
                System.out.println("# Original Java model base dispatch, nullable contracts and destination text");
                original.forEach((key, value) -> System.out.println(key + "\t" + value));
            }
            return;
        }
        if (args.length < 1 || args.length > 2) throw new IllegalArgumentException("<golden.tsv> [original-java.jar] | --print-baseline <original-java.jar>");
        for (Class<?> type : List.of(ModelTrainBase.class, ModelSimpleTrainBase.class, ModelDoorOverlayTopBase.class)) {
            require(Arrays.stream(type.getDeclaredAnnotations()).anyMatch(a -> a.annotationType().getName().equals("kotlin.Metadata")), "Stale Java base: " + type.getName());
        }
        Map<String, String> golden = new TreeMap<>();
        for (String line : Files.readAllLines(Path.of(args[0]))) {
            if (line.isBlank() || line.startsWith("#")) continue;
            String[] row = line.split("\t", 2);
            require(row.length == 2 && golden.put(row[0], row[1]) == null, "Invalid golden");
        }
        compare(golden, Probe.snapshot());
        compare(golden, Probe.snapshot());
        if (args.length == 2) try (BaselineLoader loader = new BaselineLoader(Path.of(args[1]))) { compare(golden, snapshot(loader)); }
        require(HidingProbe.isIndex(0, 0, null), "Non-final protected static hiding changed");
        System.out.println("PASS: Java generic subclass/static hiding, model base dispatch/order, nullable hooks, overlay callbacks, destination text and index bounds (" + golden.size() + " records)");
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> snapshot(ClassLoader loader) throws Exception {
        try { return (Map<String, String>) Class.forName(Probe.class.getName(), true, loader).getMethod("snapshot").invoke(null); }
        catch (InvocationTargetException error) { throw new AssertionError("Original Java probe failed", error.getCause()); }
    }
    private static void compare(Map<String, String> expected, Map<String, String> actual) {
        require(expected.keySet().equals(actual.keySet()), "Base scenarios changed");
        expected.forEach((key, value) -> require(value.equals(actual.get(key)), key + "\nexpected " + value + "\nactual " + actual.get(key)));
    }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }

    public static class Probe extends ModelSimpleTrainBase<Probe> {
        private final List<String> calls = new ArrayList<>();
        private int[] windows = {-24, 24}, doors = {-40, 40}, ends = {-64, 64};
        private boolean nullDefault;
        private final ModelDoorOverlay overlay = new RecordingOverlay(calls);
        private final ModelDoorOverlayTopBase top = new RecordingTop(calls);
        public Probe() { this(DoorAnimationType.CONSTANT, true); }
        public Probe(DoorAnimationType animation, boolean overlays) { super(animation, overlays); }
        @Override public Probe createNew(DoorAnimationType animation, boolean overlays) { return new Probe(animation, overlays); }
        @Override protected int getDoorMax() { calls.add("doorMax"); return 16; }
        @Override protected float getDoorDuration() { calls.add("doorDuration"); return 0.5F; }
        @Override protected void baseTransform(PoseStack matrices) { calls.add("baseTransform"); }
        @Override protected String defaultDestinationString() { return nullDefault ? null : "default"; }
        @Override protected int[] getWindowPositions() { calls.add("windows"); return windows; }
        @Override protected int[] getDoorPositions() { calls.add("doors"); return doors; }
        @Override protected int[] getEndPositions() { calls.add("ends"); return ends; }
        @Override protected ModelDoorOverlay getModelDoorOverlay() { calls.add("overlay"); return overlay; }
        @Override protected ModelDoorOverlayTopBase getModelDoorOverlayTop() { calls.add("top"); return top; }

        private void record(String part, PoseStack matrices, VertexConsumer vertices, RenderStage stage, int light, int position, boolean details, float lx, float rx, float lz, float rz, boolean... flags) {
            calls.add(part + ":" + (matrices == null) + ":" + (vertices == null) + ":" + stage + ":" + light + ":" + position + ":" + details + ":" + lx + ":" + rx + ":" + lz + ":" + rz + ":" + Arrays.toString(flags));
        }
        @Override protected void renderWindowPositions(PoseStack matrices, VertexConsumer vertices, RenderStage stage, int light, int position, boolean details, float lx, float rx, float lz, float rz, boolean first, boolean second) {
            record("renderWindowPositions", matrices, vertices, stage, light, position, details, lx, rx, lz, rz, first, second);
        }
        @Override protected void renderDoorPositions(PoseStack matrices, VertexConsumer vertices, RenderStage stage, int light, int position, boolean details, float lx, float rx, float lz, float rz, boolean first, boolean second) {
            record("renderDoorPositions", matrices, vertices, stage, light, position, details, lx, rx, lz, rz, first, second);
        }
        @Override protected void renderHeadPosition1(PoseStack matrices, VertexConsumer vertices, RenderStage stage, int light, int position, boolean details, float lx, float rx, float lz, float rz, boolean first) {
            record("renderHeadPosition1", matrices, vertices, stage, light, position, details, lx, rx, lz, rz, first);
        }
        @Override protected void renderHeadPosition2(PoseStack matrices, VertexConsumer vertices, RenderStage stage, int light, int position, boolean details, float lx, float rx, float lz, float rz, boolean first) {
            record("renderHeadPosition2", matrices, vertices, stage, light, position, details, lx, rx, lz, rz, first);
        }
        @Override protected void renderEndPosition1(PoseStack matrices, VertexConsumer vertices, RenderStage stage, int light, int position, boolean details, float lx, float rx, float lz, float rz) {
            record("renderEndPosition1", matrices, vertices, stage, light, position, details, lx, rx, lz, rz);
        }
        @Override protected void renderEndPosition2(PoseStack matrices, VertexConsumer vertices, RenderStage stage, int light, int position, boolean details, float lx, float rx, float lz, float rz) {
            record("renderEndPosition2", matrices, vertices, stage, light, position, details, lx, rx, lz, rz);
        }

        public static Map<String, String> snapshot() throws Exception {
            Map<String, String> result = new TreeMap<>();
            Probe probe = new Probe();
            for (int car : new int[]{0, 1, 2}) for (boolean front : new boolean[]{false, true}) {
                probe.calls.clear();
                probe.render(null, null, car == 1 ? null : RenderStage.EXTERIOR, 123, 1F, 2F, 3F, 4F, car, 3, front, true);
                result.put("dispatch/" + car + "/" + front, probe.calls.toString());
            }
            probe.calls.clear();
            probe.render(null, null, RenderStage.INTERIOR, 456, 4F, 3F, 2F, 1F, 0, 1, true, false);
            result.put("dispatch/single", probe.calls.toString());
            probe.calls.clear();
            probe.renderExtraDetails(null, null, 111, 222, true, 1F, 2F, 3F, 4F);
            result.put("overlays/enabled", probe.calls.toString());
            Probe disabled = new Probe(DoorAnimationType.CONSTANT, false);
            disabled.renderExtraDetails(null, null, 111, 222, true, 1F, 2F, 3F, 4F);
            result.put("overlays/disabled", disabled.calls.toString());
            PoseStack matrices = new PoseStack();
            matrices.translate(1.25, -2.5, 3.75);
            var before = new org.joml.Matrix4f(matrices.last().pose());
            probe.calls.clear();
            probe.render(matrices, null, null, null, 123, 0.25F, 0.5F, false, 0, 1, false, true, true, false, true);
            require(before.equals(matrices.last().pose()), "Translucent no-details path leaked pose");
            result.put("render/translucent-no-details", probe.calls.toString());
            // A non-head car never dereferenced these arguments in Java.
            probe.renderFrontDestination(null, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, null, true, 1, 3);
            result.put("front/middle-null-noop", "ok");
            probe.setupAnim(null, 0, 0, 0, 0, 0);
            probe.renderToBuffer(null, null, 0, 0, 0);
            probe.renderTextDisplays(null, null, null, null, null, null, null, null, null, null, 0, 0, false, null);
            probe.top.setupAnim(null, 0, 0, 0, 0, 0);
            probe.top.renderToBuffer(null, null, 0, 0, 0);
            result.put("hooks/null-noops", "ok");
            Probe nullable = new Probe(null, false);
            require(nullable.doorAnimationType == null && !nullable.renderDoorOverlay, "Constructor rejects nullable animation");
            result.put("constructor/null-animation", outcome(() -> nullable.render(new PoseStack(), null, null, null, 0, 0, 0, false, 0, 1, false, false, true, false, false)));
            for (String text : new String[]{"", "|", "||", "a|", "a||b|", "a||b", "汉字|abc", "中|abc", "汉字|", "latin", "  汉字  |mixed"}) {
                for (TextSpacingType spacing : TextSpacingType.values()) for (boolean upper : new boolean[]{false, true}) {
                    result.put("text/" + encode(text) + "/" + spacing + "/" + upper, encode(probe.getDestinationString(null, text, spacing, upper)));
                }
            }
            result.put("text/default", encode(probe.getDestinationString(null, null, TextSpacingType.NORMAL, false)));
            result.put("text/null-spacing", encode(probe.getDestinationString(null, "a|汉字|", null, false)));
            probe.nullDefault = true;
            result.put("text/null-default", encode(probe.getDestinationString(null, null, TextSpacingType.NORMAL, false)));
            result.put("text/null-default-upper", outcome(() -> probe.getDestinationString(null, null, TextSpacingType.NORMAL, true)));
            result.put("text/null-default-spacing", outcome(() -> probe.getDestinationString(null, null, TextSpacingType.SPACE_CJK, false)));
            for (int index : new int[]{Integer.MIN_VALUE, -4, -3, -1, 0, 2, 3, Integer.MAX_VALUE}) {
                for (int value : new int[]{10, 20, 30}) result.put("index/" + index + "/" + value, Boolean.toString(isIndex(index, value, new int[]{10,20,30})));
            }
            result.put("index/empty", Boolean.toString(isIndex(-1, 0, new int[0])));
            result.put("index/null", outcome(() -> isIndex(0, 0, null)));
            java.lang.reflect.Field gameTick = mtr.MTRClient.class.getDeclaredField("gameTick");
            gameTick.setAccessible(true);
            float savedTick = gameTick.getFloat(null);
            try {
                for (float tick : new float[]{-30F, 0F, 29.99F, 30F, 60F, Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY}) {
                    gameTick.setFloat(null, tick);
                    for (String text : new String[]{"", "|", "a|b|", "a||b"}) {
                        String value;
                        try { value = encode(getAlternatingString(text)); }
                        catch (RuntimeException error) { value = error.getClass().getName(); }
                        result.put("alternating/" + Float.toHexString(tick) + "/" + encode(text), value);
                    }
                }
            } finally { gameTick.setFloat(null, savedTick); }
            result.put("hong-kong/missing", encode(getHongKongNextStationString(null, null, true, false)));
            result.put("london/missing", encode(getLondonNextStationString(null, null, null, null, null, null, true)));
            return result;
        }

        private static String encode(String value) { return value == null ? "<null>" : "b64:" + Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8)); }
        private static String outcome(Runnable action) {
            try { action.run(); return "ok"; } catch (RuntimeException error) { return error.getClass().getName(); }
        }
    }

    public static final class HidingProbe extends Probe {
        protected static boolean isIndex(int index, int value, int[] array) { return true; }
    }

    public static final class RecordingOverlay extends ModelDoorOverlay {
        private final List<String> calls;
        public RecordingOverlay(List<String> calls) { super(16, 0, "left.png", "right.png"); this.calls = calls; }
        @Override public void render(PoseStack matrices, RenderBufferSource consumers, ModelTrainBase.RenderStage stage, int light, int position, float lx, float rx, float lz, float rz, boolean lights) {
            calls.add("overlay-render:" + stage + ":" + light + ":" + position + ":" + lx + ":" + rx + ":" + lz + ":" + rz + ":" + lights);
        }
    }

    public static final class RecordingTop extends ModelDoorOverlayTopBase {
        private final List<String> calls;
        public RecordingTop(List<String> calls) { this.calls = calls; }
        @Override public void render(PoseStack matrices, RenderBufferSource consumers, int light, int position, float lx, float rx, float lz, float rz) {
            calls.add("top-render:" + light + ":" + position + ":" + lx + ":" + rx + ":" + lz + ":" + rz);
        }
    }

    private static final class BaselineLoader extends URLClassLoader {
        private BaselineLoader(Path jar) throws Exception {
            super(new URL[]{jar.toUri().toURL(), KotlinModelBasesCompatibilityCheck.class.getProtectionDomain().getCodeSource().getLocation()}, KotlinModelBasesCompatibilityCheck.class.getClassLoader());
        }
        @Override protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            if (!name.startsWith("mtr.model.")) return super.loadClass(name, resolve);
            synchronized (getClassLoadingLock(name)) {
                Class<?> result = findLoadedClass(name);
                if (result == null) result = findClass(name);
                if (resolve) resolveClass(result);
                return result;
            }
        }
    }
}

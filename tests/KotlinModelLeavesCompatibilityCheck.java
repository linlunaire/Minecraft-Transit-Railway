package mtr.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mtr.client.DoorAnimationType;
import mtr.mappings.ModelDataWrapper;
import mtr.mappings.ModelMapper;
import mtr.mappings.RenderBufferSource;
import net.minecraft.world.entity.Entity;
import org.joml.Matrix4f;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.*;

/** Real leaf constructors, train layout/factories, and lift/mini geometry without a world or GPU. */
public final class KotlinModelLeavesCompatibilityCheck {
    private static final String[] MODELS = {
        "ModelATrainMini", "ModelATrainSmall", "ModelBogie", "ModelCableCarGrip",
        "ModelClass802Mini", "ModelCMStockMini", "ModelCMStockSmall", "ModelCTrainMini",
        "ModelCTrainSmall", "ModelDoorOverlay", "ModelDoorOverlayTop", "ModelE44Mini",
        "ModelKTrainMini", "ModelKTrainSmall", "ModelLift1", "ModelLondonUndergroundD78Mini",
        "ModelMLRMini", "ModelMLRSmall", "ModelMTrainMini", "ModelMTrainSmall", "ModelR179Mini",
        "ModelR211Mini", "ModelRTrainMini", "ModelRTrainSmall", "ModelSP1900Mini",
        "ModelSP1900Small", "ModelSTrainMini", "ModelSTrainSmall"
    };
    private static final int LIGHT = 0x00B00050, OVERLAY = 0x00090004;

    public static void main(String[] args) throws Exception {
        if (args.length == 2 && args[0].equals("--print-baseline")) {
            try (ModelLoader loader = new ModelLoader(Path.of(args[1]))) {
                Map<String, String> expected = snapshot(loader);
                compare(expected, snapshot(loader), "Java repeatability");
                System.out.println("# Original Java leaf models, Minecraft 26.2; sorted full quads");
                System.out.println("# Position/normal quantum=0.0001, UV quantum=0.000001");
                expected.forEach((key, value) -> System.out.println(key + "\t" + value));
            }
            return;
        }
        require(args.length >= 1 && args.length <= 2, "Usage: <golden.tsv> [java-baseline.jar] | --print-baseline <java-baseline.jar>");
        Map<String, String> golden = new TreeMap<>();
        for (String line : Files.readAllLines(Path.of(args[0]))) {
            if (line.isBlank() || line.startsWith("#")) continue;
            String[] row = line.split("\t", 2);
            require(row.length == 2 && golden.put(row[0], row[1]) == null, "Invalid golden row");
        }
        Map<String, String> current = snapshot(KotlinModelLeavesCompatibilityCheck.class.getClassLoader());
        compare(golden, current, "Kotlin versus Java golden");
        compare(current, snapshot(KotlinModelLeavesCompatibilityCheck.class.getClassLoader()), "Kotlin repeatability");
        if (args.length == 2) try (ModelLoader loader = new ModelLoader(Path.of(args[1]))) {
            compare(golden, snapshot(loader), "Independent Java baseline");
        }
        System.out.println("PASS: 28 Kotlin model leaves, constructor geometry, mini/small layouts and factories, lift stages and nullable no-op contracts (" + golden.size() + " records)");
    }

    private static Map<String, String> snapshot(ClassLoader loader) throws Exception {
        Map<String, String> result = new TreeMap<>();
        for (String name : MODELS) {
            Class<?> type = Class.forName("mtr.model." + name, true, loader);
            boolean kotlin = Arrays.stream(type.getDeclaredAnnotations()).anyMatch(a -> a.annotationType().getName().equals("kotlin.Metadata"));
            require(kotlin != (loader instanceof ModelLoader), "Stale or mixed model output: " + name);
            if (loader instanceof ModelLoader) require(type.getClassLoader() == loader, "Baseline isolation failed");
            if (name.equals("ModelLift1")) {
                exercise(type.getConstructor(int.class, int.class, int.class, boolean.class).newInstance(4, 2, 2, false), name + "/compact", result, false);
                exercise(type.getConstructor(int.class, int.class, int.class, boolean.class).newInstance(7, 4, 3, false), name + "/large", result, false);
                exercise(type.getConstructor(int.class, int.class, int.class, boolean.class).newInstance(6, 2, 3, true), name + "/double-narrow", result, false);
                exercise(type.getConstructor(int.class, int.class, int.class, boolean.class).newInstance(8, 4, 4, true), name + "/double-wide", result, false);
            } else if (name.equals("ModelDoorOverlay")) {
                exercise(type.getConstructor(int.class, float.class, String.class, String.class).newInstance(16, 6.3F, "left.png", "right.png"), name + "/default-pivot", result, false);
                exercise(type.getConstructor(int.class, float.class, int.class, String.class, String.class).newInstance(24, -12.5F, 11, "left.png", "right.png"), name + "/custom-pivot", result, false);
                exercise(type.getConstructor(int.class, float.class, int.class, String.class, String.class, boolean.class, boolean.class).newInstance(20, 0F, 14, null, null, false, false), name + "/disabled-null-textures", result, false);
            } else {
                try {
                    var constructor = type.getConstructor(boolean.class);
                    exercise(constructor.newInstance(false), name + "/false", result, false);
                    exercise(constructor.newInstance(true), name + "/true", result, name.startsWith("ModelMLR"));
                } catch (NoSuchMethodException noBooleanConstructor) {
                    exercise(type.getConstructor().newInstance(), name, result, false);
                }
            }
        }
        return result;
    }

    private static void exercise(Object model, String key, Map<String, String> result, boolean christmas) throws Exception {
        Class<?> type = model.getClass();
        Field wrapperField = ModelMapper.class.getDeclaredField("modelDataWrapper");
        wrapperField.setAccessible(true);
        Set<ModelDataWrapper> wrappers = Collections.newSetFromMap(new IdentityHashMap<>());
        int modelFields = 0;
        for (Class<?> owner = type; owner != Object.class; owner = owner.getSuperclass()) {
            for (Field field : owner.getDeclaredFields()) if (field.getType() == ModelMapper.class) {
                field.setAccessible(true);
                ModelMapper mapper = (ModelMapper) field.get(model);
                require(mapper != null, "Uninitialized part: " + key + "/" + field.getName());
                wrappers.add((ModelDataWrapper) wrapperField.get(mapper));
                modelFields++;
            }
        }
        require(wrappers.size() == 1 && modelFields > 0, "Expected one real baked wrapper: " + key);
        var root = wrappers.iterator().next().modelPart;
        int[] cubes = {0};
        root.visit(new PoseStack(), (pose, path, index, cube) -> cubes[0]++);
        Quads construction = new Quads();
        root.render(new PoseStack(), construction, LIGHT, OVERLAY);
        result.put(key + "/construction", "fields=" + modelFields + ";cubes=" + cubes[0] + ";" + construction.finish());

        // These inherited/declared no-ops accepted null in Java and must not gain parameter checks.
        type.getMethod("setupAnim", Entity.class, float.class, float.class, float.class, float.class, float.class)
            .invoke(model, null, 0F, 0F, 0F, 0F, 0F);
        type.getMethod("renderToBuffer", PoseStack.class, VertexConsumer.class, int.class, int.class, int.class)
            .invoke(model, null, null, 0, 0, 0);

        ClassLoader loader = type.getClassLoader();
        Class<?> base = Class.forName("mtr.model.ModelTrainBase", true, loader);
        Class<?> stageType = Class.forName("mtr.model.ModelTrainBase$RenderStage", true, loader);
        if (!base.isInstance(model)) {
            if (type.getSimpleName().equals("ModelDoorOverlay")) {
                Method render = type.getMethod("render", PoseStack.class, RenderBufferSource.class, stageType, int.class, int.class, float.class, float.class, float.class, float.class, boolean.class);
                render.invoke(model, null, null, enumValue(stageType, "LIGHTS"), 0, 0, 0F, 0F, 0F, 0F, false);
                try {
                    render.invoke(model, null, null, null, 0, 0, 0F, 0F, 0F, 0F, false);
                    throw new AssertionError("Null switch selector became a no-op");
                } catch (InvocationTargetException expected) {
                    require(expected.getCause() instanceof NullPointerException, "Wrong null stage failure");
                }
                if (key.endsWith("disabled-null-textures")) render.invoke(model, null, null, enumValue(stageType, "EXTERIOR"), 0, 0, 0F, 0F, 0F, 0F, false);
                StringBuilder flags = new StringBuilder();
                for (String fieldName : List.of("doorOverlayTextureLeft", "doorOverlayTextureRight", "renderLeft", "renderRight")) {
                    Field field = type.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    flags.append(fieldName).append('=').append(field.get(model)).append(';');
                }
                result.put(key + "/overlay", flags.toString());
            }
            return;
        }

        Field animationField = base.getField("doorAnimationType"), overlayField = base.getField("renderDoorOverlay");
        int maximum = (int) method(type, "getDoorMax").invoke(model);
        float duration = (float) method(type, "getDoorDuration").invoke(model);
        DoorAnimationType animation = (DoorAnimationType) animationField.get(model);
        String layout = layout(model);
        result.put(key + "/configuration", animation + ";overlay=" + overlayField.getBoolean(model) + ";doorMax=" + maximum + ";duration=" + Float.toHexString(duration) + ";" + layout);

        if (!type.getSimpleName().equals("ModelLift1")) {
            Method factory = type.getMethod("createNew", DoorAnimationType.class, boolean.class);
            for (DoorAnimationType requested : new DoorAnimationType[]{DoorAnimationType.PLUG_SLOW, null}) {
                Object copy = factory.invoke(model, requested, true);
                require(copy != model && copy.getClass() == type, "Factory changed concrete type: " + key);
                require(animationField.get(copy) == requested && overlayField.getBoolean(copy), "Factory lost arguments: " + key);
                require(layout.equals(layout(copy)), "Factory lost constructor variant/layout: " + key);
            }
        }

        Method render = method(type, "render", PoseStack.class, VertexConsumer.class, stageType, int.class, float.class, float.class, float.class, float.class, int.class, int.class, boolean.class, boolean.class);
        Method transform = method(type, "baseTransform", PoseStack.class);
        for (int scenario = 0; scenario < 2; scenario++) {
            StringBuilder stages = new StringBuilder();
            float left = scenario == 0 ? 0 : 0.45F, right = scenario == 0 ? 0 : 0.2F;
            float lx = DoorAnimationType.getDoorAnimationX(animation, left), rx = DoorAnimationType.getDoorAnimationX(animation, right);
            float lz = DoorAnimationType.getDoorAnimationZ(animation, maximum, duration, left, scenario == 0);
            float rz = DoorAnimationType.getDoorAnimationZ(animation, maximum, duration, right, scenario == 0);
            for (String stage : List.of("LIGHTS", "ALWAYS_ON_LIGHTS", "INTERIOR", "INTERIOR_TRANSLUCENT", "EXTERIOR")) {
                // Existing parent MLR Christmas lights use wall-clock time; do not bless a flaky golden.
                if (christmas && (stage.equals("INTERIOR") || stage.equals("ALWAYS_ON_LIGHTS"))) continue;
                PoseStack matrices = new PoseStack();
                matrices.translate(1.25, -2.5, 3.75);
                transform.invoke(model, matrices);
                Matrix4f before = new Matrix4f(matrices.last().pose());
                Quads vertices = new Quads();
                render.invoke(model, matrices, vertices, enumValue(stageType, stage), LIGHT, lx, rx, lz, rz, scenario, scenario == 0 ? 1 : 3, scenario == 0, scenario != 0);
                require(before.equals(matrices.last().pose()), "Leaked model transform: " + key + "/" + stage);
                stages.append(stage).append('=').append(vertices.finish()).append('\n');
            }
            result.put(key + (scenario == 0 ? "/closed-stages" : "/open-middle-stages"), digest(stages.toString().getBytes(StandardCharsets.UTF_8)));
        }
    }

    private static String layout(Object model) throws Exception {
        StringBuilder result = new StringBuilder();
        for (String name : List.of("getWindowPositions", "getDoorPositions", "getEndPositions", "getBogiePositions")) {
            try {
                Method method = method(model.getClass(), name);
                int[] first = (int[]) method.invoke(model);
                int[] second = (int[]) method.invoke(model);
                require(Arrays.equals(first, second), "Layout is not repeatable: " + name);
                result.append(name).append('=').append(Arrays.toString(first)).append(';');
            } catch (NoSuchMethodException absentInLift) {
                require(name.equals("getBogiePositions") || model.getClass().getSimpleName().equals("ModelLift1"), "Missing train layout: " + name);
                result.append(name).append("=absent;");
            }
        }
        for (String name : List.of("renderFirstDestination", "renderSecondDestination")) {
            try {
                Method method = method(model.getClass(), name, boolean.class, boolean.class);
                for (boolean first : new boolean[]{false, true}) for (boolean second : new boolean[]{false, true}) result.append(method.invoke(model, first, second)).append(',');
            } catch (NoSuchMethodException absentInLift) {
                result.append(name).append("=absent;");
            }
        }
        return result.toString();
    }

    private static Method method(Class<?> type, String name, Class<?>... parameters) throws NoSuchMethodException {
        for (Class<?> owner = type; owner != null; owner = owner.getSuperclass()) {
            try { Method result = owner.getDeclaredMethod(name, parameters); result.setAccessible(true); return result; }
            catch (NoSuchMethodException ignored) {}
        }
        throw new NoSuchMethodException(type.getName() + "." + name);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Object enumValue(Class<?> type, String name) { return Enum.valueOf((Class) type, name); }
    private static String digest(byte[] bytes) throws Exception { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes)); }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
    private static void compare(Map<String, String> expected, Map<String, String> actual, String label) {
        require(expected.keySet().equals(actual.keySet()), label + ": scenario set changed");
        expected.forEach((key, value) -> require(value.equals(actual.get(key)), label + ": " + key + "\nexpected " + value + "\nactual " + actual.get(key)));
    }
    private static final class ModelLoader extends URLClassLoader {
        private ModelLoader(Path jar) throws Exception { super(new URL[]{jar.toUri().toURL()}, KotlinModelLeavesCompatibilityCheck.class.getClassLoader()); }
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
	/** Sort whole quads, not individual vertices: ignore random child names but retain winding. */
	private static final class Quads implements VertexConsumer {
		private final List<byte[]> quads = new ArrayList<>();
		private final ByteBuffer quad = ByteBuffer.allocate(4 * 11 * Integer.BYTES);
		private final int[] current = new int[11];
		private int attributes;
		private int vertices;
		private boolean started;
		private boolean finished;

		@Override
		public VertexConsumer addVertex(float x, float y, float z) {
			flushVertex();
			Arrays.fill(current, 0);
			current[0] = quantize(x, 10000);
			current[1] = quantize(y, 10000);
			current[2] = quantize(z, 10000);
			attributes = 0;
			started = true;
			return this;
		}

		@Override public VertexConsumer setColor(int red, int green, int blue, int alpha) { return setColor(alpha << 24 | red << 16 | green << 8 | blue); }
		@Override public VertexConsumer setColor(int color) { current[3] = color; attributes |= 1; return this; }
		@Override public VertexConsumer setUv(float u, float v) { current[4] = quantize(u, 1000000); current[5] = quantize(v, 1000000); attributes |= 2; return this; }
		@Override public VertexConsumer setUv1(int u, int v) { current[6] = u | v << 16; attributes |= 4; return this; }
		@Override public VertexConsumer setUv2(int u, int v) { current[7] = u | v << 16; attributes |= 8; return this; }
		@Override public VertexConsumer setNormal(float x, float y, float z) { current[8] = quantize(x, 10000); current[9] = quantize(y, 10000); current[10] = quantize(z, 10000); attributes |= 16; return this; }
		@Override public VertexConsumer setLineWidth(float width) { throw new AssertionError("Train mesh unexpectedly emitted line geometry"); }

		private void flushVertex() {
			if (!started) return;
			require(!finished && attributes == 31, "A train vertex lost one or more required attributes");
			for (int value : current) quad.putInt(value);
			vertices++;
			if (!quad.hasRemaining()) {
				quads.add(quad.array().clone());
				quad.clear();
			}
			started = false;
		}

		private String finish() throws Exception {
			flushVertex();
			require(!finished && vertices % 4 == 0, "Incomplete train quad");
			finished = true;
			quads.sort(Arrays::compareUnsigned);
			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
			for (byte[] values : quads) digest.update(values);
			return "vertices=" + vertices + ";quads=" + quads.size() + ";sha256=" + HexFormat.of().formatHex(digest.digest());
		}

		private static int quantize(float value, int units) {
			require(Float.isFinite(value), "Non-finite model coordinate/attribute");
			return Math.round(value * units);
		}
	}
}

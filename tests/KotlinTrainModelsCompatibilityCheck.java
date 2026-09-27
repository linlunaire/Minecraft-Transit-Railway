package mtr.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mtr.client.DoorAnimationType;
import mtr.mappings.ModelDataWrapper;
import mtr.mappings.ModelMapper;
import net.minecraft.client.model.geom.ModelPart;
import org.joml.Matrix4f;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Actual baked train geometry and stage animation, without a window, world, font or GPU. */
public final class KotlinTrainModelsCompatibilityCheck {

	private static final String[] MODELS = {"ModelA320", "ModelClass802"};
	private static final String[] STAGES = {"LIGHTS", "ALWAYS_ON_LIGHTS", "INTERIOR", "INTERIOR_TRANSLUCENT", "EXTERIOR"};
	private static final int LIGHT = 0x00B00050;
	private static final int OVERLAY = 0x00090004;
	private static final Sample[] SAMPLES = {
		new Sample("closed-single", 0, 0, true, 0, 1, true, false),
		new Sample("open-first", 0.5F, 0.3F, true, 0, 3, true, true),
		new Sample("closing-last", 0.6F, 0.15F, false, 2, 3, false, true),
		new Sample("middle", 0.1F, 0.4F, true, 1, 3, true, false)
	};

	public static void main(String[] args) throws Exception {
		if (args.length == 2 && args[0].equals("--print-baseline")) {
			try (ModelLoader loader = new ModelLoader(Path.of(args[1]))) {
				final Map<String, String> baseline = snapshot(loader);
				compare(baseline, snapshot(loader), "Java baseline repeatability");
				System.out.println("# Original Java model geometry: A320 and Class802, Minecraft 26.2");
				System.out.println("# Sorted full quads retain winding and duplicates; position/normal quantum=0.0001, UV quantum=0.000001");
				baseline.forEach((key, value) -> System.out.println(key + "\t" + value));
			}
			return;
		}
		require(args.length >= 1 && args.length <= 2, "Usage: <golden.tsv> [java-baseline.jar], or --print-baseline <java-baseline.jar>");
		final Map<String, String> golden = readGolden(Path.of(args[0]));
		final Map<String, String> current = snapshot(KotlinTrainModelsCompatibilityCheck.class.getClassLoader());
		compare(golden, current, "Current models versus Java golden");
		compare(current, snapshot(KotlinTrainModelsCompatibilityCheck.class.getClassLoader()), "Current constructor repeatability");
		if (args.length == 2) {
			try (ModelLoader loader = new ModelLoader(Path.of(args[1]))) {
				compare(golden, snapshot(loader), "Independent original Java models");
			}
		}
		System.out.println("PASS: real A320/Class802 construction, baked cube geometry, 40 material/door/car stages, quad winding, UV/normal/color/light/overlay attributes and repeatable model state");
	}

	private static Map<String, String> snapshot(ClassLoader loader) throws Exception {
		final Map<String, String> result = new TreeMap<>();
		final Class<?> base = Class.forName("mtr.model.ModelTrainBase", true, loader);
		final Class<?> stageType = Class.forName("mtr.model.ModelTrainBase$RenderStage", true, loader);
		final Method render = base.getDeclaredMethod("render", PoseStack.class, VertexConsumer.class, stageType, int.class, float.class, float.class, float.class, float.class, int.class, int.class, boolean.class, boolean.class);
		final Method baseTransform = base.getDeclaredMethod("baseTransform", PoseStack.class);
		final Method doorMax = base.getDeclaredMethod("getDoorMax");
		final Method doorDuration = base.getDeclaredMethod("getDoorDuration");
		render.setAccessible(true);
		baseTransform.setAccessible(true);
		doorMax.setAccessible(true);
		doorDuration.setAccessible(true);
		final Field wrapperField = ModelMapper.class.getDeclaredField("modelDataWrapper");
		wrapperField.setAccessible(true);

		for (String name : MODELS) {
			final Class<?> type = Class.forName("mtr.model." + name, true, loader);
			if (loader instanceof ModelLoader) require(type.getClassLoader() == loader, "Baseline model came from the current implementation: " + name);
			final boolean kotlin = Arrays.stream(type.getDeclaredAnnotations()).anyMatch(annotation -> annotation.annotationType().getName().equals("kotlin.Metadata"));
			require(kotlin != (loader instanceof ModelLoader), "Expected " + (loader instanceof ModelLoader ? "original Java" : "migrated Kotlin") + " model class, not stale or mixed output: " + name);
			final Object model = type.getConstructor().newInstance();
			final Map<ModelDataWrapper, Boolean> wrappers = new IdentityHashMap<>();
			int modelFields = 0;
			for (Field field : type.getDeclaredFields()) {
				if (field.getType() == ModelMapper.class) {
					field.setAccessible(true);
					final ModelMapper mapper = (ModelMapper) field.get(model);
					require(mapper != null, "Uninitialized constructor model field: " + name + "." + field.getName());
					wrappers.put((ModelDataWrapper) wrapperField.get(mapper), Boolean.TRUE);
					modelFields++;
				}
			}
			require(wrappers.size() == 1 && modelFields > 100, "Large model constructor was not fully exercised: " + name);
			final ModelPart root = wrappers.keySet().iterator().next().modelPart;
			final int[] cubes = {0};
			root.visit(new PoseStack(), (pose, path, index, cube) -> cubes[0]++);
			final Quads construction = new Quads();
			root.render(new PoseStack(), construction, LIGHT, OVERLAY);
			result.put(name + "/construction", "fields=" + modelFields + ";cubes=" + cubes[0] + ";" + construction.finish());
			final DoorAnimationType animation = (DoorAnimationType) base.getField("doorAnimationType").get(model);
			final int maximum = (int) doorMax.invoke(model);
			final float duration = (float) doorDuration.invoke(model);
			result.put(name + "/configuration", animation.name() + ";overlay=" + base.getField("renderDoorOverlay").getBoolean(model) + ";doorMax=" + maximum + ";duration=" + Float.toHexString(duration));

			for (Sample sample : SAMPLES) {
				final float leftX = DoorAnimationType.getDoorAnimationX(animation, sample.left);
				final float rightX = DoorAnimationType.getDoorAnimationX(animation, sample.right);
				final float leftZ = DoorAnimationType.getDoorAnimationZ(animation, maximum, duration, sample.left, sample.opening);
				final float rightZ = DoorAnimationType.getDoorAnimationZ(animation, maximum, duration, sample.right, sample.opening);
				result.put(name + "/" + sample.name + "/doors", Float.toHexString(leftX) + "," + Float.toHexString(rightX) + "," + Float.toHexString(leftZ) + "," + Float.toHexString(rightZ));
				for (String stage : STAGES) {
					final PoseStack matrices = new PoseStack();
					matrices.translate(1.25, -2.5, 3.75);
					baseTransform.invoke(model, matrices);
					final Matrix4f before = new Matrix4f(matrices.last().pose());
					final Quads vertices = new Quads();
					render.invoke(model, matrices, vertices, enumValue(stageType, stage), LIGHT, leftX, rightX, leftZ, rightZ, sample.car, sample.cars, sample.front, sample.details);
					require(before.equals(matrices.last().pose()), "Model stage leaked its matrix transform: " + name + "/" + stage);
					result.put(name + "/" + sample.name + "/" + stage, vertices.finish());
				}
			}
			require(!result.get(name + "/closed-single/EXTERIOR").equals(result.get(name + "/open-first/EXTERIOR")), "Animation scenarios produced identical exteriors: " + name);
		}
		return result;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private static Object enumValue(Class<?> type, String name) {
		return Enum.valueOf((Class) type, name);
	}

	private static Map<String, String> readGolden(Path path) throws Exception {
		final Map<String, String> result = new LinkedHashMap<>();
		for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
			if (line.isBlank() || line.startsWith("#")) continue;
			final String[] entry = line.split("\t", 2);
			require(entry.length == 2 && result.put(entry[0], entry[1]) == null, "Malformed or duplicate model golden: " + line);
		}
		require(result.size() == MODELS.length * (2 + SAMPLES.length * (STAGES.length + 1)), "Incomplete model golden");
		return result;
	}

	private static void compare(Map<String, String> expected, Map<String, String> actual, String label) {
		require(expected.keySet().equals(actual.keySet()), label + ": missing or added scenarios");
		for (String key : expected.keySet()) {
			require(expected.get(key).equals(actual.get(key)), label + ": " + key + "\nexpected " + expected.get(key) + "\nactual   " + actual.get(key));
		}
	}

	private static void require(boolean condition, String message) {
		if (!condition) throw new AssertionError(message);
	}

	private record Sample(String name, float left, float right, boolean opening, int car, int cars, boolean front, boolean details) {}

	/** Only historical train classes are child-first; Minecraft and unchanged adapters are shared. */
	private static final class ModelLoader extends URLClassLoader {
		private ModelLoader(Path jar) throws Exception {
			super(new URL[]{jar.toUri().toURL()}, KotlinTrainModelsCompatibilityCheck.class.getClassLoader());
		}

		@Override
		protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
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

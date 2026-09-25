package mtr.mappings;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.CustomFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/** Actual many-car ModelMapper extraction, including delayed door animation and mixed geometry. */
public final class ModelCaptureCompatibilityCheck {

	private static final RenderType TYPE = RenderTypes.entityCutout(Identifier.parse("mtr:textures/test/model.png"));
	private static volatile RenderSnapshot retained;
	private static volatile long emittedChecksum;

	public static void main(String[] args) {
		checkDelayedModels();
		checkAllocation();
		System.out.println("PASS: delayed model geometry, many-car ownership, mutable doors/visibility/scale, mixed vertex order and bounded model extraction allocation");
	}

	private static void checkDelayedModels() {
		final ModelDataWrapper data = new ModelDataWrapper(null, 64, 64);
		final ModelMapper body = new ModelMapper(data);
		body.setRotationAngle(0.2F, 0.3F, -0.1F);
		body.addBox(0, 0, 0, 16, 8, 16, 0.5F, false);
		body.addBox(1, 1, 1, 4, 8, 6, 0, true);
		final ModelMapper door = new ModelMapper(data);
		body.addChild(door);
		door.addBox(0, 0, 0, 4, 12, 1, 0, true);
		data.setModelPart(64, 64);
		body.setModelPart();
		door.setModelPart(body.name);
		final ModelPart bodyPart = data.modelPart.getChild(body.name);
		final ModelPart doorPart = bodyPart.getChild(door.name);
		// The first extraction must build complete topology even when a child starts hidden.
		doorPart.visible = false;
		final PoseStack matrices = new PoseStack();
		matrices.translate(3, -2, 9);
		matrices.mulPose(Axis.YP.rotationDegrees(17));
		matrices.scale(-1.5F, 0.5F, 2);
		final Vertices expected = new Vertices();
		final RenderSnapshot snapshot;
		VertexConsumer stale;
		try (RenderBufferSource source = RenderBufferSource.begin(Vec3.ZERO)) {
			stale = source.getBuffer(TYPE);
			for (int car = 0; car < 6; car++) {
				emitMarker(stale, car);
				emitMarker(expected, car);
				door.setOffset(car * 3, car % 2, -car);
				doorPart.visible = car != 0 && car != 2;
				doorPart.skipDraw = car == 4;
				doorPart.xRot = car * 0.1F;
				doorPart.xScale = car == 5 ? -1 : 1;
				bodyPart.skipDraw = car == 3;
				bodyPart.visible = car != 1;
				body.render(matrices, expected, car, car * 2, car * 20, car * 0.2F, 0x00F00030 + car, 0x000A0000 + car);
				body.render(matrices, stale, car, car * 2, car * 20, car * 0.2F, 0x00F00030 + car, 0x000A0000 + car);
			}
			emitMarker(stale, 100);
			emitMarker(expected, 100);
			snapshot = source.snapshot();
			try {
				body.render(matrices, stale, 0, 0, 0, 0, 0, 0);
				throw new AssertionError("A model was accepted by a sealed consumer");
			} catch (IllegalStateException correct) {
			}
		}
		// A delayed frame must not read mutable animation/model/matrix state from a later car.
		bodyPart.visible = false;
		doorPart.visible = false;
		door.setOffset(1000, 1000, 1000);
		matrices.setIdentity();
		final Vertices actual = replay(snapshot);
		expected.check(actual);
		expected.check(replay(snapshot));
		final PoseStack parent = new PoseStack();
		parent.translate(13, 7, -8);
		parent.mulPose(Axis.XP.rotationDegrees(33));
		parent.scale(0.5F, -2, 1.5F);
		final Vertices transformed = new Vertices();
		for (float[] vertex : expected.values) {
			transformed.addVertex(parent.last(), vertex[0], vertex[1], vertex[2])
				.setColor(Float.floatToRawIntBits(vertex[3])).setUv(vertex[4], vertex[5])
				.setOverlay(Float.floatToRawIntBits(vertex[6])).setLight(Float.floatToRawIntBits(vertex[7]))
				.setNormal(parent.last(), vertex[8], vertex[9], vertex[10]);
		}
		final Vertices transformedActual = new Vertices();
		replay(snapshot, parent, transformedActual);
		transformed.check(transformedActual);
		// Rebaking replaces the source model; both old snapshots and the new model must work.
		data.setModelPart(128, 128);
		body.setModelPart();
		door.setModelPart(body.name);
		final Vertices rebaked = new Vertices();
		body.render(new PoseStack(), rebaked, 1, 2, 3, 0.4F, 0, 0);
		try (RenderBufferSource source = RenderBufferSource.begin(Vec3.ZERO)) {
			body.render(new PoseStack(), source.getBuffer(TYPE), 1, 2, 3, 0.4F, 0, 0);
			rebaked.check(replay(source.snapshot()));
		}
		expected.check(replay(snapshot));
	}

	private static void checkAllocation() {
		final ModelDataWrapper data = new ModelDataWrapper(null, 64, 64);
		final ModelMapper model = new ModelMapper(data);
		for (int cube = 0; cube < 1024; cube++) {
			model.addBox(cube % 16, cube / 16 % 16, cube / 256, 1, 1, 1, 0, false);
		}
		data.setModelPart(64, 64);
		model.setModelPart();
		for (int warmup = 0; warmup < 3; warmup++) captureCars(model, 2);
		final com.sun.management.ThreadMXBean allocation = (com.sun.management.ThreadMXBean) ManagementFactory.getThreadMXBean();
		require(allocation.isThreadAllocatedMemorySupported(), "The JDK must expose thread allocation measurements");
		allocation.setThreadAllocatedMemoryEnabled(true);
		final long thread = Thread.currentThread().threadId();
		final long before = allocation.getThreadAllocatedBytes(thread);
		final long started = System.nanoTime();
		captureCars(model, 16);
		final long elapsed = System.nanoTime() - started;
		final long perCar = (allocation.getThreadAllocatedBytes(thread) - before) / 16;
		System.out.printf("Model extraction: 16 cars x 1,024 cubes, %,d bytes/car, %.3f ms/extraction%n", perCar, elapsed / 1_000_000.0);
		require(perCar < 256 * 1024, "Model extraction still allocates per-vertex storage: " + perCar + " bytes/car");
		for (int cars : new int[]{1, 16, 64}) {
			measureFrame(model, cars, true, allocation);
			measureFrame(model, cars, false, allocation);
		}
	}

	private static void measureFrame(ModelMapper model, int cars, boolean legacyCapture, com.sun.management.ThreadMXBean allocation) {
		final CountVertices sink = new CountVertices();
		final long before = allocation.getThreadAllocatedBytes(Thread.currentThread().threadId());
		final long started = System.nanoTime();
		try (RenderBufferSource source = RenderBufferSource.begin(Vec3.ZERO)) {
			final PoseStack pose = new PoseStack();
			final VertexConsumer vertices = legacyCapture ? new ForwardingVertices(source.getBuffer(TYPE)) : source.getBuffer(TYPE);
			for (int car = 0; car < cars; car++) model.render(pose, vertices, car, 0, car * 20, car * 0.1F, 0x00F000F0, 0);
			retained = source.snapshot();
		}
		final long captured = System.nanoTime();
		replay(retained, new PoseStack(), sink);
		final long finished = System.nanoTime();
		final long bytes = allocation.getThreadAllocatedBytes(Thread.currentThread().threadId()) - before;
		emittedChecksum = sink.checksum;
		require(sink.count == cars * 1024 * 24, "Scaling fixture lost vertices");
		System.out.printf("%s %2d cars: capture %.3f ms, capture+final emission %.3f ms, %,d bytes, %,d vertices%n",
			legacyCapture ? "Vertex capture" : "Deferred model", cars, (captured - started) / 1_000_000.0, (finished - started) / 1_000_000.0, bytes, sink.count);
		if (!legacyCapture) require(bytes < cars * 256 * 1024L + 16384, "End-to-end allocation grew beyond the per-car budget");
	}

	private static void captureCars(ModelMapper model, int cars) {
		final PoseStack pose = new PoseStack();
		try (RenderBufferSource source = RenderBufferSource.begin(Vec3.ZERO)) {
			final VertexConsumer vertices = source.getBuffer(TYPE);
			for (int car = 0; car < cars; car++) model.render(pose, vertices, car, 0, car * 20, 0.1F * car, 0x00F000F0, 0);
			retained = source.snapshot();
		}
	}

	private static Vertices replay(RenderSnapshot snapshot) {
		final Vertices vertices = new Vertices();
		replay(snapshot, new PoseStack(), vertices);
		return vertices;
	}

	private static void replay(RenderSnapshot snapshot, PoseStack matrices, VertexConsumer vertices) {
		final SubmitNodeStorage storage = new SubmitNodeStorage();
		snapshot.submit(matrices, storage, new CameraRenderState());
		storage.drainPhases(phase -> phase.sortInto((node, blending) -> {
			require(node instanceof CustomFeatureRenderer.Submit, "Model left the native material batch");
			final CustomFeatureRenderer.Submit submit = (CustomFeatureRenderer.Submit) node;
			submit.customGeometryRenderer().render(submit.pose(), vertices);
		}));
	}

	/** Deliberately not a Collector: reproduces the previous per-vertex extraction path. */
	private record ForwardingVertices(VertexConsumer target) implements VertexConsumer {
		@Override public VertexConsumer addVertex(float x, float y, float z) { target.addVertex(x, y, z); return this; }
		@Override public VertexConsumer setColor(int r, int g, int b, int a) { target.setColor(r, g, b, a); return this; }
		@Override public VertexConsumer setColor(int color) { target.setColor(color); return this; }
		@Override public VertexConsumer setUv(float u, float v) { target.setUv(u, v); return this; }
		@Override public VertexConsumer setUv1(int u, int v) { target.setUv1(u, v); return this; }
		@Override public VertexConsumer setUv2(int u, int v) { target.setUv2(u, v); return this; }
		@Override public VertexConsumer setNormal(float x, float y, float z) { target.setNormal(x, y, z); return this; }
		@Override public VertexConsumer setLineWidth(float width) { target.setLineWidth(width); return this; }
	}

	private static final class CountVertices implements VertexConsumer {
		private int count;
		private long checksum;
		private void consume(int value) { checksum = checksum * 31 + value; }
		private void consume(float value) { consume(Float.floatToRawIntBits(value)); }
		@Override public VertexConsumer addVertex(float x, float y, float z) { count++; consume(x); consume(y); consume(z); return this; }
		@Override public VertexConsumer setColor(int r, int g, int b, int a) { return setColor(a << 24 | r << 16 | g << 8 | b); }
		@Override public VertexConsumer setColor(int color) { consume(color); return this; }
		@Override public VertexConsumer setUv(float u, float v) { consume(u); consume(v); return this; }
		@Override public VertexConsumer setUv1(int u, int v) { consume(u); consume(v); return this; }
		@Override public VertexConsumer setUv2(int u, int v) { consume(u); consume(v); return this; }
		@Override public VertexConsumer setNormal(float x, float y, float z) { consume(x); consume(y); consume(z); return this; }
		@Override public VertexConsumer setLineWidth(float width) { consume(width); return this; }
	}

	private static void emitMarker(VertexConsumer vertices, int value) {
		vertices.addVertex(value, -value, value * 2).setColor(0xFF987654).setUv(0.1F, 0.7F).setLight(value).setOverlay(value + 1).setNormal(0, 1, 0);
	}

	private static final class Vertices implements VertexConsumer {
		private final List<float[]> values = new ArrayList<>();
		private float[] current;
		@Override public VertexConsumer addVertex(float x, float y, float z) { current = new float[12]; current[0] = x; current[1] = y; current[2] = z; values.add(current); return this; }
		@Override public VertexConsumer setColor(int r, int g, int b, int a) { return setColor(a << 24 | r << 16 | g << 8 | b); }
		@Override public VertexConsumer setColor(int color) { current[3] = Float.intBitsToFloat(color); return this; }
		@Override public VertexConsumer setUv(float u, float v) { current[4] = u; current[5] = v; return this; }
		@Override public VertexConsumer setUv1(int u, int v) { current[6] = Float.intBitsToFloat(u | v << 16); return this; }
		@Override public VertexConsumer setUv2(int u, int v) { current[7] = Float.intBitsToFloat(u | v << 16); return this; }
		@Override public VertexConsumer setNormal(float x, float y, float z) { current[8] = x; current[9] = y; current[10] = z; return this; }
		@Override public VertexConsumer setLineWidth(float width) { return this; }
		private void check(Vertices actual) {
			require(values.size() == actual.values.size(), "Delayed models lost or duplicated vertices");
			for (int vertex = 0; vertex < values.size(); vertex++) {
				for (int attribute = 0; attribute < 11; attribute++) {
					final float a = values.get(vertex)[attribute], b = actual.values.get(vertex)[attribute];
					final boolean exact = attribute == 3 || attribute == 6 || attribute == 7;
					require(exact ? Float.floatToRawIntBits(a) == Float.floatToRawIntBits(b) : Math.abs(a - b) < 0.00002F,
						"Delayed model changed vertex " + vertex + " attribute " + attribute + ": " + a + " vs " + b);
				}
			}
		}
	}

	private static void require(boolean condition, String message) {
		if (!condition) throw new AssertionError(message);
	}
}

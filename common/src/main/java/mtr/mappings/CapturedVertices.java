package mtr.mappings;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.rendertype.RenderType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Packed ad-hoc vertices and deferred model draws preserve one ordered material batch. */
final class CapturedVertices implements VertexConsumer, ModelGeometry.Collector {

	private static final int STRIDE = 12;
	private int[] values;
	private List<ModelEntry> models;
	private int size;
	private boolean sealed;

	@Override
	public VertexConsumer addVertex(float x, float y, float z) {
		ensureWritable();
		if (values == null) values = new int[STRIDE * 64];
		if (size + STRIDE > values.length) {
			values = Arrays.copyOf(values, values.length * 2);
		}
		values[size] = Float.floatToRawIntBits(x);
		values[size + 1] = Float.floatToRawIntBits(y);
		values[size + 2] = Float.floatToRawIntBits(z);
		values[size + 3] = -1;
		values[size + 11] = Float.floatToRawIntBits(1);
		size += STRIDE;
		return this;
	}

	@Override
	public VertexConsumer setColor(int red, int green, int blue, int alpha) {
		return setColor(alpha << 24 | red << 16 | green << 8 | blue);
	}

	@Override
	public VertexConsumer setColor(int color) {
		return put(3, color);
	}

	@Override
	public VertexConsumer setUv(float u, float v) {
		put(4, Float.floatToRawIntBits(u));
		return put(5, Float.floatToRawIntBits(v));
	}

	@Override
	public VertexConsumer setUv1(int u, int v) {
		return put(6, (u & 0xFFFF) | v << 16);
	}

	@Override
	public VertexConsumer setUv2(int u, int v) {
		return put(7, (u & 0xFFFF) | v << 16);
	}

	@Override
	public VertexConsumer setNormal(float x, float y, float z) {
		put(8, Float.floatToRawIntBits(x));
		put(9, Float.floatToRawIntBits(y));
		return put(10, Float.floatToRawIntBits(z));
	}

	@Override
	public VertexConsumer setLineWidth(float width) {
		return put(11, Float.floatToRawIntBits(width));
	}

	boolean isEmpty() {
		return size == 0 && models == null;
	}

	@Override
	public void captureModel(ModelGeometry geometry, PoseStack.Pose pose, int light, int overlay) {
		ensureWritable();
		if (models == null) models = new ArrayList<>();
		models.add(new ModelEntry(size, geometry.capture(pose, light, overlay)));
	}

	RenderSnapshot.Submission snapshot(RenderType type) {
		ensureWritable();
		// Sealing releases the only writable reference; the delayed submission owns the buffer.
		final int[] captured = values;
		final int capturedSize = size;
		final List<ModelEntry> capturedModels = models;
		seal();
		return (matrices, collector, cameraState) -> collector.submitCustomGeometry(matrices, type, (pose, vertices) -> {
			int offset = 0;
			if (capturedModels != null) {
				for (ModelEntry model : capturedModels) {
					replay(captured, offset, model.offset, pose, vertices);
					model.capture.emit(pose, vertices);
					offset = model.offset;
				}
			}
			replay(captured, offset, capturedSize, pose, vertices);
		});
	}

	private static void replay(int[] captured, int start, int end, PoseStack.Pose pose, VertexConsumer vertices) {
		for (int offset = start; offset < end; offset += STRIDE) {
			vertices.addVertex(pose, Float.intBitsToFloat(captured[offset]), Float.intBitsToFloat(captured[offset + 1]), Float.intBitsToFloat(captured[offset + 2]))
				.setColor(captured[offset + 3])
				.setUv(Float.intBitsToFloat(captured[offset + 4]), Float.intBitsToFloat(captured[offset + 5]))
				.setOverlay(captured[offset + 6])
				.setLight(captured[offset + 7])
				.setNormal(pose, Float.intBitsToFloat(captured[offset + 8]), Float.intBitsToFloat(captured[offset + 9]), Float.intBitsToFloat(captured[offset + 10]))
				.setLineWidth(Float.intBitsToFloat(captured[offset + 11]));
		}
	}

	void seal() {
		sealed = true;
		values = null;
		models = null;
	}

	private VertexConsumer put(int attribute, int value) {
		ensureWritable();
		if (size == 0) {
			throw new IllegalStateException("Vertex attributes require addVertex first");
		}
		values[size - STRIDE + attribute] = value;
		return this;
	}

	private void ensureWritable() {
		if (sealed) {
			throw new IllegalStateException("This vertex batch has already been captured");
		}
	}

	private record ModelEntry(int offset, ModelGeometry.Capture capture) {
	}
}

package mtr.mappings;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.rendertype.RenderType;

import java.util.Arrays;

/** Packed primitive storage avoids allocating an object for each model vertex. */
final class CapturedVertices implements VertexConsumer {

	private static final int STRIDE = 12;
	private int[] values = new int[STRIDE * 64];
	private int size;
	private boolean sealed;

	@Override
	public VertexConsumer addVertex(float x, float y, float z) {
		ensureWritable();
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
		return size == 0;
	}

	RenderSnapshot.Submission snapshot(RenderType type) {
		ensureWritable();
		final int[] captured = Arrays.copyOf(values, size);
		seal();
		return (matrices, collector, cameraState) -> collector.submitCustomGeometry(matrices, type, (pose, vertices) -> {
			for (int offset = 0; offset < captured.length; offset += STRIDE) {
				vertices.addVertex(pose, Float.intBitsToFloat(captured[offset]), Float.intBitsToFloat(captured[offset + 1]), Float.intBitsToFloat(captured[offset + 2]))
					.setColor(captured[offset + 3])
					.setUv(Float.intBitsToFloat(captured[offset + 4]), Float.intBitsToFloat(captured[offset + 5]))
					.setOverlay(captured[offset + 6])
					.setLight(captured[offset + 7])
					.setNormal(pose, Float.intBitsToFloat(captured[offset + 8]), Float.intBitsToFloat(captured[offset + 9]), Float.intBitsToFloat(captured[offset + 10]))
					.setLineWidth(Float.intBitsToFloat(captured[offset + 11]));
			}
		});
	}

	void seal() {
		sealed = true;
		values = null;
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
}

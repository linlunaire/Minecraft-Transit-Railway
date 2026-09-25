package mtr.mappings;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.util.ARGB;
import net.minecraft.util.LightCoordsUtil;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

/** Exercises actual baked ModelPart geometry without a render device or game world. */
public final class ModelCompatibilityCheck {

	private static final int LIGHT = (5 << 4) | (11 << 20);
	private static final int OVERLAY = (9 << 16) | 4;

	public static void main(String[] args) {
		final ModelDataWrapper data = new ModelDataWrapper(null, 64, 64);
		final ModelMapper parent = new ModelMapper(data);
		parent.setPos(8, 16, -8);
		parent.texOffs(0, 0).addBox(0, 0, 0, 16, 16, 16, 0, false);
		final ModelMapper child = new ModelMapper(data);
		parent.addChild(child);
		child.setPos(16, 0, 0);
		child.texOffs(0, 0).addBox(0, 0, 0, 8, 8, 8, 0, true);
		data.setModelPart(64, 64);
		parent.setModelPart();
		child.setModelPart(parent.name);

		final PoseStack matrices = new PoseStack();
		matrices.translate(2, 3, 4);
		final Matrix4f originalPose = new Matrix4f(matrices.last().pose());
		final CapturedVertices all = new CapturedVertices();
		parent.render(matrices, all, 8, 16, -8, 0, LIGHT, OVERLAY);
		require(all.vertices.size() == 48, "Parent and mirrored child must each emit six quad faces");
		all.checkBounds(2.5F, 4, 3.5F, 4, 5, 4.5F);
		all.checkAttributes();
		require(originalPose.equals(matrices.last().pose()), "Rendering must restore the caller's pose");

		final CapturedVertices selected = new CapturedVertices();
		child.render(new PoseStack(), selected, 0, 0, 0, 0, LIGHT, OVERLAY);
		require(selected.vertices.size() == 24, "Rendering a selected material part must not draw its parent");
		selected.checkBounds(0, 0, 0, 0.5F, 0.5F, 0.5F);
		selected.checkAttributes();

		final ModelDataWrapper inflatedData = new ModelDataWrapper(null, 64, 64);
		final ModelMapper inflated = new ModelMapper(inflatedData);
		inflated.addBox(0, 0, 0, 16, 16, 16, 1, false);
		inflatedData.setModelPart(64, 64);
		inflated.setModelPart();
		final CapturedVertices rotated = new CapturedVertices();
		inflated.render(new PoseStack(), rotated, 0, 0, 0, (float) (Math.PI / 2), LIGHT, OVERLAY);
		rotated.checkBounds(-1F / 16, -1F / 16, -17F / 16, 17F / 16, 17F / 16, 1F / 16);
		rotated.checkAttributes();

		for (int block = 0; block <= 15; block++) {
			for (int sky = 0; sky <= 15; sky++) {
				final int light = LightCoordsUtil.pack(block, sky);
				require(light == (block << 4 | sky << 20), "Packed light changed from the legacy format");
				require(LightCoordsUtil.block(light) == block && LightCoordsUtil.sky(light) == sky, "Light channels changed");
			}
		}
		for (int alpha = 0; alpha <= 255; alpha++) {
			final int argb = (alpha << 24) | 0x123456;
			require(ARGB.toABGR(argb) == ((alpha << 24) | 0x563412), "Pixel conversion swapped or lost a color/alpha channel");
			require(ARGB.fromABGR(ARGB.toABGR(argb)) == argb, "Pixel conversion did not round trip");
		}
		System.out.println("PASS: actual baked model vertices, parent/child and selected-part rendering, mirroring, inflation/rotation, pose restoration, UV/color/light/overlay attributes and ABGR alpha preservation");
	}

	private static final class CapturedVertices implements VertexConsumer {

		private final List<Vertex> vertices = new ArrayList<>();
		private Vertex current;

		@Override
		public VertexConsumer addVertex(float x, float y, float z) {
			current = new Vertex(x, y, z);
			vertices.add(current);
			return this;
		}

		@Override
		public VertexConsumer setColor(int red, int green, int blue, int alpha) {
			return setColor(alpha << 24 | red << 16 | green << 8 | blue);
		}

		@Override
		public VertexConsumer setColor(int color) {
			current.color = color;
			return this;
		}

		@Override
		public VertexConsumer setUv(float u, float v) {
			current.u = u;
			current.v = v;
			return this;
		}

		@Override
		public VertexConsumer setUv1(int u, int v) {
			current.overlay = u | v << 16;
			return this;
		}

		@Override
		public VertexConsumer setUv2(int u, int v) {
			current.light = u | v << 16;
			return this;
		}

		@Override
		public VertexConsumer setNormal(float x, float y, float z) {
			current.normalLength = x * x + y * y + z * z;
			return this;
		}

		@Override
		public VertexConsumer setLineWidth(float width) {
			throw new AssertionError("Cube models must not emit line vertices");
		}

		private void checkBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
			require(!vertices.isEmpty(), "A model must emit geometry");
			final float[] minimum = {Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY};
			final float[] maximum = {Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY};
			for (Vertex vertex : vertices) {
				for (int axis = 0; axis < 3; axis++) {
					minimum[axis] = Math.min(minimum[axis], vertex.position[axis]);
					maximum[axis] = Math.max(maximum[axis], vertex.position[axis]);
				}
			}
			final float[] expectedMin = {minX, minY, minZ}, expectedMax = {maxX, maxY, maxZ};
			for (int axis = 0; axis < 3; axis++) {
				require(Math.abs(minimum[axis] - expectedMin[axis]) < 0.00001F && Math.abs(maximum[axis] - expectedMax[axis]) < 0.00001F, "Incorrect model geometry bounds on axis " + axis);
			}
		}

		private void checkAttributes() {
			for (Vertex vertex : vertices) {
				require(vertex.color == -1 && vertex.light == LIGHT && vertex.overlay == OVERLAY, "Model vertex lost color, light or overlay");
				require(vertex.u >= 0 && vertex.u <= 1 && vertex.v >= 0 && vertex.v <= 1, "Model texture coordinates are invalid");
				require(Math.abs(vertex.normalLength - 1) < 0.00001F, "Model normal must be unit length");
			}
		}
	}

	private static final class Vertex {
		private final float[] position;
		private int color, light, overlay;
		private float u = Float.NaN, v = Float.NaN, normalLength;

		private Vertex(float x, float y, float z) {
			position = new float[]{x, y, z};
		}
	}

	private static void require(boolean condition, String message) {
		if (!condition) {
			throw new AssertionError(message);
		}
	}
}

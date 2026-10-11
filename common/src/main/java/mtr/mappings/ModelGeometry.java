package mtr.mappings;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Baked cubes are shared; each delayed draw owns only its small animation state. */
final class ModelGeometry {

	private static final int STRIDE = 10;
	private final ModelPart[] parts;
	private final Node root;

	ModelGeometry(ModelPart model) {
		final Map<String, Builder> builders = new LinkedHashMap<>();
		final Builder rootBuilder = new Builder(model, 0);
		builders.put("", rootBuilder);
		// Visit once, not once per car/frame. The visitor exposes the actual baked cubes,
		// including mirroring, deformation and per-face UVs, without copying their vertices.
		model.visit(new PoseStack(), (pose, path, index, cube) -> {
			Builder parent = rootBuilder;
			String key = "";
			for (String name : path.split("/")) {
				if (name.isEmpty()) continue;
				key += "/" + name;
				Builder child = builders.get(key);
				if (child == null) {
					child = new Builder(parent.part.getChild(name), builders.size());
					builders.put(key, child);
					parent.children.add(child);
				}
				parent = child;
			}
			parent.cubes.add(cube);
		});
		parts = builders.values().stream().map(builder -> builder.part).toArray(ModelPart[]::new);
		root = rootBuilder.build();
	}

	Capture capture(PoseStack.Pose pose, int light, int overlay) {
		final float[] state = new float[parts.length * STRIDE];
		for (int index = 0; index < parts.length; index++) {
			final ModelPart part = parts[index];
			final int offset = index * STRIDE;
			state[offset] = part.x;
			state[offset + 1] = part.y;
			state[offset + 2] = part.z;
			state[offset + 3] = part.xRot;
			state[offset + 4] = part.yRot;
			state[offset + 5] = part.zRot;
			state[offset + 6] = part.xScale;
			state[offset + 7] = part.yScale;
			state[offset + 8] = part.zScale;
			state[offset + 9] = (part.visible ? 1 : 0) | (part.skipDraw ? 2 : 0);
		}
		// Capture retains no ModelPart or live animation state, only baked geometry.
		return new Capture(root, state, pose.copy(), light, overlay);
	}

	interface Collector {
		void captureModel(ModelGeometry geometry, PoseStack.Pose pose, int light, int overlay);
	}

	static final class Capture {
		private final Node root;
		private final float[] state;
		private final PoseStack.Pose pose;
		private final int light, overlay;

		private Capture(Node root, float[] state, PoseStack.Pose pose, int light, int overlay) {
			this.root = root;
			this.state = state;
			this.pose = pose;
			this.light = light;
			this.overlay = overlay;
		}

		void emit(PoseStack.Pose parent, VertexConsumer vertices) {
			final PoseStack matrices = new PoseStack();
			matrices.last().set(parent);
			matrices.mulPose(pose.pose());
			root.emit(matrices, vertices, state, light, overlay);
		}
	}

	private record Node(int offset, ModelPart.Cube[] cubes, Node[] children) {
		private void emit(PoseStack matrices, VertexConsumer vertices, float[] state, int light, int overlay) {
			final int flags = (int) state[offset + 9];
			if ((flags & 1) == 0) return;
			matrices.pushPose();
			try {
				// Same transform order and pixel units as ModelPart.translateAndRotate.
				matrices.translate(state[offset] / 16, state[offset + 1] / 16, state[offset + 2] / 16);
				if (state[offset + 3] != 0 || state[offset + 4] != 0 || state[offset + 5] != 0) {
					matrices.mulPose(new Quaternionf().rotationZYX(state[offset + 5], state[offset + 4], state[offset + 3]));
				}
				if (state[offset + 6] != 1 || state[offset + 7] != 1 || state[offset + 8] != 1) {
					matrices.scale(state[offset + 6], state[offset + 7], state[offset + 8]);
				}
				if ((flags & 2) == 0) {
					for (ModelPart.Cube cube : cubes) cube.compile(matrices.last(), vertices, light, overlay, -1);
				}
				for (Node child : children) child.emit(matrices, vertices, state, light, overlay);
			} finally {
				matrices.popPose();
			}
		}
	}

	private static final class Builder {
		private final ModelPart part;
		private final int index;
		private final List<ModelPart.Cube> cubes = new ArrayList<>();
		private final List<Builder> children = new ArrayList<>();

		private Builder(ModelPart part, int index) {
			this.part = part;
			this.index = index;
		}

		private Node build() {
			return new Node(index * STRIDE, cubes.toArray(ModelPart.Cube[]::new), children.stream().map(Builder::build).toArray(Node[]::new));
		}
	}
}

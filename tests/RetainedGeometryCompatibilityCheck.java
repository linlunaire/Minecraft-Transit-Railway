package mtr.mappings;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.CustomFeatureRenderer;
import net.minecraft.client.renderer.feature.submit.SubmitNode;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/** No window/GPU: verifies the actual retained submit path and its transformed CPU fallback. */
public final class RetainedGeometryCompatibilityCheck {

	private static final int COLOR = 0x80336699, LIGHT = 0x00B00050, OVERLAY = 0x00090004;

	public static void main(String[] args) throws Exception {
		final RenderType entity = material(RenderPipelines.ENTITY_CUTOUT_CULL, "entity", true);
		final RenderType beam = material(RenderPipelines.BEACON_BEAM_OPAQUE, "beam", true);
		require(RetainedGeometry.supports(entity) && RetainedGeometry.supports(beam), "Supported opaque material rejected");
		require(!RetainedGeometry.supports(material(RenderPipelines.ENTITY_TRANSLUCENT_CULL, "translucent", true)), "Blended material must use ordered fallback");
		require(!RetainedGeometry.supports(material(RenderPipelines.ENTITY_CUTOUT_CULL, "no_depth", false)), "Non-depth-writing material must use fallback");
		require(!RetainedGeometry.supports(null), "Null material supported");
		final AtomicInteger emissions = new AtomicInteger();
		final RetainedGeometry geometry = new RetainedGeometry(entity, 3, consumer -> {
			emissions.incrementAndGet();
			for (int i = 0; i < 3; i++) consumer.addVertex(i, 2, 3).setColor(COLOR).setUv(.25F, .75F)
				.setLight(LIGHT).setOverlay(OVERLAY).setNormal(1, 0, 0).setLineWidth(2);
		});
		final Matrix4f capturedPose = new Matrix4f().translation(1, 2, 3);
		final RenderSnapshot snapshot;
		try (RenderBufferSource source = RenderBufferSource.begin(Vec3.ZERO)) {
			source.drawRetained(geometry, capturedPose);
			snapshot = source.snapshot();
		}
		capturedPose.translation(999, 999, 999);
		final PoseStack parent = new PoseStack();
		parent.translate(10, 20, 30);
		final Matrix4f before = new Matrix4f(parent.last().pose());
		for (int iteration = 0; iteration < 2; iteration++) {
			final List<SubmitNode> nodes = submit(snapshot, parent);
			require(parent.last().pose().equals(before), "Submission mutated its parent pose");
			require(nodes.size() == 1 && nodes.getFirst() instanceof RetainedGeometry.Submit, "Expected real retained solid feature");
			final RetainedGeometry.Submit node = (RetainedGeometry.Submit) nodes.getFirst();
			require(node.geometry() == geometry && node.batchKey() == entity, "Geometry/material identity changed");
			near(node.x(), 11); near(node.y(), 22); near(node.z(), 33);
			node.pose().identity();
			near(node.pose().m30(), 11);
		}
		require(emissions.get() == 0, "Extraction/submission decoded or copied retained vertices");

		parent.mulPose(Axis.ZP.rotationDegrees(90));
		parent.scale(2, 3, 4);
		final Matrix4f transformedParent = new Matrix4f(parent.last().pose());
		final List<SubmitNode> fallbackNodes = submit(snapshot, parent);
		require(parent.last().pose().equals(transformedParent), "Fallback mutated its parent pose");
		parent.setIdentity();
		require(fallbackNodes.size() == 1 && fallbackNodes.getFirst() instanceof CustomFeatureRenderer.Submit, "Rotated/scaled parent must use CPU fallback");
		final CustomFeatureRenderer.Submit fallback = (CustomFeatureRenderer.Submit) fallbackNodes.getFirst();
		final PoseStack expectedPose = new PoseStack();
		expectedPose.mulPose(transformedParent);
		expectedPose.translate(1, 2, 3);
		final InspectVertices output = new InspectVertices(expectedPose.last());
		fallback.customGeometryRenderer().render(fallback.pose(), output);
		require(output.count == 3 && emissions.get() == 1, "Fallback dropped or repeated vertices");
		try (RenderBufferSource source = RenderBufferSource.begin(Vec3.ZERO)) {
			try {
				source.drawRetained(geometry, new Matrix4f().rotationZ(1));
				throw new AssertionError("Non-translation extraction pose accepted");
			} catch (IllegalArgumentException expected) { }
		}
		checkShaders(Path.of(args[0]), entity, beam);
		System.out.println("PASS: retained identity without vertex emission, immutable copied translation, repeated delayed submission, parent composition, rotated/non-uniform scaled fallback normals/attributes, pipeline state and shader equivalence (no GPU/runtime claim)");
	}

	private static List<SubmitNode> submit(RenderSnapshot snapshot, PoseStack parent) {
		final SubmitNodeStorage storage = new SubmitNodeStorage();
		snapshot.submit(parent, storage, new CameraRenderState());
		final List<SubmitNode> nodes = new ArrayList<>();
		storage.drainPhases(phase -> phase.sortInto((node, blending) -> nodes.add(node)));
		return nodes;
	}

	private static void checkShaders(Path root, RenderType entity, RenderType beam) throws Exception {
		final RetainedGeometryRenderer renderer = new RetainedGeometryRenderer();
		final var factory = RetainedGeometryRenderer.class.getDeclaredMethod("pipeline", RenderPipeline.class);
		factory.setAccessible(true);
		for (RenderType type : List.of(entity, beam)) {
			final RenderPipeline original = type.pipeline();
			final RenderPipeline retained = (RenderPipeline) factory.invoke(renderer, original);
			require(retained.getFragmentShader().equals(original.getFragmentShader()), "Fragment shader changed");
			require(retained.getShaderDefines().equals(original.getShaderDefines()), "Shader defines changed");
			require(retained.getBindGroupLayouts().equals(original.getBindGroupLayouts()), "Uniform/lightmap/overlay bindings changed");
			require(retained.getColorTargetState().equals(original.getColorTargetState()), "Blend/color write state changed");
			require(retained.getDepthStencilState().equals(original.getDepthStencilState()), "Depth state changed");
			require(retained.isCull() == original.isCull() && retained.getPrimitiveTopology() == PrimitiveTopology.TRIANGLES,
				"Culling/topology changed");
			final String vanilla = resource("assets/minecraft/shaders/" + original.getVertexShader().getPath() + ".vsh");
			final String generated = Files.readString(root.resolve("common/src/main/resources/assets/mtr/shaders/" + retained.getVertexShader().getPath() + ".vsh"));
			final String expected = type == beam ? vanilla.replace("vec4(Position, 1.0)", "vec4(Position + ModelOffset, 1.0)")
				: vanilla.replace("gl_Position =", "vec3 position = Position + ModelOffset; gl_Position =")
					.replace("vec4(Position, 1.0)", "vec4(position, 1.0)")
					.replace("fog_spherical_distance(Position)", "fog_spherical_distance(position)")
					.replace("fog_cylindrical_distance(Position)", "fog_cylindrical_distance(position)");
			require(canonical(generated).equals(canonical(expected)), "Retained shader changed more than camera-relative translation: " + retained.getVertexShader());
		}
		renderer.close();
	}

	private static String canonical(String shader) {
		return shader.replaceAll("(?s)/\\*.*?\\*/", "").replaceAll("(?m)//.*$", "").replaceAll("\\s+", "");
	}

	private static String resource(String name) throws Exception {
		try (var stream = RetainedGeometryCompatibilityCheck.class.getClassLoader().getResourceAsStream(name)) {
			require(stream != null, "Missing real game shader: " + name);
			return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
		}
	}

	private static RenderType material(RenderPipeline original, String name, boolean writeDepth) {
		final var snippet = new RenderPipeline.Snippet(Optional.of(original.getVertexShader()), Optional.of(original.getFragmentShader()),
			Optional.of(original.getShaderDefines()), Optional.of(original.getBindGroupLayouts()), original.getColorTargetStates().clone(),
			original.getColorTargetStates().length, Optional.ofNullable(original.getDepthStencilState()), Optional.of(original.getPolygonMode()),
			Optional.of(original.isCull()), original.getVertexFormatBindings().clone(), Optional.of(PrimitiveTopology.TRIANGLES));
		final RenderPipeline pipeline = RenderPipeline.builder(snippet).withLocation(Identifier.parse("mtr:retained_test_" + name))
			.withDepthStencilState(new DepthStencilState(original.getDepthStencilState().depthTest(), writeDepth)).build();
		return RenderType.create(name, RenderSetup.builder(pipeline).createRenderSetup());
	}

	private static final class InspectVertices implements VertexConsumer {
		private final PoseStack.Pose expectedPose;
		private int count;
		private InspectVertices(PoseStack.Pose expectedPose) { this.expectedPose = expectedPose; }
		@Override public VertexConsumer addVertex(float x, float y, float z) {
			final Vector3f expected = expectedPose.pose().transformPosition(new Vector3f(count++, 2, 3));
			near(x, expected.x); near(y, expected.y); near(z, expected.z); return this;
		}
		@Override public VertexConsumer setColor(int r, int g, int b, int a) { return setColor(a << 24 | r << 16 | g << 8 | b); }
		@Override public VertexConsumer setColor(int color) { require(color == COLOR, "Color changed"); return this; }
		@Override public VertexConsumer setUv(float u, float v) { near(u, .25F); near(v, .75F); return this; }
		@Override public VertexConsumer setUv1(int u, int v) { require((u | v << 16) == OVERLAY, "Overlay changed"); return this; }
		@Override public VertexConsumer setUv2(int u, int v) { require((u | v << 16) == LIGHT, "Light changed"); return this; }
		@Override public VertexConsumer setNormal(float x, float y, float z) {
			final Vector3f expected = expectedPose.transformNormal(1, 0, 0, new Vector3f());
			near(x, expected.x); near(y, expected.y); near(z, expected.z); return this;
		}
		@Override public VertexConsumer setLineWidth(float width) { near(width, 2); return this; }
	}

	private static void near(float actual, float expected) { require(Math.abs(actual - expected) < .0001F, "Expected " + expected + ", got " + actual); }
	private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}

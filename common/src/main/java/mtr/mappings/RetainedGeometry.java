package mtr.mappings;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.feature.FeatureRendererType;
import net.minecraft.client.renderer.feature.submit.BatchableSubmit;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.joml.Matrix4f;

import java.util.Objects;
import java.util.function.Consumer;

/** Immutable CPU geometry. GPU allocation and retirement belong to the feature renderer. */
public final class RetainedGeometry {

	private final RenderType type;
	private final int vertexCount;
	private final Consumer<VertexConsumer> emitter;

	/** The emitter must only refer to immutable, object-space vertex data. */
	public RetainedGeometry(RenderType type, int vertexCount, Consumer<VertexConsumer> emitter) {
		if (!supports(type) || vertexCount <= 0 || vertexCount % 3 != 0) {
			throw new IllegalArgumentException("Retained geometry requires opaque, depth-writing entity/beam triangles");
		}
		this.type = type;
		this.vertexCount = vertexCount;
		this.emitter = Objects.requireNonNull(emitter);
	}

	public static boolean supports(RenderType type) {
		if (type == null || type.hasBlending() || type.primitiveTopology() != PrimitiveTopology.TRIANGLES
			|| type.pipeline().getDepthStencilState() == null || !type.pipeline().getDepthStencilState().writeDepth()) {
			return false;
		}
		final String shader = type.pipeline().getVertexShader().toString();
		return shader.equals("minecraft:core/entity") || shader.equals("minecraft:core/rendertype_beacon_beam");
	}

	public RenderType type() { return type; }
	public int vertexCount() { return vertexCount; }
	public void emit(VertexConsumer consumer) { emitter.accept(consumer); }

	/** CPU fallback for a rotated/scaled parent pose; normals use Minecraft's normal matrix. */
	public void emit(PoseStack.Pose pose, VertexConsumer consumer) {
		emit(new VertexConsumer() {
			@Override public VertexConsumer addVertex(float x, float y, float z) { consumer.addVertex(pose, x, y, z); return this; }
			@Override public VertexConsumer setColor(int r, int g, int b, int a) { consumer.setColor(r, g, b, a); return this; }
			@Override public VertexConsumer setColor(int color) { consumer.setColor(color); return this; }
			@Override public VertexConsumer setUv(float u, float v) { consumer.setUv(u, v); return this; }
			@Override public VertexConsumer setUv1(int u, int v) { consumer.setUv1(u, v); return this; }
			@Override public VertexConsumer setUv2(int u, int v) { consumer.setUv2(u, v); return this; }
			@Override public VertexConsumer setNormal(float x, float y, float z) { consumer.setNormal(pose, x, y, z); return this; }
			@Override public VertexConsumer setLineWidth(float width) { consumer.setLineWidth(width); return this; }
		});
	}

	/** Only a copied translation is captured; no extraction matrix can mutate the submitted frame. */
	public record Submit(RetainedGeometry geometry, float x, float y, float z) implements BatchableSubmit {
		@Override public Object batchKey() { return geometry.type; }
		@Override public FeatureRendererType<Submit> featureType() { return RetainedGeometryRenderer.TYPE; }
		public Matrix4f pose() { return new Matrix4f().translation(x, y, z); }
	}
}

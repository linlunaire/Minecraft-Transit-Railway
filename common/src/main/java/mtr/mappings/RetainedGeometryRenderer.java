package mtr.mappings;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import net.minecraft.client.renderer.feature.FeatureFrameContext;
import net.minecraft.client.renderer.feature.FeatureRenderer;
import net.minecraft.client.renderer.feature.FeatureRendererType;
import net.minecraft.client.renderer.rendertype.PreparedRenderType;
import net.minecraft.resources.Identifier;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Upload static meshes once; draw them during the engine's normal solid feature phase. */
public final class RetainedGeometryRenderer implements FeatureRenderer<RetainedGeometry.Submit> {

	public static final FeatureRendererType<RetainedGeometry.Submit> TYPE = FeatureRendererType.create("mtr_retained_geometry");
	private static final int MAX_IDLE_FRAMES = 120;
	private static final long MAX_CACHE_BYTES = 256L * 1024 * 1024;
	private final FrameGeometryCache<RetainedGeometry, Entry> cache = new FrameGeometryCache<>(
		MAX_CACHE_BYTES, MAX_IDLE_FRAMES, entry -> entry.vertexBuffer.size(), entry -> entry.vertexBuffer.close());
	private final Map<RenderPipeline, RenderPipeline> pipelines = new IdentityHashMap<>();
	private final List<List<Draw>> groups = new ArrayList<>();

	@Override
	public void beginPrepare(FeatureFrameContext context) {
		groups.clear();
		cache.beginFrame();
	}

	@Override
	public void prepareGroup(FeatureFrameContext context, List<RetainedGeometry.Submit> submits, boolean translucent) {
		final List<Draw> prepared = new ArrayList<>(submits.size());
		for (RetainedGeometry.Submit submit : submits) {
			final RetainedGeometry geometry = submit.geometry();
			final Entry entry = cache.get(geometry, this::upload);
			final PreparedRenderType material = geometry.type().prepare();
			// Keep the view rotation separate: the retained shaders use ModelOffset for
			// camera-relative position/fog, while normals stay in the original world axes.
			final var transforms = RenderSystem.getDynamicUniforms().writeTransform(RenderSystem.getModelViewMatrixCopy(),
				new Vector4f(1), new Vector3f(submit.x(), submit.y(), submit.z()), new Matrix4f());
			prepared.add(new Draw(entry, new PreparedRenderType(pipelines.computeIfAbsent(material.pipeline(), this::pipeline),
				material.outputTarget(), transforms, material.scissorState(), material.textures())));
		}
		groups.add(prepared);
	}

	@Override
	public void executeGroup(FeatureFrameContext context, int groupIndex, List<RetainedGeometry.Submit> submits, boolean translucent) {
		for (Draw draw : groups.get(groupIndex)) {
			final var indices = RenderSystem.getSequentialBuffer(PrimitiveTopology.TRIANGLES);
			draw.material.drawFromBuffer(draw.entry.vertexBuffer, indices.getBuffer(draw.entry.vertexCount), indices.type(), 0, 0, draw.entry.vertexCount);
		}
	}

	@Override
	public void finishExecute(FeatureFrameContext context) {
		groups.clear();
		cache.finishFrame();
	}

	@Override
	public void close() {
		pipelines.clear();
		groups.clear();
		cache.close();
	}

	private Entry upload(RetainedGeometry geometry) {
		final int bytes = Math.multiplyExact(geometry.vertexCount(), geometry.type().format().getVertexSize());
		try (ByteBufferBuilder allocator = new ByteBufferBuilder(bytes)) {
			final BufferBuilder builder = new BufferBuilder(allocator, PrimitiveTopology.TRIANGLES, geometry.type().format());
			geometry.emit(builder);
			try (MeshData mesh = builder.buildOrThrow()) {
				if (mesh.drawState().vertexCount() != geometry.vertexCount()) {
					throw new IllegalStateException("Retained geometry vertex count changed");
				}
				final GpuBuffer buffer = RenderSystem.getDevice().createBuffer(() -> "MTR retained geometry", GpuBuffer.USAGE_VERTEX, mesh.vertexBuffer());
				return new Entry(buffer, geometry.vertexCount());
			}
		}
	}

	private RenderPipeline pipeline(RenderPipeline template) {
		final boolean beam = template.getVertexShader().toString().equals("minecraft:core/rendertype_beacon_beam");
		final var snippet = new RenderPipeline.Snippet(Optional.of(template.getVertexShader()), Optional.of(template.getFragmentShader()),
			Optional.of(template.getShaderDefines()), Optional.of(template.getBindGroupLayouts()), template.getColorTargetStates().clone(),
			template.getColorTargetStates().length, Optional.ofNullable(template.getDepthStencilState()), Optional.of(template.getPolygonMode()),
			Optional.of(template.isCull()), template.getVertexFormatBindings().clone(), Optional.of(template.getPrimitiveTopology()));
		return RenderPipeline.builder(snippet)
			.withLocation(Identifier.fromNamespaceAndPath("mtr", "pipeline/retained_" + pipelines.size()))
			.withVertexShader(Identifier.fromNamespaceAndPath("mtr", beam ? "core/retained_beam" : "core/retained_entity")).build();
	}

	private static final class Entry {
		private final GpuBuffer vertexBuffer;
		private final int vertexCount;
		private Entry(GpuBuffer vertexBuffer, int vertexCount) {
			this.vertexBuffer = vertexBuffer;
			this.vertexCount = vertexCount;
		}
	}

	private record Draw(Entry entry, PreparedRenderType material) { }
}

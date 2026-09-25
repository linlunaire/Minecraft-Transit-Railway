package mtr.mappings;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Extraction-only bridge from MTR's material batches to 26.2 submit nodes. */
public final class RenderBufferSource implements AutoCloseable {

	private static final ThreadLocal<RenderBufferSource> ACTIVE = new ThreadLocal<>();
	private final RenderBufferSource owner;
	private final RenderBufferSource previous;
	private final Vec3 origin;
	private final List<RenderSnapshot.Submission> submissions;
	private final Map<RenderType, CapturedVertices> batches = new LinkedHashMap<>();
	private RenderBufferSource immediate;
	private boolean finished;
	private boolean closed;

	private RenderBufferSource(Vec3 origin, RenderBufferSource previous) {
		owner = this;
		this.origin = origin;
		this.previous = previous;
		submissions = new ArrayList<>();
	}

	private RenderBufferSource(RenderBufferSource owner) {
		this.owner = owner;
		previous = null;
		origin = owner.origin;
		submissions = owner.submissions;
	}

	public static RenderBufferSource begin(Vec3 origin) {
		final RenderBufferSource source = new RenderBufferSource(Objects.requireNonNull(origin), ACTIVE.get());
		ACTIVE.set(source);
		return source;
	}

	public static RenderBufferSource current() {
		final RenderBufferSource source = ACTIVE.get();
		if (source == null) {
			throw new IllegalStateException("MTR geometry must be captured during render-state extraction");
		}
		return source;
	}

	public Vec3 origin() {
		return origin;
	}

	public RenderBufferSource immediate() {
		ensureOpen();
		if (owner.immediate == null) {
			owner.immediate = new RenderBufferSource(owner);
		}
		return owner.immediate;
	}

	public VertexConsumer getBuffer(RenderType type) {
		ensureOpen();
		return batches.computeIfAbsent(Objects.requireNonNull(type), ignored -> new CapturedVertices());
	}

	public void drawText(FormattedCharSequence text, float x, float y, int color, boolean shadow, Matrix4f pose, int background, int light) {
		drawText(text, x, y, color, shadow, pose, Font.DisplayMode.NORMAL, background, light);
	}

	public void drawText(FormattedCharSequence text, float x, float y, int color, boolean shadow, Matrix4f pose, Font.DisplayMode displayMode, int background, int light) {
		ensureOpen();
		submissions.add(RenderSnapshot.text(pose, text, x, y, color, shadow, displayMode, background, light));
	}

	public void drawText(Component text, float x, float y, int color, boolean shadow, Matrix4f pose, int background, int light) {
		drawText(text.getVisualOrderText(), x, y, color, shadow, pose, background, light);
	}

	public void drawText(String text, float x, float y, int color, boolean shadow, Matrix4f pose, int background, int light) {
		drawText(Component.literal(text), x, y, color, shadow, pose, background, light);
	}

	public void drawText(String text, float x, float y, int color, boolean shadow, Matrix4f pose, Font.DisplayMode displayMode, int background, int light) {
		drawText(Component.literal(text).getVisualOrderText(), x, y, color, shadow, pose, displayMode, background, light);
	}

	/** Resolve each marker during extraction, including resource-pack and special item models. */
	public void drawItem(ItemModelResolver resolver, ItemStack item, ItemDisplayContext context, Level level, Matrix4f pose, int light, int overlay, int seed) {
		ensureOpen();
		final ItemStackRenderState state = new ItemStackRenderState();
		resolver.updateForTopItem(state, item, context, level, null, seed);
		final Matrix4f capturedPose = new Matrix4f(pose);
		submissions.add((matrices, collector, cameraState) -> {
			matrices.pushPose();
			try {
				matrices.mulPose(capturedPose);
				state.submit(matrices, collector, light, overlay, 0);
			} finally {
				matrices.popPose();
			}
		});
	}

	public void drawEntity(EntityRenderDispatcher dispatcher, Entity entity, float tickDelta, Matrix4f pose, double x, double y, double z, int light) {
		ensureOpen();
		// Minecraft creates a fresh state for each extraction; no live passenger is retained.
		final EntityRenderState state = dispatcher.extractEntity(entity, tickDelta);
		state.lightCoords = light;
		final Matrix4f capturedPose = new Matrix4f(pose);
		submissions.add((matrices, collector, cameraState) -> {
			matrices.pushPose();
			try {
				matrices.mulPose(capturedPose);
				dispatcher.submit(state, cameraState, x, y, z, matrices, collector);
			} finally {
				matrices.popPose();
			}
		});
	}

	/** Flush just this source; the legacy immediate source is separate from model batches. */
	public void endBatch() {
		ensureOpen();
		batches.forEach((type, vertices) -> {
			if (vertices.isEmpty()) {
				vertices.seal();
			} else {
				submissions.add(vertices.snapshot(type));
			}
		});
		batches.clear();
	}

	public RenderSnapshot snapshot() {
		ensureOpen();
		if (owner != this) {
			throw new IllegalStateException("Only the extraction owner can finish a frame");
		}
		if (immediate != null) {
			immediate.endBatch();
		}
		endBatch();
		finished = true;
		return submissions.isEmpty() ? RenderSnapshot.EMPTY : new RenderSnapshot(submissions);
	}

	@Override
	public void close() {
		if (closed) {
			return;
		}
		if (owner != this || ACTIVE.get() != this) {
			throw new IllegalStateException("Render extraction scopes must close in reverse order");
		}
		closed = true;
		batches.values().forEach(CapturedVertices::seal);
		batches.clear();
		if (immediate != null) {
			immediate.batches.values().forEach(CapturedVertices::seal);
			immediate.batches.clear();
		}
		submissions.clear();
		if (previous == null) {
			ACTIVE.remove();
		} else {
			ACTIVE.set(previous);
		}
	}

	private void ensureOpen() {
		if (owner.closed || owner.finished) {
			throw new IllegalStateException("Render extraction is already finished");
		}
	}
}

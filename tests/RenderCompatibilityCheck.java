package mtr.mappings;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.CustomFeatureRenderer;
import net.minecraft.client.renderer.feature.TextFeatureRenderer;
import net.minecraft.client.renderer.feature.submit.SubmitNode;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/** Captures MTR geometry, then inspects real 26.2 submit nodes after extraction is closed. */
public final class RenderCompatibilityCheck {

	private static final int COLOR = 0x80123456, LIGHT = 0x00B00050, OVERLAY = 0x00090004;

	public static void main(String[] args) throws Exception {
		final RenderType opaque = RenderTypes.entityCutout(Identifier.parse("mtr:textures/test/opaque.png"));
		final RenderType translucent = RenderTypes.beaconBeam(Identifier.parse("mtr:textures/test/light.png"), true);
		final AtomicReference<String> mutableText = new AtomicReference<>("港A🚇");
		final Style style = Style.EMPTY.withFont(new FontDescription.Resource(Identifier.parse("mtr:mtr"))).withBold(true);
		final Matrix4f textPose = new Matrix4f().translation(1, 2, 3);
		final RenderSnapshot snapshot;
		final VertexConsumer stale;
		expectIllegalState(RenderBufferSource::current);
		try (RenderBufferSource source = RenderBufferSource.begin(new Vec3(101, 202, 303))) {
			require(source.origin().equals(new Vec3(101, 202, 303)), "Extraction origin changed");
			stale = source.getBuffer(opaque);
			emit(stale, 260);
			require(source.getBuffer(opaque) == stale, "One material must share its current batch");
			final RenderBufferSource immediate = source.immediate();
			require(immediate != source, "Immediate flushes must not seal the main model batch");
			emit(immediate.getBuffer(translucent), 4);
			immediate.drawText(sink -> FormattedCharSequence.forward(mutableText.get(), style).accept(sink), 4, 5, COLOR, true, textPose, 0x40112233, LIGHT);
			immediate.endBatch();
			// The long-lived main consumer remains writable after a text-source flush.
			emitOne(stale, 260);
			emitOne(stale, 261);
			emitOne(stale, 262);
			emitOne(stale, 263);
			try (RenderBufferSource nested = RenderBufferSource.begin(Vec3.ZERO)) {
				require(RenderBufferSource.current() == nested, "Nested extraction did not become current");
			}
			require(RenderBufferSource.current() == source, "Nested extraction did not restore its parent");
			final AtomicReference<Throwable> threadFailure = new AtomicReference<>();
			final Thread other = new Thread(() -> {
				try {
					expectIllegalState(RenderBufferSource::current);
				} catch (Throwable failure) {
					threadFailure.set(failure);
				}
			});
			other.start();
			other.join();
			require(threadFailure.get() == null, "Extraction context leaked to another thread");
			snapshot = source.snapshot();
			expectIllegalState(() -> source.getBuffer(opaque));
			expectIllegalState(() -> stale.addVertex(999, 999, 999));
		}
		expectIllegalState(RenderBufferSource::current);
		mutableText.set("changed after extraction");
		textPose.identity();

		final PoseStack incoming = new PoseStack();
		incoming.translate(10, 20, 30);
		incoming.mulPose(Axis.ZP.rotationDegrees(90));
		final Matrix4f originalPose = new Matrix4f(incoming.last().pose());
		final SubmitNodeStorage storage = new SubmitNodeStorage();
		snapshot.submit(incoming, storage, new CameraRenderState());
		require(originalPose.equals(incoming.last().pose()), "Snapshot submission changed the caller's matrix");
		// Changing the caller's pose must not change nodes already submitted to Minecraft.
		incoming.setIdentity();
		final List<SubmitNode> nodes = new ArrayList<>();
		storage.drainPhases(phase -> phase.sortInto((node, blending) -> nodes.add(node)));
		int geometryCount = 0, textCount = 0;
		for (SubmitNode node : nodes) {
			if (node instanceof CustomFeatureRenderer.Submit geometry) {
				final int expectedCount = geometry.renderType() == opaque ? 264 : 4;
				require(geometry.renderType() == opaque || geometry.renderType() == translucent, "Render material was changed");
				final InspectVertices vertices = new InspectVertices();
				geometry.customGeometryRenderer().render(geometry.pose(), vertices);
				require(vertices.count == expectedCount, "Vertices were dropped, duplicated or truncated after growth");
				geometryCount++;
			} else if (node instanceof TextFeatureRenderer.Submit text) {
				final StringBuilder recovered = new StringBuilder();
				text.string().accept((index, actualStyle, codePoint) -> {
					require(actualStyle.equals(style), "Text font or formatting changed");
					recovered.appendCodePoint(codePoint);
					return true;
				});
				require(recovered.toString().equals("港A🚇"), "Delayed text read mutable input or lost Unicode");
				require(text.color() == COLOR && text.lightCoords() == LIGHT && text.backgroundColor() == 0x40112233 && text.outlineColor() == 0, "Text colors/light were mapped to the wrong API arguments");
				require(text.x() == 4 && text.y() == 5 && text.dropShadow() && text.displayMode() == Font.DisplayMode.NORMAL, "Text placement/display mode changed");
				near(text.pose().m30(), 8);
				near(text.pose().m31(), 21);
				near(text.pose().m32(), 33);
				textCount++;
			}
		}
		require(geometryCount == 2 && textCount == 1, "Expected two material batches and one text node");
		checkBatchLifetime(opaque);
		checkSnapshotOwnership(opaque);
		checkAddonSubmissions();
		checkMaterials();
		checkVanillaVehicles();
		System.out.println("PASS: real 26.2 geometry/text submit nodes, immutable delayed capture, 268 vertices, material batching, matrix/normal transforms, vertex attributes, Unicode/font/alpha, separate immediate flush and scoped context cleanup");
	}

	private static void checkMaterials() {
		final Identifier texture = Identifier.parse("mtr:textures/test/material.png");
		final RenderType cutout = RenderLayerMapper.entityCutout(texture);
		final RenderType translucent = RenderLayerMapper.entityTranslucentCull(texture);
		require(cutout.pipeline().isCull() && translucent.pipeline().isCull(), "Train material lost back-face culling");
		require(cutout.outputTarget() == OutputTarget.MAIN_TARGET && translucent.outputTarget() == OutputTarget.MAIN_TARGET, "Train material moved off its legacy main target");
		require(cutout.pipeline().getColorTargetState().blendFunction().isEmpty(), "Cutout material became blended");
		require(translucent.pipeline().getColorTargetState().blendFunction().isPresent(), "Window material lost blending");
		require(cutout.pipeline().getDepthStencilState().writeDepth() && translucent.pipeline().getDepthStencilState().writeDepth(), "Train material lost legacy depth writes");
		final RenderType preview = RenderTypes.solidMovingBlock();
		require(preview.pipeline().getDepthStencilState().writeDepth() && preview.pipeline().getColorTargetState().blendFunction().isEmpty(), "Creator background is no longer opaque and depth-writing");
		System.out.println("PASS: actual material pipelines retain culling, main output target, cutout/window blending and depth writes");
	}

	private static void checkAddonSubmissions() {
		net.minecraft.SharedConstants.tryDetectVersion();
		net.minecraft.server.Bootstrap.bootStrap();
		net.minecraft.core.registries.BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.build(net.minecraft.data.registries.VanillaRegistries.createLookup()).forEach(pending -> pending.apply());
		final List<net.minecraft.client.renderer.item.ItemStackRenderState> states = new ArrayList<>();
		final var resolver = new net.minecraft.client.renderer.item.ItemModelResolver(null) {
			@Override
			public void appendItemLayers(net.minecraft.client.renderer.item.ItemStackRenderState state, net.minecraft.world.item.ItemStack item,
					net.minecraft.world.item.ItemDisplayContext context, net.minecraft.world.level.Level level, net.minecraft.world.entity.ItemOwner owner, int seed) {
				require(owner == null && level == null, "Marker item owner/level changed");
				states.add(state);
				// An ordinary vanilla layer exercises real item submission without loading a GPU atlas.
				state.newLayer().tintLayers().add(item.getCount() + seed);
			}
		};
		final var item = new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.STICK, 3);
		final Matrix4f pose = new Matrix4f().translation(2, 3, 4);
		final RenderSnapshot snapshot;
		try (RenderBufferSource source = RenderBufferSource.begin(Vec3.ZERO)) {
			source.drawText("穿透🚇", 1, 2, COLOR, false, pose, Font.DisplayMode.SEE_THROUGH, 0, LIGHT);
			source.drawItem(resolver, item, net.minecraft.world.item.ItemDisplayContext.GROUND, null, pose, LIGHT, OVERLAY, 7);
			item.setCount(5);
			source.drawItem(resolver, item, net.minecraft.world.item.ItemDisplayContext.FIXED, null, pose, LIGHT, OVERLAY, 8);
			require(states.size() == 2 && states.get(0) != states.get(1), "Item render state reused between markers");
			snapshot = source.snapshot();
			expectIllegalState(() -> source.drawItem(resolver, item, net.minecraft.world.item.ItemDisplayContext.GROUND, null, pose, LIGHT, OVERLAY, 0));
		}
		item.setCount(40);
		pose.identity();
		final PoseStack matrices = new PoseStack();
		matrices.translate(10, 20, 30);
		final Matrix4f before = new Matrix4f(matrices.last().pose());
		final SubmitNodeStorage storage = new SubmitNodeStorage();
		snapshot.submit(matrices, storage, new CameraRenderState());
		require(before.equals(matrices.last().pose()), "Item submission leaked its transform");
		final List<SubmitNode> nodes = new ArrayList<>();
		storage.drainPhases(phase -> phase.sortInto((node, blending) -> nodes.add(node)));
		int items = 0, texts = 0;
		for (SubmitNode node : nodes) {
			if (node instanceof TextFeatureRenderer.Submit text) {
				require(text.displayMode() == Font.DisplayMode.SEE_THROUGH, "Rail labels lost see-through rendering");
				near(text.pose().m30(), 12);
				texts++;
			} else if (node instanceof net.minecraft.client.renderer.feature.ItemFeatureRenderer.Submit captured) {
				require(captured.lightCoords() == LIGHT && captured.overlayCoords() == OVERLAY, "Marker light/overlay changed");
				require(captured.tintLayers()[0] == (items == 0 ? 10 : 13), "Delayed marker read live item or reused a state");
				require(captured.displayContext() == (items == 0 ? net.minecraft.world.item.ItemDisplayContext.GROUND : net.minecraft.world.item.ItemDisplayContext.FIXED), "Marker display context changed");
				// Vanilla ItemTransform centers the unit model by -0.5 on each axis.
				near(captured.pose().pose().m30(), 11.5F);
				near(captured.pose().pose().m31(), 22.5F);
				near(captured.pose().pose().m32(), 33.5F);
				items++;
			}
		}
		require(items == 2 && texts == 1, "Missing addon item/text submit nodes");
		System.out.println("PASS: addon see-through text and two independently extracted marker items, delayed matrix/tint/context/light/overlay");
	}

	private static void checkVanillaVehicles() {
		final VanillaVehicleModels.BoatAnimation animation = new VanillaVehicleModels.BoatAnimation();
		float progress = 0;
		for (float step : new float[]{0.25F, 0.75F, 0, 0, 0.125F, -0.25F}) {
			final BoatRenderState state = animation.advance(step);
			near(state.rowingTimeLeft, progress += step);
			near(state.rowingTimeRight, progress += step);
		}
		VanillaVehicleModels.clearTrains();
		final List<Float> boat = vehicleVertices(true, 1, 0.25F);
		require(boat.size() > 300 && boat.size() % 12 == 0, "Boat did not emit complete model quads");
		final List<Float> changed = vehicleVertices(true, 1, 0.25F);
		require(!boat.equals(changed), "Boat paddles did not animate");
		require(boat.equals(vehicleVertices(true, 2, 0.25F)), "Boat animation leaked between trains");
		final List<Float> stopped = vehicleVertices(true, 1, 0);
		require(stopped.equals(vehicleVertices(true, 1, 0)), "Zero rowing step did not hold animation");
		VanillaVehicleModels.removeTrain(1);
		require(boat.equals(vehicleVertices(true, 1, 0.25F)), "Removing a train did not reset its paddles");
		VanillaVehicleModels.clearTrains();
		require(boat.equals(vehicleVertices(true, 2, 0.25F)), "Clearing trains retained paddle state");
		final List<Float> cart = vehicleVertices(false, 3, 0);
		require(cart.size() > 300 && cart.size() % 12 == 0, "Minecart did not emit complete model quads");
		require(cart.equals(vehicleVertices(false, 3, 123)), "Minecart inherited rowing animation");
		VanillaVehicleModels.clearTrains();
		System.out.println("PASS: real boat/minecart model submission, " + boat.size() / 3 + "/" + cart.size() / 3 + " vertices, two-paddle update order, per-train animation, stopped paddles and removal/reload reset");
	}

	private static List<Float> vehicleVertices(boolean boat, long trainId, float step) {
		final RenderSnapshot snapshot;
		final Identifier texture = Identifier.parse("mtr:textures/test/vehicle.png");
		try (RenderBufferSource buffers = RenderBufferSource.begin(Vec3.ZERO)) {
			VanillaVehicleModels.render(boat, trainId, step, new PoseStack(), buffers, texture, LIGHT);
			snapshot = buffers.snapshot();
		}
		final SubmitNodeStorage storage = new SubmitNodeStorage();
		snapshot.submit(new PoseStack(), storage, new CameraRenderState());
		final List<Float> positions = new ArrayList<>();
		storage.drainPhases(phase -> phase.sortInto((node, blending) -> {
			require(node instanceof CustomFeatureRenderer.Submit, "Vehicle did not use geometry submission");
			final CustomFeatureRenderer.Submit geometry = (CustomFeatureRenderer.Submit) node;
			geometry.customGeometryRenderer().render(geometry.pose(), new VertexConsumer() {
				@Override
				public VertexConsumer addVertex(float x, float y, float z) {
					require(Float.isFinite(x) && Float.isFinite(y) && Float.isFinite(z), "Non-finite vehicle position");
					positions.add(x); positions.add(y); positions.add(z); return this;
				}
				@Override
				public VertexConsumer setColor(int r, int g, int b, int a) { require(r == 255 && g == 255 && b == 255 && a == 255, "Vehicle tint changed"); return this; }
				@Override
				public VertexConsumer setColor(int color) { require(color == -1, "Vehicle tint changed"); return this; }
				@Override
				public VertexConsumer setUv(float u, float v) { require(Float.isFinite(u) && Float.isFinite(v), "Invalid vehicle UV"); return this; }
				@Override
				public VertexConsumer setUv1(int u, int v) { require((u | v << 16) == OverlayTexture.NO_OVERLAY, "Vehicle overlay changed"); return this; }
				@Override
				public VertexConsumer setUv2(int u, int v) { require((u | v << 16) == LIGHT, "Vehicle light changed"); return this; }
				@Override
				public VertexConsumer setNormal(float x, float y, float z) { near(x * x + y * y + z * z, 1); return this; }
				@Override
				public VertexConsumer setLineWidth(float width) { return this; }
			});
		}));
		return positions;
	}

	private static void checkBatchLifetime(RenderType type) {
		try (RenderBufferSource source = RenderBufferSource.begin(Vec3.ZERO)) {
			final VertexConsumer first = source.getBuffer(type);
			emit(first, 4);
			source.endBatch();
			expectIllegalState(() -> first.setColor(0));
			require(source.getBuffer(type) != first, "Flushing must create a new batch for subsequent vertices");
			try {
				try (RenderBufferSource ignored = RenderBufferSource.begin(Vec3.ZERO)) {
					throw new IllegalArgumentException("simulated extraction failure");
				}
			} catch (IllegalArgumentException expected) {
				require(RenderBufferSource.current() == source, "Failed nested extraction leaked its context");
			}
		}
		expectIllegalState(RenderBufferSource::current);
	}

	private static void checkSnapshotOwnership(RenderType type) {
		// Warm the snapshot/flush path before measuring just the ownership handoff.
		for (int i = 0; i < 128; i++) {
			try (RenderBufferSource source = RenderBufferSource.begin(Vec3.ZERO)) {
				emit(source.getBuffer(type), 4);
				source.endBatch();
			}
		}
		final var threadBean = ManagementFactory.getThreadMXBean();
		final com.sun.management.ThreadMXBean allocations = threadBean instanceof com.sun.management.ThreadMXBean bean
			&& bean.isThreadAllocatedMemorySupported() && bean.isThreadAllocatedMemoryEnabled() ? bean : null;
		final long threadId = Thread.currentThread().threadId();
		final RenderSnapshot snapshot;
		final int largeBatchCount = 513;
		final long snapshotAllocation;
		try (RenderBufferSource source = RenderBufferSource.begin(Vec3.ZERO)) {
			final VertexConsumer first = source.getBuffer(type);
			emit(first, largeBatchCount);
			final long before = allocations == null ? 0 : allocations.getThreadAllocatedBytes(threadId);
			source.endBatch();
			snapshotAllocation = allocations == null ? 0 : allocations.getThreadAllocatedBytes(threadId) - before;
			require(snapshotAllocation < 4096, "Sealing a batch copied its vertex storage: " + snapshotAllocation + " bytes allocated");
			expectIllegalState(() -> first.addVertex(999, 999, 999));
			expectIllegalState(() -> first.setColor(0));
			expectIllegalState(() -> first.setUv(0, 0));
			expectIllegalState(() -> first.setUv1(0, 0));
			expectIllegalState(() -> first.setUv2(0, 0));
			expectIllegalState(() -> first.setNormal(0, 0, 0));
			expectIllegalState(() -> first.setLineWidth(0));
			emit(source.getBuffer(type), 4);
			snapshot = source.snapshot();
		}
		// Later extraction and repeated submission must never alter an owned batch.
		try (RenderBufferSource source = RenderBufferSource.begin(Vec3.ZERO)) {
			emit(source.getBuffer(type), largeBatchCount + 100);
		}
		for (int i = 0; i < 2; i++) {
			final PoseStack matrices = new PoseStack();
			matrices.translate(10, 20, 30);
			matrices.mulPose(Axis.ZP.rotationDegrees(90));
			final SubmitNodeStorage storage = new SubmitNodeStorage();
			snapshot.submit(matrices, storage, new CameraRenderState());
			final List<Integer> counts = new ArrayList<>();
			storage.drainPhases(phase -> phase.sortInto((node, blending) -> {
				require(node instanceof CustomFeatureRenderer.Submit, "Owned batch lost its geometry node");
				final CustomFeatureRenderer.Submit geometry = (CustomFeatureRenderer.Submit) node;
				final InspectVertices vertices = new InspectVertices();
				geometry.customGeometryRenderer().render(geometry.pose(), vertices);
				counts.add(vertices.count);
			}));
			require(counts.size() == 2 && counts.contains(largeBatchCount) && counts.contains(4),
				"Owned batches changed, merged or replayed unused capacity: " + counts);
		}
		System.out.println("PASS: no-copy vertex ownership, partial capacity, sealed attributes, isolated later batches and repeated delayed submission"
			+ (allocations == null ? " (allocation counting unavailable)" : " (snapshot allocation: " + snapshotAllocation + " bytes)"));
	}

	private static void emit(VertexConsumer vertices, int count) {
		for (int i = 0; i < count; i++) {
			emitOne(vertices, i);
		}
	}

	private static void emitOne(VertexConsumer vertices, int index) {
		vertices.addVertex(index, 2, 3).setColor(COLOR).setUv(0.25F, 0.75F).setOverlay(OVERLAY).setLight(LIGHT).setNormal(1, 0, 0).setLineWidth(2.5F);
	}

	private static final class InspectVertices implements VertexConsumer {
		private int count;

		@Override
		public VertexConsumer addVertex(float x, float y, float z) {
			near(x, 8);
			near(y, 20 + count);
			near(z, 33);
			count++;
			return this;
		}

		@Override
		public VertexConsumer setColor(int r, int g, int b, int a) { return setColor(a << 24 | r << 16 | g << 8 | b); }
		@Override
		public VertexConsumer setColor(int color) { require(color == COLOR, "Vertex alpha/color changed"); return this; }
		@Override
		public VertexConsumer setUv(float u, float v) { near(u, 0.25F); near(v, 0.75F); return this; }
		@Override
		public VertexConsumer setUv1(int u, int v) { require((u | v << 16) == OVERLAY, "Overlay changed"); return this; }
		@Override
		public VertexConsumer setUv2(int u, int v) { require((u | v << 16) == LIGHT, "Light changed"); return this; }
		@Override
		public VertexConsumer setNormal(float x, float y, float z) { near(x, 0); near(y, 1); near(z, 0); return this; }
		@Override
		public VertexConsumer setLineWidth(float width) { near(width, 2.5F); return this; }
	}

	private static void expectIllegalState(Runnable action) {
		try {
			action.run();
			throw new AssertionError("Expected IllegalStateException");
		} catch (IllegalStateException expected) {
		}
	}

	private static void near(float actual, float expected) {
		require(Math.abs(actual - expected) < 0.0001F, "Expected " + expected + ", got " + actual);
	}

	private static void require(boolean condition, String message) {
		if (!condition) {
			throw new AssertionError(message);
		}
	}
}

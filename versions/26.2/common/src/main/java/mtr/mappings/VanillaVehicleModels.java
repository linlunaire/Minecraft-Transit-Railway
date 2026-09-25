package mtr.mappings;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.model.object.cart.MinecartModel;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.client.renderer.entity.state.MinecartRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public final class VanillaVehicleModels {

	private static final BoatModel BOAT = new BoatModel(BoatModel.createBoatModel().bakeRoot());
	private static final MinecartModel MINECART = new MinecartModel(MinecartModel.createBodyLayer().bakeRoot());
	private static final Long2ObjectOpenHashMap<BoatAnimation> BOATS = new Long2ObjectOpenHashMap<>();

	private VanillaVehicleModels() {
	}

	public static void render(boolean boat, long trainId, float rowingStep, PoseStack matrices, RenderBufferSource buffers, Identifier texture, int light) {
		if (boat) {
			final BoatAnimation animation = BOATS.computeIfAbsent(trainId, ignored -> new BoatAnimation());
			BOAT.setupAnim(animation.advance(rowingStep));
			BOAT.renderToBuffer(matrices, buffers.getBuffer(BOAT.renderType(texture)), light, OverlayTexture.NO_OVERLAY, -1);
		} else {
			MINECART.setupAnim(new MinecartRenderState());
			MINECART.renderToBuffer(matrices, buffers.getBuffer(MINECART.renderType(texture)), light, OverlayTexture.NO_OVERLAY, -1);
		}
	}

	public static void removeTrain(long trainId) {
		BOATS.remove(trainId);
	}

	public static void clearTrains() {
		BOATS.clear();
	}

	static final class BoatAnimation {
		private final BoatRenderState state = new BoatRenderState();
		private float progress;

		BoatRenderState advance(float step) {
			// The old FakeBoat.getRowingTime advanced once for each paddle, in this order.
			state.rowingTimeLeft = progress += step;
			state.rowingTimeRight = progress += step;
			return state;
		}
	}
}

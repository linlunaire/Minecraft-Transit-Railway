package mtr;

import com.mojang.blaze3d.vertex.PoseStack;
import mtr.client.CustomResources;
import mtr.client.ICustomResources;
import mtr.render.RenderDrivingOverlay;
import mtr.render.RenderTrains;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.phys.Vec3;

public class MTRFabricClient implements ClientModInitializer, ICustomResources {

	@Override
	public void onInitializeClient() {
		MTRClient.init();
		HudElementRegistry.addLast(Identifier.parse("mtr:driving_overlay"), (graphics, tickDelta) -> RenderDrivingOverlay.render(graphics));
		ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new CustomResourcesWrapper());
	}

	private static class CustomResourcesWrapper implements SimpleSynchronousResourceReloadListener {

		@Override
		public Identifier getFabricId() {
			return Identifier.fromNamespaceAndPath(MTR.MOD_ID, CUSTOM_RESOURCES_ID);
		}

		@Override
		public void onResourceManagerReload(ResourceManager resourceManager) {
			CustomResources.reload(resourceManager);
		}
	}
}

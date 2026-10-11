package mtr.mappings;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

/** Material factories moved off RenderType; preserve the old culling choices. */
public abstract class RenderLayerMapper {

	protected static RenderType beaconBeam(Identifier texture, boolean translucent) {
		return RenderTypes.beaconBeam(texture, translucent);
	}

	protected static RenderType entityCutout(Identifier texture) {
		return RenderTypes.entityCutoutCull(texture);
	}

	protected static RenderType entityTranslucentCull(Identifier texture) {
		// The old entityTranslucentCull used MAIN_TARGET. The renamed vanilla factory
		// now uses ITEM_ENTITY_TARGET, which changes the composition of train windows.
		return RenderType.create("mtr_entity_translucent_cull", RenderSetup.builder(RenderPipelines.ENTITY_TRANSLUCENT_CULL)
			.withTexture("Sampler0", texture).setOutputTarget(OutputTarget.MAIN_TARGET)
			.useLightmap().useOverlay().affectsCrumbling().sortOnUpload()
			.setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup());
	}
}

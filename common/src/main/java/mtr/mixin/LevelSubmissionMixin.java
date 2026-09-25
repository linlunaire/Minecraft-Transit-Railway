package mtr.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import mtr.mappings.LevelRenderStateExtension;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelSubmissionMixin {

	@Inject(method = "submitFeatures", at = @At("TAIL"))
	private void mtr$submit(LevelRenderState state, SubmitNodeCollector collector, boolean renderBlockOutline, CallbackInfo callback) {
		((LevelRenderStateExtension) state).mtr$snapshot().submit(new PoseStack(), collector, state.cameraRenderState);
	}
}

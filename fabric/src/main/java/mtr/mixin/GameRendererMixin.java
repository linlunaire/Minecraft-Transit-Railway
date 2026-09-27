package mtr.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import mtr.screen.ResourcePackCreatorScreen;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

	@Inject(method = "renderLevel", at = @At(value = "RETURN"))
	private void renderLevel(DeltaTracker deltaTracker, CallbackInfo ci) {
		ResourcePackCreatorScreen.render(new PoseStack());
	}
}

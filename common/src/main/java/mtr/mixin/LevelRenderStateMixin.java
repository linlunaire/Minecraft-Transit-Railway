package mtr.mixin;

import mtr.mappings.LevelRenderStateExtension;
import mtr.mappings.RenderSnapshot;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderState.class)
public abstract class LevelRenderStateMixin implements LevelRenderStateExtension {
	@Unique private RenderSnapshot mtr$snapshot = RenderSnapshot.EMPTY;

	@Override public RenderSnapshot mtr$snapshot() { return mtr$snapshot; }
	@Override public void mtr$setSnapshot(RenderSnapshot snapshot) { mtr$snapshot = snapshot; }

	@Inject(method = "reset", at = @At("TAIL"))
	private void mtr$resetSnapshot(CallbackInfo callback) { mtr$snapshot = RenderSnapshot.EMPTY; }
}

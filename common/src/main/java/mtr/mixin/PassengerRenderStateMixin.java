package mtr.mixin;

import mtr.mappings.PassengerRenderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AvatarRenderState.class)
public abstract class PassengerRenderStateMixin implements PassengerRenderState {
	@Unique private boolean mtr$ridingTrain;

	@Override public boolean mtr$isRidingTrain() { return mtr$ridingTrain; }
	@Override public void mtr$setRidingTrain(boolean riding) { mtr$ridingTrain = riding; }
}

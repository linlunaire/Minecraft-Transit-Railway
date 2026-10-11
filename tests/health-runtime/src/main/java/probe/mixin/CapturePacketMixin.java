package probe.mixin;

import probe.HealthRuntimeProbe;

import mtr.RegistryClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Captures only the transport boundary; the artifact's sender/receiver/codecs stay real. */
@Mixin(value = RegistryClient.class, remap = false)
public abstract class CapturePacketMixin {
    @Inject(method = "sendToServer", at = @At("HEAD"), cancellable = true, remap = false)
    private static void capture(ResourceLocation id, FriendlyByteBuf packet, CallbackInfo callback) {
        if (HealthRuntimeProbe.capture(id, packet)) callback.cancel();
    }
}

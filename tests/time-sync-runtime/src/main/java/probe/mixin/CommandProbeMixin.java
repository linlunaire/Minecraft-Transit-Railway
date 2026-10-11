package probe.mixin;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import probe.TimeSyncRuntimeProbe;

/** Reject commands only while this isolated fixture exercises the MTR toggle. */
@Mixin(Commands.class)
public abstract class CommandProbeMixin {
    @Inject(method = "performPrefixedCommand", at = @At("HEAD"), cancellable = true)
    private void rejectPluginCommands(CommandSourceStack source, String command, CallbackInfo callback) {
        if (TimeSyncRuntimeProbe.captureCommand(command)) callback.cancel();
    }
}

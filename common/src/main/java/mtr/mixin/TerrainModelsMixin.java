package mtr.mixin;

import mtr.mappings.TerrainRenderLayers;
import net.minecraft.client.renderer.block.BlockStateModelSet;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Map;

@Mixin(BlockStateModelSet.class)
public abstract class TerrainModelsMixin {

	@ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true, ordinal = 0)
	private static Map<BlockState, BlockStateModel> mtr$terrainLayers(Map<BlockState, BlockStateModel> models) {
		return TerrainRenderLayers.apply(models);
	}
}

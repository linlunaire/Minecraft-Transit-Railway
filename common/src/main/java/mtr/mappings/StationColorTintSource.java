package mtr.mappings;

import mtr.MTRClient;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public final class StationColorTintSource implements BlockTintSource {

	public static final StationColorTintSource INSTANCE = new StationColorTintSource();

	private StationColorTintSource() {
	}

	@Override
	public int color(BlockState state) { return MTRClient.getStationColor(null) | 0xFF000000; }

	@Override
	public int colorInWorld(BlockState state, BlockAndTintGetter world, BlockPos pos) { return MTRClient.getStationColor(pos) | 0xFF000000; }
}

package mtr.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.SlabType

open class BlockStationColorGlass(settings: Properties) : BlockStationColor(settings) {
    public override fun skipRendering(state: BlockState, neighborState: BlockState, direction: Direction): Boolean {
        return neighborState.getBlock() is BlockStationColorGlass || (neighborState.getBlock() is BlockStationColorGlassSlab && IBlock.getStatePropertySafe(
            neighborState,
            SlabBlock.TYPE
        ) === SlabType.DOUBLE) || super.skipRendering(state, neighborState, direction)
    }

    public override fun getShadeBrightness(state: BlockState, world: BlockGetter, pos: BlockPos): Float {
        return 1f
    }

    public override fun propagatesSkylightDown(state: BlockState): Boolean {
        return true
    }
}

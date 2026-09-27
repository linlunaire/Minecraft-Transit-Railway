package mtr.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.SlabType

open class BlockStationColorGlassSlab(settings: Properties) : BlockStationColorSlab(settings) {
    public override fun skipRendering(state: BlockState, neighborState: BlockState, direction: Direction): Boolean {
        if (neighborState.getBlock() is BlockStationColorGlassSlab) {
            val slabType: SlabType? = IBlock.getStatePropertySafe(state, TYPE)
            val neighborSlabType: SlabType? = IBlock.getStatePropertySafe(neighborState, TYPE)
            if (direction.getAxis().isHorizontal()) {
                return slabType == neighborSlabType
            } else {
                if (direction == Direction.UP) {
                    return slabType != SlabType.BOTTOM && neighborSlabType != SlabType.TOP
                } else {
                    return slabType != SlabType.TOP && neighborSlabType != SlabType.BOTTOM
                }
            }
        } else if (neighborState.getBlock() is BlockStationColorGlass) {
            return IBlock.getStatePropertySafe(state, TYPE) === SlabType.DOUBLE
        } else {
            return super.skipRendering(state, neighborState, direction)
        }
    }

    public override fun getShadeBrightness(state: BlockState, world: BlockGetter, pos: BlockPos): Float {
        return 1f
    }

    public override fun propagatesSkylightDown(state: BlockState): Boolean {
        return true
    }
}

package mtr.block

import mtr.mappings.BlockMapper
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

open class BlockClockPole(settings: Properties) : BlockMapper(settings) {
    public override fun getShape(
        state: BlockState,
        blockGetter: BlockGetter,
        pos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        return box(7.5, 0.0, 7.5, 8.5, 16.0, 8.5)
    }
}

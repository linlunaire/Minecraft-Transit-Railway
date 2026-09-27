package mtr.block

import mtr.mappings.Text
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

open class BlockPIDSPole(settings: Properties) : BlockPoleCheckBase(settings) {
    public override fun getShape(
        state: BlockState,
        blockGetter: BlockGetter,
        pos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        return IBlock.getVoxelShapeByDirection(
            7.5,
            0.0,
            12.5,
            8.5,
            16.0,
            13.5,
            IBlock.getStatePropertySafe(state, FACING)
        )
    }

    protected override fun isBlock(block: Block?): Boolean {
        return block is BlockPIDSBaseHorizontal || block is BlockPIDSPole
    }

    protected override fun getTooltipBlockText(): Component? {
        return Text.translatable("block.mtr.pids_1")
    }

    protected override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING)
    }
}

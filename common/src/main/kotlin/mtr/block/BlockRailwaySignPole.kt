package mtr.block

import mtr.mappings.Text
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

open class BlockRailwaySignPole(settings: Properties) : BlockPoleCheckBase(settings) {
    public override fun getShape(
        state: BlockState,
        blockGetter: BlockGetter,
        pos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        val facing: Direction = IBlock.getStatePropertySafe(state, FACING)
        when (IBlock.getStatePropertySafe(state, TYPE)) {
            0 -> return IBlock.getVoxelShapeByDirection(14.0, 0.0, 7.0, 15.25, 16.0, 9.0, facing)
            1 -> return IBlock.getVoxelShapeByDirection(10.0, 0.0, 7.0, 11.25, 16.0, 9.0, facing)
            2 -> return IBlock.getVoxelShapeByDirection(6.0, 0.0, 7.0, 7.25, 16.0, 9.0, facing)
            3 -> return IBlock.getVoxelShapeByDirection(2.0, 0.0, 7.0, 3.25, 16.0, 9.0, facing)
            else -> return Shapes.block()
        }
    }

    protected override fun placeWithState(stateBelow: BlockState): BlockState {
        var type: Int
        val block = stateBelow.getBlock()
        if (block is BlockRailwaySign) {
            type = (block.length + (if (block.isOdd) 2 else 0)) % 4
        } else {
            type = IBlock.getStatePropertySafe(stateBelow, TYPE)
        }
        return super.placeWithState(stateBelow).setValue(TYPE, type)
    }

    protected override fun isBlock(block: Block?): Boolean {
        return (block is BlockRailwaySign && block.length > 0) || block is BlockRailwaySignPole
    }

    protected override fun getTooltipBlockText(): Component? {
        return Text.translatable("block.mtr.railway_sign")
    }

    protected override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, TYPE)
    }

    companion object {
        @JvmField
        val TYPE: IntegerProperty = IntegerProperty.create("type", 0, 3)
    }
}

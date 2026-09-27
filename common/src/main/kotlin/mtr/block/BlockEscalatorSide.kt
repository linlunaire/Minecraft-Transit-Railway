package mtr.block

import mtr.block.IBlock.EnumSide
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.ScheduledTickAccess
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

open class BlockEscalatorSide : BlockEscalatorBase() {
    override fun updateShape(
        state: BlockState,
        world: LevelReader,
        scheduledTicks: ScheduledTickAccess,
        pos: BlockPos,
        direction: Direction,
        posFrom: BlockPos,
        newState: BlockState,
        random: RandomSource
    ): BlockState {
        if (direction == Direction.DOWN && world.getBlockState(pos.below()).getBlock() !is BlockEscalatorStep) {
            return Blocks.AIR.defaultBlockState()
        } else {
            return super.updateShape(state, world, scheduledTicks, pos, direction, posFrom, newState, random)
        }
    }

    override fun getCollisionShape(
        state: BlockState,
        world: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        return Shapes.join(
            getShape(state, world, pos, context),
            super.getCollisionShape(state, world, pos, context),
            BooleanOp.AND
        )
    }

    public override fun getVisualShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        return Shapes.empty()
    }

    override fun playerWillDestroy(world: Level, pos: BlockPos, state: BlockState, player: Player): BlockState {
        var offsetPos = pos.below()
        if (IBlock.getStatePropertySafe(state, SIDE) === EnumSide.RIGHT) {
            offsetPos = offsetPos.relative(IBlock.getSideDirection(state))
        }
        IBlock.onBreakCreative(world, player, offsetPos)
        return super.playerWillDestroy(world, pos, state, player)
    }

    public override fun getShape(
        state: BlockState,
        world: BlockGetter,
        pos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        val orientation = getOrientation(world, pos, state)
        val isBottom = orientation == EnumEscalatorOrientation.LANDING_BOTTOM
        val isTop = orientation == EnumEscalatorOrientation.LANDING_TOP
        val isRight = IBlock.getStatePropertySafe(state, SIDE) === EnumSide.RIGHT
        return IBlock.getVoxelShapeByDirection(
            (if (isRight) 12 else 0).toDouble(),
            0.0,
            (if (isTop) 8 else 0).toDouble(),
            (if (isRight) 16 else 4).toDouble(),
            16.0,
            (if (isBottom) 8 else 16).toDouble(),
            IBlock.getStatePropertySafe(state, FACING)
        )
    }

    protected override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, ORIENTATION, SIDE)
    }
}

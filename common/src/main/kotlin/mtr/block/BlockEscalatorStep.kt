package mtr.block

import mtr.block.IBlock.EnumSide
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.InsideBlockEffectApplier
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.ScheduledTickAccess
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

open class BlockEscalatorStep : BlockEscalatorBase() {
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
        if (direction == Direction.UP && world.getBlockState(pos.above()).getBlock() !is BlockEscalatorSide) {
            return Blocks.AIR.defaultBlockState()
        } else {
            return super.updateShape(state, world, scheduledTicks, pos, direction, posFrom, newState, random)
        }
    }

    override fun playerWillDestroy(world: Level, pos: BlockPos, state: BlockState, player: Player): BlockState {
        if (IBlock.getStatePropertySafe(state, SIDE) === EnumSide.RIGHT) {
            IBlock.onBreakCreative(world, player, pos.relative(IBlock.getSideDirection(state)))
        }
        return super.playerWillDestroy(world, pos, state, player)
    }

    override fun getCollisionShape(
        state: BlockState,
        world: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        val orientation: EnumEscalatorOrientation? = IBlock.getStatePropertySafe(state, ORIENTATION)
        if (orientation == EnumEscalatorOrientation.FLAT || orientation == EnumEscalatorOrientation.TRANSITION_BOTTOM) {
            return box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0)
        } else {
            return Shapes.join(
                box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0),
                super.getCollisionShape(state, world, pos, context),
                BooleanOp.AND
            )
        }
    }

    public override fun entityInside(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        entity: Entity,
        effects: InsideBlockEffectApplier,
        isPrecise: Boolean
    ) {
        val facing: Direction = IBlock.getStatePropertySafe(state, FACING)
        val direction: Boolean = IBlock.getStatePropertySafe(state, DIRECTION)
        val speed = 0.1f

        if (IBlock.getStatePropertySafe(state, STATUS)) {
            when (facing) {
                Direction.NORTH -> entity.push(0.0, 0.0, (if (direction) -speed else speed).toDouble())
                Direction.EAST -> entity.push((if (direction) speed else -speed).toDouble(), 0.0, 0.0)
                Direction.SOUTH -> entity.push(0.0, 0.0, (if (direction) speed else -speed).toDouble())
                Direction.WEST -> entity.push((if (direction) -speed else speed).toDouble(), 0.0, 0.0)
                else -> {}
            }
        }
    }

    public override fun useWithoutItem(
        state: BlockState,
        world: Level,
        pos: BlockPos,
        player: Player,
        blockHitResult: BlockHitResult
    ): InteractionResult {
        return IBlock.checkHoldingBrush(world, player, java.lang.Runnable {
            val direction: Boolean = IBlock.getStatePropertySafe(state, DIRECTION)
            val running: Boolean = IBlock.getStatePropertySafe(state, STATUS)
            val blockFacing: Direction = IBlock.getStatePropertySafe(state, FACING)
            var newDirection: Boolean
            var newRunning: Boolean

            if (direction && running) {
                // FORWARD to BACKWARD
                newDirection = false
                newRunning = true
            } else if (!direction && running) {
                // BACKWARD to STOP
                newDirection = false
                newRunning = false
            } else {
                // STOP to FORWARD
                newDirection = true
                newRunning = true
            }

            update(world, pos, blockFacing, newDirection, newRunning)
            update(world, pos, blockFacing.getOpposite(), newDirection, newRunning)

            val sidePos = pos.relative(IBlock.getSideDirection(state))
            if (isStep(world, sidePos)) {
                val block = world.getBlockState(sidePos).getBlock() as BlockEscalatorStep
                block.update(world, sidePos, blockFacing, newDirection, newRunning)
                block.update(world, sidePos, blockFacing.getOpposite(), newDirection, newRunning)
            }
        })
    }

    override fun softenLanding(): Boolean {
        return true
    }

    protected override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, DIRECTION, ORIENTATION, SIDE, STATUS)
    }

    private fun update(world: Level, pos: BlockPos, offset: Direction, direction: Boolean, running: Boolean) {
        world.setBlockAndUpdate(
            pos, world.getBlockState(pos).setValue(DIRECTION, direction).setValue(
                STATUS, running
            )
        )
        val offsetPos = pos.relative(offset)

        if (isStep(world, offsetPos)) {
            update(world, offsetPos, offset, direction, running)
        }
        if (isStep(world, offsetPos.above())) {
            update(world, offsetPos.above(), offset, direction, running)
        }
        if (isStep(world, offsetPos.below())) {
            update(world, offsetPos.below(), offset, direction, running)
        }
    }

    private fun isStep(world: Level, pos: BlockPos): Boolean {
        val block = world.getBlockState(pos).getBlock()
        return block is BlockEscalatorStep
    }

    companion object {
        @JvmField
        val DIRECTION: BooleanProperty = BooleanProperty.create("direction")
        @JvmField
        val STATUS: BooleanProperty = BooleanProperty.create("status")
    }
}

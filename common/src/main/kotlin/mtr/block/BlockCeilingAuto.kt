package mtr.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.ScheduledTickAccess
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty

open class BlockCeilingAuto(settings: Properties) : BlockCeiling(settings) {
    override fun getStateForPlacement(ctx: BlockPlaceContext): BlockState? {
        val facing = ctx.getHorizontalDirection().getAxis() === Direction.Axis.X
        return defaultBlockState().setValue(FACING, facing)
            .setValue(LIGHT, hasLight(facing, ctx.getClickedPos()))
    }

    public override fun updateShape(
        state: BlockState,
        world: LevelReader,
        scheduledTicks: ScheduledTickAccess,
        pos: BlockPos,
        direction: Direction,
        posFrom: BlockPos,
        newState: BlockState,
        random: RandomSource
    ): BlockState {
        return state.setValue(LIGHT, hasLight(IBlock.getStatePropertySafe(state, FACING), pos))
    }

    override fun randomTick(state: BlockState, world: ServerLevel, pos: BlockPos) {
        val light: Boolean = hasLight(IBlock.getStatePropertySafe(state, FACING), pos)
        if (IBlock.getStatePropertySafe(state, LIGHT) != light) {
            world.setBlockAndUpdate(pos, state.setValue(LIGHT, light))
        }
    }

    public override fun isRandomlyTicking(blockState: BlockState): Boolean {
        return true
    }

    protected override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, LIGHT)
    }

    companion object {
        @JvmField
        val LIGHT: BooleanProperty = BooleanProperty.create("light")

        private fun hasLight(facing: Boolean, pos: BlockPos): Boolean {
            if (facing) {
                return pos.getZ() % 3 == 0
            } else {
                return pos.getX() % 3 == 0
            }
        }
    }
}

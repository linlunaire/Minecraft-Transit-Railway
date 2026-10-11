package mtr.item

import mtr.Blocks
import mtr.CreativeModeTabs
import mtr.block.BlockEscalatorBase
import mtr.block.IBlock
import mtr.block.IBlock.EnumSide
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.context.UseOnContext

open class ItemEscalator() : ItemWithCreativeTabBase(CreativeModeTabs.ESCALATORS_LIFTS), IBlock {
    override fun useOn(context: UseOnContext): InteractionResult {
        if (ItemPSDAPGBase.Companion.blocksNotReplaceable(context, 2, 2, null)) {
            return InteractionResult.FAIL
        }

        val world = context.getLevel()
        var playerFacing = context.getHorizontalDirection()
        var pos1 = context.getClickedPos().relative(context.getClickedFace())
        var pos2 = pos1.relative(playerFacing.getClockWise())

        val frontState = world.getBlockState(pos1.relative(playerFacing))
        if (frontState.getBlock() is BlockEscalatorBase) {
            if (IBlock.getStatePropertySafe(frontState, BlockEscalatorBase.FACING) === playerFacing.getOpposite()) {
                playerFacing = playerFacing.getOpposite()
                val pos3 = pos1
                pos1 = pos2
                pos2 = pos3
            }
        }

        val stepState =
            Blocks.ESCALATOR_STEP.get().defaultBlockState().setValue(BlockEscalatorBase.FACING, playerFacing)
        world.setBlockAndUpdate(pos1, stepState.setValue(IBlock.SIDE, EnumSide.LEFT))
        world.setBlockAndUpdate(pos2, stepState.setValue(IBlock.SIDE, EnumSide.RIGHT))

        val sideState =
            Blocks.ESCALATOR_SIDE.get().defaultBlockState().setValue(BlockEscalatorBase.FACING, playerFacing)
        world.setBlockAndUpdate(pos1.above(), sideState.setValue(IBlock.SIDE, EnumSide.LEFT))
        world.setBlockAndUpdate(pos2.above(), sideState.setValue(IBlock.SIDE, EnumSide.RIGHT))

        context.getItemInHand().shrink(1)
        return InteractionResult.SUCCESS
    }
}

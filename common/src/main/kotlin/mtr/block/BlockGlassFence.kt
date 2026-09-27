package mtr.block

import mtr.mappings.BlockTooltip
import mtr.mappings.Text
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.item.Item.TooltipContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

open class BlockGlassFence() : BlockDirectionalDoubleBlockBase(
    Properties.of().mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(2f).noOcclusion()
), BlockTooltip {
    public override fun getShape(
        state: BlockState,
        blockGetter: BlockGetter,
        pos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        val facing: Direction = IBlock.getStatePropertySafe(state, FACING)
        if (IBlock.getStatePropertySafe(state, HALF) === DoubleBlockHalf.UPPER) {
            return IBlock.getVoxelShapeByDirection(0.0, 0.0, 0.0, 16.0, 3.0, 3.0, facing)
        } else {
            return IBlock.getVoxelShapeByDirection(0.0, 0.0, 0.0, 16.0, 16.0, 3.0, facing)
        }
    }

    public override fun getCollisionShape(
        state: BlockState,
        world: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        val facing: Direction = IBlock.getStatePropertySafe(state, FACING)
        return Shapes.or(
            getShape(state, world, pos, context),
            IBlock.getVoxelShapeByDirection(0.0, 0.0, 0.0, 16.0, 8.0, 3.0, facing)
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

    override fun appendHoverText(
        stack: ItemStack,
        tooltipContext: TooltipContext?,
        tooltip: MutableList<Component?>,
        tooltipFlag: TooltipFlag?
    ) {
        tooltip.add(
            Text.translatable("tooltip." + stack.getItem().getDescriptionId())
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
        )
    }

    protected override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, HALF, NUMBER)
    }

    protected override fun getAdditionalState(pos: BlockPos, facing: Direction?): BlockState {
        return defaultBlockState().setValue(NUMBER, getNumber(pos, facing))
    }

    companion object {
        @JvmField
        val NUMBER: IntegerProperty = IntegerProperty.create("number", 1, 7)

        private fun getNumber(pos: BlockPos, facing: Direction?): Int {
            val x = (pos.getX() % 7 + 7) % 7
            val z = (pos.getZ() % 7 + 7) % 7
            if (facing == Direction.NORTH || facing == Direction.EAST) {
                return ((x + z) % 7) + 1
            } else {
                return ((-x - z) % 7) + 7
            }
        }
    }
}

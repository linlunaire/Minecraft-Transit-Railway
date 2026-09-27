package mtr.block

import mtr.mappings.BlockDirectionalMapper
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

open class BlockRubbishBin(settings: Properties) : BlockDirectionalMapper(settings) {
    init {
        registerDefaultState(defaultBlockState().setValue(FILLED, 0))
    }

    override fun getStateForPlacement(ctx: BlockPlaceContext): BlockState? {
        return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection())
    }

    public override fun getShape(
        state: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        return IBlock.getVoxelShapeByDirection(2.0, 0.0, 0.0, 14.0, 16.0, 4.5, state.getValue(FACING))
    }

    public override fun useWithoutItem(
        state: BlockState,
        world: Level,
        pos: BlockPos,
        player: Player,
        blockHitResult: BlockHitResult
    ): InteractionResult {
        return IBlock.checkHoldingBrush(world, player, java.lang.Runnable {
            world.setBlockAndUpdate(
                pos, state.setValue(
                    FILLED, 0
                )
            )
        }, java.lang.Runnable {
            val currentLevel: Int = IBlock.getStatePropertySafe(state, FILLED)
            if (!player.getMainHandItem().isEmpty() && currentLevel < MAX_LEVEL) {
                world.setBlockAndUpdate(pos, state.setValue(FILLED, currentLevel + 1))
                if (!player.isCreative()) {
                    player.getMainHandItem().shrink(1)
                }
            }
        })
    }

    override fun randomTick(state: BlockState, world: ServerLevel, pos: BlockPos) {
        val newLevel: Int = IBlock.getStatePropertySafe(state, FILLED) - 1
        if (newLevel >= 0) {
            world.setBlockAndUpdate(pos, state.setValue(FILLED, newLevel))
        }
    }

    public override fun isRandomlyTicking(blockState: BlockState): Boolean {
        return true
    }

    protected override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, FILLED)
    }

    companion object {
        const val MAX_LEVEL: Int = 15
        @JvmField
        val FILLED: IntegerProperty = IntegerProperty.create("filled", 0, MAX_LEVEL)
    }
}

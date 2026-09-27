package mtr.block

import mtr.SoundEvents
import mtr.data.TicketSystem
import mtr.data.TicketSystem.EnumTicketBarrierOpen
import mtr.mappings.BlockDirectionalMapper
import mtr.mappings.Utilities
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.InsideBlockEffectApplier
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

open class BlockTicketBarrier(private val isEntrance: Boolean) : BlockDirectionalMapper(
    Properties.of().mapColor(
        MapColor.COLOR_GRAY
    ).requiresCorrectToolForDrops().strength(2f).lightLevel(java.util.function.ToIntFunction { state -> 5 })
        .noOcclusion()
) {
    public override fun entityInside(
        state: BlockState,
        world: Level,
        pos: BlockPos,
        entity: Entity,
        effects: InsideBlockEffectApplier,
        isPrecise: Boolean
    ) {
        if (!world.isClientSide() && entity is Player) {
            val facing: Direction = IBlock.getStatePropertySafe(state, FACING)
            val playerPosRotated = entity.position().subtract(pos.getX() + 0.5, 0.0, pos.getZ() + 0.5)
                .yRot(Math.toRadians(facing.toYRot().toDouble()).toFloat())
            val open: EnumTicketBarrierOpen = IBlock.getStatePropertySafe(state, OPEN)

            if (open.isOpen() && playerPosRotated.z > 0) {
                world.setBlockAndUpdate(pos, state.setValue(OPEN, EnumTicketBarrierOpen.CLOSED))
            } else if (!open.isOpen() && playerPosRotated.z < 0) {
                val newOpen = TicketSystem.passThrough(
                    world,
                    pos,
                    entity,
                    isEntrance,
                    !isEntrance,
                    SoundEvents.TICKET_BARRIER,
                    SoundEvents.TICKET_BARRIER_CONCESSIONARY,
                    SoundEvents.TICKET_BARRIER,
                    SoundEvents.TICKET_BARRIER_CONCESSIONARY,
                    null,
                    false
                )
                world.setBlockAndUpdate(pos, state.setValue(OPEN, newOpen))
                if (newOpen != EnumTicketBarrierOpen.CLOSED && !world.getBlockTicks().hasScheduledTick(pos, this)) {
                    Utilities.scheduleBlockTick(world, pos, this, 40)
                }
            }
        }
    }

    override fun tick(state: BlockState, world: ServerLevel, pos: BlockPos) {
        world.setBlockAndUpdate(pos, state.setValue(OPEN, EnumTicketBarrierOpen.CLOSED))
    }

    override fun getStateForPlacement(ctx: BlockPlaceContext): BlockState? {
        return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection())
            .setValue(OPEN, EnumTicketBarrierOpen.CLOSED)
    }

    public override fun getShape(
        state: BlockState,
        blockGetter: BlockGetter,
        pos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        val facing: Direction = IBlock.getStatePropertySafe(state, FACING)
        return IBlock.getVoxelShapeByDirection(12.0, 0.0, 0.0, 16.0, 15.0, 16.0, facing)
    }

    public override fun getCollisionShape(
        state: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        val facing: Direction = IBlock.getStatePropertySafe(state, FACING)
        val open: EnumTicketBarrierOpen = IBlock.getStatePropertySafe(state, OPEN)
        val base = IBlock.getVoxelShapeByDirection(15.0, 0.0, 0.0, 16.0, 24.0, 16.0, facing)
        return if (open.isOpen()) base else Shapes.or(
            IBlock.getVoxelShapeByDirection(
                0.0,
                0.0,
                7.0,
                16.0,
                24.0,
                9.0,
                facing
            ), base
        )
    }

    protected override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, OPEN)
    }

    companion object {
        @JvmField
        val OPEN: EnumProperty<EnumTicketBarrierOpen> = EnumProperty.create("open", EnumTicketBarrierOpen::class.java)
    }
}

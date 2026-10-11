package mtr.block

import mtr.data.IGui
import mtr.mappings.BlockEntityClientSerializableMapper
import mtr.mappings.EntityBlockMapper
import mtr.mappings.Text
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

abstract class BlockPSDAPGDoorBase : BlockPSDAPGBase(), EntityBlockMapper {

    init {
        // Keep TEMP for old saves, but newly placed doors already use the entity renderer.
        registerDefaultState(defaultBlockState().setValue(TEMP, false))
    }

    override fun <T : BlockEntity> getTicker(world: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
        if (world.isClientSide || !state.getValue(TEMP) || !type.isValid(state)) return null
        // Migrate legacy static models outside NBT serialization. Once TEMP is false,
        // the chunk replaces this ticker with null; normal doors have no per-tick work.
        return BlockEntityTicker { level, pos, currentState, entity ->
            if (entity is TileEntityPSDAPGDoorBase && currentState.`is`(this) && currentState.getValue(TEMP)) {
                level.setBlock(pos, currentState.setValue(TEMP, false), Block.UPDATE_CLIENTS)
            }
        }
    }

    override fun updateShape(
        state: BlockState,
        world: net.minecraft.world.level.LevelReader,
        scheduledTicks: net.minecraft.world.level.ScheduledTickAccess,
        pos: BlockPos,
        direction: Direction,
        posFrom: BlockPos,
        newState: BlockState,
        random: net.minecraft.util.RandomSource,
    ): BlockState {
        if (IBlock.getSideDirection(state) == direction && !newState.`is`(this)) {
            return Blocks.AIR.defaultBlockState()
        }
        val superState = super.updateShape(state, world, scheduledTicks, pos, direction, posFrom, newState, random)
        if (superState.block == Blocks.AIR) return superState
        val end = world.getBlockState(pos.relative(IBlock.getSideDirection(state).opposite)).block is BlockPSDAPGGlassEndBase
        return superState.setValue(END, end)
    }

    override fun playerWillDestroy(world: Level, pos: BlockPos, state: BlockState, player: Player): BlockState {
        var offsetPos = pos
        if (IBlock.getStatePropertySafe(state, HALF) == DoubleBlockHalf.UPPER) offsetPos = offsetPos.below()
        if (IBlock.getStatePropertySafe(state, SIDE) == IBlock.EnumSide.RIGHT) {
            offsetPos = offsetPos.relative(IBlock.getSideDirection(state))
        }
        IBlock.onBreakCreative(world, player, offsetPos)
        return super.playerWillDestroy(world, pos, state, player)
    }

    override fun tick(state: BlockState, world: ServerLevel, pos: BlockPos) {
        val entity = world.getBlockEntity(pos)
        if (IBlock.getStatePropertySafe(state, UNLOCKED) && entity is TileEntityPSDAPGDoorBase) entity.setOpen(0)
    }

    public override fun useWithoutItem(state: BlockState, world: Level, pos: BlockPos, player: Player, blockHitResult: BlockHitResult): InteractionResult =
        IBlock.checkHoldingBrush(world, player) {
            val unlocked = IBlock.getStatePropertySafe(state, UNLOCKED)
            for (y in -1..1) {
                val scanState = world.getBlockState(pos.above(y))
                if (state.`is`(scanState.block)) lockDoor(world, pos.above(y), scanState, !unlocked)
            }
            mtr.mappings.PlayerUtilities.displayClientMessage(
                player,
                Text.translatable(if (!unlocked) "gui.mtr.psd_apg_door_unlocked" else "gui.mtr.psd_apg_door_locked"),
                true,
            )
        }

    public override fun getCollisionShape(state: BlockState, world: BlockGetter, pos: BlockPos, collisionContext: CollisionContext): VoxelShape {
        val entity = world.getBlockEntity(pos)
        return if (entity is TileEntityPSDAPGDoorBase && entity.isOpen()) Shapes.empty()
        else super.getCollisionShape(state, world, pos, collisionContext)
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(END, FACING, HALF, SIDE, TEMP, UNLOCKED)
    }

    abstract class TileEntityPSDAPGDoorBase(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) :
        BlockEntityClientSerializableMapper(type, pos, state), IGui {

        private var open: Int = 0
        private var openClient: Float = 0F

        override fun readCompoundTag(compoundTag: CompoundTag) {
            open = mtr.mappings.CompoundTagMapper.getInt(compoundTag, KEY_OPEN)
        }

        override fun writeCompoundTag(compoundTag: CompoundTag) {
            // NeoForge snapshots call this before setBlock. Mutating the world here
            // re-enters snapshot serialization and overflows the client/server stack.
            compoundTag.putInt(KEY_OPEN, open)
        }

        open fun getRenderBoundingBox(): AABB = AABB(
            Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
            Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
        )

        open fun setOpen(open: Int) {
            if (open != this.open) {
                this.open = open
                setChanged()
                syncData()
            }
        }

        open fun getOpen(lastFrameDuration: Float): Float {
            val change = lastFrameDuration * 0.95F
            if (Math.abs(open - IGui.SMALL_OFFSET_16 * 2 - openClient) < change) {
                openClient = open - IGui.SMALL_OFFSET_16 * 2
            } else if (openClient < open) {
                openClient += change
            } else {
                openClient -= change
            }
            return openClient / 32
        }

        open fun isOpen(): Boolean = open > 0

        private companion object {
            const val KEY_OPEN = "open"
        }
    }

    companion object {
        const val MAX_OPEN_VALUE = 32
        @JvmField val END: BooleanProperty = BooleanProperty.create("end")
        @JvmField val UNLOCKED: BooleanProperty = BooleanProperty.create("unlocked")
        @JvmField val TEMP: BooleanProperty = BooleanProperty.create("temp")

        private fun lockDoor(world: Level, pos: BlockPos, state: BlockState, unlocked: Boolean) {
            val facing = IBlock.getStatePropertySafe(state, FACING)
            val leftPos = pos.relative(facing.counterClockWise)
            val rightPos = pos.relative(facing.clockWise)
            val leftState = world.getBlockState(leftPos)
            val rightState = world.getBlockState(rightPos)
            if (leftState.`is`(state.block)) world.setBlockAndUpdate(leftPos, leftState.setValue(UNLOCKED, unlocked))
            if (rightState.`is`(state.block)) world.setBlockAndUpdate(rightPos, rightState.setValue(UNLOCKED, unlocked))
            world.setBlockAndUpdate(pos, state.setValue(UNLOCKED, unlocked))
        }
    }
}

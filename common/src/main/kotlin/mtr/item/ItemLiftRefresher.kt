package mtr.item

import mtr.block.BlockLiftTrack
import mtr.block.BlockLiftTrackFloor
import mtr.block.BlockLiftTrackFloor.TileEntityLiftTrackFloor
import mtr.block.IBlock
import mtr.data.LiftServer
import mtr.data.RailwayData
import mtr.packet.PacketTrainDataGuiServer
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.entity.BlockEntity
import java.util.ArrayList
import java.util.function.Function

open class ItemLiftRefresher() : mtr.item.ItemWithCreativeTabBase(
    mtr.CreativeModeTabs.ESCALATORS_LIFTS,
    Function { properties -> properties.stacksTo(1) }) {
    override fun useOn(context: UseOnContext): InteractionResult {
        if (!context.getLevel().isClientSide()) {
            return mtr.item.ItemLiftRefresher.Companion.refreshLift(
                context.getLevel(),
                context.getClickedPos(),
                context.getPlayer(),
                0,
                0,
                2,
                2,
                false,
                null
            )
        } else {
            return super.useOn(context)
        }
    }

    companion object {
        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun refreshLift(
            world: net.minecraft.world.level.Level,
            clickedPos: BlockPos,
            offsetX: kotlin.Int,
            offsetZ: kotlin.Int,
            width: kotlin.Int,
            depth: kotlin.Int,
            isDoubleSided: kotlin.Boolean,
            forceFacing: net.minecraft.core.Direction?
        ) {
            mtr.item.ItemLiftRefresher.Companion.refreshLift(
                world,
                clickedPos,
                null,
                offsetX,
                offsetZ,
                width,
                depth,
                isDoubleSided,
                forceFacing
            )
        }

        private fun refreshLift(
            world: net.minecraft.world.level.Level,
            clickedPos: BlockPos,
            player: net.minecraft.world.entity.player.Player?,
            offsetX: kotlin.Int,
            offsetZ: kotlin.Int,
            width: kotlin.Int,
            depth: kotlin.Int,
            isDoubleSided: kotlin.Boolean,
            forceFacing: net.minecraft.core.Direction?
        ): InteractionResult {
            val railwayData: RailwayData? = RailwayData.getInstance(world)

            if (world.getBlockState(clickedPos).getBlock() is BlockLiftTrack && railwayData != null) {
                val floors: MutableList<BlockPos> = ArrayList()
                val liftsToModify: MutableSet<LiftServer> = HashSet()
                var i = 0
                var scanForFloors = false
                var firstFloor: BlockPos? = null
                var facing: net.minecraft.core.Direction? = null

                railwayData.lifts.removeIf({ lift -> lift.isInvalidLift(world) })

                while (true) {
                    val checkPos: BlockPos = clickedPos.below(i)
                    val checkBlock = world.getBlockState(checkPos).getBlock()

                    if (checkBlock !is BlockLiftTrack) {
                        if (scanForFloors) {
                            break
                        } else {
                            scanForFloors = true
                        }
                    }

                    if (scanForFloors && checkBlock is BlockLiftTrackFloor) {
                        val blockEntity: BlockEntity? = world.getBlockEntity(checkPos)
                        if (blockEntity is TileEntityLiftTrackFloor) {
                            floors.add(checkPos)
                            if (firstFloor == null || facing == null) {
                                firstFloor = checkPos
                                facing =
                                    IBlock.getStatePropertySafe(world, checkPos, HorizontalDirectionalBlock.FACING)
                            }
                        }
                        railwayData.lifts.forEach({ lift ->
                            if (lift.hasFloor(checkPos)) {
                                liftsToModify.add(lift)
                            }
                        })
                    }

                    i += (if (scanForFloors) -1 else 1)
                }

                var result: InteractionResult
                if (floors.isEmpty() || firstFloor == null || facing == null) {
                    if (player != null) {
                        mtr.mappings.PlayerUtilities.displayClientMessage(
                            player,
                            mtr.mappings.Text.translatable("gui.mtr.no_lift_tracks_floor_found"),
                            true
                        )
                    }
                    result = InteractionResult.FAIL
                } else {
                    var hasSetFloors = false
                    var liftId: kotlin.Long = 0
                    for (lift in liftsToModify) {
                        if (hasSetFloors) {
                            railwayData.lifts.remove(lift)
                        } else {
                            liftId = mtr.item.ItemLiftRefresher.Companion.setLiftData(
                                lift,
                                floors,
                                offsetX,
                                offsetZ,
                                width,
                                depth,
                                isDoubleSided
                            )
                            hasSetFloors = true
                        }
                    }

                    if (!hasSetFloors) {
                        val newLift: LiftServer =
                            LiftServer(firstFloor, if (forceFacing == null) facing else forceFacing)
                        liftId = mtr.item.ItemLiftRefresher.Companion.setLiftData(
                            newLift,
                            floors,
                            offsetX,
                            offsetZ,
                            width,
                            depth,
                            isDoubleSided
                        )
                        railwayData.lifts.add(newLift)
                    }

                    if (player != null) {
                        PacketTrainDataGuiServer.openLiftCustomizationScreenS2C(player as ServerPlayer, liftId)
                    }
                    result = InteractionResult.SUCCESS
                }

                railwayData.dataCache.sync()
                return result
            } else {
                if (player != null) {
                    mtr.mappings.PlayerUtilities.displayClientMessage(
                        player,
                        mtr.mappings.Text.translatable("gui.mtr.lift_track_required"),
                        true
                    )
                }
                return InteractionResult.FAIL
            }
        }

        private fun setLiftData(
            lift: LiftServer,
            floors: MutableList<BlockPos>,
            offsetX: kotlin.Int,
            offsetZ: kotlin.Int,
            width: kotlin.Int,
            depth: kotlin.Int,
            isDoubleSided: kotlin.Boolean
        ): kotlin.Long {
            lift.setFloors(floors)
            lift.liftOffsetX = offsetX
            lift.liftOffsetZ = offsetZ
            lift.liftWidth = width
            lift.liftDepth = depth
            lift.isDoubleSided = isDoubleSided
            return lift.id
        }
    }
}

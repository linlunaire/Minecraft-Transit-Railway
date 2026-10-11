package mtr.item

import mtr.block.BlockNode
import mtr.block.BlockNode.BlockContinuousMovementNode
import mtr.data.*
import mtr.mappings.PlayerUtilities
import mtr.mappings.Text
import mtr.packet.PacketTrainDataGuiServer
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.component.TooltipDisplay
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

open class ItemRailModifier : ItemNodeModifierBase {
    private val isOneWay: Boolean
    private val railType: RailType?

    constructor() : super(true, true, true, false) {
        isOneWay = false
        railType = null
    }

    constructor(
        forNonContinuousMovementNode: Boolean,
        forContinuousMovementNode: Boolean,
        forAirplaneNode: Boolean,
        isOneWay: Boolean,
        railType: RailType?
    ) : super(forNonContinuousMovementNode, forContinuousMovementNode, forAirplaneNode, true) {
        this.isOneWay = isOneWay
        this.railType = railType
    }

    public override fun appendHoverText(
        itemStack: ItemStack,
        tooltipContext: TooltipContext,
        tooltipDisplay: TooltipDisplay,
        tooltip: java.util.function.Consumer<Component>,
        tooltipFlag: TooltipFlag
    ) {
        if (isConnector && railType != null && railType.canAccelerate) {
            tooltip.accept(
                Text.translatable("tooltip.mtr.rail_speed_limit", railType.speedLimit)
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
            )
        }
        super.appendHoverText(itemStack, tooltipContext, tooltipDisplay, tooltip, tooltipFlag)
    }

    protected override fun onConnect(
        world: Level,
        stack: ItemStack,
        transportMode: TransportMode,
        stateStart: BlockState,
        stateEnd: BlockState,
        posStart: BlockPos,
        posEnd: BlockPos,
        facingStart: RailAngle,
        facingEnd: RailAngle,
        player: Player?,
        railwayData: RailwayData
    ) {
        if (railType!!.hasSavedRail && (railwayData.hasSavedRail(posStart) || railwayData.hasSavedRail(posEnd))) {
            if (player != null) {
                PlayerUtilities.displayClientMessage(
                    player,
                    Text.translatable("gui.mtr.platform_or_siding_exists"),
                    true
                )
            }
        } else {
            var isValidContinuousMovement: Boolean
            var newRailType: RailType?
            if (transportMode.continuousMovement) {
                val blockStart = stateStart.getBlock()
                val blockEnd = stateEnd.getBlock()

                if (blockStart is BlockContinuousMovementNode && blockEnd is BlockContinuousMovementNode) {
                    if (blockStart.isStation && blockEnd.isStation) {
                        isValidContinuousMovement = true
                        newRailType = if (railType.hasSavedRail) railType else RailType.CABLE_CAR_STATION
                    } else {
                        val differenceX = posEnd.getX() - posStart.getX()
                        val differenceZ = posEnd.getZ() - posStart.getZ()
                        isValidContinuousMovement = !railType.hasSavedRail && facingStart.isParallel(facingEnd)
                                && ((facingStart == RailAngle.N || facingStart == RailAngle.S) && differenceX == 0 || (facingStart == RailAngle.E || facingStart == RailAngle.W) && differenceZ == 0 || (facingStart == RailAngle.NE || facingStart == RailAngle.SW) && differenceX == -differenceZ || (facingStart == RailAngle.SE || facingStart == RailAngle.NW) && differenceX == differenceZ)
                        newRailType = RailType.CABLE_CAR
                    }
                } else {
                    isValidContinuousMovement = false
                    newRailType = railType
                }
            } else {
                isValidContinuousMovement = true
                newRailType = railType
            }

            val rail1 = Rail(
                posStart,
                facingStart,
                posEnd,
                facingEnd,
                if (isOneWay) RailType.NONE else newRailType,
                transportMode
            )
            val rail2 = Rail(posEnd, facingEnd, posStart, facingStart, newRailType, transportMode)

            val goodRadius = rail1.goodRadius() && rail2.goodRadius()
            val isValid = rail1.isValid() && rail2.isValid()

            if (goodRadius && isValid && isValidContinuousMovement) {
                railwayData.addRail(player, transportMode, posStart, posEnd, rail1, false)
                val newId = railwayData.addRail(player, transportMode, posEnd, posStart, rail2, true)
                world.setBlockAndUpdate(posStart, stateStart.setValue(BlockNode.IS_CONNECTED, true))
                world.setBlockAndUpdate(posEnd, stateEnd.setValue(BlockNode.IS_CONNECTED, true))
                PacketTrainDataGuiServer.createRailS2C(world, transportMode, posStart, posEnd, rail1, rail2, newId)
            } else if (player != null) {
                PlayerUtilities.displayClientMessage(
                    player,
                    Text.translatable(if (isValidContinuousMovement) if (goodRadius) "gui.mtr.invalid_orientation" else "gui.mtr.radius_too_small" else "gui.mtr.cable_car_invalid_orientation"),
                    true
                )
            }
        }
    }

    protected override fun onRemove(
        world: Level,
        posStart: BlockPos,
        posEnd: BlockPos,
        player: Player?,
        railwayData: RailwayData
    ) {
        railwayData.removeRailConnection(player, posStart, posEnd)
        PacketTrainDataGuiServer.removeRailConnectionS2C(world, posStart, posEnd)
    }
}

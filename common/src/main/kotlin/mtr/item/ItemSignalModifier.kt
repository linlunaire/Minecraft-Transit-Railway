package mtr.item

import mtr.data.RailAngle
import mtr.data.RailwayData
import mtr.data.TransportMode
import mtr.mappings.PlayerUtilities
import mtr.mappings.Text
import mtr.packet.PacketTrainDataGuiServer
import mtr.path.PathData
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

open class ItemSignalModifier(isConnector: Boolean, private val color: DyeColor?) :
    ItemNodeModifierBase(true, false, true, isConnector) {
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
        if (railwayData.containsRail(posStart, posEnd)) {
            PacketTrainDataGuiServer.createSignalS2C(
                world,
                railwayData.addSignal(player, color, posStart, posEnd),
                color,
                PathData.getRailProduct(posStart, posEnd)
            )
        } else if (player != null) {
            PlayerUtilities.displayClientMessage(player, Text.translatable("gui.mtr.rail_not_found"), true)
        }
    }

    protected override fun onRemove(
        world: Level,
        posStart: BlockPos,
        posEnd: BlockPos,
        player: Player?,
        railwayData: RailwayData
    ) {
        PacketTrainDataGuiServer.removeSignalS2C(
            world,
            railwayData.removeSignal(player, color, posStart, posEnd),
            color,
            PathData.getRailProduct(posStart, posEnd)
        )
    }
}

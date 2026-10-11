package mtr.item

import java.util.function.Function

import mtr.CreativeModeTabs
import mtr.data.RailwayData
import mtr.data.TransportMode
import mtr.packet.PacketTrainDataGuiServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level

open class ItemDashboard(private val transportMode: TransportMode) : ItemWithCreativeTabBase(
    CreativeModeTabs.CORE, Function { properties -> properties.stacksTo(1) }) {
    override fun use(world: Level, player: Player, interactionHand: InteractionHand): InteractionResult {
        if (!world.isClientSide()) {
            val railwayData = RailwayData.getInstance(world)
            if (railwayData != null) {
                PacketTrainDataGuiServer.openDashboardScreenS2C(
                    player as ServerPlayer,
                    transportMode,
                    railwayData.getUseTimeAndWindSync()
                )
            }
        }
        return super.use(world, player, interactionHand)
    }
}

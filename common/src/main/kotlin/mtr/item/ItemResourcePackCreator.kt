package mtr.item

import mtr.CreativeModeTabs
import mtr.packet.PacketTrainDataGuiServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level

open class ItemResourcePackCreator() : ItemWithCreativeTabBase(CreativeModeTabs.CORE) {
    override fun use(world: Level, user: Player, hand: InteractionHand): InteractionResult {
        if (!world.isClientSide()) {
            PacketTrainDataGuiServer.openResourcePackCreatorScreenS2C(user as ServerPlayer)
        }
        return super.use(world, user, hand)
    }
}

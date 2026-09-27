package mtr.block

import mtr.SoundEvents
import mtr.data.TicketSystem
import mtr.mappings.PlayerUtilities
import mtr.mappings.Text
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

open class BlockTicketProcessorEnquiry() : BlockTicketProcessor(false, false, false) {
    override fun useWithoutItem(
        state: BlockState,
        world: Level,
        pos: BlockPos,
        player: Player,
        blockHitResult: BlockHitResult
    ): InteractionResult {
        if (!world.isClientSide()) {
            val playerScore = TicketSystem.getPlayerScore(world, player, TicketSystem.BALANCE_OBJECTIVE)!!.get()
            PlayerUtilities.displayClientMessage(
                player,
                Text.translatable("gui.mtr.balance", playerScore.toString()),
                true
            )
            world.playSound(null, pos, SoundEvents.TICKET_PROCESSOR_ENTRY, SoundSource.BLOCKS, 1f, 1f)
        }
        return InteractionResult.SUCCESS
    }
}

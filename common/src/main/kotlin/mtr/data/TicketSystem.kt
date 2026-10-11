package mtr.data

import mtr.mappings.Text
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.util.StringRepresentable
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.scores.ScoreAccess
import net.minecraft.world.scores.ScoreHolder
import net.minecraft.world.scores.criteria.ObjectiveCriteria
import java.util.function.BooleanSupplier
import java.util.function.IntConsumer

open class TicketSystem {
    /** Runs once before a recorded journey is charged, on the calling game thread.
     * [fare] already includes the concession calculation. Implementations may
     * credit [balanceScore]; exceptions propagate without clearing the entry record.
     * Never retain player, station or score references beyond this call.
     */
    fun interface FareAdjustment {
        fun beforeCharge(station: Station, player: Player, balanceScore: ScoreAccess, fare: Int)
    }

    companion object {
        const val BALANCE_OBJECTIVE = "mtr_balance"
        private const val ENTRY_ZONE_OBJECTIVE = "mtr_entry_zone"
        private val fareAdjustments = LinkedHashMap<String, FareAdjustment>()
        @Volatile private var fareAdjustmentSnapshot = emptyArray<FareAdjustment>()

        /** Register at mod initialization with a namespaced ID. Replacing an ID
         * keeps its order and prevents duplicate callbacks during repeated setup.
         */
        @JvmStatic
        fun registerFareAdjustment(id: String, adjustment: FareAdjustment) {
            require(id.matches(Regex("[a-z0-9_.-]+:[a-z0-9_./-]+"))) { "Expected a namespaced fare-adjustment ID" }
            synchronized(fareAdjustments) {
                fareAdjustments[id] = adjustment
                fareAdjustmentSnapshot = fareAdjustments.values.toTypedArray()
            }
        }

        /** Remove a registration; an already running payment uses its snapshot. */
        @JvmStatic
        fun unregisterFareAdjustment(id: String): Boolean = synchronized(fareAdjustments) {
            val removed = fareAdjustments.remove(id) != null
            if (removed) fareAdjustmentSnapshot = fareAdjustments.values.toTypedArray()
            removed
        }

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun passThrough(world: Level?, pos: BlockPos?, player: Player?, isEntrance: Boolean, isExit: Boolean,
                             entrySound: SoundEvent?, entrySoundConcessionary: SoundEvent?, exitSound: SoundEvent?,
                             exitSoundConcessionary: SoundEvent?, failSound: SoundEvent?, remindIfNoRecord: Boolean): EnumTicketBarrierOpen {
            val railwayData = RailwayData.getInstance(world) ?: return EnumTicketBarrierOpen.CLOSED
            val station = RailwayData.getStation(railwayData.stations, railwayData.dataCache, pos) ?: return EnumTicketBarrierOpen.CLOSED
            addObjectivesIfMissing(world)
            val balanceScore = getPlayerScore(world, player, BALANCE_OBJECTIVE)
            val entryZoneScore = getPlayerScore(world, player, ENTRY_ZONE_OBJECTIVE)
            val account = object : TicketTransaction.Account {
                override var balance: Int
                    get() = balanceScore!!.get()
                    set(value) { balanceScore!!.set(value) }
                override var entryZone: Int
                    get() = entryZoneScore!!.get()
                    set(value) { entryZoneScore!!.set(value) }
            }
            val transaction = TicketTransaction(account, BooleanSupplier { isConcessionary(player) }, TicketTransaction.Feedback { notice, fare ->
                val message = when (notice) {
                    TicketTransaction.Notice.ALREADY_ENTERED -> Text.translatable("gui.mtr.already_entered")
                    TicketTransaction.Notice.ALREADY_EXITED -> Text.translatable("gui.mtr.already_exited")
                    TicketTransaction.Notice.INSUFFICIENT_BALANCE -> Text.translatable("gui.mtr.insufficient_balance", account.balance)
                    TicketTransaction.Notice.ENTERED -> Text.translatable("gui.mtr.enter_barrier", String.format("%s (%s)", station.name!!.replace('|', ' '), station.zone), account.balance)
                    TicketTransaction.Notice.EXITED -> Text.translatable("gui.mtr.exit_barrier", String.format("%s (%s)", station.name!!.replace('|', ' '), station.zone), fare, account.balance)
                }
                mtr.mappings.PlayerUtilities.displayClientMessage(player, message, true)
            }, IntConsumer { fare ->
                for (adjustment in fareAdjustmentSnapshot) {
                    adjustment.beforeCharge(station, player!!, balanceScore!!, fare)
                }
            })
            val outcome = transaction.pass(station.zone, isEntrance, isExit, remindIfNoRecord)
            if (outcome.allowed) {
                world!!.playSound(null, javaReference(pos), javaReference(if (isConcessionary(player)) {
                    if (outcome.entering) entrySoundConcessionary else exitSoundConcessionary
                } else {
                    if (outcome.entering) entrySound else exitSound
                }), SoundSource.BLOCKS, 1F, 1F)
            } else if (failSound != null) {
                world!!.playSound(null, javaReference(pos), failSound, SoundSource.BLOCKS, 1F, 1F)
            }
            return if (outcome.allowed) {
                if (isConcessionary(player)) EnumTicketBarrierOpen.OPEN_CONCESSIONARY else EnumTicketBarrierOpen.OPEN
            } else EnumTicketBarrierOpen.CLOSED
        }

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun addObjectivesIfMissing(world: Level?) {
            try {
                val scoreboard = world!!.scoreboard
                if (scoreboard.getObjective(BALANCE_OBJECTIVE) == null) {
                    scoreboard.addObjective(BALANCE_OBJECTIVE, ObjectiveCriteria.DUMMY, Text.literal("Balance"), ObjectiveCriteria.RenderType.INTEGER, false, null)
                }
            } catch (ignored: Exception) {
            }
            try {
                val scoreboard = world!!.scoreboard
                if (scoreboard.getObjective(ENTRY_ZONE_OBJECTIVE) == null) {
                    scoreboard.addObjective(ENTRY_ZONE_OBJECTIVE, ObjectiveCriteria.DUMMY, Text.literal("Entry Zone"), ObjectiveCriteria.RenderType.INTEGER, false, null)
                }
            } catch (ignored: Exception) {
            }
        }

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun getPlayerScore(world: Level?, player: Player?, objectiveName: String?): ScoreAccess? =
            world!!.scoreboard.getOrCreatePlayerScore(ScoreHolder.fromGameProfile(player!!.gameProfile), javaReference(world.scoreboard.getObjective(objectiveName)))

        private fun isConcessionary(player: Player?): Boolean = player!!.isCreative

        // Java callers previously forwarded null to these annotated game APIs.
        // Let the actual callee decide its behavior, without an earlier Kotlin check.
        @Suppress("UNCHECKED_CAST")
        private fun <T> javaReference(value: T?): T = value as T
    }

    enum class EnumTicketBarrierOpen(private val serializedName: String) : StringRepresentable {
        CLOSED("closed"), OPEN("open"), OPEN_CONCESSIONARY("open_concessionary");

        override fun getSerializedName(): String = serializedName
        open fun isOpen(): Boolean = this != CLOSED
    }
}

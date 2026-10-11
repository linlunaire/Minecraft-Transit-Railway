package mtr.data

import java.util.function.BooleanSupplier
import java.util.function.IntConsumer

/** Ticket rules independent of Minecraft, with ordered live account updates. */
internal class TicketTransaction @JvmOverloads constructor(
    private val account: Account,
    private val concessionary: BooleanSupplier,
    private val feedback: Feedback,
    private val beforeCharge: IntConsumer = NO_ADJUSTMENT
) {
    interface Account {
        var balance: Int
        var entryZone: Int
    }

    fun interface Feedback {
        // Balance is deliberately not snapshotted: the adapter reads it when
        // formatting the message, after the preceding account updates.
        fun show(notice: Notice, fare: Int)
    }

    enum class Notice { ALREADY_ENTERED, ALREADY_EXITED, INSUFFICIENT_BALANCE, ENTERED, EXITED }

    enum class Outcome(@JvmField val entering: Boolean, @JvmField val allowed: Boolean) {
        ENTRY_ALLOWED(true, true), ENTRY_DENIED(true, false),
        EXIT_ALLOWED(false, true), EXIT_DENIED(false, false)
    }

    /** Feedback failures propagate; account changes already performed stay applied. */
    fun pass(zone: Int, entrance: Boolean, exit: Boolean, remind: Boolean): Outcome {
        val entering = if (entrance && exit) account.entryZone == 0 else entrance
        return if (entering) {
            if (enter(zone, remind)) Outcome.ENTRY_ALLOWED else Outcome.ENTRY_DENIED
        } else {
            if (leave(zone, remind)) Outcome.EXIT_ALLOWED else Outcome.EXIT_DENIED
        }
    }

    private fun enter(zone: Int, remind: Boolean): Boolean {
        val entryZone = account.entryZone
        if (entryZone != 0) {
            if (remind) {
                feedback.show(Notice.ALREADY_ENTERED, 0)
                return false
            } else {
                account.entryZone = 0
                account.balance = account.balance - EVASION_FINE
            }
        }
        return if (account.balance >= 0) {
            account.entryZone = encodeZone(zone)
            feedback.show(Notice.ENTERED, 0)
            true
        } else {
            feedback.show(Notice.INSUFFICIENT_BALANCE, 0)
            false
        }
    }

    private fun leave(zone: Int, remind: Boolean): Boolean {
        val entryZone = account.entryZone
        val fare = BASE_FARE + ZONE_FARE * Math.abs(zone - decodeZone(entryZone))
        val finalFare = if (entryZone != 0) {
            // Preserve legacy float rounding and query eligibility only when used.
            if (concessionary.asBoolean) Math.ceil((fare / 2F).toDouble()).toInt() else fare
        } else EVASION_FINE
        if (entryZone == 0 && remind) {
            feedback.show(Notice.ALREADY_EXITED, 0)
            return false
        }
        // Addons may credit the live account before payment. No hook on evasion;
        // failures propagate before MTR changes the entry record or debits fare.
        if (entryZone != 0) beforeCharge.accept(finalFare)
        account.entryZone = 0
        account.balance = account.balance - finalFare
        feedback.show(Notice.EXITED, finalFare)
        return true
    }

    private fun encodeZone(zone: Int): Int = if (zone >= 0) zone + 1 else zone
    private fun decodeZone(zone: Int): Int = if (zone > 0) zone - 1 else zone

    private companion object {
        val NO_ADJUSTMENT = IntConsumer { }
        const val BASE_FARE = 2
        const val ZONE_FARE = 1
        const val EVASION_FINE = 500
    }
}

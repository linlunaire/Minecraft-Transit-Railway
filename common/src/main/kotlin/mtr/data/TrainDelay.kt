package mtr.data

open class TrainDelay {
	private var currentDelayCounter = 0
	private var delayTicks = 0
	private var lastDelayTime = 0L

	open fun delaying() {
		val millis = System.currentTimeMillis()
		if (millis - lastDelayTime > CURRENT_DELAY_RESET_MILLIS) {
			currentDelayCounter = 0
		}
		if (millis - lastDelayTime > TOTAL_DELAY_RESET_MILLIS) {
			delayTicks = currentDelayCounter
		}
		currentDelayCounter++
		delayTicks = maxOf(currentDelayCounter, delayTicks)
		lastDelayTime = millis
	}

	open fun getDelayTicks(): Int = delayTicks

	open fun getLastDelayTime(): Long = lastDelayTime

	open fun isExpired(): Boolean =
		System.currentTimeMillis() - lastDelayTime > TOTAL_DELAY_RESET_MILLIS + CURRENT_DELAY_RESET_MILLIS

	private companion object {
		const val CURRENT_DELAY_RESET_MILLIS = 1000
		const val TOTAL_DELAY_RESET_MILLIS = 300000
	}
}

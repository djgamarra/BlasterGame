package com.djgamarra.blaster.utils

/**
 * Util class that holds a counter from 1 to n-1.
 *
 * @property[maxTicks] Max count.
 */
class TickCounter(private val maxTicks: Int) {
    /**
     * Current count.
     */
    var tickCounter = 1

    /**
     * Counts 1.
     *
     * @return True if max count reached, False otherwise.
     */
    fun tick(): Boolean {
        tickCounter = (tickCounter + 1) % maxTicks

        return tickCounter == 0
    }

    /**
     * Resets teh counter to 1.
     */
    fun reset() {
        tickCounter = 1
    }
}

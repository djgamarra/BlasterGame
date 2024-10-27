package com.djgamarra.blaster.utils

class TickCounter(private val maxTicks: Int) {
    private var tickCounter = 1

    fun tick(): Boolean {
        tickCounter = (tickCounter + 1) % maxTicks

        return tickCounter == 0
    }

    fun reset() {
        tickCounter = 1
    }
}

package com.djgamarra.blaster.data

class TickCounter(private val maxTicks: Int) {
    private var tickCounter = 1

    fun tick(): Boolean {
        tickCounter = (tickCounter + 1) % maxTicks

        return tickCounter == 0
    }
}

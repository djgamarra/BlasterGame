package com.djgamarra.blaster.data

import com.djgamarra.blaster.Utils
import com.djgamarra.blaster.views.ViewUtils
import kotlin.math.max

class RenderMetrics {
    private var currentTick = System.nanoTime()
    private var oldTick = currentTick - 1
    private var renderStart = currentTick
    private var tickCount = 0

    var fpsCount = 0L

    val sleepTimeToNewTick
        get() = max(
            0,
            ((Utils.SECOND_IN_NS / ViewUtils.MAX_FPS) - (System.nanoTime() - renderStart)) / Utils.MS_IN_NS
        )

    fun tickStart() {
        renderStart = System.nanoTime()
    }

    fun tickEnd() {
        oldTick = currentTick
        currentTick = System.nanoTime()
        tickCount = (tickCount + 1) % 20

        if (tickCount == 0) {
            syncFpsCounter()
        }
    }

    private fun syncFpsCounter() {
        fpsCount = Utils.SECOND_IN_NS / (currentTick - oldTick)
    }
}

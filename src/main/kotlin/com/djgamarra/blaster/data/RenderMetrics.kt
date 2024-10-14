package com.djgamarra.blaster.data

import com.djgamarra.blaster.Utils
import com.djgamarra.blaster.views.ViewUtils
import kotlin.math.max

object RenderMetrics {
    private var frameStart: Long
    private var frameEnd: Long

    private var frameCount = 0

    var currentFps = ViewUtils.MAX_FPS
        private set

    val sleepTime: Long
        get() = max(
            0,
            ((Utils.SECOND_IN_NS / ViewUtils.MAX_FPS) - (System.nanoTime() - frameStart)) / Utils.MS_IN_NS
        )

    init {
        val now = System.nanoTime()

        frameStart = now - 1
        frameEnd = now
    }

    fun measureFrame(block: (it: RenderMetrics) -> Unit) {
        frameStart = System.nanoTime()

        block(this)

        frameEnd = System.nanoTime()
        frameCount = (frameCount + 1) % ViewUtils.MAX_FPS_COUNT

        if (frameCount == 0) {
            syncFps()
        }
    }

    private fun syncFps() {
        currentFps = Utils.SECOND_IN_NS / (frameEnd - frameStart)
    }
}

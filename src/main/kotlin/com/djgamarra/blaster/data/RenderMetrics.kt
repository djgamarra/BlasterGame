package com.djgamarra.blaster.data

import com.djgamarra.blaster.utils.MathUtils
import com.djgamarra.blaster.utils.TickCounter
import java.lang.Thread.sleep
import kotlin.math.max

object RenderMetrics {
    private var frameStart: Long
    private var frameEnd: Long

    private val frameTickCounter = TickCounter(20)

    var currentFps = Settings.fps
        private set

    private val sleepTime: Long
        get() = max(
            0,
            ((MathUtils.SECOND_IN_NS / Settings.fps) - (System.nanoTime() - frameStart)) / MathUtils.MS_IN_NS
        )

    init {
        val now = System.nanoTime()

        frameStart = now - 1
        frameEnd = now
    }

    fun startFrame(block: RenderMetrics.(ctx: RenderContext) -> Unit) {
        frameStart = System.nanoTime()
        this.block(RenderContext(frameStart))
        sleep(sleepTime)
        frameEnd = System.nanoTime()

        if (frameTickCounter.tick()) {
            syncFps()
        }
    }

    private fun syncFps() {
        currentFps = MathUtils.SECOND_IN_NS / (frameEnd - frameStart)
    }
}

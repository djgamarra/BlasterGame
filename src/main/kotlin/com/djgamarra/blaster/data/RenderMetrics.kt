package com.djgamarra.blaster.data

import com.djgamarra.blaster.data.RenderMetrics.currentFps
import com.djgamarra.blaster.utils.MathUtils
import com.djgamarra.blaster.utils.TickCounter
import java.lang.Thread.sleep
import kotlin.math.max

/**
 * This singleton holds information about rendering. It measures render time
 * and calcs how much time we need to sleep before rendering the next frame.
 */
object RenderMetrics {
    /**
     * Nano time just before rendering.
     */
    private var frameStart: Long

    /**
     * Nano time just after rendering and sleeping. It is used to debug the
     * real render time (fps).
     */
    private var frameEnd: Long

    /**
     * Helper to know when to update the fps, so that we do not calc it in
     * every render but every 20 renders.
     */
    private val frameTickCounter = TickCounter(20)

    /**
     * Real fps.
     */
    var currentFps = Settings.fps
        private set

    /**
     * Getter to know how much time to sleep to get the desired fps.
     */
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

    /**
     * This function wraps each render frame, calcs metrics and sleeps the
     * necessary time.
     */
    fun startFrame(block: RenderMetrics.(ctx: RenderContext) -> Unit) {
        frameStart = System.nanoTime()
        this.block(RenderContext(frameStart))
        sleep(sleepTime)
        frameEnd = System.nanoTime()

        if (frameTickCounter.tick()) {
            syncFps()
        }
    }

    /**
     * Sets the value for [currentFps].
     */
    private fun syncFps() {
        currentFps = MathUtils.SECOND_IN_NS / (frameEnd - frameStart)
    }
}

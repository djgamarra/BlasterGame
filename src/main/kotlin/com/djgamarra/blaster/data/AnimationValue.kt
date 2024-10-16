package com.djgamarra.blaster.data

import com.djgamarra.blaster.Utils
import kotlin.math.max
import kotlin.math.min

class AnimationValue(
    private val initialValue: Int,
    private val finalValue: Int,
    private val duration: Int,
) {
    private var startTime = System.nanoTime()
    private val reversed = initialValue < finalValue
    private var cachedValue: Int = initialValue
    var enabled = false
        private set

    fun valueFor(ctx: RenderContext): Int {
        if (enabled) {
            val elapsedTime = min(
                ((ctx.renderTime - startTime) / Utils.MS_IN_NS).toInt(), duration
            ).toFloat()
            val newValue = (initialValue + (elapsedTime / duration) * (finalValue - initialValue)).toInt()

            cachedValue = if (reversed) {
                min(finalValue, newValue)
            } else {
                max(finalValue, newValue)
            }

            if (cachedValue == finalValue) {
                enabled = false
            }
        }

        return cachedValue
    }

    fun start() {
        synchronized(this) {
            enabled = true
            startTime = System.nanoTime()
            cachedValue = initialValue
        }
    }
}

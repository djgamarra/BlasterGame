package com.djgamarra.blaster.utils

import com.djgamarra.blaster.data.RenderContext
import kotlin.math.max
import kotlin.math.min

class AnimationValue(
    private var initialValue: Int,
    private var finalValue: Int,
    private val duration: Int,
    private val onAnimationEnded: (() -> Unit)? = null
) {
    private var startTime = System.nanoTime()
    private val reversed = initialValue < finalValue
    private var cachedValue: Int = initialValue
    var enabled = false
        private set

    fun valueFor(ctx: RenderContext): Int {
        if (enabled) {
            val elapsedTime = min(
                ((ctx.renderTime - startTime) / MathUtils.MS_IN_NS).toInt(), duration
            ).toFloat()
            val newValue = (initialValue + (elapsedTime / duration) * (finalValue - initialValue)).toInt()

            cachedValue = if (reversed) {
                min(finalValue, newValue)
            } else {
                max(finalValue, newValue)
            }

            if (cachedValue == finalValue) {
                enabled = false
                onAnimationEnded?.invoke()
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

    fun start(initialValue: Int, finalValue: Int) {
        synchronized(this) {
            this.initialValue = initialValue
            this.finalValue = finalValue

            enabled = true
            startTime = System.nanoTime()
            cachedValue = initialValue
        }
    }
}

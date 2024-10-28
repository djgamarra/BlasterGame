package com.djgamarra.blaster.utils

import com.djgamarra.blaster.data.RenderContext
import kotlin.math.max
import kotlin.math.min

class AnimationValue(
    initialValueN: Number,
    finalValueN: Number,
    durationN: Number,

    private val easeFunction: EaseFunction,
    private val onAnimationEnded: (AnimationValue.() -> Unit)? = null
) {
    private var initialValue = initialValueN.toDouble()
    private var finalValue = finalValueN.toDouble()
    private val duration = durationN.toDouble()

    private var startTime = System.nanoTime()
    private var delta = finalValue - initialValue
    private var reversed = initialValue < finalValue
    private var cachedValue = initialValue
    var enabled = false

    private fun getValue(ctx: RenderContext?): Double {
        if (enabled && ctx != null) {
            val progress = min(((ctx.renderTime - startTime) / MathUtils.MS_IN_NS).toDouble(), duration) / duration
            val newValue = easeFunction.getValue(initialValue, delta, progress)

            cachedValue = if (reversed) {
                min(finalValue, newValue)
            } else {
                max(finalValue, newValue)
            }

            if (cachedValue == finalValue) {
                stopAnimation()
                this.onAnimationEnded?.invoke(this)
            }
        }

        return cachedValue
    }

    fun getIntValue(ctx: RenderContext?): Int = getValue(ctx).toInt()

    fun start() = synchronized(this) {
        startAnimation()
    }

    fun startReverse() = synchronized(this) {
        val tempInitialValue = initialValue
        initialValue = finalValue
        finalValue = tempInitialValue

        reversed = !reversed
        delta = -delta

        startAnimation()
    }

    fun startFromCurrent() = synchronized(this) {
        stopAnimation()

        val delta = finalValue - initialValue

        initialValue = cachedValue
        finalValue = cachedValue + delta

        startAnimation()
    }

    private fun startAnimation() {
        enabled = true
        startTime = System.nanoTime()
        cachedValue = initialValue
    }

    private fun stopAnimation() {
        enabled = false
    }
}

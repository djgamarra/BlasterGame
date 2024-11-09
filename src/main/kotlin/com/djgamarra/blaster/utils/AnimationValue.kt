package com.djgamarra.blaster.utils

import com.djgamarra.blaster.data.RenderContext
import kotlin.math.max
import kotlin.math.min

/**
 * Util class which holds time based animations. Initial, final
 * value and duration can be any type of number, but we will use
 * them as Doubles.
 *
 * @param[initialValueN] Initial value.
 * @param[finalValueN] Final value.
 * @param[durationN] Duration of the animation.
 *
 * @property[easeFunction] Easing function to use.
 * @property[onAnimationEnded] Animation end callback.
 */
class AnimationValue(
    initialValueN: Number,
    finalValueN: Number,
    durationN: Number,

    private val easeFunction: EaseFunction,
    private val onAnimationEnded: (AnimationValue.() -> Unit)? = null
) {
    /**
     * Initial value in Double.
     */
    private var initialValue = initialValueN.toDouble()

    /**
     * Final value in Double.
     */
    private var finalValue = finalValueN.toDouble()

    /**
     * Duration in Double.
     */
    private val duration = durationN.toDouble()

    /**
     * Start time of the animation.
     */
    private var startTime = System.nanoTime()

    /**
     * Delta (final - initial).
     */
    private var delta = finalValue - initialValue

    /**
     * Animation goes downwards or upwards.
     */
    private var reversed = initialValue < finalValue

    /**
     * Last value.
     */
    private var cachedValue = initialValue

    /**
     * Enabled.
     */
    var enabled = false

    /**
     * This function returns the value for a given context.
     *
     * When enabled and context passed it calcs a new value based on
     * [startTime] and context time, otherwise it will return the
     * cached value.
     *
     * When animation gets to the end it will invoke the end callback.
     */
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

    /**
     * Utility to get the value parsed to Int.
     */
    fun getIntValue(ctx: RenderContext?): Int = getValue(ctx).toInt()

    /**
     * Starts the animation.
     */
    fun start() = synchronized(this) {
        startAnimation()
    }

    /**
     * Starts the animation from final to initial.
     */
    fun startReverse() = synchronized(this) {
        val tempInitialValue = initialValue
        initialValue = finalValue
        finalValue = tempInitialValue

        reversed = !reversed
        delta = -delta

        startAnimation()
    }

    /**
     * Starts the animation from the current value. It will
     * set the initial value to the current value and the final
     * value to current + delta.
     */
    fun startFromCurrent() = synchronized(this) {
        stopAnimation()

        val delta = finalValue - initialValue

        initialValue = cachedValue
        finalValue = cachedValue + delta

        startAnimation()
    }

    /**
     * Start animation logic.
     */
    private fun startAnimation() {
        enabled = true
        startTime = System.nanoTime()
        cachedValue = initialValue
    }

    /**
     * Stop animation logic.
     */
    private fun stopAnimation() {
        enabled = false
    }
}

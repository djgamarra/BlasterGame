package com.djgamarra.blaster.utils

import kotlin.math.PI
import kotlin.math.cos

/**
 * Easing function enum.
 */
enum class EaseFunction {
    /**
     * Ease in out with SIN.
     */
    IN_OUT {
        override fun getValue(initial: Double, delta: Double, progress: Double): Double =
            (initial + (-(cos(PI * progress) - 1) / 2) * delta)
    },

    /**
     * Linear function.
     */
    LINEAR {
        override fun getValue(initial: Double, delta: Double, progress: Double): Double =
            (initial + progress * delta)
    };

    /**
     * Function to get a value given the config of the animation
     * and the current progress.
     */
    abstract fun getValue(initial: Double, delta: Double, progress: Double): Double
}

package com.djgamarra.blaster.utils

import kotlin.math.PI
import kotlin.math.cos

enum class EaseFunction {
    IN_OUT {
        override fun getValue(initial: Double, delta: Double, progress: Double): Double =
            (initial + (-(cos(PI * progress) - 1) / 2) * delta)
    },
    LINEAR {
        override fun getValue(initial: Double, delta: Double, progress: Double): Double =
            (initial + progress * delta)
    };

    abstract fun getValue(initial: Double, delta: Double, progress: Double): Double
}

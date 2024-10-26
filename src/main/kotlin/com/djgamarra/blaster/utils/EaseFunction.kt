package com.djgamarra.blaster.utils

import kotlin.math.PI
import kotlin.math.cos

enum class EaseFunction(val getValue: (initial: Double, delta: Double, progress: Double) -> Double) {
    IN_OUT({ initial, delta, progress -> (initial + (-(cos(PI * progress) - 1) / 2) * delta) }),
    LINEAR({ initial, delta, progress -> (initial + progress * delta) })
}

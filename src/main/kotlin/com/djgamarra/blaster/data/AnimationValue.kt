package com.djgamarra.blaster.data

import kotlin.math.max
import kotlin.math.min

class AnimationValue(
    private val initialValue: Int,
    private val finalValue: Int,
    private val step: Int,
    private val onAnimationEnd: (() -> Unit)? = null
) {
    private var enabled = false
    var value: Int = initialValue
        private set(value) {
            synchronized(this) {
                field = value
            }
        }
        get() {
            synchronized(this) {
                return field
            }
        }

    fun start() {
        synchronized(this) {
            enabled = true
            value = initialValue
        }
    }

    fun tick() {
        synchronized(this) {
            if (!enabled) {
                return
            }

            value = if (finalValue > initialValue) {
                min(finalValue, value + step)
            } else {
                max(finalValue, value + step)
            }

            if (value == finalValue) {
                enabled = false
                onAnimationEnd?.invoke()
            }
        }
    }
}

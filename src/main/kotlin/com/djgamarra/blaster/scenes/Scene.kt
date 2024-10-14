package com.djgamarra.blaster.scenes

import java.awt.Graphics2D
import java.awt.event.MouseEvent

abstract class Scene {
    open fun mouseMoved(e: MouseEvent) {}

    open fun mouseClicked(e: MouseEvent) {}

    open fun tick() {}

    abstract fun draw(g: Graphics2D)

    companion object {
        const val TICK_WAIT = 10L
    }
}
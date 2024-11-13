package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.RenderContext
import java.awt.Graphics2D
import java.awt.event.MouseEvent

/**
 * Everything that is meant to be drawn on the screen
 * is a Scene, entire views or even small components.
 * A scene can be composed of other scenes, each one
 * is responsible for implementing mouse event handlers
 * or ticks and if it is composed by other ones it will
 * be responsible for calling these handlers to them
 * (events and/or ticks propagation).
 */
abstract class Scene {
    open fun mouseMoved(e: MouseEvent) {}

    open fun mouseClicked(e: MouseEvent) {}

    open fun tick() {}

    abstract fun draw(g: Graphics2D, ctx: RenderContext)

    companion object {
        const val TICK_WAIT = 10L
    }
}

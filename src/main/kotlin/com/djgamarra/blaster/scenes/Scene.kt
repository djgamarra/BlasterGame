package com.djgamarra.blaster.scenes

import java.awt.Graphics2D
import java.awt.event.MouseEvent

abstract class Scene {
    open val tickWait = 10L

    abstract fun mouseMoved(e: MouseEvent)

    abstract fun mouseClicked(e: MouseEvent)

    abstract fun tick()

    abstract fun draw(g: Graphics2D)
}
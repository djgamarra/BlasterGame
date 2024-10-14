package com.djgamarra.blaster.scenes

import java.awt.Graphics2D
import java.awt.event.MouseEvent

abstract class Scene {
    abstract val tickWait: Long

    abstract fun mouseMoved(e: MouseEvent)

    abstract fun tick()

    abstract fun draw(g: Graphics2D)
}
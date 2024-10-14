package com.djgamarra.blaster.views

import com.djgamarra.blaster.data.Game
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener

class MouseWindowListener : MouseMotionListener, MouseListener {
    override fun mouseDragged(e: MouseEvent) {
    }

    override fun mouseMoved(e: MouseEvent) {
        Game.mouseMoved(e)
    }

    override fun mouseClicked(e: MouseEvent) {
        Game.mouseClicked(e)
    }

    override fun mousePressed(e: MouseEvent) {
    }

    override fun mouseReleased(e: MouseEvent) {
    }

    override fun mouseEntered(e: MouseEvent) {
    }

    override fun mouseExited(e: MouseEvent) {
    }
}
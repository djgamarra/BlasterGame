package com.djgamarra.blaster.views

import com.djgamarra.blaster.workers.GameWorker
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener

class MouseWindowListener : MouseMotionListener {
    override fun mouseDragged(e: MouseEvent) {
    }

    override fun mouseMoved(e: MouseEvent) {
        GameWorker.mouseMoved(e)
    }
}
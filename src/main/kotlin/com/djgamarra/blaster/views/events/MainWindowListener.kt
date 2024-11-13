package com.djgamarra.blaster.views.events

import com.djgamarra.blaster.workers.DrawerWorker
import com.djgamarra.blaster.workers.GameWorker
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

/**
 * Custom listener. This is used to stop the threads when the window
 * gets closed.
 */
class MainWindowListener : WindowAdapter() {
    override fun windowClosing(e: WindowEvent?) {
        DrawerWorker.interrupt()
        GameWorker.interrupt()

        super.windowClosing(e)
    }
}

package com.djgamarra.blaster.views.events

import com.djgamarra.blaster.workers.DrawerWorker
import com.djgamarra.blaster.workers.GameWorker
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

class MainWindowListener : WindowAdapter() {
    override fun windowClosing(e: WindowEvent?) {
        DrawerWorker.interrupt()
        GameWorker.interrupt()

        super.windowClosing(e)
    }
}

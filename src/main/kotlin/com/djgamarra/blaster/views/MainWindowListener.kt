package com.djgamarra.blaster.views

import com.djgamarra.blaster.workers.DrawerWorker
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

class MainWindowListener(private val worker: DrawerWorker) : WindowAdapter() {
    override fun windowClosing(e: WindowEvent?) {
        worker.interrupt()
        super.windowClosing(e)
    }
}
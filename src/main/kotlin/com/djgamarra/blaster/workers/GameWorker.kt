package com.djgamarra.blaster.workers

class GameWorker : Thread() {
    private val drawerWorker = DrawerWorker(this)

    override fun run() {
        drawerWorker.start()
    }
}
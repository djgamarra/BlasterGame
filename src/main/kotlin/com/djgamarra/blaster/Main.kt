package com.djgamarra.blaster

import com.djgamarra.blaster.workers.DrawerWorker
import com.djgamarra.blaster.workers.GameWorker

fun main() {
    GameWorker.start()
    DrawerWorker.start()
}

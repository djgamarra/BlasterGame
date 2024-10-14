package com.djgamarra.blaster.workers

import com.djgamarra.blaster.data.Game
import java.awt.event.MouseEvent

object GameWorker : Thread() {
    override fun run() {
        try {
            loop()
        } catch (e: InterruptedException) {
            return
        }
    }

    fun mouseMoved(e: MouseEvent) {
        Game.player.moveTo(e.x)
    }

    private fun loop() {
//        while (true) {
//        }
    }
}

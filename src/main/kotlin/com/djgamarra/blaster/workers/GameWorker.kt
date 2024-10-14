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
        Game.mouseMoved(e)
    }

    private fun loop() {
        var projectileCounter = 0

        while (true) {
            Game.tick()

            if (projectileCounter == 0) {
                Game.shot()
            }
            projectileCounter = (projectileCounter + 1) % 20

            sleep(10)
        }
    }
}

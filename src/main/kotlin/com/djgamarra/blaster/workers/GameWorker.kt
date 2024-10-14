package com.djgamarra.blaster.workers

import com.djgamarra.blaster.scenes.RootScene

object GameWorker : Thread() {
    override fun run() {
        try {
            loop()
        } catch (e: InterruptedException) {
            return
        }
    }

    private fun loop() {
        while (true) {
            RootScene.tick()
        }
    }
}

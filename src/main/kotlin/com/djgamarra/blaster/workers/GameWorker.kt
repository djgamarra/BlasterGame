package com.djgamarra.blaster.workers

import com.djgamarra.blaster.scenes.RootScene
import com.djgamarra.blaster.scenes.Scene

/**
 * Main game loop. This is thread is responsible for sending
 * a tick each 10ms to the RootScene.
 */
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
            sleep(Scene.TICK_WAIT)
        }
    }
}

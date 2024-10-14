package com.djgamarra.blaster.data

import com.djgamarra.blaster.scenes.BlasterGameScene
import com.djgamarra.blaster.scenes.Scene
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.lang.Thread.sleep

object Game {
    private val currentScene: Scene = BlasterGameScene()

    fun mouseMoved(e: MouseEvent) {
        currentScene.mouseMoved(e)
    }

    fun tick() {
        currentScene.tick()
        sleep(currentScene.tickWait)
    }

    fun draw(g: Graphics2D) {
        currentScene.draw(g)
    }
}

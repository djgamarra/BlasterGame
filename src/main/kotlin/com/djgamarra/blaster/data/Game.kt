package com.djgamarra.blaster.data

import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.scenes.StartScene
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.lang.Thread.sleep

object Game {
    private var currentScene: Scene = StartScene()

    fun mouseMoved(e: MouseEvent) {
        currentScene.mouseMoved(e)
    }

    fun mouseClicked(e: MouseEvent) {
        currentScene.mouseClicked(e)
    }

    fun tick() {
        currentScene.tick()
        sleep(currentScene.tickWait)
    }

    fun changeScene(scene: Scene) {
        synchronized(currentScene) {
            currentScene = scene
        }
    }

    fun draw(g: Graphics2D) {
        synchronized(currentScene) {
            currentScene.draw(g)
        }
    }
}

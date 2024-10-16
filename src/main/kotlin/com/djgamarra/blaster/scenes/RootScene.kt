package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.RenderContext
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.lang.Thread.sleep

object RootScene : Scene() {
    private var currentScene: Scene = StartScene()

    override fun mouseMoved(e: MouseEvent) {
        currentScene.mouseMoved(e)
    }

    override fun mouseClicked(e: MouseEvent) {
        currentScene.mouseClicked(e)
    }

    override fun tick() {
        currentScene.tick()
        sleep(TICK_WAIT)
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        currentScene.draw(g, ctx)
    }

    fun changeScene(scene: Scene) {
        synchronized(currentScene) {
            currentScene = scene
        }
    }
}

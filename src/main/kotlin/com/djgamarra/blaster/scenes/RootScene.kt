package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.RenderContext
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.lang.Thread.sleep

/**
 * This is our root scene, it is a singleton because
 * it is only one. It will coordinate other scenes by:
 *
 * 1. Managing the current scene.
 * 2. Drawing it.
 * 3. Sending ticks to it.
 * 4. Sending mouse events to it.
 */
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
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        currentScene.draw(g, ctx)
    }

    fun changeScene(scene: Scene) = synchronized(currentScene) {
        currentScene = scene
    }
}

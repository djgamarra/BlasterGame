package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.AnimationValue
import com.djgamarra.blaster.views.ViewUtils
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.lang.Thread.sleep

object RootScene : Scene() {
    private var currentScene: Scene = StartScene()
    private var backScene: Scene? = null
    private val sceneTransition = AnimationValue(0, ViewUtils.VIEWPORT_HEIGHT, 1, onAnimationEnd = {
        synchronized(currentScene) {
            backScene = null
        }
    })

    override fun mouseMoved(e: MouseEvent) {
        currentScene.mouseMoved(e)
        backScene?.mouseMoved(e)
    }

    override fun mouseClicked(e: MouseEvent) {
        currentScene.mouseClicked(e)
        backScene?.mouseClicked(e)
    }

    override fun tick() {
        currentScene.tick()
        backScene?.tick()

        sceneTransition.tick()
        sleep(TICK_WAIT)
    }

    override fun draw(g: Graphics2D) {
        val sceneTransitionValue = sceneTransition.value

        g.translate(0, -sceneTransitionValue)
        currentScene.draw(g)
        g.translate(0, sceneTransitionValue * 2)
        backScene?.draw(g)
        g.translate(0, -sceneTransitionValue)
    }

    fun changeScene(scene: Scene) {
        synchronized(currentScene) {
            if (backScene != null) {
                return
            }

            backScene = currentScene
            currentScene = scene

            sceneTransition.start()
        }
    }
}

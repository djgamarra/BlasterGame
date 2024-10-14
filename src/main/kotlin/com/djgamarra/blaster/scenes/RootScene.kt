package com.djgamarra.blaster.scenes

import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.lang.Thread.sleep

object RootScene : Scene() {
    private var visibleScenes = mutableListOf<Scene>(StartScene())

    override fun mouseMoved(e: MouseEvent) {
        visibleScenes.forEach { it.mouseMoved(e) }
    }

    override fun mouseClicked(e: MouseEvent) {
        visibleScenes.forEach { it.mouseClicked(e) }
    }

    override fun tick() {
        visibleScenes.forEach { it.tick() }
        sleep(TICK_WAIT)
    }

    override fun draw(g: Graphics2D) {
        visibleScenes.forEach { it.draw(g) }
    }

    fun changeScene(scene: Scene) {
        val newScenes = mutableListOf(scene)
        newScenes.addAll(0, visibleScenes)
        visibleScenes = newScenes
    }
}

package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.Button
import com.djgamarra.blaster.views.ViewUtils
import java.awt.Graphics2D
import java.awt.event.MouseEvent

class StartScene : Scene() {
    override val tickWait = 100L

    private val buttons = buildList {
        var y = ViewUtils.VIEWPORT_HEIGHT - 50 - ViewUtils.spacing()
        add(0, Button("SETTINGS", y = y))

        y -= 50 + ViewUtils.spacing(0.5F)
        add(0, Button("START NEW GAME", y = y))
    }

    override fun mouseMoved(e: MouseEvent) {
        buttons.forEach { it.mouseMoved(e) }
    }

    override fun tick() {
    }

    override fun draw(g: Graphics2D) {
        buttons.forEach { it.draw(g) }
    }
}

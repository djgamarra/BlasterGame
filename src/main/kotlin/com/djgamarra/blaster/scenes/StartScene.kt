package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.Button
import com.djgamarra.blaster.data.Game
import com.djgamarra.blaster.data.StartBackground
import com.djgamarra.blaster.views.ViewUtils
import java.awt.Graphics2D
import java.awt.event.MouseEvent

class StartScene : Scene() {
    private val background = StartBackground()
    private val buttons = buildList {
        var y = ViewUtils.VIEWPORT_HEIGHT - 50 - ViewUtils.spacing()
        add(0, Button("SETTINGS", y = y, onClick = {}))

        y -= 50 + ViewUtils.spacing(0.5F)
        add(0, Button("START NEW GAME", y = y, onClick = {
            Game.changeScene(GameScene())
        }))
    }

    override fun mouseMoved(e: MouseEvent) {
        buttons.forEach { it.mouseMoved(e) }
    }

    override fun mouseClicked(e: MouseEvent) {
        buttons.forEach { it.mouseClicked(e) }
    }

    override fun tick() {
        background.move()
    }

    override fun draw(g: Graphics2D) {
        background.draw(g)
        buttons.forEach { it.draw(g) }
    }
}

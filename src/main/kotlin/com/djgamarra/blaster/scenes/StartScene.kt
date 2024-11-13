package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.components.Button
import com.djgamarra.blaster.scenes.components.StartBackground
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D
import java.awt.event.MouseEvent

/**
 * This is the initial scene in which we can go
 * to settings or start a new game.
 */
class StartScene(private val background: StartBackground = StartBackground()) : Scene() {
    private val buttons = buildList {
        var y = ViewUtils.VIEWPORT_HEIGHT - 50 - ViewUtils.spacing()
        add(Button("SETTINGS", y = y, onClick = {
            RootScene.changeScene(SettingsScene(background))
        }))

        y -= 50 + ViewUtils.spacing(0.5F)
        add(Button("START NEW GAME", y = y, onClick = {
            RootScene.changeScene(GameScene())
        }))
    }

    override fun mouseMoved(e: MouseEvent) {
        background.mouseMoved(e)
        buttons.forEach { it.mouseMoved(e) }
    }

    override fun mouseClicked(e: MouseEvent) {
        buttons.forEach { it.mouseClicked(e) }
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        background.draw(g, ctx)
        buttons.forEach { it.draw(g, ctx) }
    }
}

package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.data.Settings
import com.djgamarra.blaster.scenes.components.Button
import com.djgamarra.blaster.scenes.components.StartBackground
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D
import java.awt.event.MouseEvent

class SettingsScene(private val background: StartBackground) : Scene() {
    private val buttons = buildList {
        add(Button("BACK", y = ViewUtils.VIEWPORT_HEIGHT - 50 - ViewUtils.spacing(), onClick = {
            RootScene.changeScene(StartScene(background))
        }))

        add(Button("FPS", y = 50 + ViewUtils.spacing(), active = true))

        val spacing = 150
        add(Button("30", y = 50 + ViewUtils.spacing(), x = spacing + ViewUtils.spacing(), onClick = {
            Settings.fps = 30L
        }))
        add(Button("60", y = 50 + ViewUtils.spacing(), x = spacing + ViewUtils.spacing() + 80, onClick = {
            Settings.fps = 60L
        }))
        add(Button("120", y = 50 + ViewUtils.spacing(), x = spacing + ViewUtils.spacing() + 160, onClick = {
            Settings.fps = 120L
        }))
        add(Button("300", y = 50 + ViewUtils.spacing(), x = spacing + ViewUtils.spacing() + 253, onClick = {
            Settings.fps = 300L
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
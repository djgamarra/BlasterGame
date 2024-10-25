package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.data.Settings
import com.djgamarra.blaster.scenes.components.Button
import com.djgamarra.blaster.scenes.components.StartBackground
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D
import java.awt.event.MouseEvent

class SettingsScene(private val background: StartBackground) : Scene() {
    private var currentFps = Settings.fps

    private val fpsButtons = buildMap {
        val spacing = 150

        put(30L, Button("30", y = 50 + ViewUtils.spacing(), x = spacing + ViewUtils.spacing(), onClick = {
            changeFps(30L)
        }))
        put(60L, Button("60", y = 50 + ViewUtils.spacing(), x = spacing + ViewUtils.spacing() + 80, onClick = {
            changeFps(60L)
        }))
        put(120L, Button("120", y = 50 + ViewUtils.spacing(), x = spacing + ViewUtils.spacing() + 160, onClick = {
            changeFps(120L)
        }))
        put(144L, Button("144", y = 50 + ViewUtils.spacing(), x = spacing + ViewUtils.spacing() + 253, onClick = {
            changeFps(144L)
        }))
    }

    init {
        changeFps(Settings.fps)
    }

    private val buttons =
        fpsButtons.values + Button("BACK", y = ViewUtils.VIEWPORT_HEIGHT - 50 - ViewUtils.spacing(), onClick = {
            RootScene.changeScene(StartScene(background))
        }) + Button("FPS", y = 50 + ViewUtils.spacing(), active = true)

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

    private fun changeFps(fps: Long) {
        fpsButtons[currentFps]?.active = false
        Settings.fps = fps
        currentFps = fps
        fpsButtons[Settings.fps]?.active = true
    }
}

package com.djgamarra.blaster.workers

import com.djgamarra.blaster.data.Game
import com.djgamarra.blaster.data.RenderMetrics
import com.djgamarra.blaster.views.MainWindow
import com.djgamarra.blaster.views.ViewUtils
import java.awt.Color
import java.awt.Graphics2D
import java.awt.RenderingHints


object DrawerWorker : Thread() {
    private val image = ViewUtils.createImage(ViewUtils.VIEWPORT_WIDTH, ViewUtils.VIEWPORT_HEIGHT)
    private val mainWindow = MainWindow()

    private val drawingGraphics: Graphics2D
        get() = (image.graphics as Graphics2D).also { g ->
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        }

    override fun run() {
        mainWindow.start()

        try {
            loop()
        } catch (e: InterruptedException) {
            return
        }
    }

    private fun loop() {
        while (true) {
            RenderMetrics.measureFrame {
                drawingGraphics.let { g ->
                    renderBackground(g)
                    renderGame(g)
                    renderFps(g)

                    g.dispose()
                }

                mainWindow.drawCanvas(image)
                sleep(it.sleepTime)
            }
        }
    }

    private fun renderBackground(g: Graphics2D) {
        g.color = ViewUtils.GAME_BACKGROUND_COLOR
        g.fillRect(0, 0, ViewUtils.VIEWPORT_WIDTH, ViewUtils.VIEWPORT_HEIGHT)
    }

    private fun renderGame(g: Graphics2D) {
        Game.draw(g)
    }

    private fun renderFps(g: Graphics2D) {
        g.color = Color.WHITE
        g.font = ViewUtils.DEFAULT_FONT.deriveFont(12F)
        g.drawString("${RenderMetrics.currentFps} FPS", ViewUtils.spacing(), ViewUtils.spacing() + 12)
    }
}

package com.djgamarra.blaster.workers

import com.djgamarra.blaster.data.RenderMetrics
import com.djgamarra.blaster.views.MainWindow
import com.djgamarra.blaster.views.ViewUtils
import java.awt.*
import kotlin.math.roundToInt


class DrawerWorker(private val mainWindow: MainWindow) : Thread() {
    private val metrics = RenderMetrics()
    private val image = ViewUtils.createImage(ViewUtils.VIEWPORT_WIDTH, ViewUtils.VIEWPORT_HEIGHT)

    private val drawingGraphics: Graphics2D
        get() {
            val g = image.graphics as Graphics2D
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            return g
        }

    override fun run() {
        mainWindow.isVisible = true

        try {
            loop()
        } catch (e: InterruptedException) {
            return
        }
    }

    private fun loop() {
        while (true) {
            metrics.tickStart()

            val g = drawingGraphics
            renderBackground(g)
            renderGame(g)
            g.dispose()

            mainWindow.drawTick(image)

            metrics.tickEnd()
            fpsDelay()
        }
    }

    private fun renderBackground(g: Graphics2D) {
        g.color = ViewUtils.GAME_BACKGROUND_COLOR
        g.fillRect(0, 0, ViewUtils.VIEWPORT_WIDTH, ViewUtils.VIEWPORT_HEIGHT)
    }

    private fun renderGame(g: Graphics2D) {
        g.color = Color.WHITE
        g.stroke = BasicStroke(5F)
        g.font = Font("Arial", Font.BOLD, 50)

        g.drawLine(0, 0, 600, 30)
        g.drawString("FPS ${metrics.fpsCount}", 10, 100)

        g.fillOval(
            (Math.random() * ViewUtils.VIEWPORT_WIDTH).roundToInt(),
            (Math.random() * ViewUtils.VIEWPORT_HEIGHT).roundToInt(),
            50,
            50
        )
    }

    private fun fpsDelay() {
        sleep(metrics.sleepTimeToNewTick)
    }
}

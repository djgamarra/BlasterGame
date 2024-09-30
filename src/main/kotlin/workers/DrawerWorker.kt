package com.djgamarra.blaster.workers

import com.djgamarra.blaster.Utils
import com.djgamarra.blaster.views.MainWindow
import com.djgamarra.blaster.views.ViewConstants
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.GraphicsEnvironment
import kotlin.math.max
import kotlin.math.roundToInt


class DrawerWorker(private val mainWindow: MainWindow) : Thread() {
    private val defaultConfig = GraphicsEnvironment.getLocalGraphicsEnvironment()
        .defaultScreenDevice
        .defaultConfiguration
    private val image = defaultConfig.createCompatibleImage(ViewConstants.VIEWPORT_WIDTH, ViewConstants.VIEWPORT_HEIGHT)

    override fun run() {
        mainWindow.isVisible = true
        loop()
    }

    private fun loop() {
        var currentTick = System.nanoTime()
        var oldTick = currentTick - 1
        var tickCount = 0
        var fpsCount = 0L

        while (true) {
            val renderStart = System.nanoTime()

            if (tickCount == 0) {
                fpsCount = Utils.SECOND_IN_NS / (currentTick - oldTick)
            }

            val g = image.graphics as Graphics2D

            renderBackground(g)

            g.color = Color.WHITE
            g.font = Font("Arial", Font.BOLD, 50)

            g.drawLine(0, 0, 100, 100)
            g.drawString("FPS $fpsCount", 10, 100)

            g.fillOval(
                (Math.random() * ViewConstants.VIEWPORT_WIDTH).roundToInt(),
                (Math.random() * ViewConstants.VIEWPORT_HEIGHT).roundToInt(),
                50,
                50
            )
            g.dispose()

            mainWindow.drawTick(image)

            oldTick = currentTick
            currentTick = System.nanoTime()
            tickCount++
            tickCount %= 10

            fpsDelay(renderStart)
        }
    }

    private fun renderBackground(g: Graphics2D) {
        g.color = ViewConstants.GAME_BACKGROUND_COLOR
        g.fillRect(0, 0, ViewConstants.VIEWPORT_WIDTH, ViewConstants.VIEWPORT_HEIGHT)
    }

    private fun fpsDelay(renderStart: Long) {
        sleep(
            max(
                0,
                ((Utils.SECOND_IN_NS / ViewConstants.MAX_FPS) - (System.nanoTime() - renderStart)) / Utils.MS_IN_NS
            )
        )
    }
}

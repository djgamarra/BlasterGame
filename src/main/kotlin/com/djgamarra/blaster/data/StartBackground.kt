package com.djgamarra.blaster.data

import com.djgamarra.blaster.views.ViewUtils
import java.awt.AlphaComposite
import java.awt.Graphics2D
import kotlin.random.Random

class StartBackground {
    private var x = 0
    private var y = 0
    private var deltaX = -1
    private var deltaY = -1

    private val image = ViewUtils.createImage(WIDTH, HEIGHT).apply {
        (graphics as Graphics2D).apply {
            color = ViewUtils.GAME_BACKGROUND_COLOR
            drawRect(0, 0, width, height)

            composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, TILE_ALPHA)

            val images = arrayOf(
                ViewUtils.readImage("red.png"),
                ViewUtils.readImage("green.png"),
                ViewUtils.readImage("yellow.png")
            )

            var x = 0
            while (x < width) {
                var y = 0
                while (y < height) {
                    val image = images[Random.nextInt(images.size)]
                    drawImage(image, x, y, null)
                    y += TILE_DELTA_Y
                }
                x += TILE_DELTA_X
            }

            dispose()
        }
    }

    fun move() {
        x += deltaX
        y += deltaY

        if (x > 0 || WIDTH + x < ViewUtils.VIEWPORT_WIDTH) {
            deltaX = -deltaX;
        }

        if (y > 0 || HEIGHT + y < ViewUtils.VIEWPORT_HEIGHT) {
            deltaY = -deltaY;
        }
    }

    fun draw(g: Graphics2D) {
        g.drawImage(image, x, y, null)
    }

    companion object {
        private const val TILE_ALPHA = 0.15F

        private const val WIDTH = ViewUtils.VIEWPORT_WIDTH * 2
        private const val HEIGHT = ViewUtils.VIEWPORT_HEIGHT * 2

        private val TILE_DELTA_X = 40 + ViewUtils.spacing(0.5F)
        private val TILE_DELTA_Y = 32 + ViewUtils.spacing(0.5F)
    }
}

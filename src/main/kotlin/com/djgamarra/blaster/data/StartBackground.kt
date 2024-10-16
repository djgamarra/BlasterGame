package com.djgamarra.blaster.data

import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.views.ViewUtils
import java.awt.AlphaComposite
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import kotlin.random.Random

class StartBackground : Scene() {
    private var x = 0
    private var y = 0

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

            for (x in 0 until width step TILE_DELTA_X) {
                for (y in 0 until height step TILE_DELTA_Y) {
                    drawImage(images[Random.nextInt(images.size)], x, y, null)
                }
            }

            dispose()
        }
    }

    override fun mouseMoved(e: MouseEvent) {
        x = -(e.x * PARALLAX_MULTIPLIER).toInt()
        y = -(e.y * PARALLAX_MULTIPLIER).toInt()
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        g.drawImage(image, x, y, null)
    }

    companion object {
        private const val TILE_ALPHA = 0.15F

        private const val SIZE_MULTIPLIER = 1.2F
        private const val PARALLAX_MULTIPLIER = SIZE_MULTIPLIER - 1

        private const val WIDTH = (ViewUtils.VIEWPORT_WIDTH * SIZE_MULTIPLIER).toInt()
        private const val HEIGHT = (ViewUtils.VIEWPORT_HEIGHT * SIZE_MULTIPLIER).toInt()

        private const val TILE_WIDTH = 40
        private const val TILE_HEIGHT = 32
        private const val TILE_SPACING = 0.5F

        private val TILE_DELTA_X = TILE_WIDTH + ViewUtils.spacing(TILE_SPACING)
        private val TILE_DELTA_Y = TILE_HEIGHT + ViewUtils.spacing(TILE_SPACING)
    }
}

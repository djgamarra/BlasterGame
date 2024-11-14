package com.djgamarra.blaster.scenes.components

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.AlphaComposite
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import kotlin.random.Random

/**
 * This scene is just the moving background on
 * the start scene.
 */
class StartBackground : Scene() {
    /**
     * Coordinate X.
     */
    private var x = 0

    /**
     * Coordinate Y.
     */
    private var y = 0

    /**
     * This is the background image. It is generated
     * as a pattern with random opponents.
     */
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

    /**
     * The background moves when the mouse moves with
     * a specific multiplier to get the desired effect.
     *
     * The effect works in that way:
     *
     * We draw the image in the negative mouse coordinate
     * multiplied by the [PARALLAX_MULTIPLIER] so
     * that the image will start off the screen by maximum
     * 20% (depending on the [PARALLAX_MULTIPLIER]) and as
     * the image is also 20% bigger than the screen there
     * will always be something to show on the entire screen.
     *
     * This will cause the image to moved just 20% of the
     * mouse movement (that is the effect).
     */
    override fun mouseMoved(e: MouseEvent) {
        x = -(e.x * PARALLAX_MULTIPLIER).toInt()
        y = -(e.y * PARALLAX_MULTIPLIER).toInt()
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        g.drawImage(image, x, y, null)
    }

    companion object {
        /**
         * This is the opacity that will be applied to
         * the pattern.
         */
        private const val TILE_ALPHA = 0.15F

        /**
         * This is a multiplier for the pattern size. It
         * will be used to calculate the size of the image
         * and the effect itself so for a bigger multiplier
         * the effect will be more noticeable. It represents
         * a percentage (1.2 = 120%).
         */
        private const val SIZE_MULTIPLIER = 1.2F

        /**
         * This represents just the extra part of the multiplier
         * (after the 100%). It will be used for calculating
         * the effect.
         */
        private const val PARALLAX_MULTIPLIER = SIZE_MULTIPLIER - 1

        /**
         * Total pattern width.
         */
        private const val WIDTH = (ViewUtils.VIEWPORT_WIDTH * SIZE_MULTIPLIER).toInt()

        /**
         * Total pattern height.
         */
        private const val HEIGHT = (ViewUtils.VIEWPORT_HEIGHT * SIZE_MULTIPLIER).toInt()

        /**
         * Width of each tile of the pattern.
         */
        private const val TILE_WIDTH = 40

        /**
         * Height of each tile of the pattern.
         */
        private const val TILE_HEIGHT = 32

        /**
         * Space between each tile of the pattern.
         */
        private const val TILE_SPACING = 0.5F

        /**
         * Space taken in the X axis by each tile with
         * their respective spacing.
         */
        private val TILE_DELTA_X = TILE_WIDTH + ViewUtils.spacing(TILE_SPACING)

        /**
         * Space taken in the Y axis by each tile with
         * their respective spacing.
         */
        private val TILE_DELTA_Y = TILE_HEIGHT + ViewUtils.spacing(TILE_SPACING)
    }
}

package com.djgamarra.blaster.scenes.components

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.utils.AnimationValue
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D
import java.awt.Rectangle

/**
 * This class represents a single opponent.
 *
 * @param[imageNumber] Opponent look to use (0: red, 1: green, 2: yellow).
 *
 * @property[initialX] Initial X coordinate inside the block.
 * @property[initialY] Initial Y coordinate inside the block.
 * @property[xAnimation] Block X animation.
 * @property[yAnimation] Block Y animation.
 */
class Opponent(
    imageNumber: Int,
    private val initialX: Int,
    private val initialY: Int,
    private val xAnimation: AnimationValue,
    private val yAnimation: AnimationValue,
) : Scene() {
    /**
     * Image to use given the desired look.
     */
    private val image = IMAGES[imageNumber]

    /**
     * Bounds of the opponent. Used to check collisions.
     */
    private val bounds: Rectangle
        get() = Rectangle(getX(), getY(), WIDTH, HEIGHT)

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        g.drawImage(image, getX(ctx), getY(ctx), null)
    }

    /**
     * Check collisions by verifying if the bounds of
     * the opponent intersects the bounds of the projectile.
     */
    fun checkCollision(projectile: Projectile): Boolean {
        return bounds.intersects(projectile.bounds)
    }

    /**
     * Real X coordinate will be [initialX] + block X
     * animation value.
     */
    private fun getX(ctx: RenderContext? = null) = initialX + xAnimation.getIntValue(ctx)

    /**
     * Real Y coordinate will be [initialY] + block Y
     * animation value.
     */
    fun getY(ctx: RenderContext? = null) = initialY + yAnimation.getIntValue(ctx)

    companion object {
        /**
         * List of opponent looks.
         */
        private val IMAGES = arrayOf(
            ViewUtils.readImage("red.png"),
            ViewUtils.readImage("green.png"),
            ViewUtils.readImage("yellow.png")
        )

        /**
         * Image width.
         */
        const val WIDTH = 40

        /**
         * Image height.
         */
        const val HEIGHT = 32
    }
}

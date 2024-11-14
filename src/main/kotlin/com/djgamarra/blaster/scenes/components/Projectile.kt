package com.djgamarra.blaster.scenes.components

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.utils.AnimationValue
import com.djgamarra.blaster.utils.EaseFunction
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Rectangle

/**
 * This scene represents a single projectile.
 */
class Projectile(playerX: Int, private val onDeath: Projectile.() -> Unit) : Scene() {
    /**
     * The X coordinate will always be the same. It
     * will start in the center of the player.
     */
    private val x = playerX + Player.WIDTH / 2 - WIDTH / 2

    /**
     * This is the animation used to make the projectile
     * go to the top of the screen. Starts on the position
     * of the player (bottom of the screen) and ends when
     * the projectile is not visible anymore.
     *
     * When the animation ends we invoke [onDeath].
     */
    private val yAnimation = AnimationValue(
        ViewUtils.VIEWPORT_HEIGHT - HEIGHT - Player.HEIGHT,
        Y_ANIMATION_CONSTRAINT_END,
        ANIMATION_DURATION,
        EaseFunction.LINEAR,
        onAnimationEnded = { this@Projectile.onDeath() }
    ).apply { start() }

    /**
     * These are the current bounds of the projectile. It
     * is used for checking collisions.
     */
    val bounds: Rectangle
        get() = Rectangle(x, getY(), WIDTH, HEIGHT)

    /**
     * It is only a white rectangle :).
     */
    override fun draw(g: Graphics2D, ctx: RenderContext) {
        g.color = Color.WHITE
        g.fillRect(x, getY(ctx), WIDTH, HEIGHT)
    }

    /**
     * This will return the current Y value given
     * a [ctx] or the last cached value if no [ctx]
     * is given.
     */
    private fun getY(ctx: RenderContext? = null) = yAnimation.getIntValue(ctx)

    companion object {
        /**
         * Width of the projectile.
         */
        private const val WIDTH = 10

        /**
         * Height of the projectile.
         */
        private const val HEIGHT = 30

        /**
         * The animation will end when the Y projectile
         * is entirely off the screen.
         */
        private const val Y_ANIMATION_CONSTRAINT_END = -HEIGHT

        /**
         * The animation will last 1 second.
         */
        private const val ANIMATION_DURATION = 1000
    }
}

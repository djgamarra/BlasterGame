package com.djgamarra.blaster.scenes.components

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.utils.AnimationValue
import com.djgamarra.blaster.utils.EaseFunction
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Rectangle

class Projectile(playerX: Int, onDeath: Projectile.() -> Unit) : Scene() {
    private val x = playerX + Player.WIDTH / 2 - WIDTH / 2
    private val yAnimation = AnimationValue(
        ViewUtils.VIEWPORT_HEIGHT - HEIGHT - Player.HEIGHT,
        Y_ANIMATION_CONSTRAINT_END,
        ANIMATION_DURATION,
        EaseFunction.LINEAR,
        onAnimationEnded = { this@Projectile.onDeath() }
    ).apply { start() }
    val bounds: Rectangle
        get() = Rectangle(x, getY(), WIDTH, HEIGHT)

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        g.color = Color.WHITE
        g.fillRect(x, getY(ctx), WIDTH, HEIGHT)
    }

    private fun getY(ctx: RenderContext? = null) = yAnimation.getIntValue(ctx)

    companion object {
        private const val WIDTH = 10
        private const val HEIGHT = 30

        private const val Y_ANIMATION_CONSTRAINT_END = -HEIGHT

        private const val ANIMATION_DURATION = 1000
    }
}

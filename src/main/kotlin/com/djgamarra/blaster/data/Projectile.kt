package com.djgamarra.blaster.data

import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.views.ViewUtils
import java.awt.Color
import java.awt.Graphics2D

class Projectile(playerX: Int) : Scene() {
    private val x = playerX + Player.WIDTH / 2 - WIDTH / 2
    private val y = AnimationValue(
        ViewUtils.VIEWPORT_HEIGHT - HEIGHT - Player.HEIGHT, CONSTRAINT_END, ANIMATION_DURATION
    ).apply { start() }

    val isAlive: Boolean
        get() = y.enabled

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        g.color = Color.WHITE
        g.fillRect(x, y.valueFor(ctx), WIDTH, HEIGHT)
    }

    companion object {
        private const val WIDTH = 10
        private const val HEIGHT = 30

        private const val CONSTRAINT_END = -HEIGHT

        private const val ANIMATION_DURATION = 1000
    }
}

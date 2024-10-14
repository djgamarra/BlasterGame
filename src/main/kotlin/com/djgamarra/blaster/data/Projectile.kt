package com.djgamarra.blaster.data

import com.djgamarra.blaster.views.ViewUtils
import java.awt.Color
import java.awt.Graphics2D

class Projectile(playerX: Int) {
    private val x = playerX + Player.WIDTH / 2 - WIDTH / 2
    private var y = ViewUtils.VIEWPORT_HEIGHT - HEIGHT - Player.HEIGHT

    fun moveBy(): Boolean {
        this.y -= STEP

        return this.y < CONSTRAINT_END
    }

    fun draw(g: Graphics2D) {
        g.color = Color.WHITE
        g.fillRect(x, y, WIDTH, HEIGHT)
    }

    companion object {
        private const val WIDTH = 10
        private const val HEIGHT = 30
        private const val STEP = 10

        private const val CONSTRAINT_END = -HEIGHT
    }
}

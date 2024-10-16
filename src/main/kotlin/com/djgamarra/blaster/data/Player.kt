package com.djgamarra.blaster.data

import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.views.ViewUtils
import java.awt.Graphics2D
import kotlin.math.max
import kotlin.math.min

class Player : Scene() {
    var x = ViewUtils.VIEWPORT_WIDTH / 2 - WIDTH / 2
        private set
    private val y = ViewUtils.VIEWPORT_HEIGHT - HEIGHT - ViewUtils.spacing()

    fun moveTo(x: Int) {
        this.x = min(max(x, CONSTRAINT_START), CONSTRAINT_END)
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        g.drawImage(IMAGE, x, y, null)
    }

    companion object {
        const val WIDTH = 60
        const val HEIGHT = 30

        private val CONSTRAINT_START = ViewUtils.spacing()
        private val CONSTRAINT_END = ViewUtils.VIEWPORT_WIDTH - ViewUtils.spacing() - WIDTH

        private val IMAGE = ViewUtils.readImage("player.png")
    }
}

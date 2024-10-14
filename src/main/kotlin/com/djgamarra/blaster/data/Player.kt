package com.djgamarra.blaster.data

import com.djgamarra.blaster.views.ViewUtils
import java.awt.Graphics2D
import kotlin.math.max
import kotlin.math.min

class Player {
    private var x = ViewUtils.VIEWPORT_WIDTH / 2 - WIDTH / 2
    private var y = ViewUtils.VIEWPORT_HEIGHT - HEIGHT - ViewUtils.spacing()

    private val image = ViewUtils.readImage(ASSET)

    fun moveTo(x: Int) {
        this.x = min(max(x, CONSTRAINT_START), CONSTRAINT_END)
    }

    fun draw(g: Graphics2D) {
        g.drawImage(image, x, y, null)
    }

    companion object {
        private const val ASSET = "player.png"
        private const val WIDTH = 60
        private const val HEIGHT = 30
        private val CONSTRAINT_START = ViewUtils.spacing()
        private val CONSTRAINT_END = ViewUtils.VIEWPORT_WIDTH - ViewUtils.spacing() - WIDTH
    }
}

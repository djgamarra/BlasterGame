package com.djgamarra.blaster.scenes.components

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D

class Oponent(imageNumber: Int, private var x: Int, private var y: Int) : Scene() {
    private val image = IMAGES[imageNumber]

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        g.drawImage(image, x, y, null)
    }

    companion object {
        private val IMAGES = arrayOf(
            ViewUtils.readImage("red.png"),
            ViewUtils.readImage("green.png"),
            ViewUtils.readImage("yellow.png")
        )
    }
}

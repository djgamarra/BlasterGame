package com.djgamarra.blaster.scenes.components

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.utils.AnimationValue
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D

class Opponent(
    imageNumber: Int,
    private val initialX: Int,
    private val initialY: Int,
    private val xAnimation: AnimationValue,
    private val yAnimation: AnimationValue,
) : Scene() {
    private val image = IMAGES[imageNumber]

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        g.drawImage(image, initialX + xAnimation.intValueFor(ctx), initialY + yAnimation.intValueFor(ctx), null)
    }

    companion object {
        private val IMAGES = arrayOf(
            ViewUtils.readImage("red.png"),
            ViewUtils.readImage("green.png"),
            ViewUtils.readImage("yellow.png")
        )
        const val WIDTH = 40
        const val HEIGHT = 32
    }
}

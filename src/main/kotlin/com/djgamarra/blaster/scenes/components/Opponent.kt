package com.djgamarra.blaster.scenes.components

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.utils.AnimationValue
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D

class Opponent(imageNumber: Int, initialX: Int, private val initialY: Int) : Scene() {
    private val image = IMAGES[imageNumber]

    private var reverseX = true
    private val x = AnimationValue(initialX, initialX + CONSTRAINT_X_END, ANIMATION_DURATION, onAnimationEnded = {
        if (reverseX) {
            start(initialX + CONSTRAINT_X_END, initialX)
        } else {
            start(initialX, initialX + CONSTRAINT_X_END)
        }
        reverseX = !reverseX
    }).apply { start() }

    private var reverseY = true
    private val y = AnimationValue(initialY, initialY + CONSTRAINT_Y_END, ANIMATION_DURATION, onAnimationEnded = {
        if (reverseY) {
            start(initialY + CONSTRAINT_Y_END, initialY)
        } else {
            start(initialY, initialY + CONSTRAINT_Y_END)
        }
        reverseY = !reverseY
    }).apply { start() }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        g.drawImage(image, x.valueFor(ctx), y.valueFor(ctx), null)
    }

    companion object {
        private val IMAGES = arrayOf(
            ViewUtils.readImage("red.png"),
            ViewUtils.readImage("green.png"),
            ViewUtils.readImage("yellow.png")
        )
        const val WIDTH = 40
        const val HEIGHT = 32

        private val CONSTRAINT_X_END = (ViewUtils.spacing(1) + WIDTH) * 3 - ViewUtils.spacing(1)
        private val CONSTRAINT_Y_END = (ViewUtils.spacing(1) + HEIGHT) * 3 - ViewUtils.spacing(1)

        private const val ANIMATION_DURATION = 1000
    }
}

package com.djgamarra.blaster.scenes.components

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.utils.AnimationValue
import com.djgamarra.blaster.utils.EaseFunction
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D

class Opponent(imageNumber: Int, initialX: Int, initialY: Int) : Scene() {
    private val image = IMAGES[imageNumber]

    private val x = AnimationValue(
        initialX,
        initialX + CONSTRAINT_X_END,
        ANIMATION_DURATION,
        EaseFunction.IN_OUT,
        onAnimationEnded = { startReverse() }
    ).apply { start() }

    private val y = AnimationValue(initialY, initialY + CONSTRAINT_Y_END, ANIMATION_DURATION, EaseFunction.IN_OUT)

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        g.drawImage(image, x.intValueFor(ctx), y.intValueFor(ctx), null)
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

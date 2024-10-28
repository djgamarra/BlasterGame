package com.djgamarra.blaster.scenes.components

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.utils.AnimationValue
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D
import java.awt.Rectangle

class Opponent(
    imageNumber: Int,
    private val initialX: Int,
    private val initialY: Int,
    private val xAnimation: AnimationValue,
    private val yAnimation: AnimationValue,
) : Scene() {
    private val image = IMAGES[imageNumber]
    private val bounds: Rectangle
        get() = Rectangle(getX(), getY(), WIDTH, HEIGHT)

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        g.drawImage(image, getX(ctx), getY(ctx), null)
    }

    fun checkCollision(projectile: Projectile): Boolean {
        return bounds.intersects(projectile.bounds)
    }

    private fun getX(ctx: RenderContext? = null) = initialX + xAnimation.getIntValue(ctx)

    fun getY(ctx: RenderContext? = null) = initialY + yAnimation.getIntValue(ctx)

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

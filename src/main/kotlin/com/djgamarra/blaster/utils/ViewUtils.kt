package com.djgamarra.blaster.utils

import java.awt.*
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

object ViewUtils {
    const val VIEWPORT_HEIGHT = 700
    const val VIEWPORT_WIDTH = 900
    const val MAX_FPS = 60L
    const val MAX_FPS_COUNT = 20

    val GAME_BACKGROUND_COLOR = Color(33, 33, 33)
    val DEFAULT_FONT: Font = Font.createFont(Font.TRUETYPE_FONT, javaClass.getResourceAsStream("/fonts/Steelar.otf"))

    val defaultGraphicsEnvironment: GraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment()
    private val defaultScreenConfig: GraphicsConfiguration = defaultGraphicsEnvironment
        .defaultScreenDevice
        .defaultConfiguration

    fun createImage(
        width: Int = VIEWPORT_WIDTH,
        height: Int = VIEWPORT_HEIGHT,
        transparency: Int = Transparency.TRANSLUCENT
    ): BufferedImage = defaultScreenConfig.createCompatibleImage(width, height, transparency)

    fun spacing(n: Int = 1): Int = 10 * n

    fun spacing(n: Float): Int = (10 * n).toInt()

    fun readImage(name: String): BufferedImage = ImageIO.read(javaClass.getResourceAsStream("/images/$name"))
}

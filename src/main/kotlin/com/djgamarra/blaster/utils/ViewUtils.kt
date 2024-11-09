package com.djgamarra.blaster.utils

import java.awt.*
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

/**
 * Util with view constants and util functions.
 */
object ViewUtils {
    /**
     * Game height.
     */
    const val VIEWPORT_HEIGHT = 700

    /**
     * Game width.
     */
    const val VIEWPORT_WIDTH = 900

    /**
     * Default background color.
     */
    val GAME_BACKGROUND_COLOR = Color(33, 33, 33)

    /**
     * Steelar font.
     */
    val DEFAULT_FONT: Font = Font.createFont(Font.TRUETYPE_FONT, javaClass.getResourceAsStream("/fonts/Steelar.otf"))

    /**
     * Current screen environment.
     */
    val defaultGraphicsEnvironment: GraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment()

    /**
     * Current screen config.
     */
    private val defaultScreenConfig: GraphicsConfiguration = defaultGraphicsEnvironment
        .defaultScreenDevice
        .defaultConfiguration

    /**
     * Utility to create a compatible image with the default
     * game with and height.
     */
    fun createImage(
        width: Int = VIEWPORT_WIDTH,
        height: Int = VIEWPORT_HEIGHT,
        transparency: Int = Transparency.TRANSLUCENT
    ): BufferedImage = defaultScreenConfig.createCompatibleImage(width, height, transparency)

    /**
     * Utility to normalize paddings.
     *
     * @param[n] Factor.
     */
    fun spacing(n: Int = 1): Int = 10 * n

    fun spacing(n: Float): Int = (10 * n).toInt()

    /**
     * Reads an image in the /images folder.
     *
     * @param[name] Image name.
     */
    fun readImage(name: String): BufferedImage = ImageIO.read(javaClass.getResourceAsStream("/images/$name"))
}

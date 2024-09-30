package com.djgamarra.blaster.views

import java.awt.Color
import java.awt.GraphicsConfiguration
import java.awt.GraphicsEnvironment
import java.awt.Transparency
import java.awt.image.BufferedImage

object ViewUtils {
    const val VIEWPORT_HEIGHT = 700
    const val VIEWPORT_WIDTH = 900
    const val MAX_FPS = 60L

    val GAME_BACKGROUND_COLOR = Color(33, 33, 33)

    val defaultGraphicsEnvironment: GraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment()
    private val defaultScreenConfig: GraphicsConfiguration = defaultGraphicsEnvironment
        .defaultScreenDevice
        .defaultConfiguration

    fun createImage(
        width: Int = VIEWPORT_WIDTH,
        height: Int = VIEWPORT_HEIGHT,
        transparency: Int = Transparency.TRANSLUCENT
    ): BufferedImage {
        return defaultScreenConfig.createCompatibleImage(width, height, transparency)
    }
}

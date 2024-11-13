package com.djgamarra.blaster.views

import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Canvas
import java.awt.Dimension

/**
 * Main canvas, here we will draw all of our game.
 */
class GameCanvas : Canvas() {
    init {
        size = Dimension(ViewUtils.VIEWPORT_WIDTH, ViewUtils.VIEWPORT_HEIGHT)
        background = ViewUtils.GAME_BACKGROUND_COLOR
    }
}

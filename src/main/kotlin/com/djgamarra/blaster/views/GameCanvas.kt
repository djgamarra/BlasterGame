package com.djgamarra.blaster.views

import java.awt.Canvas
import java.awt.Dimension

class GameCanvas : Canvas() {
    init {
        size = Dimension(ViewUtils.VIEWPORT_WIDTH, ViewUtils.VIEWPORT_HEIGHT)
        background = ViewUtils.GAME_BACKGROUND_COLOR
    }
}

package com.djgamarra.blaster.views

import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension

class GameCanvas : Canvas() {
    init {
        size = Dimension(ViewConstants.VIEWPORT_WIDTH, ViewConstants.VIEWPORT_HEIGHT)
        background = ViewConstants.GAME_BACKGROUND_COLOR
    }
}

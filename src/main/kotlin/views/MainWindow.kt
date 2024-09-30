package com.djgamarra.blaster.views

import java.awt.Dimension
import javax.swing.JFrame

class MainWindow() : JFrame() {
    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Blaster Game"
        isResizable = false
        size = Dimension(ViewConstants.VIEWPORT_WIDTH, ViewConstants.VIEWPORT_HEIGHT)

        add(GameCanvas())

        isVisible = true
    }
}

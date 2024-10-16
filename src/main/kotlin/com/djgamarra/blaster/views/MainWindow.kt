package com.djgamarra.blaster.views

import com.djgamarra.blaster.utils.ViewUtils
import com.djgamarra.blaster.views.events.MainWindowListener
import com.djgamarra.blaster.views.events.MouseWindowListener
import java.awt.Dimension
import java.awt.image.BufferStrategy
import java.awt.image.BufferedImage
import javax.swing.JFrame
import javax.swing.SwingUtilities


class MainWindow : JFrame() {
    private val canvas = GameCanvas()
    private val strategy: BufferStrategy

    init {
        defaultCloseOperation = EXIT_ON_CLOSE

        val mouseListener = MouseWindowListener()

        canvas.addMouseListener(mouseListener)
        canvas.addMouseMotionListener(mouseListener)
        addWindowListener(MainWindowListener())

        title = "Blaster Game"
        isResizable = false
        size = Dimension(ViewUtils.VIEWPORT_WIDTH, ViewUtils.VIEWPORT_HEIGHT)
        location = ViewUtils.defaultGraphicsEnvironment.centerPoint.apply {
            translate(
                -ViewUtils.VIEWPORT_WIDTH / 2, -ViewUtils.VIEWPORT_HEIGHT / 2
            )
        }

        add(canvas)
        pack()

        canvas.createBufferStrategy(1)
        strategy = canvas.bufferStrategy
    }

    fun start() {
        SwingUtilities.invokeLater {
            isVisible = true
        }
    }

    fun drawCanvas(image: BufferedImage) {
        SwingUtilities.invokeLater {
            do {
                do {
                    strategy.drawGraphics.apply {
                        drawImage(image, 0, 0, null)
                        dispose()
                    }
                } while (strategy.contentsRestored())

                strategy.show()
            } while (strategy.contentsLost())
        }
    }
}

package com.djgamarra.blaster.views

import com.djgamarra.blaster.workers.DrawerWorker
import java.awt.Dimension
import java.awt.image.BufferStrategy
import java.awt.image.BufferedImage
import javax.swing.JFrame
import javax.swing.SwingUtilities


class MainWindow(drawerWorker: DrawerWorker) : JFrame() {
    private val canvas = GameCanvas()
    private val strategy: BufferStrategy

    init {
        defaultCloseOperation = EXIT_ON_CLOSE

        addWindowListener(MainWindowListener(drawerWorker))

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

        canvas.createBufferStrategy(2)
        strategy = canvas.bufferStrategy
    }

    fun start() {
        SwingUtilities.invokeLater {
            isVisible = true
        }
    }

    fun drawTick(image: BufferedImage) {
        SwingUtilities.invokeLater {
            do {
                do {
                    val graphics = strategy.drawGraphics
                    graphics.drawImage(image, 0, 0, null)
                    graphics.dispose()
                } while (strategy.contentsRestored())

                strategy.show()
            } while (strategy.contentsLost())
        }
    }
}

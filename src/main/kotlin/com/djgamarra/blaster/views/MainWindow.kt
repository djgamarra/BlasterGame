package com.djgamarra.blaster.views

import com.djgamarra.blaster.utils.ViewUtils
import com.djgamarra.blaster.views.events.MainWindowListener
import com.djgamarra.blaster.views.events.MouseWindowListener
import java.awt.Dimension
import java.awt.image.BufferStrategy
import java.awt.image.BufferedImage
import javax.swing.JFrame
import javax.swing.SwingUtilities

/**
 * Main JFrame.
 */
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

        /**
         * Start the JFrame in the center of the physical screen.
         */
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

    /**
     * This shows up the JFrame.
     */
    fun start() {
        SwingUtilities.invokeLater {
            isVisible = true
        }
    }

    /**
     * This function draws the virtual screen into the Canvas. This code was
     * adapted from the [Oracle Docs](https://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html).
     */
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

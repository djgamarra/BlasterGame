package com.djgamarra.blaster.views

import com.djgamarra.blaster.workers.DrawerWorker
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Toolkit
import java.awt.image.BufferedImage
import javax.swing.JFrame
import javax.swing.SwingUtilities

class MainWindow : JFrame() {
    private val worker = DrawerWorker(this)
    private val canvas = GameCanvas()

    init {
        defaultCloseOperation = EXIT_ON_CLOSE

        addWindowListener(MainWindowListener(worker))

        title = "Blaster Game"
        isResizable = false
        size = Dimension(ViewUtils.VIEWPORT_WIDTH, ViewUtils.VIEWPORT_HEIGHT)
        location = ViewUtils.defaultGraphicsEnvironment.centerPoint.apply {
            translate(
                -ViewUtils.VIEWPORT_WIDTH / 2,
                -ViewUtils.VIEWPORT_HEIGHT / 2
            )
        }

        add(canvas)

        worker.start()
    }

    fun drawTick(image: BufferedImage) {
        SwingUtilities.invokeLater {
            val graphics = canvas.graphics as Graphics2D
            graphics.drawImage(image, 0, 0, null)
            graphics.dispose()
            Toolkit.getDefaultToolkit().sync()
        }
    }
}

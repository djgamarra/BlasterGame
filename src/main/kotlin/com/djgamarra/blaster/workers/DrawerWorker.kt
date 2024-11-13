package com.djgamarra.blaster.workers

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.data.RenderMetrics
import com.djgamarra.blaster.scenes.RootScene
import com.djgamarra.blaster.utils.ViewUtils
import com.djgamarra.blaster.views.MainWindow
import java.awt.Graphics2D
import java.awt.RenderingHints

/**
 * Drawer loop. This thread is responsible for drawing
 * the actual game view at a fixed rate (fps).
 */
object DrawerWorker : Thread() {
    /**
     * This is our virtual screen (second buffer). We will draw on it instead of
     * drawing directly to the Canvas so that after the entire screen has been
     * drawn we can draw to the Canvas at once. This will make our game look a
     * bit smoother.
     */
    private val image = ViewUtils.createImage(ViewUtils.VIEWPORT_WIDTH, ViewUtils.VIEWPORT_HEIGHT)

    /**
     * This is our JFrame.
     */
    private val mainWindow = MainWindow()

    /**
     * This getter gives us a Graphics2D instance to draw on our virtual screen.
     * This also sets some rendering hints to enable antialias.
     */
    private val drawingGraphics: Graphics2D
        get() = (image.graphics as Graphics2D).also { g ->
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        }

    override fun run() {
        mainWindow.start()

        try {
            loop()
        } catch (e: InterruptedException) {
            return
        }
    }

    /**
     * Main loop. Here we are drawing to the second screen and after that making
     * the canvas to draw the image built.
     */
    private fun loop() {
        while (true) {
            RenderMetrics.startFrame { ctx ->
                drawingGraphics.let { g ->
                    renderBackground(g)
                    renderGame(g, ctx)
//                    renderFps(g)

                    g.dispose()
                }

                mainWindow.drawCanvas(image)
            }
        }
    }

    /**
     * We paint the entire screen in a dark color before starting drawing the
     * game itself.
     */
    private fun renderBackground(g: Graphics2D) {
        g.color = ViewUtils.GAME_BACKGROUND_COLOR
        g.fillRect(0, 0, ViewUtils.VIEWPORT_WIDTH, ViewUtils.VIEWPORT_HEIGHT)
    }

    /**
     * We let the root scene draw the current game on our virtual screen.
     */
    private fun renderGame(g: Graphics2D, ctx: RenderContext) {
        RootScene.draw(g, ctx)
    }

//    private fun renderFps(g: Graphics2D) {
//        g.color = Color.WHITE
//        g.font = ViewUtils.DEFAULT_FONT.deriveFont(12F)
//        g.drawString("${RenderMetrics.currentFps} FPS", ViewUtils.spacing(), ViewUtils.spacing() + 12)
//    }
}

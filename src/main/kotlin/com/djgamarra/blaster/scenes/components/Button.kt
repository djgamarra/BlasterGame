package com.djgamarra.blaster.scenes.components

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.MouseEvent

class Button(
    private val label: String,
    private val fontSize: Float = 25F,

    private val x: Int = ViewUtils.spacing(),
    private val y: Int = ViewUtils.spacing(),

    private val onClick: (Button.() -> Unit)? = null,

    var active: Boolean = false
) : Scene() {
    private var width: Int = 0
    private var height: Int = 0

    private val fontX = x + ViewUtils.spacing()
    private val fontY = (y + fontSize).toInt() + ViewUtils.spacing()

    private var rendered = false
    private var hovered = active

    override fun mouseMoved(e: MouseEvent) {
        if (active) {
            return
        }

        hovered = e.x >= x && e.x < x + width && e.y >= y && e.y < y + height
    }

    override fun mouseClicked(e: MouseEvent) {
        if (e.x >= x && e.x < x + width && e.y >= y && e.y < y + height) {
            onClick?.invoke(this)
        }
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        g.font = ViewUtils.DEFAULT_FONT.deriveFont(fontSize)

        if (!rendered) {
            onFirstRender(g)
        }

        g.color = Color.WHITE
        if (hovered or active) {
            g.fillRect(x, y, width, height)
            g.color = ViewUtils.GAME_BACKGROUND_COLOR
        } else {
            g.stroke = STROKE
            g.drawRect(x, y, width, height)
        }

        g.drawString(label, fontX, fontY)
    }

    private fun onFirstRender(g: Graphics2D) {
        g.fontMetrics.let {
            val bounds = it.getStringBounds(label, g)

            width = bounds.width.toInt() + ViewUtils.spacing(2)
            height = bounds.height.toInt() + ViewUtils.spacing(2)
        }

        rendered = true
    }

    companion object {
        private val STROKE = BasicStroke(2F)
    }
}

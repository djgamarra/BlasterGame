package com.djgamarra.blaster.scenes.components

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.utils.TickCounter
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import kotlin.math.max
import kotlin.math.min

class Player : Scene() {
    private var x = ViewUtils.VIEWPORT_WIDTH / 2 - WIDTH / 2
    val y = ViewUtils.VIEWPORT_HEIGHT - HEIGHT - ViewUtils.spacing()

    private val projectilesTickCounter = TickCounter(15)
    var projectiles = listOf<Projectile>()
        private set

    override fun mouseMoved(e: MouseEvent) {
        moveTo(e.x)
    }

    override fun tick() {
        if (projectilesTickCounter.tick()) {
            shoot()
        }
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        projectiles.forEach { it.draw(g, ctx) }
        g.drawImage(IMAGE, x, y, null)
    }

    private fun shoot() = synchronized(projectiles) {
        projectiles += Projectile(x, onDeath = { removeProjectile(this) })
    }

    fun removeProjectile(projectile: Projectile) = synchronized(projectiles) {
        projectiles = projectiles.filter { it != projectile }
    }

    private fun moveTo(x: Int) {
        this.x = min(max(x - WIDTH / 2, CONSTRAINT_START), CONSTRAINT_END)
    }

    companion object {
        const val WIDTH = 60
        const val HEIGHT = 30

        private val CONSTRAINT_START = ViewUtils.spacing()
        private val CONSTRAINT_END = ViewUtils.VIEWPORT_WIDTH - ViewUtils.spacing() - WIDTH

        private val IMAGE = ViewUtils.readImage("player.png")
    }
}

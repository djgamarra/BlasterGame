package com.djgamarra.blaster.scenes.components

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.utils.TickCounter
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D
import kotlin.math.max
import kotlin.math.min

class Player(private val opponentsBlock: OpponentsBlock) : Scene() {
    private var x = ViewUtils.VIEWPORT_WIDTH / 2 - WIDTH / 2
    private val y = ViewUtils.VIEWPORT_HEIGHT - HEIGHT - ViewUtils.spacing()

    private val projectilesTickCounter = TickCounter(20)
    private var projectiles = listOf<Projectile>()

    fun moveTo(x: Int) {
        this.x = min(max(x - WIDTH / 2, CONSTRAINT_START), CONSTRAINT_END)
    }

    override fun tick() {
        checkCollisions()

        if (projectilesTickCounter.tick()) {
            shoot()
        }
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        projectiles.forEach { it.draw(g, ctx) }
        g.drawImage(IMAGE, x, y, null)
    }

    private fun checkCollisions() {
        opponentsBlock.opponents.forEach { opponent ->
            val collidingProjectile = projectiles.find { projectile -> opponent.checkCollision(projectile) }

            if (collidingProjectile != null) {
                removeProjectile(collidingProjectile)
                opponentsBlock.removeOpponent(opponent)
            }
        }
    }

    private fun shoot() = synchronized(projectiles) {
        projectiles += Projectile(x, onDeath = { removeProjectile(this) })
    }

    private fun removeProjectile(projectile: Projectile) = synchronized(projectiles) {
        projectiles = projectiles.filter { it != projectile }
    }

    companion object {
        const val WIDTH = 60
        const val HEIGHT = 30

        private val CONSTRAINT_START = ViewUtils.spacing()
        private val CONSTRAINT_END = ViewUtils.VIEWPORT_WIDTH - ViewUtils.spacing() - WIDTH

        private val IMAGE = ViewUtils.readImage("player.png")
    }
}

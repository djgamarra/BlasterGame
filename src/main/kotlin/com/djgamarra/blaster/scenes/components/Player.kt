package com.djgamarra.blaster.scenes.components

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.utils.TickCounter
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import kotlin.math.max
import kotlin.math.min

/**
 * This scene represents the player.
 */
class Player : Scene() {
    /**
     * The X coordinate starts in the center of the screen.
     */
    private var x = ViewUtils.VIEWPORT_WIDTH / 2 - WIDTH / 2

    /**
     * The Y coordinate will always be the same: Bottom of
     * the screen + a padding.
     */
    val y = ViewUtils.VIEWPORT_HEIGHT - HEIGHT - ViewUtils.spacing()

    /**
     * Helper to know when to dispatch another shot. Each 15
     * ticks we will add a new one.
     */
    private val projectilesTickCounter = TickCounter(15)

    /**
     * List of projectiles.
     */
    var projectiles = listOf<Projectile>()
        private set

    /**
     * The player only moves on the X coordinate.
     */
    override fun mouseMoved(e: MouseEvent) {
        moveTo(e.x)
    }

    override fun tick() {
        if (projectilesTickCounter.tick()) {
            shoot()
        }
    }

    /**
     * Draw all the projectiles and the player image.
     */
    override fun draw(g: Graphics2D, ctx: RenderContext) {
        projectiles.forEach { it.draw(g, ctx) }
        g.drawImage(IMAGE, x, y, null)
    }

    /**
     * Dispatch a new projectile.
     */
    private fun shoot() = synchronized(projectiles) {
        projectiles += Projectile(x, onDeath = { removeProjectile(this) })
    }

    /**
     * Removes the desired projectile.
     */
    fun removeProjectile(projectile: Projectile) = synchronized(projectiles) {
        projectiles = projectiles.filter { it != projectile }
    }

    /**
     * Moves the player to the desired X coordinate
     * between the bounds (start and end of the screen).
     */
    private fun moveTo(x: Int) {
        this.x = min(max(x - WIDTH / 2, CONSTRAINT_START), CONSTRAINT_END)
    }

    companion object {
        /**
         * Player image width.
         */
        const val WIDTH = 60

        /**
         * Player image height.
         */
        const val HEIGHT = 30

        /**
         * The minimum X coordinate the player can be is
         * the start of the screen + a padding so that it
         * will never touch the left side (0 coordinate).
         */
        private val CONSTRAINT_START = ViewUtils.spacing()

        /**
         * The maximum X coordinate is calculated so that
         * it will never touch the right of the screen.
         */
        private val CONSTRAINT_END = ViewUtils.VIEWPORT_WIDTH - ViewUtils.spacing() - WIDTH

        /**
         * We load the image once.
         */
        private val IMAGE = ViewUtils.readImage("player.png")
    }
}

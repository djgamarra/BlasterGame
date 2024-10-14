package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.Player
import com.djgamarra.blaster.data.Projectile
import com.djgamarra.blaster.data.TickCounter
import java.awt.Graphics2D
import java.awt.event.MouseEvent

class BlasterGameScene : Scene() {
    override val tickWait = 10L

    private val player = Player()

    private val projectilesTickCounter = TickCounter(20)
    private val projectiles = mutableListOf<Projectile>()

    override fun mouseMoved(e: MouseEvent) {
        synchronized(player) {
            player.moveTo(e.x)
        }
    }

    override fun tick() {
        synchronized(projectiles) {
            if (projectilesTickCounter.tick()) {
                synchronized(player) {
                    projectiles.add(Projectile(player.x))
                }
            }

            projectiles.removeAll { it.moveBy() }
        }
    }

    override fun draw(g: Graphics2D) {
        synchronized(projectiles) {
            projectiles.forEach { it.draw(g) }
        }
        synchronized(player) {
            player.draw(g)
        }
    }
}

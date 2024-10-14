package com.djgamarra.blaster.data

import java.awt.Graphics2D
import java.awt.event.MouseEvent

object Game {
    private val lock = Any()
    private val player = Player()
    private val projectiles = mutableListOf<Projectile>()

    fun mouseMoved(e: MouseEvent) {
        synchronized(lock) {
            player.moveTo(e.x)
        }
    }

    fun shot() {
        synchronized(lock) {
            projectiles.add(Projectile(player.x))
        }
    }

    fun tick() {
        synchronized(lock) {
            projectiles.forEach {
                it.moveBy()
            }
        }
    }

    fun draw(g: Graphics2D) {
        synchronized(lock) {
            player.draw(g)
            projectiles.forEach { it.draw(g) }
        }
    }
}

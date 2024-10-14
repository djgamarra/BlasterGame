package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.Player
import com.djgamarra.blaster.data.Projectile
import com.djgamarra.blaster.data.TickCounter
import java.awt.Graphics2D
import java.awt.event.MouseEvent

class GameScene : Scene() {
    private val player = Player()

    private val projectilesTickCounter = TickCounter(20)
    private var projectiles = mutableListOf<Projectile>()

    override fun mouseMoved(e: MouseEvent) {
        player.moveTo(e.x)
    }

    override fun tick() {
        val newProjectiles = projectiles.filter { it.move() }.toMutableList()
        if (projectilesTickCounter.tick()) {
            newProjectiles.add(Projectile(player.x))
        }
        projectiles = newProjectiles
    }

    override fun draw(g: Graphics2D) {
        projectiles.forEach { it.draw(g) }
        player.draw(g)
    }
}

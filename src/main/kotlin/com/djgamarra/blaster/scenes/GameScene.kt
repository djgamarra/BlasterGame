package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.components.Oponent
import com.djgamarra.blaster.scenes.components.Player
import com.djgamarra.blaster.scenes.components.Projectile
import com.djgamarra.blaster.utils.TickCounter
import java.awt.Graphics2D
import java.awt.event.MouseEvent

class GameScene : Scene() {
    private val player = Player()

    private val projectilesTickCounter = TickCounter(20)
    private var projectiles = mutableListOf<Projectile>()
    private var oponents = mutableListOf<Oponent>(Oponent(0, 10, 10))

    override fun mouseMoved(e: MouseEvent) {
        player.moveTo(e.x)
    }

    override fun tick() {
        synchronized(projectiles) {
            val newProjectiles = projectiles.filter { it.isAlive }.toMutableList()
            if (projectilesTickCounter.tick()) {
                newProjectiles.add(Projectile(player.x))
            }
            projectiles = newProjectiles
        }
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        projectiles.forEach { it.draw(g, ctx) }
        oponents.forEach { it.draw(g, ctx) }
        player.draw(g, ctx)
    }
}
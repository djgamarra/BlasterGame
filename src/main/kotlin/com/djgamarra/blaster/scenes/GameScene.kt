package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.components.OpponentsBlock
import com.djgamarra.blaster.scenes.components.Player
import com.djgamarra.blaster.scenes.components.Projectile
import com.djgamarra.blaster.utils.TickCounter
import java.awt.Graphics2D
import java.awt.event.MouseEvent

class GameScene : Scene() {
    private val player = Player()

    private val projectilesTickCounter = TickCounter(20)
    private var projectiles = listOf<Projectile>()
    private val opponentsBlock = OpponentsBlock()

    override fun mouseMoved(e: MouseEvent) {
        player.moveTo(e.x)
    }

    override fun tick() {
        if (projectilesTickCounter.tick()) {
            shoot()
        }

        opponentsBlock.tick()
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        projectiles.forEach { it.draw(g, ctx) }
        opponentsBlock.draw(g, ctx)
        player.draw(g, ctx)
    }

    private fun shoot() = synchronized(projectiles) {
        projectiles += Projectile(player.x, onDeath = {
            removeProjectile(this)
        })
    }

    private fun removeProjectile(projectile: Projectile) = synchronized(projectiles) {
        projectiles = projectiles.filter { it != projectile }
    }
}

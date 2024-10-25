package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.components.Opponent
import com.djgamarra.blaster.scenes.components.Player
import com.djgamarra.blaster.scenes.components.Projectile
import com.djgamarra.blaster.utils.TickCounter
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D
import java.awt.event.MouseEvent

class GameScene : Scene() {
    private val player = Player()

    private val projectilesTickCounter = TickCounter(20)
    private var projectiles = listOf<Projectile>()
    private var opponents = buildList {
        var image = 0

        for (i in 0..3) {
            for (j in 0..14) {
                add(
                    Opponent(
                        image % 3,
                        ViewUtils.spacing(1) + j * (ViewUtils.spacing(1) + Opponent.WIDTH),
                        ViewUtils.spacing(1) + i * (ViewUtils.spacing(1) + Opponent.HEIGHT)
                    )
                )
            }
            image++
        }
    }

    override fun mouseMoved(e: MouseEvent) {
        player.moveTo(e.x)
    }

    override fun tick() {
        if (projectilesTickCounter.tick()) {
            shoot()
        }
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        projectiles.forEach { it.draw(g, ctx) }
        opponents.forEach { it.draw(g, ctx) }
        player.draw(g, ctx)
    }

    private fun shoot() {
        synchronized(projectiles) {
            projectiles += Projectile(player.x, onDeath = {
                removeProjectile(this)
            })
        }
    }

    private fun removeProjectile(projectile: Projectile) {
        synchronized(projectiles) {
            projectiles = projectiles.filter { it != projectile }
        }
    }
}

package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.components.Button
import com.djgamarra.blaster.scenes.components.Opponent
import com.djgamarra.blaster.scenes.components.OpponentsBlock
import com.djgamarra.blaster.scenes.components.Player
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D
import java.awt.event.MouseEvent

class GameScene : Scene() {
    private val opponentsBlock = OpponentsBlock()
    private val player = Player()

    private val exitButton =
        Button("Exit", x = ViewUtils.spacing(1), y = ViewUtils.VIEWPORT_HEIGHT - 50 - ViewUtils.spacing(), onClick = {
            RootScene.changeScene(StartScene())
        })
    private val gameOverLabel = Button("Game Over", fontSize = 50F, active = true, x = 0, y = 0)
    private val endPointsLabel = Button("Points: 0", fontSize = 30F, active = true, x = 0, y = 60)

    private var dead = false
    private var points = 0

    override fun mouseClicked(e: MouseEvent) {
        exitButton.mouseClicked(e)
    }

    override fun mouseMoved(e: MouseEvent) {
        exitButton.mouseMoved(e)

        if (dead) {
            return
        }

        player.mouseMoved(e)
    }

    override fun tick() {
        if (dead) {
            return
        }

        checkCollisions()

        player.tick()
        opponentsBlock.tick()
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        player.draw(g, ctx)
        opponentsBlock.draw(g, ctx)
        exitButton.draw(g, ctx)

        g.font = ViewUtils.DEFAULT_FONT.deriveFont(20F)
        g.drawString("$points", ViewUtils.spacing(1), ViewUtils.spacing(1) + 20)

        if (dead) {
            gameOverLabel.draw(g, ctx)
            endPointsLabel.draw(g, ctx)
        }
    }

    private fun checkCollisions() {
        opponentsBlock.opponents.forEach { opponent ->
            if (opponent.getY() + Opponent.HEIGHT >= player.y) {
                gameOver()
                return
            }

            val collidingProjectile = player.projectiles.find { projectile -> opponent.checkCollision(projectile) }

            if (collidingProjectile != null) {
                player.removeProjectile(collidingProjectile)
                opponentsBlock.removeOpponent(opponent)
                points++
            }
        }
    }

    private fun gameOver() {
        endPointsLabel.label = "Points: $points"
        dead = true
        opponentsBlock.gameOver()
    }
}

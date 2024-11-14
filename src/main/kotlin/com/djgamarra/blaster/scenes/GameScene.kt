package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.components.Button
import com.djgamarra.blaster.scenes.components.Opponent
import com.djgamarra.blaster.scenes.components.OpponentsBlock
import com.djgamarra.blaster.scenes.components.Player
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.MouseEvent

/**
 * This is the main game scene.
 */
class GameScene : Scene() {
    private val opponentsBlock = OpponentsBlock()
    private val player = Player()

    private val exitButton =
        Button("Exit", x = ViewUtils.spacing(1), y = ViewUtils.VIEWPORT_HEIGHT - 50 - ViewUtils.spacing(), onClick = {
            RootScene.changeScene(StartScene())
        })
    private val gameOverLabel = Button("Game Over", fontSize = 50F, active = true, x = 0, y = 0)
    private val endPointsLabel = Button("Points: 0", fontSize = 30F, active = true, x = 0, y = 60)

    /**
     * This will be true when the game is over.
     */
    private var dead = false

    /**
     * Every opponent killed sums +1.
     */
    private var points = 0

    override fun mouseClicked(e: MouseEvent) {
        exitButton.mouseClicked(e)
    }

    override fun mouseMoved(e: MouseEvent) {
        exitButton.mouseMoved(e)

        /**
         * When the game is over the player will stop
         * receiving mouse events.
         */
        if (dead) {
            return
        }

        player.mouseMoved(e)
    }

    /**
     * Her we check for collisions and propagate
     * ticks.
     */
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

        g.color = Color.WHITE
        g.font = ViewUtils.DEFAULT_FONT.deriveFont(20F)
        g.drawString("$points", ViewUtils.spacing(1), ViewUtils.spacing(1) + 20)

        /**
         * When the game is over we will
         * draw the game over label.
         */
        if (dead) {
            gameOverLabel.draw(g, ctx)
            endPointsLabel.draw(g, ctx)
        }
    }

    private fun checkCollisions() {
        opponentsBlock.opponents.forEach { opponent ->
            /**
             * If there is an opponent touching the player
             * the game is over.
             */
            if (opponent.getY() + Opponent.HEIGHT >= player.y) {
                gameOver()
                return
            }

            /**
             * If there is a projectile touching an opponent
             * we kill the opponent and remove the projectile.
             */
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

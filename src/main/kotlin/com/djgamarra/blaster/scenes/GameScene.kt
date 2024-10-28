package com.djgamarra.blaster.scenes

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.components.OpponentsBlock
import com.djgamarra.blaster.scenes.components.Player
import java.awt.Graphics2D
import java.awt.event.MouseEvent

class GameScene : Scene() {
    private val opponentsBlock = OpponentsBlock()
    private val player = Player(opponentsBlock)

    override fun mouseMoved(e: MouseEvent) {
        player.mouseMoved(e)
    }

    override fun tick() {
        player.tick()
        opponentsBlock.tick()
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        player.draw(g, ctx)
        opponentsBlock.draw(g, ctx)
    }
}

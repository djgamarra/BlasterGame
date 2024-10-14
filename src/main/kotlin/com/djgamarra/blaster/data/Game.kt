package com.djgamarra.blaster.data

import java.awt.Graphics2D

object Game {
    val player = Player()

    fun draw(g: Graphics2D) {
        player.draw(g)
    }
}
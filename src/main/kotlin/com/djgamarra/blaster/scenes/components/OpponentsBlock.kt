package com.djgamarra.blaster.scenes.components

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.utils.AnimationValue
import com.djgamarra.blaster.utils.EaseFunction
import com.djgamarra.blaster.utils.TickCounter
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D
import kotlin.math.max

class OpponentsBlock : Scene() {
    private val xAnimation = AnimationValue(
        0,
        X_ANIMATION_CONSTRAINT_END,
        ANIMATION_DURATION,
        EaseFunction.IN_OUT,
        onAnimationEnded = {
            if (!gameFinished) {
                startReverse()
            }
        }
    ).apply { start() }
    private val yAnimation = AnimationValue(
        0,
        Y_ANIMATION_CONSTRAINT_END,
        ANIMATION_DURATION / 5,
        EaseFunction.IN_OUT
    )

    private val opponentsTickCounter = TickCounter(NEW_OPPONENTS_ROW_TICK)

    private var opponentsRow = 0
    var opponents = listOf<Opponent>()
        private set

    private var gameFinished = false

    init {
        addOpponentsRow()
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        opponents.forEach { it.draw(g, ctx) }
    }

    override fun tick() {
        if (!gameFinished and opponentsTickCounter.tick()) {
            opponentsTickCounter.tickCounter -= max(
                NEW_OPPONENTS_ROW_TICK - opponentsRow * 5,
                NEW_OPPONENTS_ROW_TICK / 6
            )
            addOpponentsRow()
            yAnimation.startFromCurrent()
        }
    }

    fun removeOpponent(opponent: Opponent) = synchronized(opponents) {
        opponents = opponents.filter { it != opponent }
    }

    fun gameOver() {
        gameFinished = true

        xAnimation.enabled = false
        yAnimation.enabled = false
    }

    private fun addOpponentsRow(): Unit = synchronized(opponents) {
        opponents = buildList {
            addAll(opponents)

            for (j in 0..<OPPONENTS_NUMBER) {
                add(
                    Opponent(
                        opponentsRow % 3,
                        ViewUtils.spacing(1) + j * (ViewUtils.spacing(1) + Opponent.WIDTH),
                        ViewUtils.spacing(1) - opponentsRow * (ViewUtils.spacing(1) + Opponent.HEIGHT),
                        xAnimation,
                        yAnimation
                    )
                )
            }
        }

        opponentsRow++
    }

    companion object {
        private const val OPPONENTS_NUMBER = 15

        private val X_ANIMATION_CONSTRAINT_END = (ViewUtils.spacing(1) + Opponent.WIDTH) * 3 - ViewUtils.spacing(1)
        private val Y_ANIMATION_CONSTRAINT_END = ViewUtils.spacing(1) + Opponent.HEIGHT

        private const val ANIMATION_DURATION = 1000

        private const val NEW_OPPONENTS_ROW_TICK = 300
    }
}

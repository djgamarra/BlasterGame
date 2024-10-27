package com.djgamarra.blaster.scenes.components

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.utils.AnimationValue
import com.djgamarra.blaster.utils.EaseFunction
import com.djgamarra.blaster.utils.TickCounter
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D

class OpponentsBlock : Scene() {
    private val xAnimation = AnimationValue(
        0,
        X_ANIMATION_CONSTRAINT_END,
        ANIMATION_DURATION,
        EaseFunction.IN_OUT,
        onAnimationEnded = { startReverse() }
    ).apply { start() }
    private val yAnimation = AnimationValue(
        0,
        Y_ANIMATION_CONSTRAINT_END,
        ANIMATION_DURATION,
        EaseFunction.IN_OUT
    )

    private val opponentsTickCounter = TickCounter(200)

    private var opponentsRow = 0
    var opponents = listOf<Opponent>()
        private set

    init {
        addOpponentsRow()
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        opponents.forEach { it.draw(g, ctx) }
    }

    override fun tick() {
        if (opponentsTickCounter.tick()) {
            addOpponentsRow()
            yAnimation.startFromCurrent()
        }
    }

    private fun addOpponentsRow(): Unit = synchronized(opponents) {
        if (opponentsRow == 9) {
            return
        }

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
    }
}

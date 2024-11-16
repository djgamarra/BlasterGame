package com.djgamarra.blaster.scenes.components

import com.djgamarra.blaster.data.RenderContext
import com.djgamarra.blaster.scenes.Scene
import com.djgamarra.blaster.utils.AnimationValue
import com.djgamarra.blaster.utils.EaseFunction
import com.djgamarra.blaster.utils.TickCounter
import com.djgamarra.blaster.utils.ViewUtils
import java.awt.Graphics2D
import kotlin.math.max

/**
 * This class represents the block of opponents. It
 * is responsible for animating the entire block and
 * add new rows of opponents.
 */
class OpponentsBlock : Scene() {
    /**
     * X animation for the block. Automatically started
     * when the block gets initialized. It will move the
     * block of opponents with ease from right to left.
     *
     * When the animation ends it will automatically start
     * itself reversed.
     */
    private val xAnimation = AnimationValue(
        0,
        X_ANIMATION_CONSTRAINT_END,
        ANIMATION_DURATION,
        EaseFunction.IN_OUT,
        onAnimationEnded = {
            if (!gameFinished) {
                /**
                 * This will cause the block to go to right
                 * when gets to the left, and left when gets
                 * to the right.
                 */
                startReverse()
            }
        }
    ).apply { start() }

    /**
     * Y animation for the block. It will be used
     * everytime a new row of opponents is added to
     * make them go one row down.
     */
    private val yAnimation = AnimationValue(
        0,
        Y_ANIMATION_CONSTRAINT_END,
        ANIMATION_DURATION / 5,
        EaseFunction.IN_OUT
    )

    /**
     * Helper to add new rows of opponents each n ticks.
     */
    private val opponentsTickCounter = TickCounter(NEW_OPPONENTS_ROW_TICK)

    /**
     * Current row of opponents. It will be used to
     * calculate the initial Y position of the new
     * rows that will be added.
     */
    private var opponentsRow = 0

    /**
     * List of opponents.
     */
    var opponents = listOf<Opponent>()
        private set

    /**
     * When the game is over this will be true.
     */
    private var gameFinished = false

    init {
        /**
         * Initially we will add just one opponents row.
         */
        addOpponentsRow()
    }

    override fun draw(g: Graphics2D, ctx: RenderContext) {
        opponents.forEach { it.draw(g, ctx) }
    }

    override fun tick() {
        if (!gameFinished and opponentsTickCounter.tick()) {
            /**
             * Here we reduce the number of ticks required
             * to dispatch a new row of opponents so that
             * the game gets harder and harder.
             *
             * This tick counter is meant to go from [NEW_OPPONENTS_ROW_TICK]
             * to a sixth of it.
             */
            opponentsTickCounter.tickCounter -= max(
                NEW_OPPONENTS_ROW_TICK - opponentsRow * 5,
                NEW_OPPONENTS_ROW_TICK / 6
            )
            addOpponentsRow()
            yAnimation.startFromCurrent()
        }
    }

    /**
     * Removes an opponent when it gets killed
     * from the list.
     */
    fun removeOpponent(opponent: Opponent) = synchronized(opponents) {
        opponents = opponents.filter { it != opponent }
    }

    /**
     * When the game is over we just set [gameFinished]
     * equals to true and disable the animations.
     */
    fun gameOver() {
        gameFinished = true

        xAnimation.enabled = false
        yAnimation.enabled = false
    }

    /**
     * This function adds a new row of opponents.
     */
    private fun addOpponentsRow(): Unit = synchronized(opponents) {
        opponents = buildList {
            /**
             * First we add all the current opponents.
             */
            addAll(opponents)

            /**
             * Then we add [OPPONENTS_NUMBER] opponents
             * with coordinates like a single row.
             */
            for (j in 0..<OPPONENTS_NUMBER) {
                /**
                 * We add a new opponent. The [opponentsRow]
                 * is used to know:
                 *
                 * 1. Which look will be used for the entire row.
                 * 2. The Y coordinate.
                 */
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
        /**
         * Every row will be of [OPPONENTS_NUMBER] opponents.
         */
        private const val OPPONENTS_NUMBER = 15

        /**
         * This is the right boundary.
         */
        private val X_ANIMATION_CONSTRAINT_END = (ViewUtils.spacing(1) + Opponent.WIDTH) * 3 - ViewUtils.spacing(1)

        /**
         * This is the left boundary.
         */
        private val Y_ANIMATION_CONSTRAINT_END = ViewUtils.spacing(1) + Opponent.HEIGHT

        /**
         * Duration of the animations on X and Y.
         */
        private const val ANIMATION_DURATION = 1000

        /**
         * Initially every [NEW_OPPONENTS_ROW_TICK] ticks
         * a new row will appear.
         */
        private const val NEW_OPPONENTS_ROW_TICK = 300
    }
}

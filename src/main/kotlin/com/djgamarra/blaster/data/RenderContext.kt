package com.djgamarra.blaster.data

/**
 * This class holds basic info useful for rendering.
 * Each render would instantiate a new one.
 *
 * @property[renderTime] Holds the current nano time. It is used later
 * in every time based data on render. It serves to keep multiple things
 * in sync, so that different items on screen can be rendered with a
 * common time base even if there is a difference in the time they were
 * rendered (ns or even ms).
 */
data class RenderContext(val renderTime: Long)

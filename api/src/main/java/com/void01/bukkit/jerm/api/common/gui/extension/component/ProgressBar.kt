package com.void01.bukkit.jerm.api.common.gui.extension.component

import com.void01.bukkit.jerm.api.common.gui.component.Texture

interface ProgressBar {
    enum class Orientation {
        HORIZONTAL, // VERTICAL
    }

    interface AnimationDurationFunction {
        fun calculate(deltaProgress: Double): Double
    }

    interface AnimationEaseFunction {
        fun calculate(originalProgress: Double): Double
    }

    val texture: Texture

    fun setAnimationEaseFunction(animationEaseFunction: AnimationEaseFunction)

    fun getAnimationEaseFunction(): AnimationEaseFunction

    fun setAnimationDurationFunction(animationDurationFunction: AnimationDurationFunction)

    fun getAnimationDurationFunction(): AnimationDurationFunction

    fun setMaxWidth(maxWidth: String)

    fun getMaxWidth(): String

    fun setBaseEndU(baseEndU: Int)

    fun getBaseEndU(): Int

    fun setOrientation(orientation: Orientation)

    fun getOrientation(): Orientation

    fun getProgress(): Double

    fun setProgress(progress: Double)

    fun setProgressSmoothly(progress: Double)
}
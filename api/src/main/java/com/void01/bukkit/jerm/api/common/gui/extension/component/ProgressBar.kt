package com.void01.bukkit.jerm.api.common.gui.extension.component

import com.void01.bukkit.jerm.api.common.gui.component.Texture

interface ProgressBar {
    enum class Orientation {
        HORIZONTAL,
        VERTICAL
    }

    interface AnimationDurationFunction {
        fun calculate(deltaProgress: Double): Double
    }

    interface AnimationEaseFunction {
        fun calculate(originalProgress: Double): Double
    }

    val texture: Texture
    var orientation: Orientation
    var animationEaseFunction: AnimationEaseFunction
    var animationDurationFunction: AnimationDurationFunction
    var maxWidth: String?
    var maxHeight: String?
    var maxEndU: Int?
    var maxEndV: Int?

    fun setProgress(progress: Double)

    fun setProgressSmoothly(progress: Double)
}
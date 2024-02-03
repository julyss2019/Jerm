package com.void01.bukkit.jerm.api.common.gui.extension.component

interface ProgressBar {
    interface DurationFunction {
        fun calculate(deltaProgress: Double): Double
    }

    interface EaseFunction {
        fun calculate(originalProgress: Double): Double
    }

    var easeFunction: EaseFunction
    var durationFunction: DurationFunction

    fun getProgress(): Double

    fun setProgress(progress: Double)

    fun setProgressSmoothly(progress: Double)
}
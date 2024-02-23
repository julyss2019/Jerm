package com.void01.bukkit.jerm.core.gui.extension

import com.void01.bukkit.jerm.api.common.gui.component.Texture
import com.void01.bukkit.jerm.api.common.gui.extension.component.ProgressBar
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import kotlin.math.pow

class ProgressBarImpl(
    private val plugin: JavaPlugin,
    override val texture: Texture,
) : ProgressBar {
    private var maxWidth: String = "50"
    private var baseEndU: Int = -1
    private var orientation: ProgressBar.Orientation = ProgressBar.Orientation.HORIZONTAL
    private var animationDurationFunction: ProgressBar.AnimationDurationFunction =
        object : ProgressBar.AnimationDurationFunction {
            override fun calculate(deltaProgress: Double): Double {
                return 2.4 - 1.4 * deltaProgress
            }
        }
    private var animationEaseFunction: ProgressBar.AnimationEaseFunction = object : ProgressBar.AnimationEaseFunction {
        override fun calculate(originalProgress: Double): Double {
            return 1 - (1 - originalProgress).pow(3.0)
        }
    }
    private var bukkitTask: BukkitTask? = null

    private var currentProgress: Double = 0.0
    private var lastDeltaProgress: Double = 0.0
    private var startTime: Long = -1

    init {
        setWidthByProgress(0.0)
    }

    private fun setWidthByProgress(progress: Double) {
        texture.handle.width = "($maxWidth)*$progress"

        if (baseEndU != -1) {
            texture.handle.endU = "${baseEndU * progress}"
        }
    }

    private fun flushRapidly() {
        if (bukkitTask != null) {
            bukkitTask!!.cancel()
            currentProgress += lastDeltaProgress
            setWidthByProgress(currentProgress)
        }
    }

    override fun setAnimationEaseFunction(animationEaseFunction: ProgressBar.AnimationEaseFunction) {
        flushRapidly()
        this.animationEaseFunction = animationEaseFunction
    }

    override fun getAnimationEaseFunction(): ProgressBar.AnimationEaseFunction {
        return animationEaseFunction
    }

    override fun setAnimationDurationFunction(animationDurationFunction: ProgressBar.AnimationDurationFunction) {
        flushRapidly()
        this.animationDurationFunction = animationDurationFunction
    }

    override fun getAnimationDurationFunction(): ProgressBar.AnimationDurationFunction {
        return animationDurationFunction
    }

    override fun setMaxWidth(maxWidth: String) {
        flushRapidly()
        this.maxWidth = maxWidth
    }

    override fun getMaxWidth(): String {
        return maxWidth
    }

    override fun setBaseEndU(baseEndU: Int) {
        flushRapidly()
        this.baseEndU = baseEndU
    }

    override fun getBaseEndU(): Int {
        return baseEndU
    }

    override fun setOrientation(orientation: ProgressBar.Orientation) {
        flushRapidly()
        this.orientation = orientation
    }

    override fun getOrientation(): ProgressBar.Orientation {
        return orientation
    }

    override fun getProgress(): Double {
        return currentProgress
    }

    override fun setProgress(progress: Double) {
        flushRapidly()
        setWidthByProgress(progress)
    }

    override fun setProgressSmoothly(progress: Double) {
        if (lastDeltaProgress != getDeltaProgress(progress)) {
            flushRapidly()
        } else {
            return
        }

        startTime = System.currentTimeMillis()
        lastDeltaProgress = getDeltaProgress(progress)

        if (lastDeltaProgress != 0.0) {
            bukkitTask = object : BukkitRunnable() {
                override fun run() {
                    val durationSeconds = animationDurationFunction.calculate(lastDeltaProgress)
                    val elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000.0
                    val originalProgress = (elapsedSeconds / durationSeconds).coerceAtMost(1.0)
                    val easeProgress = animationEaseFunction.calculate(originalProgress)

                    setWidthByProgress(currentProgress + easeProgress * lastDeltaProgress)

                    if (elapsedSeconds > durationSeconds) {
                        currentProgress += lastDeltaProgress
                        bukkitTask = null
                        cancel()
                    }
                }
            }.runTaskTimer(plugin, 0L, 0L)
        }
    }

    private fun getDeltaProgress(progress: Double): Double {
        return progress - currentProgress
    }
}
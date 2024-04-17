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
    orientation: ProgressBar.Orientation
) : ProgressBar {
    override var orientation: ProgressBar.Orientation = orientation
        set(value) {
            flush()
            field = value
        }
    override var maxWidth: String = texture.handle.width
        set(value) {
            flush()
            field = value
        }
    override var maxHeight: String = texture.handle.height
        set(value) {
            flush()
            field = value
        }
    override var maxEndU: Int? = null
        set(value) {
            flush()
            field = value
        }
    override var maxEndV: Int? = null
        set(value) {
            flush()
            field = value
        }
    override var animationDurationFunction: ProgressBar.AnimationDurationFunction = object : ProgressBar.AnimationDurationFunction {
        override fun calculate(deltaProgress: Double): Double {
            return 2.4 - 1.4 * deltaProgress
        }
    }
        set(value) {
            flush()
            field = value
        }
    override var animationEaseFunction: ProgressBar.AnimationEaseFunction = object : ProgressBar.AnimationEaseFunction {
        override fun calculate(originalProgress: Double): Double {
            return 1 - (1 - originalProgress).pow(3.0)
        }
    }
        set(value) {
            flush()
            field = value
        }

    private var bukkitTask: BukkitTask? = null
    private var currentProgress: Double = 0.0
    private var lastDeltaProgress: Double = 0.0
    private var startTime: Long = -1

    init {
        setComponentProgress(0.0)
    }

    private fun setComponentProgress(progress: Double) {
        if (orientation == ProgressBar.Orientation.HORIZONTAL) {
            setHorizontal(progress)
        } else {
            setVertical(progress)
        }
    }

    private fun setHorizontal(progress: Double) {
        texture.handle.width = "($maxWidth)*$progress"

        if (maxEndU != null) {
            texture.handle.endU = "${maxEndU!! * progress}"
        }
    }

    private fun setVertical(progress: Double) {
        texture.handle.height = "($maxHeight)*$progress"

        if (maxEndU != null) {
            texture.handle.endV = "${maxEndV!! * progress}"
        }
    }

    private fun flush() {
        if (bukkitTask != null) {
            bukkitTask!!.cancel()
            currentProgress += lastDeltaProgress
            setComponentProgress(currentProgress)
        }
    }

    override fun setProgress(progress: Double) {
        flush()
        setComponentProgress(progress)
    }

    private fun getDeltaProgress(progress: Double): Double {
        return progress - currentProgress
    }

    override fun setProgressSmoothly(progress: Double) {
        if (lastDeltaProgress != getDeltaProgress(progress)) {
            flush()
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

                    setComponentProgress(currentProgress + easeProgress * lastDeltaProgress)

                    if (elapsedSeconds > durationSeconds) {
                        currentProgress += lastDeltaProgress
                        bukkitTask = null
                        cancel()
                    }
                }
            }.runTaskTimer(plugin, 0L, 0L)
        }
    }
}
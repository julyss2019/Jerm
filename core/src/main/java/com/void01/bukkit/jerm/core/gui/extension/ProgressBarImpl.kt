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
    override var orientation: ProgressBar.Orientation
) : ProgressBar {
    override var maxWidth: String? = null
    override var maxHeight: String? = null
    override var maxEndU: Int? = null
    override var maxEndV: Int? = null
    override var animationDurationFunction: ProgressBar.AnimationDurationFunction =
        object : ProgressBar.AnimationDurationFunction {
            override fun calculate(deltaProgress: Double): Double {
                return .6
            }
        }
        set(value) {
            stopTask()
            field = value
        }
    override var animationEaseFunction: ProgressBar.AnimationEaseFunction = object : ProgressBar.AnimationEaseFunction {
        override fun calculate(originalProgress: Double): Double {
            return 1 - (1 - originalProgress).pow(3.0)
        }
    }
        set(value) {
            stopTask()
            field = value
        }

    // 缓动任务
    private var smoothTask: BukkitTask? = null

    // 当前进度
    private var currentProgress: Double = 0.0

    // 差异进度
    private var lastDeltaProgress: Double = 0.0

    // 差异开始时间
    private var startTime: Long = -1

    private fun setTextureProgress(progress: Double) {
        require(progress in 0.0..1.0) {
            "Progress must be between 0 and 1.0, actual: $progress"
        }

        if (orientation == ProgressBar.Orientation.HORIZONTAL) {
            setHorizontal(progress)
        } else {
            setVertical(progress)
        }
    }

    private fun setHorizontal(progress: Double) {
        if (maxWidth == null) {
            throw IllegalArgumentException("The maxWidth must be non-null")
        }

        texture.handle.width = "($maxWidth)*$progress"

        if (maxEndU != null) {
            texture.handle.endU = "${maxEndU!! * progress}"
        }
    }

    private fun setVertical(progress: Double) {
        if (maxHeight == null) {
            throw IllegalArgumentException("The maxHeight must be non-null")
        }

        texture.handle.height = "($maxHeight)*$progress"

        if (maxEndV != null) {
            texture.handle.endV = "${maxEndV!! * progress}"
        }
    }

    private fun stopTask() {
        if (smoothTask != null) {
            smoothTask!!.cancel()
            currentProgress += lastDeltaProgress
            smoothTask = null
        }
    }

    private fun flushCurrentProgress() {
        setTextureProgress(currentProgress)
    }

    private fun getDeltaProgress(progress: Double): Double {
        return progress - currentProgress
    }

    override fun setProgress(progress: Double) {
        stopTask()
        setTextureProgress(progress)
    }

    override fun setProgressSmoothly(progress: Double) {
        stopTask()
        flushCurrentProgress()

        val delta = getDeltaProgress(progress)

        if (delta != 0.0) {
            startTime = System.currentTimeMillis()
            lastDeltaProgress = delta
            smoothTask = object : BukkitRunnable() {
                override fun run() {
                    val durationSeconds = animationDurationFunction.calculate(lastDeltaProgress)
                    val elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000.0
                    val originalProgress = (elapsedSeconds / durationSeconds).coerceAtMost(1.0)
                    val easeProgress = animationEaseFunction.calculate(originalProgress)

                    setTextureProgress(currentProgress + easeProgress * lastDeltaProgress)

                    if (elapsedSeconds > durationSeconds) {
                        currentProgress += lastDeltaProgress
                        smoothTask = null
                        cancel()
                    }
                }
            }.runTaskTimer(plugin, 0L, 0L)
        }
    }
}
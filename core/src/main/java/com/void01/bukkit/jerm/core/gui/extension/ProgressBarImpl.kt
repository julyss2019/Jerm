package com.void01.bukkit.jerm.core.gui.extension

import com.void01.bukkit.jerm.api.common.gui.component.Texture
import com.void01.bukkit.jerm.api.common.gui.extension.component.ProgressBar
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import kotlin.math.pow

class ProgressBarImpl(
    private val plugin: JavaPlugin,
    private val texture: Texture,
    private val maxWidth: String,
    private val baseEndU: Int = -1
) : ProgressBar {
    private var bukkitTask: BukkitTask? = null
    private var lastProgress: Double = 0.0
    private var deltaProgress: Double = 0.0
    private var startTime: Long = -1
    override var durationFunction = object : ProgressBar.DurationFunction {
        override fun calculate(deltaProgress: Double): Double {
            return 2.4 - 1.4 * deltaProgress
        }
    }
    override var easeFunction = object : ProgressBar.EaseFunction {
        override fun calculate(originalProgress: Double): Double {
            return 1 - (1 - originalProgress).pow(3.0)
        }
    }

    init {
        setWidthByProgress(0.0)
    }

    private fun setWidthByProgress(progress: Double) {
        texture.handle.width = "($maxWidth)*$progress"

        if (baseEndU != -1) {
            texture.handle.endU = "${baseEndU * progress}"
        }
    }

    override fun getProgress(): Double {
        return lastProgress
    }

    override fun setProgress(progress: Double) {
        setWidthByProgress(progress)
    }

    override fun setProgressSmoothly(progress: Double) {
        if (bukkitTask != null) {
            bukkitTask!!.cancel()
            lastProgress += deltaProgress // 直接完成上次的 delta 目标
        }

        startTime = System.currentTimeMillis()
        deltaProgress = progress - lastProgress

        if (deltaProgress != 0.0) {
            bukkitTask = object : BukkitRunnable() {
                override fun run() {
                    val durationSeconds = durationFunction.calculate(deltaProgress)
                    val elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000.0
                    val originalProgress = (elapsedSeconds / durationSeconds).coerceAtMost(1.0)
                    val easeProgress = easeFunction.calculate(originalProgress)

                    setWidthByProgress(lastProgress + easeProgress * deltaProgress)

                    if (elapsedSeconds > durationSeconds) {
                        lastProgress += deltaProgress
                        bukkitTask = null
                        cancel()
                    }
                }
            }.runTaskTimer(plugin, 0L, 0L)
        }
    }
}
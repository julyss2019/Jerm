package com.void01.bukkit.jerm.api.common.animation

import org.bukkit.entity.Entity
import org.bukkit.entity.Player

interface Animation {
    val id: String

    /**
     * 播放动画
     * speed = 1, reverse = false
     * @param viewer 观众
     * @param performer 目标, 默认为观众
     */
    fun play(performer: Entity, viewer: Player) {
        play(performer, viewer, 1F, false)
    }

    /**
     * 播放动画
     * @param viewer 观众
     * @param performer 目标, 默认为观众
     * @param speed 速度, 默认为 1
     * @param reverse 反转
     */
    fun play(performer: Entity, viewer: Player, speed: Float, reverse: Boolean)
}

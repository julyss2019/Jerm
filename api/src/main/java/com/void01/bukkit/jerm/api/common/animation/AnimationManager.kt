package com.void01.bukkit.jerm.api.common.animation

import org.bukkit.entity.Entity
import org.bukkit.entity.Player

interface AnimationManager {
    fun playAnimation(performer: Entity, viewers: List<Player>, animationId: String)

    fun playAnimation(performer: Entity, viewers: List<Player>, animationId: String, speed: Float)

    fun playAnimation(performer: Entity, viewers: List<Player>, animationId: String, speed: Float, reverse: Boolean)
}

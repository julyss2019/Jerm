package com.void01.bukkit.jerm.api.common.animation


import org.bukkit.entity.Entity
import org.bukkit.entity.Player

interface AnimationManager {
    val animations: List<Animation>

    fun getAnimationLength(id: String): Double

    fun playAnimation(performer: Entity, viewers: List<Player>, animationId: String)

    fun playAnimation(performer: Entity, viewers: List<Player>, animationId: String, speed: Float)

    fun playAnimation(performer: Entity, viewers: List<Player>, preparedAnimation: PreparedAnimation)

    fun playAnimationToSelfAndNearbyPlayers(performer: Entity, animationId: String)

    fun playAnimationToSelfAndNearbyPlayers(performer: Entity, animationId: String, speed: Float)

    fun playAnimationToSelfAndNearbyPlayers(performer: Entity, preparedAnimation: PreparedAnimation)
}

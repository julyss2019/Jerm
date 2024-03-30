package com.void01.bukkit.jerm.api.common.animation

import com.void01.bukkit.jerm.api.common.Jerm
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

class PreparedAnimation(val animationId: String, val speed: Float, val reverse: Boolean) {
    fun play(performer:Entity, viewers: List<Player>) {
        Jerm.getAnimationManager().playAnimation(performer, viewers, this)
    }

    fun playToNearbyPlayers(performer:Entity) {
        Jerm.getAnimationManager().playAnimationToSelfAndNearbyPlayers(performer, this)
    }
}
package com.void01.bukkit.jerm.api.common.animation

interface AnimationManager {
    fun getAnimations(): List<Animation>

    fun getAnimation(id: String): Animation?
}

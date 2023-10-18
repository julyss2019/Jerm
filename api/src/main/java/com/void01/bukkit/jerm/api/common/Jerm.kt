package com.void01.bukkit.jerm.api.common

import com.void01.bukkit.jerm.api.common.animation.AnimationManager
import com.void01.bukkit.jerm.api.common.gui.GuiManager
import com.void01.bukkit.jerm.api.common.gui.GuiParser
import com.void01.bukkit.jerm.api.internal.Context
import com.void01.bukkit.jerm.api.common.player.JermPlayerManager

object Jerm {
    private lateinit var context: Context

    fun setContext(context: Context) {
        if (Jerm::context.isInitialized) {
            throw UnsupportedOperationException()
        }

        Jerm.context = context
    }

    fun getAnimationManager(): AnimationManager {
        return context.animationManager
    }

    fun getGuiManager(): GuiManager {
        return context.guiManager
    }

    fun getPlayerManager(): JermPlayerManager {
        return context.playerManager
    }

    fun getGuiParser(): GuiParser {
        return context.guiParser
    }
}
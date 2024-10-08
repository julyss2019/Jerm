package com.void01.bukkit.jerm.api.common

import com.void01.bukkit.jerm.api.common.animation.AnimationManager
import com.void01.bukkit.jerm.api.common.gui.GuiManager
import com.void01.bukkit.jerm.api.common.gui.GuiParser
import com.void01.bukkit.jerm.api.common.player.JermPlayerManager
import com.void01.bukkit.jerm.api.internal.Delegate

object Jerm {
    private lateinit var delegate: Delegate

    fun setContext(delegate: Delegate) {
        if (Jerm::delegate.isInitialized) {
            throw UnsupportedOperationException()
        }

        Jerm.delegate = delegate
    }

    fun getAnimationManager(): AnimationManager {
        return delegate.animationManager
    }

    fun getGuiManager(): GuiManager {
        return delegate.guiManager
    }

    fun getPlayerManager(): JermPlayerManager {
        return delegate.jermPlayerManager
    }

    fun getGuiParser(): GuiParser {
        return delegate.guiParser
    }
}
package com.void01.bukkit.jerm.api.common

import com.void01.bukkit.jerm.api.common.animation.AnimationManager
import com.void01.bukkit.jerm.api.common.gui.GuiManager
import com.void01.bukkit.jerm.api.common.gui.GuiParser
import com.void01.bukkit.jerm.api.common.player.JermPlayerManager
import com.void01.bukkit.jerm.api.internal.Context

object Jerm2 {
    private lateinit var context: Context

    @JvmStatic
    val animationManager: AnimationManager get() = context.animationManager

    @JvmStatic
    val guiManager: GuiManager get() = context.guiManager

    @JvmStatic
    val jermPlayerManager: JermPlayerManager get() = context.jermPlayerManager

    @JvmStatic
    val guiParser: GuiParser get() = context.guiParser

    fun setContext(context: Context) {
        if (Jerm2::context.isInitialized) {
            throw UnsupportedOperationException()
        }

        Jerm2.context = context
    }
}
package com.void01.bukkit.jerm.api.common

import com.void01.bukkit.jerm.api.common.animation.AnimationManager
import com.void01.bukkit.jerm.api.common.gui.GuiManager
import com.void01.bukkit.jerm.api.common.gui.GuiParser
import com.void01.bukkit.jerm.api.common.player.JermPlayerManager
import com.void01.bukkit.jerm.api.internal.Delegate

object Jerm2 {
    private var delegate: Delegate? = null

    @JvmStatic
    val animationManager: AnimationManager get() = delegate!!.animationManager

    @JvmStatic
    val guiManager: GuiManager get() = delegate!!.guiManager

    @JvmStatic
    val jermPlayerManager: JermPlayerManager get() = delegate!!.jermPlayerManager

    @JvmStatic
    val guiParser: GuiParser get() = delegate!!.guiParser

    fun debug(message: String) {
        delegate!!.debug(message)
    }

    fun info(message: String) {
        delegate!!.info(message)
    }

    fun warn(message: String) {
        delegate!!.warn(message)
    }

    fun setContext(delegate: Delegate) {
        if (this.delegate != null) {
            throw UnsupportedOperationException()
        }

        Jerm2.delegate = delegate
    }
}
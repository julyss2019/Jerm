package com.void01.bukkit.jerm.api.internal

import com.void01.bukkit.jerm.api.common.animation.AnimationManager
import com.void01.bukkit.jerm.api.common.gui.GuiManager
import com.void01.bukkit.jerm.api.common.gui.GuiParser
import com.void01.bukkit.jerm.api.common.player.JermPlayerManager

interface Context {
    val guiParser: GuiParser
    val guiManager: GuiManager
    val jermPlayerManager: JermPlayerManager
    val animationManager: AnimationManager
}
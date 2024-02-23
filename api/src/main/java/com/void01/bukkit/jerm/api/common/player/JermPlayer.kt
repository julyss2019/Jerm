package com.void01.bukkit.jerm.api.common.player

import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import com.void01.bukkit.jerm.api.common.gui.Gui
import org.bukkit.entity.Player

interface JermPlayer {
    var isScreenDebugEnabled: Boolean

    fun getUsingGuis() : List<Gui>

    fun isOnline(): Boolean

    fun getBukkitPlayer(): Player?

    fun getOnlineBukkitPlayer() : Player

    fun closeGuis()
    fun getUsingGui(handle: GermGuiScreen): Gui?
}
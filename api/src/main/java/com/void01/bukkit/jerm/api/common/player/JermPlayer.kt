package com.void01.bukkit.jerm.api.common.player

import com.void01.bukkit.jerm.api.common.gui.Gui
import org.bukkit.entity.Player

interface JermPlayer {
    val bukkitPlayer: Player

    fun getUsingGui(id: String): Gui

    fun getUsingGuiOrNull(id: String): Gui?

    fun getUsingGuis(): List<Gui>

    @Deprecated(message = "Player 现在在 JermPlayer 生命周期内永远在线")
    fun isOnline(): Boolean

    @Deprecated(message = "Player 现在在 JermPlayer 生命周期内永远在线")
    fun getOnlineBukkitPlayer(): Player
}
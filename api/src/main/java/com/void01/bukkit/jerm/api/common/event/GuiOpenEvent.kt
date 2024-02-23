package com.void01.bukkit.jerm.api.common.event

import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.player.JermPlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList


class GuiOpenEvent(val jermPlayer: JermPlayer, val gui: Gui) : Event() {
    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }
}
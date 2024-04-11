package com.void01.bukkit.jerm.core.listener

import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.player.JermPlayerManagerImpl
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener(plugin: JermPlugin) : Listener {
    private val jermPlayerManager = plugin.jermPlayerManager as JermPlayerManagerImpl

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        jermPlayerManager.unloadPlayer(event.player)
    }
}
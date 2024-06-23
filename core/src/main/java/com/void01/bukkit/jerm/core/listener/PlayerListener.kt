package com.void01.bukkit.jerm.core.listener

import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.player.JermPlayerManagerImpl
import com.void01.bukkit.voidframework.common.kotlin.toColored
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener(private val plugin: JermPlugin) : Listener {
    private val jermPlayerManager = plugin.jermPlayerManager as JermPlayerManagerImpl

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val bukkitPlayer = event.player

        jermPlayerManager.unloadJermPlayer(bukkitPlayer)
    }

    @EventHandler
    fun onPlayerLoginEvent(event: PlayerLoginEvent) {
        if (plugin.isLoading) {
            event.kickMessage = "&a[Jerm] &eLoading...".toColored()
            event.result = PlayerLoginEvent.Result.KICK_WHITELIST
        }
    }
}
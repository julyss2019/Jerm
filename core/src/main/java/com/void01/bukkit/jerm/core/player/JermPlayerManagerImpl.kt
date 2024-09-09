package com.void01.bukkit.jerm.core.player

import com.void01.bukkit.jerm.api.common.player.JermPlayerManager
import com.void01.bukkit.jerm.core.JermPlugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class JermPlayerManagerImpl(private val plugin: JermPlugin) : JermPlayerManager {
    private val jermPlayerMap: MutableMap<UUID, JermPlayerImpl> = ConcurrentHashMap()

    fun unloadJermPlayer(bukkitPlayer: Player) {
        jermPlayerMap.remove(bukkitPlayer.uniqueId)
    }

    fun isJermPlayerLoaded(bukkitPlayer: Player): Boolean {
        return jermPlayerMap.containsKey(bukkitPlayer.uniqueId)
    }

    override fun getJermPlayer(playerName: String): JermPlayerImpl {
        @Suppress("DEPRECATION")
        return getJermPlayer(Bukkit.getPlayer(playerName) ?: throw IllegalArgumentException("Player is offline"))
    }

    override fun getJermPlayer(bukkitPlayer: Player): JermPlayerImpl {
        require(bukkitPlayer.isOnline) { "Player is offline" }

        val uuid = bukkitPlayer.uniqueId

        if (!jermPlayerMap.containsKey(uuid)) {
            jermPlayerMap[uuid] = JermPlayerImpl(Bukkit.getPlayer(uuid)!!, plugin)
        }

        return jermPlayerMap[uuid]!!
    }
}

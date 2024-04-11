package com.void01.bukkit.jerm.core.player

import com.void01.bukkit.jerm.api.common.player.JermPlayer
import com.void01.bukkit.jerm.api.common.player.JermPlayerManager
import com.void01.bukkit.jerm.core.JermPlugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class JermPlayerManagerImpl(private val plugin: JermPlugin) : JermPlayerManager {
    private val jermPlayerImplMap: MutableMap<UUID, JermPlayerImpl> = ConcurrentHashMap()

    fun unloadPlayer(bukkitPlayer: Player) {
        jermPlayerImplMap.remove(bukkitPlayer.uniqueId)
    }

    override fun getJermPlayer(playerName: String): JermPlayer {
        return getPlayer(Bukkit.getPlayer(playerName) ?: throw IllegalArgumentException("Player is offline"))
    }

    override fun getJermPlayer(bukkitPlayer: Player): JermPlayer {
        val uuid = bukkitPlayer.uniqueId

        if (!jermPlayerImplMap.containsKey(uuid)) {
            jermPlayerImplMap[uuid] = JermPlayerImpl(bukkitPlayer, plugin)
        }

        return jermPlayerImplMap[uuid]!!
    }
}

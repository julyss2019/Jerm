package com.void01.bukkit.jerm.core.player

import com.void01.bukkit.jerm.core.JermPlugin
import com.github.julyss2019.bukkit.voidframework.common.Players
import com.void01.bukkit.jerm.api.common.player.JermPlayer
import com.void01.bukkit.jerm.api.common.player.JermPlayerManager
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class JermPlayerManagerImpl(private val plugin: JermPlugin) : JermPlayerManager {
    private val jermPlayerImplMap: MutableMap<UUID, JermPlayerImpl> = ConcurrentHashMap()

    fun forceUnloadPlayer(jermPlayerImpl: JermPlayerImpl) {
        forceUnloadPlayer(jermPlayerImpl.uuid)
    }

    fun forceUnloadPlayer(id: UUID) {
        jermPlayerImplMap.remove(id)
    }

    fun unloadPlayer(jermPlayerImpl: JermPlayerImpl) {
        unloadPlayer(jermPlayerImpl.uuid)
    }

    fun unloadPlayer(uuid: UUID) {
        if (!Players.isOnline(uuid)) {
            jermPlayerImplMap.remove(uuid)
        }
    }

    override fun getPlayer(playerName: String): JermPlayer {
        return getPlayer(Bukkit.getOfflinePlayer(playerName))
    }

    override fun getPlayer(offlinePlayer: OfflinePlayer): JermPlayer {
        return getPlayer(offlinePlayer.uniqueId)
    }

    override fun getPlayer(uuid: UUID): JermPlayer {
        if (!jermPlayerImplMap.containsKey(uuid)) {
            jermPlayerImplMap[uuid] = JermPlayerImpl(uuid, plugin)
        }

        return jermPlayerImplMap[uuid]!!
    }
}

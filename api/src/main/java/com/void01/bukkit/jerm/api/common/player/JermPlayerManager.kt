package com.void01.bukkit.jerm.api.common.player

import org.bukkit.OfflinePlayer
import java.util.UUID

interface JermPlayerManager {
    fun getPlayer(playerName: String): JermPlayer

    fun getPlayer(offlinePlayer: OfflinePlayer): JermPlayer

    fun getPlayer(uuid: UUID): JermPlayer
}
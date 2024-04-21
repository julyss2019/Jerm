package com.void01.bukkit.jerm.api.common.player

import org.bukkit.entity.Player

interface JermPlayerManager {
    @Deprecated("命名不规范", ReplaceWith("getJermPlayer(bukkitPlayer)"))
    fun getPlayer(bukkitPlayer: Player): JermPlayer = getJermPlayer(bukkitPlayer)

    fun getJermPlayer(playerName: String): JermPlayer

    fun getJermPlayer(bukkitPlayer: Player): JermPlayer
}
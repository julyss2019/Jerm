package com.void01.bukkit.jerm.core.util

import com.germ.germplugin.GermPlugin
import org.bukkit.Bukkit
import java.io.File

object GermUtils {
    fun reload() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "germplugin reload")
        MessageUtils.sendMessage(Bukkit.getConsoleSender(), "GermPlugin reloaded by Jerm")
    }

    fun getRootDataFolder(): File {
        return GermPlugin.getPlugin().dataFolder
    }

    fun getGuiFolder() : File {
        return File(getRootDataFolder(), "gui")
    }

    fun getAnimationFolder() : File {
        return File(getRootDataFolder(), "bend")
    }
}
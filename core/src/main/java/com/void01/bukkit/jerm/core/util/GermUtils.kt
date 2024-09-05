package com.void01.bukkit.jerm.core.util

import com.germ.germplugin.GermPlugin
import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import org.bukkit.Bukkit
import java.io.File
import java.util.*

object GermUtils {
    const val EMPTY_TEXTURE_PATH = "local<->textures/misc/air.png"

    fun <T : GermGuiPart<*>> cloneGuiPart(guiPart: GermGuiPart<T>): T {
        return guiPart.clone().apply {
            setIndexName("${indexName}_clone_${UUID.randomUUID()}")
        } as T
    }

    fun reload() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "germplugin reload")
        MessageUtils.sendMessage(Bukkit.getConsoleSender(), "GermPlugin reloaded by Jerm")
    }

    fun getRootDataFolder(): File {
        return GermPlugin.getPlugin().dataFolder
    }

    fun getGuiFolder(): File {
        return File(getRootDataFolder(), "gui")
    }

    fun getPath(germGuiPart: GermGuiPart<*>): String {
        val list = mutableListOf<String>()

        list.add(germGuiPart.indexName)

        var parent: GermGuiPart<*>? = germGuiPart.parentPart

        while (parent != null) {
            list.add(0, parent.indexName)
            parent = parent.parentPart
        }

        val path = list.joinToString(".")

        return path
    }
}
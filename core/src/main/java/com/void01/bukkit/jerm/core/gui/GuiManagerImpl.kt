package com.void01.bukkit.jerm.core.gui

import com.germ.germplugin.api.GermPacketAPI
import com.germ.germplugin.api.HudMessageType
import com.github.julyss2019.bukkit.voidframework.logging.logger.Logger
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.GuiManager
import com.void01.bukkit.jerm.api.common.gui.component.Texture
import com.void01.bukkit.jerm.api.common.gui.extension.component.ProgressBar
import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.gui.extension.ProgressBarImpl
import com.void01.bukkit.jerm.core.util.GermUtils
import org.bukkit.entity.Player
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption


class GuiManagerImpl(private val plugin: JermPlugin) : GuiManager {
    private val logger = plugin.pluginLogger
    private val guiParser = plugin.guiParser
    private val guiMap = mutableMapOf<String, Gui>()

    override fun reload() {
        guiMap.clear()
        load()
    }

    fun load() {
        GermUtils.getGuiFolder()
            .walk()
            .filter { it.isFile }
            .filter { it.name.endsWith(".yml") }
            .flatMap {
                try {
                    guiParser.parseGuis(it)
                } catch (ex: Exception) {
                    throw RuntimeException("在载入 ${it.absolutePath} 时出现了异常", ex)
                }
            }
            .forEach {
                guiMap[it.id] = it
            }

        logger.info("载入了 " + guiMap.size + " 个 GUI: ")
        getGuis().forEach {
                logger.info("- ${it.id}")
            }
    }

    override fun existsGui(id: String): Boolean {
        return guiMap.containsKey(id)
    }

    override fun saveGuiFile(inputStream: InputStream, fileName: String, overwrite: Boolean) {
        try {
            Files.copy(
                inputStream,
                File(GermUtils.getGuiFolder(), fileName).toPath(),
                if (overwrite) StandardCopyOption.REPLACE_EXISTING else StandardCopyOption.COPY_ATTRIBUTES
            )
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun createVerticalProgressBar(texture: Texture): ProgressBar {
        return ProgressBarImpl(plugin, texture, ProgressBar.Orientation.VERTICAL)
    }

    override fun createHorizontalProgressBar(texture: Texture): ProgressBar {
        return ProgressBarImpl(plugin, texture, ProgressBar.Orientation.VERTICAL)
    }

    override fun getGuis(): List<Gui> {
        return guiMap.values.map { it.clone() }
    }

    override fun getGuiOrThrow(id: String): Gui {
        return getGui(id) ?: throw IllegalArgumentException("GUI $id 不存在")
    }

    override fun getGui(id: String): Gui? {
        return guiMap[id]?.clone()
    }

    override fun sendHudMessage(bukkitPlayer: Player, anchorName: String, message: String) {
        GermPacketAPI.sendHudMessage(bukkitPlayer, HudMessageType.ANCHOR, "${anchorName}<->$message")
    }
}

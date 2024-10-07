package com.void01.bukkit.jerm.core.gui

import com.germ.germplugin.api.GermPacketAPI
import com.germ.germplugin.api.HudMessageType
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

    fun reload() {
        guiMap.clear()
        load()
    }

    fun load() {
        GermUtils.getGuiFolder()
            .walk()
            .filter { it.isFile }
            .filter { it.name.endsWith(".yml") }
            .filterNot { it.name == "default.yml" }
            .flatMap {
                try {
                    logger.debug("Parsing GUI: ${it.absolutePath}")
                    guiParser.parseGuis(it)
                } catch (ex: Exception) { // 萌芽似乎直接打印了异常，导致捕捉失败
                    throw RuntimeException("An exception occurred while parsing '${it.absolutePath}'", ex)
                }
            }
            .forEach {
                guiMap[it.id] = it
            }
        logger.info("${guiMap.size} GUI(s) loaded")
    }

    override fun existsGui(id: String): Boolean {
        return guiMap.containsKey(id)
    }

    override fun getGui2OrNull(id: String): Gui? {
        return guiMap[id]?.clone()
    }

    override fun getGui2(id: String): Gui {
        return getGui2OrNull(id) ?: throw IllegalArgumentException("GUI with ID '$id' not found")
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
        return ProgressBarImpl(plugin, texture, ProgressBar.Orientation.HORIZONTAL)
    }

    override fun getGuis(): List<Gui> {
        return guiMap.values.map { it.clone() }
    }

    @Deprecated("命名优化", replaceWith = ReplaceWith("getGui2(id)"))
    override fun getGuiOrThrow(id: String): Gui {
        return getGui2(id)
    }

    @Deprecated("命名优化", replaceWith = ReplaceWith("getGui2OrNull(id)"))
    override fun getGui(id: String): Gui? {
        return getGui2OrNull(id)
    }

    override fun sendHudMessage(bukkitPlayer: Player, anchorName: String, message: String) {
        GermPacketAPI.sendHudMessage(bukkitPlayer, HudMessageType.ANCHOR, "${anchorName}<->$message")
    }
}

package com.void01.bukkit.jerm.api.common.gui

import com.void01.bukkit.jerm.api.common.gui.component.Texture
import com.void01.bukkit.jerm.api.common.gui.extension.component.ProgressBar
import org.bukkit.entity.Player
import java.io.InputStream

interface GuiManager {
    /**
     * 是否存在 GUI
     */
    fun existsGui(id: String): Boolean

    /**
     * 重载
     */
    fun reload()

    /**
     * 获取 GUI，不存在则抛出异常
     */
    fun getGuiOrThrow(id: String): Gui

    /**
     * 获取 GUI，不存在则返回 null
     */
    fun getGui(id: String): Gui?

    /**
     * 获取所有 GUI
     */
    fun getGuis(): List<Gui>

    /**
     * 保存 GUI 文件到 GermPlugin 文件
     */
    fun saveGuiFile(inputStream: InputStream, fileName: String, overwrite: Boolean)

    fun createHorizontalProgressBar(texture: Texture): ProgressBar

    fun createVerticalProgressBar(texture: Texture) : ProgressBar

    fun sendHudMessage(bukkitPlayer: Player, anchorName: String, message: String)
}
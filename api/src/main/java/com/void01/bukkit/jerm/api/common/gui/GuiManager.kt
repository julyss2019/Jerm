package com.void01.bukkit.jerm.api.common.gui

import com.void01.bukkit.jerm.api.common.gui.component.Texture
import com.void01.bukkit.jerm.api.common.gui.extension.component.ProgressBar
import org.bukkit.entity.Player
import java.io.InputStream

interface GuiManager {
    fun existsGui(id: String): Boolean

    /**
     * 获取 GUI，不存在则返回 null
     */
    fun getGui2OrNull(id: String): Gui?

    /**
     * 获取 GUI，不存在则抛出异常
     */
    fun getGui2(id: String): Gui

    /**
     * 获取 GUI，不存在则抛出异常
     */
    @Deprecated("命名优化", replaceWith = ReplaceWith("getGui2(id)"))
    fun getGuiOrThrow(id: String): Gui

    /**
     * 获取 GUI，不存在则返回 null
     */
    @Deprecated("命名优化", replaceWith = ReplaceWith("getGui2OrNull(id)"))
    fun getGui(id: String): Gui?

    fun getGuis(): List<Gui>

    fun saveGuiFile(inputStream: InputStream, fileName: String, overwrite: Boolean)

    fun createHorizontalProgressBar(texture: Texture): ProgressBar

    fun createVerticalProgressBar(texture: Texture): ProgressBar

    fun sendHudMessage(bukkitPlayer: Player, anchorName: String, message: String)
}
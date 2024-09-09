package com.void01.bukkit.jerm.core.player

import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import com.germ.germplugin.api.dynamic.gui.GuiManager
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.player.JermPlayer
import com.void01.bukkit.jerm.core.JermPlugin
import org.bukkit.entity.Player

class JermPlayerImpl(override val bukkitPlayer: Player, val plugin: JermPlugin) : JermPlayer {
    private val usingGuis = mutableListOf<Gui>()

    override fun getUsingGui(handle: GermGuiScreen): Gui {
        return getUsingGuiOrNull(handle) ?: throw IllegalArgumentException("Unable to find using GUI by hande")
    }

    override fun getUsingGuiOrNull(handle: GermGuiScreen): Gui? {
        return usingGuis.firstOrNull { it.handle === handle }
    }

    fun addUsingGui(gui: Gui) {
        usingGuis.add(gui)
    }

    fun removeUsingGui(handle: GermGuiScreen) {
        usingGuis.removeIf {
            it.handle === handle
        }
    }

    override fun getUsingGui(id: String): Gui {
        return getUsingGuiOrNull(id) ?: throw IllegalArgumentException("Unable to get using GUI by id: $id")
    }

    override fun getUsingGuiOrNull(id: String): Gui? {
        return usingGuis.firstOrNull { it.id == id }
    }

    override fun getUsingGuis(): List<Gui> {
        return usingGuis.toList()
    }

    fun closeGuis() {
        GuiManager.getOpenedAllGui(bukkitPlayer).forEach {
            it.close()
        }
    }

    fun debug(message: String) {
        plugin.pluginLogger.debug("[${bukkitPlayer.name}] $message")
    }

    @Suppress("DeprecatedCallableAddReplaceWith")
    @Deprecated("Player 现在在 JermPlayer 生命周期内永远在线")
    override fun getOnlineBukkitPlayer(): Player {
        return bukkitPlayer
    }

    @Suppress("DeprecatedCallableAddReplaceWith")
    @Deprecated("Player 现在在 JermPlayer 生命周期内永远在线")
    override fun isOnline(): Boolean {
        return bukkitPlayer.isOnline
    }
}

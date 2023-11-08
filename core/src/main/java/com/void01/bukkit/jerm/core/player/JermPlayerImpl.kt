package com.void01.bukkit.jerm.core.player

import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import com.germ.germplugin.api.dynamic.gui.GuiManager
import com.github.julyss2019.bukkit.voidframework.common.Messages
import com.github.julyss2019.bukkit.voidframework.common.Validator
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.player.JermPlayer
import com.void01.bukkit.jerm.core.JermPlugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class JermPlayerImpl(val uuid: UUID, val plugin: JermPlugin) : JermPlayer {
    override var isScreenDebugEnabled = false
    private val usingGuis = mutableListOf<Gui>()

    fun getUsingGui(handle: GermGuiScreen): Gui? {
        return usingGuis.firstOrNull { it.id == handle.guiName }
    }

    fun addUsingGui(gui: Gui) {
        usingGuis.add(gui)
    }

    fun removeUsingGui(handle: GermGuiScreen) {
        usingGuis.removeIf {
            it.handle == handle
        }
    }

    override fun getOnlineBukkitPlayer(): Player {
        return getBukkitPlayer() ?: throw RuntimeException("Player is offline")
    }

    override fun getUsingGuis(): List<Gui> {
        checkOnline()

        return GuiManager.getOpenedAllGui(getBukkitPlayer())
            .map {
                GuiImpl(it, null, plugin)
            }
    }

    override fun getBukkitPlayer(): Player? = Bukkit.getPlayer(uuid)

    override fun isOnline(): Boolean {
        return getBukkitPlayer() != null
    }

    fun checkOnline() {
        Validator.checkState(isOnline(), "player is offline")
    }

    fun sendDebugMessage(message: String) {
        checkOnline()

        if (isScreenDebugEnabled) {
            val processed = "&a[Jerm-Debug] &f$message"

            Messages.sendColoredMessage(getBukkitPlayer()!!, processed)
            Messages.sendColoredMessage(Bukkit.getConsoleSender(), processed)
        }
    }

    override fun closeGuis() {
        GuiManager.getOpenedAllGui(getBukkitPlayer()!!).forEach {
            it.close()
        }
    }
}

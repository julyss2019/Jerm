package com.void01.bukkit.jerm.core.listener

import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import com.germ.germplugin.api.event.gui.GermGuiClickEvent
import com.germ.germplugin.api.event.gui.GermGuiClosedEvent
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.gui.GuiImpl
import com.void01.bukkit.jerm.core.player.JermPlayerImpl
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class GuiListener(plugin: JermPlugin) : Listener {
    private val playerManager = plugin.playerManager

    @EventHandler
    fun onGermGuiClickEvent(event: GermGuiClickEvent) {
        val handle = event.clickedGuiScreen
        val componentHandle = event.clickedPart ?: return

        val bukkitPlayer = event.player
        val jermPlayer = playerManager.getPlayer(bukkitPlayer) as JermPlayerImpl

        val usingGui = jermPlayer.getJermUsingGuiByHandle(handle) as GuiImpl? ?: return
        val component = usingGui.getComponentByHandle(componentHandle) ?: return
        val componentClickType = when (event.clickType) {
            GermGuiScreen.ClickType.LEFT_CLICK -> Component.ClickType.LEFT
            GermGuiScreen.ClickType.RIGHT_CLICK -> Component.ClickType.RIGHT
            else -> return
        }

        component.getOnClickListener()?.onClick(componentClickType)
    }

    @EventHandler
    fun onGermGuiOpenEvent(event: GermGuiClosedEvent) {
        val bukkitPlayer = event.player
        val jermPlayer = playerManager.getPlayer(bukkitPlayer) as JermPlayerImpl

        jermPlayer.removeJermUsingGuiByHandle(event.germGuiScreen)
    }
}
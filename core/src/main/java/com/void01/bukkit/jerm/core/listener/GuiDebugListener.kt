package com.void01.bukkit.jerm.core.listener

import com.germ.germplugin.api.event.gui.GermGuiClickEvent
import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.util.MessageUtils
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class GuiDebugListener(private val plugin: JermPlugin) : Listener {
    @EventHandler
    fun onGermGuiClickEvent(event: GermGuiClickEvent) {
        val player = plugin.jermPlayerManager.getPlayer(event.player)

        if (!player.isScreenDebugEnabled) {
            return
        }

        val screen = event.clickedGuiScreen
        val clickedPart = event.clickedPart
        val sb = StringBuilder()

        sb.append("event: GermGuiClickEvent\n")
        sb.append("click_x: ${event.clickX}\n")
        sb.append("click_y: ${event.clickY}\n")
        sb.append("click_type: ${event.clickType.name}\n")
        sb.append("gui: ${screen.guiName}\n")

        if (clickedPart != null) {
            sb.append("clicked: \n")
            sb.append("  type: ${clickedPart::class.java.name}\n")
            sb.append("  id: ${clickedPart.indexName}\n")
        } else {
            sb.append("clicked: null\n")
        }

        MessageUtils.sendMessage(player.getOnlineBukkitPlayer(), sb.toString())
        MessageUtils.sendMessage(Bukkit.getConsoleSender(), sb.toString())
    }
}

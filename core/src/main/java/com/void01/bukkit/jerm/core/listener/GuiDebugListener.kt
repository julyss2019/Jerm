package com.void01.bukkit.jerm.core.listener

import com.germ.germplugin.api.event.gui.GermGuiClickEvent
import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.player.JermPlayerImpl
import com.void01.bukkit.jerm.core.util.GermUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class GuiDebugListener(private val plugin: JermPlugin) : Listener {
    @EventHandler
    fun onGermGuiClickEvent(event: GermGuiClickEvent) {
        val jermPlayer = plugin.jermPlayerManager.getJermPlayer(event.player) as JermPlayerImpl
        val guiHandle = event.clickedGuiScreen
        val componentHandle = event.clickedPart
        val sb = StringBuilder()

        sb.append("event: GermGuiClickEvent\n")
        sb.append("click_x: ${event.clickX}\n")
        sb.append("click_y: ${event.clickY}\n")
        sb.append("click_type: ${event.clickType.name}\n")
        sb.append("gui: ${guiHandle.guiName}\n")

        if (componentHandle != null) {
            sb.append("clicked: \n")
            sb.append("  type: ${componentHandle::class.java.name}\n")
            sb.append("  path: ${GermUtils.getPath(componentHandle)}\n")
            sb.append("  handle: \n")
            sb.append("    $componentHandle")
        } else {
            sb.append("clicked: null\n")
        }

        jermPlayer.debug(sb.toString())
    }
}

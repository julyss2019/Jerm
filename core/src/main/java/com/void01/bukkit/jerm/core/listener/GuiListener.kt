package com.void01.bukkit.jerm.core.listener

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import com.germ.germplugin.api.event.gui.GermGuiClickEvent
import com.germ.germplugin.api.event.gui.GermGuiClosedEvent
import com.germ.germplugin.api.event.gui.GermGuiOpenedEvent
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.gui.GuiImpl
import com.void01.bukkit.jerm.core.player.JermPlayerImpl
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class GuiListener(private val plugin: JermPlugin) : Listener {
    private val playerManager = plugin.playerManager

    @EventHandler
    fun onGermGuiClickEvent(event: GermGuiClickEvent) {
        val bukkitPlayer = event.player
        val jermPlayer = playerManager.getPlayer(bukkitPlayer) as JermPlayerImpl

        val guiHandle = event.clickedGuiScreen
        val componentHandle = event.clickedPart ?: return

        val usingGui = jermPlayer.getUsingGui(guiHandle) as GuiImpl? ?: return
        // 从 handle 反向获取所有父控件
        val parents = mutableListOf<GermGuiPart<*>>()
        var tmp: GermGuiPart<*> = componentHandle

        while (tmp.parentPart != null) {
            tmp = tmp.parentPart
            parents.add(0, tmp)
        }

        var handleComponentGroup: ComponentGroup = usingGui

        // 一层层获取控件的 ComponentGroup
        parents.forEach {
            handleComponentGroup = handleComponentGroup.getComponentOrThrow(it.indexName, Component::class.java) as ComponentGroup
        }

        val clicked = handleComponentGroup.getComponentOrThrow(componentHandle.indexName, Component::class.java)
        val clickType = when (event.clickType) {
            GermGuiScreen.ClickType.LEFT_CLICK -> Component.ClickType.LEFT
            GermGuiScreen.ClickType.RIGHT_CLICK -> Component.ClickType.RIGHT
            else -> return
        }

        clicked.onClickListener?.onClick(clickType)
    }

    @EventHandler
    fun onOpen(event: GermGuiOpenedEvent) {
        val handle = event.germGuiScreen
        val bukkitPlayer = event.player
        val jermPlayer = playerManager.getPlayer(bukkitPlayer) as JermPlayerImpl

        // 这里仅处理没有使用 Jerm 打开的 GUI，将为其手动实例化一个 GUI
        if (jermPlayer.getUsingGui(handle) == null) {
            jermPlayer.addUsingGui(GuiImpl(handle, null, plugin))
        } else {
            jermPlayer.getUsingGui(handle)?.onOpenListener?.onOpen()
        }
    }

    @EventHandler
    fun onClose(event: GermGuiClosedEvent) {
        val handle = event.germGuiScreen
        val bukkitPlayer = event.player
        val jermPlayer = playerManager.getPlayer(bukkitPlayer) as JermPlayerImpl

        jermPlayer.getUsingGui(handle)?.onCloseListener?.onClose()
        jermPlayer.removeUsingGui(event.germGuiScreen)
    }
}
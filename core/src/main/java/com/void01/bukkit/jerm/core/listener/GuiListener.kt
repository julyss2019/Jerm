package com.void01.bukkit.jerm.core.listener

import com.germ.germplugin.api.dynamic.gui.*
import com.germ.germplugin.api.event.gui.*
import com.void01.bukkit.jerm.api.common.event.GuiCloseEvent
import com.void01.bukkit.jerm.api.common.event.GuiOpenEvent
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.ItemSlot
import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.gui.GuiImpl
import com.void01.bukkit.jerm.core.player.JermPlayerImpl
import com.void01.bukkit.jerm.core.util.MessageUtils
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.util.*

class GuiListener(private val plugin: JermPlugin) : Listener {
    companion object {
        private fun parseIsShiftClick(enum: Enum<*>): Boolean {
            return enum.name.contains("SHIFT")
        }
    }

    private val jermPlayerManager = plugin.jermPlayerManager

    private fun resolveComponent(
        usingGui: Gui,
        clickedComponentHandle: GermGuiPart<*>
    ): Component<*> {
        val componentNodes = Stack<GermGuiPart<*>>()
        var currentComponentHandle: GermGuiPart<*> = clickedComponentHandle

        componentNodes.push(clickedComponentHandle)

        // 从 handle 反向获取所有父控件
        while (currentComponentHandle.parentPart?.also { currentComponentHandle = it } != null) {
            componentNodes.push(currentComponentHandle)
        }

        var currentComponent: Component<*>? = usingGui.rootComponent // 在最底层的父 Component

        while (!componentNodes.empty()) {
            val pop = componentNodes.pop()
            val tmp = (currentComponent as ComponentGroup).getComponent(pop.indexName, Component::class.java)

            currentComponent =
                tmp ?: currentComponent.getPseudoComponentOrThrow(pop.indexName, Component::class.java) // 伪部件（滚动条）
        }

        return currentComponent!!
    }

    private fun fireComponentClickListener(
        component: Component<*>,
        clickType: Component.ClickType,
        isShift: Boolean,
    ) {
        component.onClickListener?.run {
            onClick(clickType)
            onClick(clickType, isShift)
        }
    }

    // LEFT, MIDDLE, RIGHT, SHIFT
    @EventHandler
    fun onSlotClick(event: GermGuiSlotClickEvent) {
        try {
            val germEventType = event.eventType
            val clickType = when (germEventType) {
                GermGuiSlot.EventType.LEFT_CLICK, GermGuiSlot.EventType.SHIFT_LEFT_CLICK -> Component.ClickType.LEFT
                GermGuiSlot.EventType.RIGHT_CLICK, GermGuiSlot.EventType.SHIFT_RIGHT_CLICK -> Component.ClickType.RIGHT
                GermGuiSlot.EventType.MIDDLE_CLICK, GermGuiSlot.EventType.SHIFT_MILLE_CLICK -> Component.ClickType.MIDDLE
                else -> return
            }
            val jermPlayer = jermPlayerManager.getPlayer(event.player)
            val usingGui = jermPlayer.getUsingGui(event.germGuiScreen) ?: return
            val resolvedComponent = resolveComponent(usingGui, event.germGuiSlot) as ItemSlot
            val isShift = parseIsShiftClick(germEventType)

            // 萌芽如果设置 interact 为 false 则连事件都不会触发了，这造成了开发不便
            // Jerm interactive 并不会调用萌芽的 setInteract 的方法，而是这里进行特殊处理
            if (!resolvedComponent.canTakeAway) {
                event.isCancelled = true
            }

            fireComponentClickListener(resolvedComponent, clickType, isShift)
        } catch (ex: Exception) {
            MessageUtils.sendMessage(event.player, "发生了致命性错误.")
            event.isCancelled = true // 防止出现意外，造成物品随便拿
            throw RuntimeException(ex)
        }
    }

    // LEFT, MIDDLE, RIGHT, SHIFT
    @EventHandler
    fun onButtonClick(event: GermGuiButtonEvent) {
        val germEventType = event.eventType
        val clickType = when (germEventType) {
            GermGuiButton.EventType.LEFT_CLICK, GermGuiButton.EventType.SHIFT_LEFT_CLICK -> Component.ClickType.LEFT
            GermGuiButton.EventType.RIGHT_CLICK, GermGuiButton.EventType.SHIFT_RIGHT_CLICK -> Component.ClickType.RIGHT
            GermGuiButton.EventType.MIDDLE_CLICK, GermGuiButton.EventType.SHIFT_MILLE_CLICK -> Component.ClickType.MIDDLE
            else -> return
        }
        val isShift = parseIsShiftClick(germEventType)
        val jermPlayer = jermPlayerManager.getPlayer(event.player)
        val usingGui = jermPlayer.getUsingGui(event.germGuiScreen) ?: return
        val resolvedComponent = resolveComponent(usingGui, event.germGuiButton)

        fireComponentClickListener(resolvedComponent, clickType, isShift)
    }

    // LEFT, RIGHT, DOWN, UP
    @EventHandler
    fun onGuiClick(event: GermGuiClickEvent) {
        val bukkitPlayer = event.player
        val jermPlayer = jermPlayerManager.getPlayer(bukkitPlayer) as JermPlayerImpl
        val guiHandle = event.clickedGuiScreen
        val usingGui = jermPlayer.getUsingGui(guiHandle) as GuiImpl? ?: return
        val clickedComponentHandle = event.clickedPart
        val germEventType = event.clickType
        val clickDown = germEventType.name.endsWith("RELEASE")
        val clickType = when (germEventType) {
            GermGuiScreen.ClickType.LEFT_CLICK, GermGuiScreen.ClickType.LEFT_CLICK_RELEASE -> Component.ClickType.LEFT
            GermGuiScreen.ClickType.RIGHT_CLICK, GermGuiScreen.ClickType.RIGHT_CLICK_RELEASE -> Component.ClickType.RIGHT
            else -> return
        }

        val resolvedComponent = if (clickedComponentHandle == null) null else resolveComponent(usingGui, clickedComponentHandle)

        if (clickDown) {
            usingGui.onGuiClickListener?.onClickDown(resolvedComponent, clickType, event)
        } else {
            usingGui.onGuiClickListener?.onClickUp(resolvedComponent, clickType, event)
        }

        if (resolvedComponent != null) {
            resolvedComponent.onClickListener?.run {
                if (clickDown) {
                    onClickDown(clickType)

                    // 如果 GermGuiSlot.isInteract 为 false 则不会触发 GermGuiSlotEvent，则在这里补，但这里会缺少 Shift 的检测
/*                    if (resolvedComponent is ItemSlot && resolvedComponent.interactive) {
                        return
                    }

                    fireComponentClickListener(resolvedComponent, clickType, false)*/
                } else {
                    onClickUp(clickType)
                }
            }
        }
    }

    @EventHandler
    fun onOpen(event: GermGuiOpenedEvent) {
        val handle = event.germGuiScreen
        val bukkitPlayer = event.player
        val jermPlayer = jermPlayerManager.getPlayer(bukkitPlayer) as JermPlayerImpl

        // 这里仅处理没有使用 Jerm 打开的 GUI，将为其手动实例化一个 GUI
        if (jermPlayer.getUsingGui(handle) == null) {
            jermPlayer.addUsingGui(GuiImpl(handle, null, plugin))
        }

        val gui = jermPlayer.getUsingGui(handle)!!

        gui.onOpenListener?.onOpen()
        Bukkit.getPluginManager().callEvent(GuiOpenEvent(jermPlayer, gui))
    }

    @EventHandler
    fun onClose(event: GermGuiClosedEvent) {
        val handle = event.germGuiScreen
        val bukkitPlayer = event.player
        val jermPlayer = jermPlayerManager.getPlayer(bukkitPlayer) as JermPlayerImpl
        val usingGui = jermPlayer.getUsingGui(handle)

        usingGui?.onCloseListener?.onClose()
        jermPlayer.removeUsingGui(event.germGuiScreen)

        if (usingGui != null) {
            Bukkit.getPluginManager().callEvent(GuiCloseEvent(jermPlayer, usingGui))
        }
    }
}
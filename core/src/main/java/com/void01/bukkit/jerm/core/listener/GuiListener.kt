package com.void01.bukkit.jerm.core.listener

import com.germ.germplugin.api.dynamic.gui.GermGuiButton
import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import com.germ.germplugin.api.dynamic.gui.GermGuiSlot
import com.germ.germplugin.api.event.gui.*
import com.void01.bukkit.jerm.api.common.event.GuiCloseEvent
import com.void01.bukkit.jerm.api.common.event.GuiOpenEvent
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Button
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.ItemSlot
import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.gui.GuiImpl
import com.void01.bukkit.jerm.core.gui.component.CheckBoxImpl
import com.void01.bukkit.jerm.core.util.MessageUtils
import com.void01.bukkit.voidframework.common.kotlin.nameWithUuid2
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.util.*

/**
 * 在萌芽中 GermGuiButton 和 GermGuisSlot 支持 LEFT, MIDDLE, RIGHT, SHIFT 事件，其他控件仅支持 LEFT，SHIFT
 *
 */
class GuiListener(private val plugin: JermPlugin) : Listener {
    companion object {
        private fun parseIsShiftClick(enum: Enum<*>): Boolean {
            return enum.name.contains("SHIFT")
        }

        private fun parseClickType(enum: Enum<*>): Component.ClickType? {
            if (enum is GermGuiSlot.EventType) {
                return when (enum) {
                    GermGuiSlot.EventType.LEFT_CLICK, GermGuiSlot.EventType.SHIFT_LEFT_CLICK -> Component.ClickType.LEFT
                    GermGuiSlot.EventType.RIGHT_CLICK, GermGuiSlot.EventType.SHIFT_RIGHT_CLICK -> Component.ClickType.RIGHT
                    GermGuiSlot.EventType.MIDDLE_CLICK, GermGuiSlot.EventType.SHIFT_MILLE_CLICK -> Component.ClickType.MIDDLE
                    else -> null
                }
            }

            if (enum is GermGuiButton.EventType) {
                return when (enum) {
                    GermGuiButton.EventType.LEFT_CLICK, GermGuiButton.EventType.SHIFT_LEFT_CLICK -> Component.ClickType.LEFT
                    GermGuiButton.EventType.RIGHT_CLICK, GermGuiButton.EventType.SHIFT_RIGHT_CLICK -> Component.ClickType.RIGHT
                    GermGuiButton.EventType.MIDDLE_CLICK, GermGuiButton.EventType.SHIFT_MILLE_CLICK -> Component.ClickType.MIDDLE
                    else -> null
                }
            }

            if (enum is GermGuiScreen.ClickType) {
                return when (enum) {
                    GermGuiScreen.ClickType.LEFT_CLICK, GermGuiScreen.ClickType.LEFT_CLICK_RELEASE -> Component.ClickType.LEFT
                    GermGuiScreen.ClickType.RIGHT_CLICK, GermGuiScreen.ClickType.RIGHT_CLICK_RELEASE -> Component.ClickType.RIGHT
                    else -> null
                }
            }

            return null
        }

        private fun findComponent(usingGui: Gui, clickedComponentHandle: GermGuiPart<*>): Component<*>? {
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
                val tmp = (currentComponent as ComponentGroup).getComponent2OrNull(pop.indexName, Component::class.java)

                currentComponent =
                    tmp ?: currentComponent.getPseudoComponent(pop.indexName, Component::class.java) // 伪部件（滚动条）
            }

            return currentComponent!!
        }

        private fun fireClickListener(
            component: Component<*>,
            clickType: Component.ClickType,
            isShift: Boolean,
            event: Event
        ) {
            component.onClickListener?.run {
                onClick(clickType)
                onClick(clickType, isShift)
                onClick(clickType, isShift, event)
            }
        }
    }

    private val jermPlayerManager = plugin.jermPlayerManager

    // LEFT, MIDDLE, RIGHT, SHIFT
    @EventHandler
    fun onSlotClick(event: GermGuiSlotClickEvent) {
        try {
            val germEventType = event.eventType
            val clickType = parseClickType(event.eventType) ?: return
            val isShift = parseIsShiftClick(germEventType)
            val jermPlayer = jermPlayerManager.getJermPlayer(event.player)
            val usingGui = jermPlayer.getUsingGuiOrNull(event.germGuiScreen) ?: return
            val itemSlot = findComponent(usingGui, event.germGuiSlot) as ItemSlot

            if (itemSlot.isViewOnly) {
                event.isCancelled = true
            }

            fireClickListener(itemSlot, clickType, isShift, event)
        } catch (ex: Exception) {
            MessageUtils.sendMessage(event.player, "&c发生了致命性错误.")
            event.isCancelled = true // 防止出现意外，造成物品随便拿
            throw RuntimeException(ex)
        }
    }

    // LEFT, MIDDLE, RIGHT, SHIFT
    @EventHandler
    fun onButtonClick(event: GermGuiButtonEvent) {
        val id = event.germGuiButton.indexName

        // 多层嵌套滚动条可能会出现问题，直接过滤
        if (id == "sliderH" || id == "sliderV") {
            return
        }

        val germEventType = event.eventType
        val jermPlayer = jermPlayerManager.getJermPlayer(event.player)
        val clickType = parseClickType(event.eventType) ?: return
        val isShift = parseIsShiftClick(germEventType)
        val usingGui = jermPlayer.getUsingGuiOrNull(event.germGuiScreen) ?: return
        val button = findComponent(usingGui, event.germGuiButton) ?: return

        fireClickListener(button, clickType, isShift, event)
    }

    // LEFT, RIGHT, DOWN, UP
    @EventHandler
    fun onGuiClick(event: GermGuiClickEvent) {
        val germEventType = event.clickType
        val bukkitPlayer = event.player
        val jermPlayer = jermPlayerManager.getJermPlayer(bukkitPlayer)
        val guiHandle = event.clickedGuiScreen
        val usingGui = jermPlayer.getUsingGuiOrNull(guiHandle) as GuiImpl? ?: return
        val isClickDown = !germEventType.name.endsWith("RELEASE")
        val clickType = parseClickType(germEventType) ?: return
        var tmp = event.clickedPart

        // ?Fix: 萌芽松开在某些情况下可能会穿透
        // 传递给所有父组件
        while (tmp != null) {
            val component = findComponent(usingGui, tmp) ?: return

            if (isClickDown) {
                usingGui.onGuiClickListener?.onClickDown(component, clickType, event)

                // Button ItemSlot 已特殊处理
                if (component !is Button && component !is ItemSlot) {
                    fireClickListener(component, clickType, false, event)
                }
            } else {
                usingGui.onGuiClickListener?.onClickUp(component, clickType, event)
            }

            tmp = tmp.parentPart
        }
    }

    @EventHandler
    fun onOpened(event: GermGuiOpenedEvent) {
        val handle = event.germGuiScreen
        val bukkitPlayer = event.player

        // Fix: 掉线后还有概率触发 GUI 打开事件
        if (!bukkitPlayer.isOnline) {
            plugin.pluginLogger.debug("Gui '${handle.guiName} opened, but player '${bukkitPlayer.nameWithUuid2}' is offline'")
            return
        }

        val jermPlayer = jermPlayerManager.getJermPlayer(bukkitPlayer)
        val gui: GuiImpl

        jermPlayer.debug("Gui '${handle.guiName}' opened")

        // 这里仅处理没有使用 Jerm 打开的 GUI，将为其手动实例化一个 GUI
        if (jermPlayer.getUsingGuiOrNull(handle) == null) {
            gui = GuiImpl(handle, null, plugin)

            jermPlayer.addUsingGui(gui)
        } else {
            gui = jermPlayer.getUsingGuiOrNull(handle)!! as GuiImpl

            // Fix: 修复萌芽 GUI 打开后秒关可能无法关闭的问题
            if (!gui.isOpening) {
                gui.close()
                return
            }

            gui.onOpenListener?.onOpen()
        }

        Bukkit.getPluginManager().callEvent(GuiOpenEvent(jermPlayer, gui))
    }

    @EventHandler
    fun onGermGuiCheckboxEvent(event: GermGuiCheckboxEvent) {
        val germGuiCheckbox = event.germGuiCheckbox
        val jermPlayer = jermPlayerManager.getJermPlayer(event.player)
        val usingGui = jermPlayer.getUsingGuiOrNull(event.germGuiScreen) ?: return

        (findComponent(usingGui, germGuiCheckbox) as CheckBoxImpl?)?.onCheckedChangeListener?.onCheckedChanged(
            germGuiCheckbox.isChecked
        )
    }

    @EventHandler
    fun onClose(event: GermGuiClosedEvent) {
        val handle = event.germGuiScreen
        val bukkitPlayer = event.player
        val jermPlayer = jermPlayerManager.getJermPlayer(bukkitPlayer)
        val usingGui = jermPlayer.getUsingGuiOrNull(handle)

        usingGui?.onCloseListener?.onClose()
        jermPlayer.debug("Gui '${handle.guiName}' closed")
        jermPlayer.removeUsingGui(handle)

        if (usingGui != null) {
            Bukkit.getPluginManager().callEvent(GuiCloseEvent(jermPlayer, usingGui))
        }
    }
}
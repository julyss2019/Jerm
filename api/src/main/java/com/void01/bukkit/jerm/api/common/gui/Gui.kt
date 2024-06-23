package com.void01.bukkit.jerm.api.common.gui

import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import com.germ.germplugin.api.event.gui.GermGuiClickEvent
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.RootComponent
import org.bukkit.entity.Player

interface Gui : Cloneable, ComponentGroup {
    val id: String
    val handle: GermGuiScreen
    var onCloseListener: OnCloseListener?
    var onOpenListener: OnOpenListener?
    var onGuiClickListener: OnClickListener?
    val rootComponent: RootComponent
    var isEnabled: Boolean

    interface OnCloseListener {
        /**
         * 特别注意，close() 并不会立即触发该方法，而是需要等待客户端回应
         */
        fun onClose()
    }

    interface OnOpenListener {
        fun onOpen()
    }

    interface OnClickListener {
        fun onClickDown(
            component: Component<*>?,
            clickType: Component.ClickType,
            event: GermGuiClickEvent
        ) {
        }

        fun onClickUp(
            component: Component<*>?,
            clickType: Component.ClickType,
            event: GermGuiClickEvent
        ) {
        }
    }

    fun openAsGui(bukkitPlayer: Player) = openAsGui(bukkitPlayer, true)

    fun openAsGui(bukkitPlayer: Player, cover: Boolean)

    fun openAsHud(bukkitPlayer: Player)

    fun close()

    public override fun clone(): Gui
}
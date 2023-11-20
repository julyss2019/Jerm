package com.void01.bukkit.jerm.api.common.gui

import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import org.bukkit.entity.Player

interface Gui : Cloneable, ComponentGroup {
    val id: String
    val handle: GermGuiScreen
    var onCloseListener: OnCloseListener?
    var onOpenListener: OnOpenListener?

    interface OnCloseListener {
        fun onClose()
    }

    interface OnOpenListener {
        fun onOpen()
    }

    fun openAsGui(bukkitPlayer: Player) = openAsGui(bukkitPlayer, true)

    fun openAsGui(bukkitPlayer: Player, cover: Boolean)

    fun openAsHud(bukkitPlayer: Player)

    fun close()

    public override fun clone(): Gui
}
package com.void01.bukkit.jerm.api.common.gui

import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import org.bukkit.entity.Player

interface Gui : Cloneable, ComponentGroup {
    val id: String
    val handle: GermGuiScreen

    interface OnCloseListener {
        fun onClose()
    }

    fun openAsGui(bukkitPlayer: Player) = openAsGui(bukkitPlayer, true)

    fun openAsGui(bukkitPlayer: Player, cover: Boolean)

    fun openAsHud(bukkitPlayer: Player)

    fun close()

    public override fun clone(): Gui
}
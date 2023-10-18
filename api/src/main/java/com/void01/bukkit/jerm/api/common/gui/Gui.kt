package com.void01.bukkit.jerm.api.common.gui

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import com.void01.bukkit.jerm.api.common.gui.component.Component
import org.bukkit.entity.Player

interface Gui : Cloneable {
    val id: String
    val handle: GermGuiScreen

    fun openAsGui(bukkitPlayer: Player) = openAsGui(bukkitPlayer, true)

    fun openAsGui(bukkitPlayer: Player, cover: Boolean)

    fun openAsHud(bukkitPlayer: Player)

    fun close()

    fun getComponents(): List<Component<*>>

    fun <T : GermGuiPart<T>> getComponentHandleOrThrow(id: String, clazz: Class<T>) : T {
        return getComponentHandle(id, clazz) ?: throw RuntimeException("Unable to find component handle: $id")
    }

    fun <T : GermGuiPart<T>> getComponentHandle(id: String, clazz: Class<T>) : T?

    fun <T : Component<*>> getComponentOrThrow(id: String, clazz: Class<T>): T {
        return getComponent(id, clazz) ?: throw RuntimeException("Unable to find component: $id(${this.id})")
    }

    fun <T : Component<*>> getComponent(id: String, clazz: Class<T>): T?

    public override fun clone() : Gui
}
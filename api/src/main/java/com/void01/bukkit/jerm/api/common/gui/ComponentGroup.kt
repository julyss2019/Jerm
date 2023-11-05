package com.void01.bukkit.jerm.api.common.gui

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.IGuiPartContainer
import com.void01.bukkit.jerm.api.common.gui.component.Component

interface ComponentGroup : Cloneable {
    val gui: Gui
    val handle: IGuiPartContainer

    fun getComponents(): List<Component<*>>

    fun <T : GermGuiPart<T>> getComponentHandleOrThrow(id: String, clazz: Class<T>): T {
        return getComponentHandle(id, clazz) ?: throw RuntimeException("Unable to find component handle: $id")
    }

    fun <T : GermGuiPart<T>> getComponentHandle(id: String, clazz: Class<T>): T?

    fun <T : Component<*>> getComponentOrThrow(id: String, clazz: Class<T>): T {
        return getComponent(id, clazz) ?: throw RuntimeException("Unable to find component: $id")
    }

    fun <T : Component<*>> getComponent(id: String, clazz: Class<T>): T?
}
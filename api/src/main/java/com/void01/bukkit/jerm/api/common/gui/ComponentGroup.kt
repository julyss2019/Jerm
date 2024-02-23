package com.void01.bukkit.jerm.api.common.gui

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.gui.component.Component

interface ComponentGroup : Cloneable {
    val components: List<Component<*>>

    fun clearComponents()

    fun getComponentsRecursively(): List<Component<*>>

    fun <T : GermGuiPart<T>> getComponentHandleOrThrow(id: String, clazz: Class<T>): T {
        return getComponentHandle(id, clazz) ?: throw RuntimeException("Unable to find component handle: $id")
    }

    fun <T : GermGuiPart<T>> getComponentHandle(id: String, clazz: Class<T>): T?

    fun <T : Component<*>> getComponentOrThrow(id: String, clazz: Class<T>): T {
        return getComponent(id, clazz) ?: throw RuntimeException("Unable to find component: $id")
    }

    fun <T : Component<*>> getComponent(id: String, clazz: Class<T>): T?

    fun removeComponent(component: Component<*>) {
        removeComponent(component.id)
    }

    fun removeComponent(id: String)

    fun addComponent(component: Component<*>)

    fun addComponent(componentHandle: GermGuiPart<*>)

    fun existsComponent(id: String): Boolean
}
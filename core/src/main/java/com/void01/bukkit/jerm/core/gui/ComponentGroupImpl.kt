package com.void01.bukkit.jerm.core.gui

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.IGuiPartContainer
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component

class ComponentGroupImpl(val gui: Gui, private val containerHandle: IGuiPartContainer) : ComponentGroup {
    override var components: List<Component<*>>
        get() = componentMap.values.toList()
        set(value) {
            clearComponents()
            value.forEach {
                addComponent(it)
            }
        }
    private val componentMap = mutableMapOf<String, Component<*>>()

    init {
        containerHandle.guiParts.forEach {
            addComponent0(HandleToComponentConverter.convert(gui, this, it))
        }
    }

    private fun addComponent0(component: Component<*>) {
        componentMap[component.id] = component
    }

    override fun existsComponent(id: String): Boolean {
        return componentMap.containsKey(id)
    }

    override fun clearComponents() {
        containerHandle.clearGuiPart()
        componentMap.clear()
    }

    override fun <T : GermGuiPart<T>> getComponentHandle(id: String, clazz: Class<T>): T? {
        return containerHandle.getGuiPart(id, clazz)
    }

    override fun <T : Component<*>> getComponent(id: String, clazz: Class<T>): T? {
        return componentMap[id] as T?
    }

    override fun removeComponent(id: String) {
        containerHandle.removeGuiPart(id)
        componentMap.remove(id)
    }

    override fun addComponent(component: Component<*>) {
        require(!existsComponent(component.id)) {
            "Component already exists with id: ${component.id}"
        }

        containerHandle.addGuiPart(component.handle)
        addComponent0(component)
    }

    override fun addComponent(componentHandle: GermGuiPart<*>) {
        addComponent(HandleToComponentConverter.convert(gui, this, componentHandle))
    }
}
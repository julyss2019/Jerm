package com.void01.bukkit.jerm.core.gui

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.IGuiPartContainer
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component

class ComponentGroupImpl(override val gui: Gui, override val handle: IGuiPartContainer) : ComponentGroup {
    private val componentMap = mutableMapOf<String, Component<*>>()
    private val ambiguousComponentIds = mutableSetOf<String>()

    init {
        putIGuiPartContainerComponents(handle)
    }

    private fun putIGuiPartContainerComponents(iGuiPartContainer: IGuiPartContainer) {
        iGuiPartContainer.guiParts.forEach {
            if (it is IGuiPartContainer) {
                putComponent(GermComponentToJermComponentConverter.convert(gui, it))
                putIGuiPartContainerComponents(it)
            } else {
                val component = GermComponentToJermComponentConverter.convert(gui, it)

                putComponent(component)
            }
        }
    }

    private fun putComponent(component: Component<*>) {
        val componentId = component.getId()

        if (componentMap.containsKey(componentId)) {
            ambiguousComponentIds.add(componentId)
        }

        componentMap[componentId] = component
    }

    override fun getComponents(): List<Component<*>> {
        return componentMap.values.toList()
    }

    override fun <T : GermGuiPart<T>> getComponentHandle(id: String, clazz: Class<T>): T? {
        return getComponents().firstOrNull { it.getId() == id } as T?
    }

    override fun <T : Component<*>> getComponent(id: String, clazz: Class<T>): T? {
        return componentMap[id] as T?
    }

    fun getComponentByHandle(handle: GermGuiPart<*>): Component<*>? {
        require(!ambiguousComponentIds.contains(handle.indexName)) {
            "Ambiguous component id: ${handle.indexName}"
        }

        return getComponents().firstOrNull { it.getId() == handle.indexName }
    }
}
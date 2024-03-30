package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.IGuiPartContainer
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup
import com.void01.bukkit.jerm.core.gui.HandleToComponentConverter

@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
abstract class BaseJermComponentGroup<T : GermGuiPart<*>>(
    gui: Gui,
    override val parent: JermComponentGroup<*>?,
    override val handle: T,
    private val containerHandle: IGuiPartContainer
) : BaseComponent<T>(gui, parent, handle), JermComponentGroup<T> {
    companion object {
        private fun getComponentsRecursively0(jermComponentGroup: JermComponentGroup<*>): List<Component<*>> {
            val list = mutableListOf<Component<*>>()

            jermComponentGroup.components.forEach {
                if (it is ComponentGroup) {
                    list.addAll(getComponentsRecursively0(it as JermComponentGroup<*>))
                } else {
                    list.add(it)
                }
            }

            return list
        }
    }

    private val componentMap = mutableMapOf<String, Component<*>>()
    override var components: List<Component<*>>
        get() = componentMap.values.toList()
        set(value) {
            clearComponents()
            value.forEach {
                addComponent(it)
            }
        }

    init {
        containerHandle.guiParts.forEach {
            addComponent0(HandleToComponentConverter.convert(gui, parent, it))
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


    override fun getComponentsRecursively(): List<Component<*>> {
        return getComponentsRecursively0(this)
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
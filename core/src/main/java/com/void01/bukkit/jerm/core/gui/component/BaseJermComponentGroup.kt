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

    override fun <T : GermGuiPart<T>> getComponentHandle2(id: String, type: Class<T>): T = getComponentHandle2OrNull(id, type) ?: throw IllegalArgumentException("Unable to find Component handle by id: $id")

    override fun <T : GermGuiPart<T>> getComponentHandle2OrNull(id: String, type: Class<T>): T? {
        return containerHandle.getGuiPart(id, type)
    }

    override fun <T : Component<*>> getComponent2(id: String, type: Class<T>): T = getComponent2OrNull(id, type) ?: throw IllegalArgumentException("Unable to find Component by id: $id")

    override fun <T : Component<*>> getComponent2OrNull(id: String, type: Class<T>): T? {
        @Suppress("UNCHECKED_CAST")
        return componentMap[id] as T?
    }

    override fun removeComponent(component: Component<*>) = removeComponent(component.id)

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

    override fun <T : Component<*>> getComponentByPath2(path: String, type: Class<T>): T {
        return getComponentByPath2OrNull(path, type) ?: throw IllegalArgumentException("Unable to find Component by path: $path")
    }

    override fun <T : Component<*>> getComponentByPath2OrNull(path: String, type: Class<T>): T? {
        val array = path.split(".")

        var parent: ComponentGroup = gui

        for (node in array.dropLast(1)) {
            parent = parent.getComponent2OrNull(node, Component::class.java) as ComponentGroup? ?: return null
        }

        return parent.getComponent2(array.last(), type)
    }
}
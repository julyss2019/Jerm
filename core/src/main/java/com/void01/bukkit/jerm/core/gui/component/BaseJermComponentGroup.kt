package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.IGuiPartContainer
import com.void01.bukkit.jerm.api.common.exception.ComponentNotFoundException
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup
import com.void01.bukkit.jerm.core.gui.GermHandleToComponentAdapter

@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
abstract class BaseJermComponentGroup<T : GermGuiPart<*>>(
    gui: Gui,
    parent: JermComponentGroup<*>?,
    handle: T,
    private val containerHandle: IGuiPartContainer // 由于 IGuiPartContainer 跟 GermGuiPart 没有任何继承关系，故需要额外引用
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

        /**
         * 用于递归获取层级字符串
         */
        private fun getHierarchyString0(component: Component<*>, level: Int): String {
            var result = ""

            result += "  ".repeat(level) + component.id + "\n"

            if (component is ComponentGroup) {
                component.components.forEach {
                    result += getHierarchyString0(it, level + 1)
                }
            }

            return result
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
        loadComponents()
    }

    private fun reloadComponents() {
        componentMap.clear()
        loadComponents()
    }

    private fun loadComponents() {
        // 载入组件
        containerHandle.guiParts.forEach {
            addComponent0(GermHandleToComponentAdapter.adapt(gui, this, it))
        }
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

    override fun removeComponent(component: Component<*>) = removeComponent(component.id)

    override fun removeComponent(id: String) {
        containerHandle.removeGuiPart(id)
        componentMap.remove(id)
    }

    private fun addComponent0(component: Component<*>) {
        componentMap[component.id] = component
    }

    override fun addComponent(component: Component<*>) {
        require(!existsComponent(component.id)) {
            "Component already exists with id: ${component.id}"
        }

        containerHandle.addGuiPart(component.handle)
        addComponent0(component)
    }

    override fun addComponent(componentHandle: GermGuiPart<*>) {
        addComponent(GermHandleToComponentAdapter.adapt(gui, this, componentHandle))
    }

    override fun <T : Component<*>> getComponentByPath2(path: String, type: Class<T>): T {
        return getComponentByPath2OrNull(path, type) ?: throw ComponentNotFoundException(gui, this, path)
    }

    override fun <T : Component<*>> getComponentByPath2OrNull(path: String, type: Class<T>): T? {
        val array = path.split(".")

        // 获取最近的 ComponentGroup
        var tmp: ComponentGroup = this

        for (node in array.dropLast(1)) {
            tmp = tmp.getComponent2OrNull(node, Component::class.java) as ComponentGroup? ?: return null
        }

        // 使用最近的 ComponentGroup 再获取组件
        return tmp.getComponent2OrNull(array.last(), type)
    }

    override fun getHierarchyString(): String {
        return getHierarchyString0(this, 0)
    }

    override fun <T : Component<*>> getComponent2(id: String, type: Class<T>): T =
        getComponent2OrNull(id, type) ?: throw ComponentNotFoundException(gui, this, id)

    @Suppress("DEPRECATION")
    @Deprecated("使用 getComponent", replaceWith = ReplaceWith(""))
    override fun <T : GermGuiPart<T>> getComponentHandle2(id: String, type: Class<T>): T =
        getComponentHandle2OrNull(id, type)
            ?: throw IllegalArgumentException("Component handle with id '$id' not found in GUI '${gui.id}'")

    @Deprecated("使用 getComponent")
    override fun <T : GermGuiPart<T>> getComponentHandle2OrNull(id: String, type: Class<T>): T? {
        return containerHandle.getGuiPart(id, type)
    }

    override fun <T : Component<*>> getComponent2OrNull(id: String, type: Class<T>): T? {
        @Suppress("UNCHECKED_CAST")
        return componentMap[id] as T?
    }
}
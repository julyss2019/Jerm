package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.GermGuiScroll
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.ScrollBox
import com.void01.bukkit.jerm.core.gui.ComponentGroupImpl
import com.void01.bukkit.jerm.core.util.GermUtils

class ScrollBoxImpl(override val gui: Gui, override val handle: GermGuiScroll) :
    BaseComponent<GermGuiScroll>(gui, handle), ComponentGroup, ScrollBox {
    override val origin: ScrollBox by lazy { clone() }
    private val componentGroup = ComponentGroupImpl(gui, handle)
    override var components: List<Component<*>>
        get() = componentGroup.components
        set(value) {
            componentGroup.components = value
        }

    override fun clearComponents() {
        componentGroup.clearComponents()
    }

    override fun <T : GermGuiPart<T>> getComponentHandle(id: String, clazz: Class<T>): T? {
        return componentGroup.getComponentHandle(id, clazz)
    }

    override fun <T : Component<*>> getComponent(id: String, clazz: Class<T>): T? {
        return componentGroup.getComponent(id, clazz)
    }

    override fun removeComponent(id: String) {
        componentGroup.removeComponent(id)
    }

    override fun addComponent(component: Component<*>) {
        componentGroup.addComponent(component)
    }

    override fun addComponent(componentHandle: GermGuiPart<*>) {
        componentGroup.addComponent(componentHandle)
    }

    override fun existsComponent(id: String): Boolean {
        return componentGroup.existsComponent(id)
    }

    override fun clone(): ScrollBox {
        return ScrollBoxImpl(gui, GermUtils.cloneGuiPart(handle))
    }
}
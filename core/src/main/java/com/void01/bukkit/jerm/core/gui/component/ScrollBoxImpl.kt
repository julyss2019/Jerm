package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiButton
import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.GermGuiScroll
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Button
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.ScrollBox
import com.void01.bukkit.jerm.core.gui.ComponentGroupImpl
import com.void01.bukkit.jerm.core.gui.HandleToComponentConverter
import com.void01.bukkit.jerm.core.util.GermUtils

class ScrollBoxImpl(gui: Gui, group: ComponentGroup, handle: GermGuiScroll) :
    BaseComponent<GermGuiScroll>(gui, group, handle), ComponentGroup, ScrollBox {
    class ScrollBarImpl(private val button: Button, override val scrollBox: ScrollBox) : ScrollBox.ScrollBar,
        Component<GermGuiButton> by button {

        override fun clone(): ScrollBox.ScrollBar {
            return ScrollBarImpl(button.clone(), scrollBox)
        }
    }

    override var horizontalScrollBar: ScrollBox.ScrollBar? = null
    override var verticalScrollBar: ScrollBox.ScrollBar? = null
    override val origin: ScrollBox by lazy { clone() }
    private val componentGroup = ComponentGroupImpl(gui, handle)
    override var components: List<Component<*>>
        get() = componentGroup.components
        set(value) {
            componentGroup.components = value
        }

    init {
        if (handle.sliderV != null) {
            verticalScrollBar =
                ScrollBarImpl(HandleToComponentConverter.convert(gui, group, handle.sliderV) as Button, this)
        }

        if (handle.sliderH != null) {
            horizontalScrollBar =
                ScrollBarImpl(HandleToComponentConverter.convert(gui, group, handle.sliderH) as Button, this)
        }
    }

    override fun clearComponents() {
        componentGroup.clearComponents()
    }

    override fun getComponentsRecursively(): List<Component<*>> {
        return componentGroup.getComponentsRecursively()
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
        return ScrollBoxImpl(gui, group, GermUtils.cloneGuiPart(handle))
    }

    override fun <T : Component<*>> getPseudoComponent(id: String, clazz: Class<T>): Component<*>? {
        if (id == horizontalScrollBar?.id) {
            return horizontalScrollBar
        }

        if (id == verticalScrollBar?.id) {
            return verticalScrollBar
        }

        return null
    }
}
package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.GermGuiScroll
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.ScrollBox
import com.void01.bukkit.jerm.core.gui.ComponentGroupImpl

class ScrollBoxImpl(override val gui: Gui, override val handle: GermGuiScroll) :
    BaseComponent<GermGuiScroll>(gui, handle), ScrollBox {
    private val componentGroupImpl = ComponentGroupImpl(gui, handle)

    override fun getComponents(): List<Component<*>> {
        return componentGroupImpl.getComponents()
    }

    override fun <T : GermGuiPart<T>> getComponentHandle(id: String, clazz: Class<T>): T? {
        return componentGroupImpl.getComponentHandle(id, clazz)
    }

    override fun <T : Component<*>> getComponent(id: String, clazz: Class<T>): T? {
        return componentGroupImpl.getComponent(id, clazz)
    }
}
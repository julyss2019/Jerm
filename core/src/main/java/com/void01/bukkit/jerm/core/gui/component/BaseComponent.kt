package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component


@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
abstract class BaseComponent<T : GermGuiPart<*>>(override val gui: Gui, override val handle: T) :
    Component<T> {
    override var id: String
        get() = handle.indexName
        set(value) {
            handle.indexName = value
        }
    override var onClickListener: Component.OnClickListener? = null
    override var enabled: Boolean
        get() = handle.isEnable
        set(value) {
            handle.enable = value
        }
    override var tooltips: List<String>
        get() = handle.tooltip
        set(value) {
            handle.tooltip = value
        }

    override fun performClick(clickType: Component.ClickType) {
        onClickListener?.onClick(clickType)
    }

    override fun <T : Component<*>> getPseudoComponent(id: String, clazz: Class<T>): Component<*>? {
        return null
    }
}
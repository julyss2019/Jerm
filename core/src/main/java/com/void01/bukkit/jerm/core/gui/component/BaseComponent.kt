package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup


@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
abstract class BaseComponent<T : GermGuiPart<*>>(
    final override val gui: Gui,
    final override val parent: JermComponentGroup<*>?,
    final override val handle: T
) : Component<T> {
    override var id: String
        get() = handle.indexName
        set(value) {
            handle.indexName = value
        }
    override var isEnabled: Boolean
        get() = handle.isEnable
        set(value) {
            handle.enable = value
        }
    override var tooltips: List<String>
        get() = handle.tooltip
        set(value) {
            handle.tooltip = value
        }
    override var onClickListener: Component.OnClickListener? = null
    override val path: String
        get() {
            val list = mutableListOf<String>()

            list.add(0, id)

            var parent = parent

            while (parent != null) {
                list.add(0, parent.id)
                parent = parent.parent
            }

            val path = list.joinToString(".")

            return path
        }

    override fun <T : Component<*>> getPseudoComponent(id: String, clazz: Class<T>): Component<*>? {
        return null
    }
}
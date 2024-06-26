package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup


@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
abstract class BaseComponent<T : GermGuiPart<*>>(
    override val gui: Gui,
    override val parent: JermComponentGroup<*>?,
    override val handle: T
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
            var tmp: JermComponentGroup<*>? = parent
            val paths = mutableListOf<String>()

            while (tmp != null) {
                paths.add(0, tmp.id)
                tmp = tmp.parent
            }

            return "${paths.joinToString(".")}.${id}"
        }

    override fun <T : Component<*>> getPseudoComponent(id: String, clazz: Class<T>): Component<*>? {
        return null
    }
}
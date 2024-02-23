package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui


@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
interface Component<out T : GermGuiPart<*>> : Cloneable {
    val parent: JermComponentGroup<GermGuiPart<*>>?
    val gui: Gui
    val handle: T
    var id: String
    var tooltips: List<String>
    var enabled: Boolean
    var onClickListener: OnClickListener?

    enum class ClickType {
        LEFT,
        RIGHT,
        MIDDLE,
    }

    interface OnClickListener {
        fun onClick(clickType: ClickType) {}

        fun onClick(clickType: ClickType, shift: Boolean) {}

        fun onClickDown(clickType: ClickType) {}

        fun onClickUp(clickType: ClickType) {}
    }

    fun performClick(clickType: ClickType)

    fun disable() {
        enabled = false
    }

    fun enable() {
        enabled = true
    }

    fun <T : Component<*>> getPseudoComponentOrThrow(id: String, clazz: Class<T>): Component<*> {
        return getPseudoComponent(id, clazz) as T? ?: throw RuntimeException("Unable to find component: $id")
    }

    fun <T : Component<*>> getPseudoComponent(id: String, clazz: Class<T>): Component<*>?

    override fun clone(): Component<T>
}
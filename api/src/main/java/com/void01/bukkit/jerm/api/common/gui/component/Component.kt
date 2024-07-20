package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.exception.ComponentNotFoundException
import com.void01.bukkit.jerm.api.common.gui.Gui
import org.bukkit.event.Event


@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
interface Component<T : GermGuiPart<*>> : Cloneable {
    val parent: JermComponentGroup<*>?
    val gui: Gui
    val handle: T
    var id: String
    var tooltips: List<String>
    var isEnabled: Boolean
    var onClickListener: OnClickListener?
    val path: String

    enum class ClickType {
        LEFT,
        RIGHT,
        MIDDLE,
    }

    /**
     * 点击监听器
     * 注意：ItemSlot 只有 interactive 为 true 时才会触发
     */
    interface OnClickListener {
        fun onClick(clickType: ClickType) {}

        fun onClick(clickType: ClickType, isShift: Boolean) {}

        fun onClick(clickType: ClickType, isShift: Boolean, event: Event) {}

        fun onClickDown(clickType: ClickType) {}

        fun onClickUp(clickType: ClickType) {}
    }

    fun disable() {
        isEnabled = false
    }

    fun enable() {
        isEnabled = true
    }

    fun <T : Component<*>> getPseudoComponentOrThrow(id: String, clazz: Class<T>): Component<*> {
        @Suppress("UNCHECKED_CAST")
        return getPseudoComponent(id, clazz) as T? ?: throw ComponentNotFoundException(gui, this, id)
    }

    fun <T : Component<*>> getPseudoComponent(id: String, clazz: Class<T>): Component<*>?

    override fun clone(): Component<T>
}
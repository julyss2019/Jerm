package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.gui.Gui


@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
interface Component<T : GermGuiPart<*>> : Cloneable {
    val gui: Gui
    val handle: T

    enum class ClickType {
        LEFT, RIGHT
    }

    interface OnClickListener {
        fun onClick(clickType: ClickType)
    }

    fun performClick(clickType: ClickType)

    fun getTooltips(): List<String>

    fun setTooltips(tooltips: List<String>)

    fun getId(): String

    fun disable() {
        setEnabled(false)
    }

    fun enable() {
        setEnabled(true)
    }

    fun setEnabled(boolean: Boolean)

    fun isEnabled(): Boolean

    fun setOnClickListener(listener: OnClickListener?)

    fun getOnClickListener(): OnClickListener?
}
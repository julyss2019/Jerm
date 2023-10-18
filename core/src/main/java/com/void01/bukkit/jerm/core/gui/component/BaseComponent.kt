package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component


@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
open class BaseComponent<T : GermGuiPart<*>>(override val gui: Gui, override val handle: T) : Component<T> {
    private var onClickListener: Component.OnClickListener? = null

    override fun performClick(clickType: Component.ClickType) {
        getOnClickListener()?.onClick(clickType)
    }

    override fun getTooltips(): List<String> {
        return handle.tooltip
    }

    override fun setTooltips(tooltips: List<String>) {
        handle.tooltip = tooltips
    }

    override fun getId(): String {
        return handle.indexName
    }

    override fun setEnabled(boolean: Boolean) {
        handle.setEnable(boolean)
    }

    override fun isEnabled(): Boolean {
        return handle.isEnable
    }

    override fun setOnClickListener(listener: Component.OnClickListener?) {
        this.onClickListener = listener
    }

    override fun getOnClickListener(): Component.OnClickListener? {
        return this.onClickListener
    }
}
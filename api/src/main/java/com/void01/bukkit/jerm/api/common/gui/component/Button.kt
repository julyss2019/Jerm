package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiButton

interface Button : Component<GermGuiButton> {
    override val origin: Button

    enum class ClickType {
        LEFT, RIGHT, MIDDLE
    }

    interface OnClickListener {
        fun onClick(event: ClickType, shift: Boolean)
    }

    var texts: List<String>
    var onButtonClickListener: OnClickListener?

    fun performButtonClick(clickType: ClickType, shift: Boolean) {
        onButtonClickListener?.onClick(clickType, shift)
    }

    public override fun clone(): Button
}
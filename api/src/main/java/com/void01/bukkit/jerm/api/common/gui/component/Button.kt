package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiButton

interface Button : Component<GermGuiButton> {
    enum class ClickType {
        LEFT, RIGHT, MIDDLE
    }

    interface OnClickListener {
        fun onClick(event: ClickType, shift: Boolean)
    }

    var texts: List<String>

    @Deprecated(message = "弃用")
    var onButtonClickListener: OnClickListener?

    @Deprecated(message = "弃用")
    fun performButtonClick(clickType: ClickType, shift: Boolean) {
        onButtonClickListener?.onClick(clickType, shift)
    }

    fun setTexts(vararg texts: String) {
        this.texts = texts.toList()
    }

    fun clearTexts() {
        this.texts = emptyList()
    }

    public override fun clone(): Button
}
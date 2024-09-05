package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiButton

interface Button : Component<GermGuiButton> {
    enum class ClickType {
        LEFT, RIGHT, MIDDLE
    }

    interface OnClickListener {
        fun onClick(event: ClickType, shift: Boolean)
    }

    var texturePath: String?
    var hoverTexturePath: String?
    var texts: List<String>

    @Deprecated(message = "弃用")
    var onButtonClickListener: OnClickListener?

    @Deprecated(message = "弃用", replaceWith = ReplaceWith("onButtonClickListener?.onClick(clickType, shift)"))
    fun performButtonClick(clickType: ClickType, shift: Boolean) {
        @Suppress("DEPRECATION")
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
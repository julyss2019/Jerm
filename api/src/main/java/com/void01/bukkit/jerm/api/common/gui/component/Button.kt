package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiButton

interface Button : Component<GermGuiButton> {
    enum class ClickType {
        LEFT, RIGHT, MIDDLE
    }

    interface OnClickListener {
        fun onClick(event: ClickType, shift: Boolean)
    }

    override val handle: GermGuiButton

    fun performButtonClick(clickType: ClickType, shift: Boolean) {
        getOnButtonClickListener()?.onClick(clickType, shift)
    }

    fun getTexts(): List<String>

    fun setTexts(texts: List<String>)

    fun setOnButtonClickListener(listener: OnClickListener?)

    fun getOnButtonClickListener(): OnClickListener?
}
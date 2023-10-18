package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiButton
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Button
import com.void01.bukkit.jerm.api.common.gui.component.Component

class ButtonImpl(gui: Gui, handle: GermGuiButton) : BaseComponent<GermGuiButton>(gui, handle), Button {
    private var onClickListener: Button.OnClickListener? = null

    init {
        handle.registerCallbackHandler({ _, _ ->
            onClickListener?.onClick(Button.ClickType.LEFT, false)
        }, GermGuiButton.EventType.LEFT_CLICK)
        handle.registerCallbackHandler({ _, _ ->
            onClickListener?.onClick(Button.ClickType.LEFT, true)
        }, GermGuiButton.EventType.SHIFT_LEFT_CLICK)

        handle.registerCallbackHandler({ _, _ ->
            onClickListener?.onClick(Button.ClickType.RIGHT, false)
        }, GermGuiButton.EventType.RIGHT_CLICK)
        handle.registerCallbackHandler({ _, _ ->
            onClickListener?.onClick(Button.ClickType.RIGHT, true)
        }, GermGuiButton.EventType.SHIFT_RIGHT_CLICK)

        handle.registerCallbackHandler({ _, _ ->
            onClickListener?.onClick(Button.ClickType.MIDDLE, false)
        }, GermGuiButton.EventType.MIDDLE_CLICK)
        handle.registerCallbackHandler({ _, _ ->
            onClickListener?.onClick(Button.ClickType.MIDDLE, true)
        }, GermGuiButton.EventType.SHIFT_MILLE_CLICK)
    }

    override fun getTexts(): List<String> {
        return handle.texts
    }

    override fun setTexts(texts: List<String>) {
        handle.texts = texts
    }

    override fun setOnButtonClickListener(listener: Button.OnClickListener?) {
        this.onClickListener = listener
    }

    override fun getOnButtonClickListener(): Button.OnClickListener? {
        return this.onClickListener
    }
}
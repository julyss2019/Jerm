package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiButton
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Button
import com.void01.bukkit.jerm.core.util.GermUtils

class ButtonImpl(gui: Gui, group: ComponentGroup, handle: GermGuiButton) :
    BaseComponent<GermGuiButton>(gui, group, handle), Button {
    override val origin: Button by lazy { clone() }
    override var texts: List<String>
        get() = handle.texts
        set(value) {
            handle.texts = value
        }
    override var onButtonClickListener: Button.OnClickListener? = null

    init {
        handle.registerCallbackHandler({ _, _ ->
            onButtonClickListener?.onClick(Button.ClickType.LEFT, false)
        }, GermGuiButton.EventType.LEFT_CLICK)
        handle.registerCallbackHandler({ _, _ ->
            onButtonClickListener?.onClick(Button.ClickType.LEFT, true)
        }, GermGuiButton.EventType.SHIFT_LEFT_CLICK)

        handle.registerCallbackHandler({ _, _ ->
            onButtonClickListener?.onClick(Button.ClickType.RIGHT, false)
        }, GermGuiButton.EventType.RIGHT_CLICK)
        handle.registerCallbackHandler({ _, _ ->
            onButtonClickListener?.onClick(Button.ClickType.RIGHT, true)
        }, GermGuiButton.EventType.SHIFT_RIGHT_CLICK)

        handle.registerCallbackHandler({ _, _ ->
            onButtonClickListener?.onClick(Button.ClickType.MIDDLE, false)
        }, GermGuiButton.EventType.MIDDLE_CLICK)
        handle.registerCallbackHandler({ _, _ ->
            onButtonClickListener?.onClick(Button.ClickType.MIDDLE, true)
        }, GermGuiButton.EventType.SHIFT_MILLE_CLICK)
    }

    override fun clone(): Button {
        return ButtonImpl(gui, group, GermUtils.cloneGuiPart(handle))
    }
}
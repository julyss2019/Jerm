package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiButton
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Button
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup
import com.void01.bukkit.jerm.core.util.GermUtils

class ButtonImpl(
    gui: Gui,
    parent: JermComponentGroup<*>?,
    handle: GermGuiButton
) : BaseComponent<GermGuiButton>(gui, parent, handle), Button {
    override var texturePath: String?
        get() = handle.defaultPath
        set(value) {
            handle.defaultPath = value ?: GermUtils.EMPTY_TEXTURE_PATH
        }
    override var hoverTexturePath: String?
        get() = handle.hoverPath
        set(value) {
            handle.hoverPath = value ?: GermUtils.EMPTY_TEXTURE_PATH
        }
    override var texts: List<String>
        get() = handle.texts
        set(value) {
            handle.texts = value
        }
    private var germHandlersRegistered = false

    @Deprecated("弃用")
    override var onButtonClickListener: Button.OnClickListener? = null
        set(value) {
            registerGermHandlers()
            field = value
        }

    @Suppress("DEPRECATION")
    private fun registerGermHandlers() {
        // 考虑原生 GUI 会被实例化为 Jerm GUI
        if (germHandlersRegistered) {
            return
        }

        GermGuiButton.EventType.entries.forEach {
            if (handle.getCallbackHandler(it) != null) {
                throw RuntimeException("Unable to register onButtonClickListener, because Germ's ${it.name} callback handler is registered")
            }
        }

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

        germHandlersRegistered = true
    }

    override fun clone(): Button {
        return ButtonImpl(gui, parent, GermUtils.cloneGuiPart(handle))
    }
}
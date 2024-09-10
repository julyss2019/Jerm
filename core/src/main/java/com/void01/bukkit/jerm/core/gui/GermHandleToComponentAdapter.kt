package com.void01.bukkit.jerm.core.gui

import com.germ.germplugin.api.dynamic.gui.*
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup
import com.void01.bukkit.jerm.core.gui.component.*

object GermHandleToComponentAdapter {
    fun adapt(gui: Gui, parent: JermComponentGroup<*>?, handle: GermGuiPart<*>): Component<*> {
        return when (handle::class.java) {
            GermGuiButton::class.java -> ButtonImpl(gui, parent, handle as GermGuiButton)
            GermGuiLabel::class.java -> LabelImpl(gui, parent, handle as GermGuiLabel)
            GermGuiSlot::class.java -> ItemSlotImpl(gui, parent, handle as GermGuiSlot)
            GermGuiScroll::class.java -> ScrollBoxImpl(gui, parent, handle as GermGuiScroll)
            GermGuiCanvas::class.java -> CanvasImpl(gui, parent, handle as GermGuiCanvas)
            GermGuiTexture::class.java -> TextureImpl(gui, parent, handle as GermGuiTexture)
            GermGuiInput::class.java -> InputImpl(gui, parent, handle as GermGuiInput)
            GermGuiCheckbox::class.java -> CheckBoxImpl(gui, parent, handle as GermGuiCheckbox)
            else -> {
                UnsupportedComponent(gui, parent, handle)
            }
        }
    }
}
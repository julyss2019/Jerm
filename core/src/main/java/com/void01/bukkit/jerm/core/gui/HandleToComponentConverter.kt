package com.void01.bukkit.jerm.core.gui

import com.germ.germplugin.api.dynamic.gui.*
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup
import com.void01.bukkit.jerm.core.gui.component.*

object HandleToComponentConverter {
    fun convert(gui: Gui, parent: JermComponentGroup<GermGuiPart<*>>?, handle: GermGuiPart<*>): Component<*> {
        return when (handle::class.java) {
            GermGuiButton::class.java -> ButtonImpl(gui, parent, handle as GermGuiButton)
            GermGuiLabel::class.java -> LabelImpl(gui, parent, handle as GermGuiLabel)
            GermGuiSlot::class.java -> ItemSlotImpl(gui, parent, handle as GermGuiSlot)
            GermGuiScroll::class.java -> ScrollBoxJerm(gui, parent, handle as GermGuiScroll)
            GermGuiCanvas::class.java -> CanvasJerm(gui, parent, handle as GermGuiCanvas)
            GermGuiTexture::class.java -> TextureImpl(gui, parent, handle as GermGuiTexture)
            else -> {
                UnsupportedComponent(gui, parent, handle)
            }
        }
    }
}
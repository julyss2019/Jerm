package com.void01.bukkit.jerm.core.gui

import com.germ.germplugin.api.dynamic.gui.*
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.core.gui.component.*

object HandleToComponentConverter {
    fun convert(gui: Gui, group: ComponentGroup, handle: GermGuiPart<*>): Component<*> {
        return when (handle::class.java) {
            GermGuiButton::class.java -> ButtonImpl(gui, group, handle as GermGuiButton)
            GermGuiLabel::class.java -> LabelImpl(gui, group, handle as GermGuiLabel)
            GermGuiSlot::class.java -> ItemSlotImpl(gui, group, handle as GermGuiSlot)
            GermGuiScroll::class.java -> ScrollBoxImpl(gui, group, handle as GermGuiScroll)
            GermGuiCanvas::class.java -> CanvasImpl(gui, group, handle as GermGuiCanvas)
            GermGuiTexture::class.java -> TextureImpl(gui, group, handle as GermGuiTexture)
            else -> {
                UnsupportedComponent(gui, group, handle)
            }
        }
    }
}
package com.void01.bukkit.jerm.core.gui

import com.germ.germplugin.api.dynamic.gui.GermGuiButton
import com.germ.germplugin.api.dynamic.gui.GermGuiCanvas
import com.germ.germplugin.api.dynamic.gui.GermGuiLabel
import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.GermGuiScroll
import com.germ.germplugin.api.dynamic.gui.GermGuiSlot
import com.germ.germplugin.api.dynamic.gui.GermGuiTexture
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.core.gui.component.*

object HandleToComponentConverter {
    fun convert(gui: Gui, handle: GermGuiPart<*>): Component<*> {
        return when (handle::class.java) {
            GermGuiButton::class.java -> ButtonImpl(gui, handle as GermGuiButton)
            GermGuiLabel::class.java -> LabelImpl(gui, handle as GermGuiLabel)
            GermGuiSlot::class.java -> ItemSlotImpl(gui, handle as GermGuiSlot)
            GermGuiScroll::class.java -> ScrollBoxImpl(gui, handle as GermGuiScroll)
            GermGuiCanvas::class.java -> CanvasImpl(gui, handle as GermGuiCanvas)
            GermGuiTexture::class.java -> TextureImpl(gui, handle as GermGuiTexture)
            else -> {
                UnsupportedComponent(gui, handle)
            }
        }
    }
}
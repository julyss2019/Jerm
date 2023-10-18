package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiButton
import com.germ.germplugin.api.dynamic.gui.GermGuiLabel
import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.GermGuiSlot
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component

object GermComponentToJermComponentConverter {
    fun convert(gui: Gui, handle: GermGuiPart<*>): Component<*> {
        return when (handle::class.java) {
            GermGuiButton::class.java -> ButtonImpl(gui, handle as GermGuiButton)
            GermGuiLabel::class.java -> LabelImpl(gui, handle as GermGuiLabel)
            GermGuiSlot::class.java -> ItemSlotImpl(gui, handle as GermGuiSlot)
            else -> {
                UnsupportedComponent(gui, handle)
            }
        }
    }
}
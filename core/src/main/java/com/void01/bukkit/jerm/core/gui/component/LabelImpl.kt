package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiLabel
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.ItemSlot
import com.void01.bukkit.jerm.api.common.gui.component.Label
import com.void01.bukkit.jerm.core.util.GermUtils

class LabelImpl(gui: Gui, handle: GermGuiLabel) : BaseComponent<GermGuiLabel>(gui, handle), Label {
    override fun getTexts(): List<String> {
        return handle.texts
    }

    override fun setTexts(texts: List<String>) {
        handle.texts = texts
    }

    override fun clone(): Label {
        return LabelImpl(gui, GermUtils.cloneGuiPart(handle))
    }
}
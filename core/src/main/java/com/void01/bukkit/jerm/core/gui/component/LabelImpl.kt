package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiLabel
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup
import com.void01.bukkit.jerm.api.common.gui.component.Label
import com.void01.bukkit.jerm.core.util.GermUtils

class LabelImpl(gui: Gui, parent: JermComponentGroup<*>?, handle: GermGuiLabel) :
    BaseComponent<GermGuiLabel>(gui, parent, handle), Label {
    override var texts: List<String>
        get() = handle.texts
        set(value) {
            handle.texts = value
        }

    override fun setTexts(vararg texts: String) {
        handle.texts = texts.toList()
    }

    override fun addText(text: String) {
        handle.addText(text)
    }

    override fun clone(): Label {
        return LabelImpl(gui, parent, GermUtils.cloneGuiPart(handle))
    }
}
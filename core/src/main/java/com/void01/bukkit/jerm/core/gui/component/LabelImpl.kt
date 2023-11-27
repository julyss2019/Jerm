package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiLabel
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Label
import com.void01.bukkit.jerm.core.util.GermUtils

class LabelImpl(gui: Gui, group: ComponentGroup, handle: GermGuiLabel) :
    BaseComponent<GermGuiLabel>(gui, group, handle), Label {
    override val origin: Label by lazy { clone() }

    override fun getTexts(): List<String> {
        return handle.texts
    }

    override fun setTexts(texts: List<String>) {
        handle.texts = texts
    }

    override fun setTexts(vararg texts: String) {
        handle.texts = texts.toList()
    }

    override fun addText(text: String) {
        handle.addText(text)
    }

    override fun clone(): Label {
        return LabelImpl(gui, group, GermUtils.cloneGuiPart(handle))
    }
}
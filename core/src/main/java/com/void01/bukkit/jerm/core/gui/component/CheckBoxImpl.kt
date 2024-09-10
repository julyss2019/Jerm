package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiCheckbox
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.CheckBox
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup
import com.void01.bukkit.jerm.core.util.GermUtils

class CheckBoxImpl(
    gui: Gui,
    parent: JermComponentGroup<*>?,
    handle: GermGuiCheckbox
) : BaseComponent<GermGuiCheckbox>(gui, parent, handle), CheckBox {
    override var onCheckedChangeListener: CheckBox.OnCheckedChangeListener? = null

    override fun clone(): Component<GermGuiCheckbox> {
        return CheckBoxImpl(gui, parent, GermUtils.cloneGuiPart(handle))
    }
}
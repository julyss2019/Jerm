package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup
import com.void01.bukkit.jerm.core.util.GermUtils

class UnsupportedComponent(gui: Gui, parent: JermComponentGroup<*>?, handle: GermGuiPart<*>) :
    BaseComponent<GermGuiPart<*>>(gui, parent, handle) {

    override fun clone(): UnsupportedComponent {
        return UnsupportedComponent(gui, parent, GermUtils.cloneGuiPart(handle))
    }
}
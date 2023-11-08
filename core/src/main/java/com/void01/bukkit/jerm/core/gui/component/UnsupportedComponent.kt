package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.core.util.GermUtils

class UnsupportedComponent(gui: Gui, handle: GermGuiPart<*>) :
    BaseComponent<GermGuiPart<*>>(gui, handle) {
    override fun clone(): Component<GermGuiPart<*>> {
        return UnsupportedComponent(gui, GermUtils.cloneGuiPart(handle))
    }
}
package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.core.util.GermUtils

class UnsupportedComponent(gui: Gui, group: ComponentGroup, handle: GermGuiPart<*>) :
    BaseComponent<GermGuiPart<*>>(gui, group, handle) {
    override val origin: UnsupportedComponent by lazy { clone() }

    override fun clone(): UnsupportedComponent {
        return UnsupportedComponent(gui, group, GermUtils.cloneGuiPart(handle))
    }
}
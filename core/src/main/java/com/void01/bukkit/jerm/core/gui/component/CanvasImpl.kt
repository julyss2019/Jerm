package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiCanvas
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Canvas
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup
import com.void01.bukkit.jerm.core.util.GermUtils

class CanvasImpl(gui: Gui, parent: JermComponentGroup<*>?, handle: GermGuiCanvas) :
    BaseJermComponentGroup<GermGuiCanvas>(
        gui = gui,
        parent = parent,
        handle = handle,
        containerHandle = handle
    ), Canvas {
    override fun clone(): Canvas {
        return CanvasImpl(gui, parent, GermUtils.cloneGuiPart(handle))
    }
}
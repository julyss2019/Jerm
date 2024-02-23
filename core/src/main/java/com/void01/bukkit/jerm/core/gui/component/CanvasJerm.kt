package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiCanvas
import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Canvas
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup
import com.void01.bukkit.jerm.core.util.GermUtils

class CanvasJerm(override val gui: Gui, parent: JermComponentGroup<GermGuiPart<*>>?, handle: GermGuiCanvas) :
    BaseJermComponentGroup<GermGuiCanvas>(gui, parent, handle, handle), Canvas {

    override fun clone(): Canvas {
        return CanvasJerm(gui, parent, GermUtils.cloneGuiPart(handle))
    }
}
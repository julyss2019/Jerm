package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiCanvas
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup

interface Canvas : JermComponentGroup<GermGuiCanvas> {
    public override fun clone(): Canvas
}
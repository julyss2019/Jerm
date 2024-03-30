package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiCanvas

interface Canvas : JermComponentGroup<GermGuiCanvas> {
    public override fun clone(): Canvas
}
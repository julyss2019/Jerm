package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiScroll
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup

interface ScrollBox : Component<GermGuiScroll>, ComponentGroup {
    public override fun clone(): ScrollBox
}
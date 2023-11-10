package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiButton
import com.germ.germplugin.api.dynamic.gui.GermGuiScroll
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup

interface ScrollBox : Component<GermGuiScroll>, ComponentGroup {
    interface ScrollBar : Component<GermGuiButton> {
        val scrollBox: ScrollBox

        override fun clone(): ScrollBar
    }

    override val origin: ScrollBox
    var horizontalScrollBar: ScrollBar?
    var verticalScrollBar: ScrollBar?

    public override fun clone(): ScrollBox
}
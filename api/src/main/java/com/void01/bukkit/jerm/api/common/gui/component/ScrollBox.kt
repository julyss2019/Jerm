package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiButton
import com.germ.germplugin.api.dynamic.gui.GermGuiScroll

interface ScrollBox : JermComponentGroup<GermGuiScroll> {
    interface ScrollBar : PseudoComponent<GermGuiButton> {
        val scrollBox: ScrollBox

        override fun clone(): ScrollBar
    }

    var horizontalScrollBar: ScrollBar?
    var verticalScrollBar: ScrollBar?

    public override fun clone(): ScrollBox
}
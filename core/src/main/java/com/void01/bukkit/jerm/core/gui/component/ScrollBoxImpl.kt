package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiButton
import com.germ.germplugin.api.dynamic.gui.GermGuiScroll
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Button
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup
import com.void01.bukkit.jerm.api.common.gui.component.ScrollBox
import com.void01.bukkit.jerm.core.gui.GermHandleToComponentAdapter
import com.void01.bukkit.jerm.core.util.GermUtils

class ScrollBoxImpl(gui: Gui, parent: JermComponentGroup<*>?, handle: GermGuiScroll) :
    BaseJermComponentGroup<GermGuiScroll>(gui, parent, handle, handle), ScrollBox {

    class ScrollBarImpl(private val button: Button, override val scrollBox: ScrollBox) : ScrollBox.ScrollBar,
        Component<GermGuiButton> by button {

        override fun clone(): ScrollBox.ScrollBar {
            return ScrollBarImpl(button.clone(), scrollBox)
        }
    }

    override var horizontalScrollBar: ScrollBox.ScrollBar? = null
    override var verticalScrollBar: ScrollBox.ScrollBar? = null

    init {
        if (handle.sliderV != null) {
            verticalScrollBar =
                ScrollBarImpl(GermHandleToComponentAdapter.adapt(gui, parent, handle.sliderV) as Button, this)
        }

        if (handle.sliderH != null) {
            horizontalScrollBar =
                ScrollBarImpl(GermHandleToComponentAdapter.adapt(gui, parent, handle.sliderH) as Button, this)
        }
    }

    override fun clone(): ScrollBox {
        return ScrollBoxImpl(gui, parent, GermUtils.cloneGuiPart(handle))
    }

    override fun <T : Component<*>> getPseudoComponent(id: String, clazz: Class<T>): Component<*>? {
        if (id == horizontalScrollBar?.id) {
            return horizontalScrollBar
        }

        if (id == verticalScrollBar?.id) {
            return verticalScrollBar
        }

        return null
    }
}
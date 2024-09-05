package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiInput
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.Input
import com.void01.bukkit.jerm.api.common.gui.component.Input.Type
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup
import com.void01.bukkit.jerm.core.util.GermUtils

class InputImpl(
    gui: Gui,
    parent: JermComponentGroup<*>?,
    handle: GermGuiInput
) : BaseComponent<GermGuiInput>(gui, parent, handle), Input {
    override var type: Type = Type.TEXT
        set(value) {
            // 萌芽的正则是反着来的，正则匹配成功则清除入，正则匹配失败则保留
            handle.regular = when (value) {
                Type.NUMBER -> "[^(0-9)]"
                Type.TEXT -> ""
            }
            field = value
        }

    override fun clone(): Component<GermGuiInput> {
        return InputImpl(gui, parent, GermUtils.cloneGuiPart(handle))
    }
}
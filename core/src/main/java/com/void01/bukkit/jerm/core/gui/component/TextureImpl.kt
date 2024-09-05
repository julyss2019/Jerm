package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiTexture
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup
import com.void01.bukkit.jerm.api.common.gui.component.Texture
import com.void01.bukkit.jerm.core.util.GermUtils

class TextureImpl(
    gui: Gui,
    group: JermComponentGroup<*>?,
    handle: GermGuiTexture
) : BaseComponent<GermGuiTexture>(gui, group, handle), Texture {
    override var texturePath: String?
        get() = handle.path
        set(value) {
            handle.path = value ?: GermUtils.EMPTY_TEXTURE_PATH
        }

    override fun clone(): Texture {
        return TextureImpl(gui, parent, GermUtils.cloneGuiPart(handle))
    }
}
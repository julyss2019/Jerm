package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiTexture
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Texture
import com.void01.bukkit.jerm.core.util.GermUtils

class TextureImpl(gui: Gui, handle: GermGuiTexture) : BaseComponent<GermGuiTexture>(gui, handle), Texture {
    override fun clone(): Component<GermGuiTexture> {
        return TextureImpl(gui, GermUtils.cloneGuiPart(handle))
    }
}
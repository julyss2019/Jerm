package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiTexture
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Texture
import com.void01.bukkit.jerm.core.util.GermUtils

class TextureImpl(gui: Gui, group: ComponentGroup, handle: GermGuiTexture) :
    BaseComponent<GermGuiTexture>(gui, group, handle), Texture {
    override val origin: Texture by lazy { clone() }

    override fun clone(): Texture {
        return TextureImpl(gui, group, GermUtils.cloneGuiPart(handle))
    }
}
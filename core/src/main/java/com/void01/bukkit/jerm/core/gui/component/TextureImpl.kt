package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.GermGuiTexture
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup
import com.void01.bukkit.jerm.api.common.gui.component.Texture
import com.void01.bukkit.jerm.core.util.GermUtils

class TextureImpl(gui: Gui, group: JermComponentGroup<GermGuiPart<*>>?, handle: GermGuiTexture) :
    BaseComponent<GermGuiTexture>(gui, group, handle), Texture {

    override fun clone(): Texture {
        return TextureImpl(gui, parent, GermUtils.cloneGuiPart(handle))
    }
}
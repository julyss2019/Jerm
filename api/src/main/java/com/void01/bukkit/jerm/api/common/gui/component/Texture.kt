package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiTexture

interface Texture : Component<GermGuiTexture> {
    var texturePath: String?

    public override fun clone(): Texture
}
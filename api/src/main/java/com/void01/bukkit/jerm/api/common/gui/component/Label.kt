package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiLabel

interface Label : Component<GermGuiLabel> {
    override val handle: GermGuiLabel

    fun getTexts(): List<String>

    fun setTexts(texts: List<String>)

    fun setText(text: String) {
        setTexts(listOf(text))
    }

    public override fun clone(): Label
}
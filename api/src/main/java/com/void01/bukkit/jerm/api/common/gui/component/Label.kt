package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiLabel

interface Label : Component<GermGuiLabel> {
    override val origin: Label
    override val handle: GermGuiLabel

    fun getTexts(): List<String>

    fun setTexts(vararg texts: String)

    fun setTexts(texts: List<String>)

    fun addText(text: String)

    fun removeTexts() {
        setTexts(emptyList())
    }

    @Deprecated(
        message = "使用 addText 或 setTexts",
        replaceWith = ReplaceWith("addText(String) or setTexts(List<String>)")
    )
    fun setText(text: String) {
        setTexts(listOf(text))
    }

    public override fun clone(): Label
}
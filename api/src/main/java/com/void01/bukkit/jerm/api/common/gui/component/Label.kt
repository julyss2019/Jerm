package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiLabel

interface Label : Component<GermGuiLabel> {
    override val handle: GermGuiLabel
    var texts : List<String>

    fun setTexts(vararg texts: String)

    fun addText(text: String)

    fun clearTexts() {
        texts = emptyList()
    }

    @Deprecated(message = "使用 clearTexts()", replaceWith = ReplaceWith("clearTexts()"))
    fun removeTexts() {
        clearTexts()
    }

    @Deprecated(
        message = "使用 addText 或 setTexts",
        replaceWith = ReplaceWith("addText(String) or setTexts(List<String>)")
    )
    fun setText(text: String) {
        setTexts(text)
    }

    public override fun clone(): Label
}
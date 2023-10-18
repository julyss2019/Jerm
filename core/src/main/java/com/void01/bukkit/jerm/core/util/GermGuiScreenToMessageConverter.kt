package com.void01.bukkit.jerm.core.util

import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import com.void01.bukkit.jerm.api.common.Jerm
import com.void01.bukkit.jerm.core.gui.GuiImpl

object GermGuiScreenToMessageConverter {
    fun convert(germGuiScreen: GermGuiScreen, containsGermGuiParts: Boolean = false): String {
        val sourceFile = (Jerm.getGuiManager().getGui(germGuiScreen.guiName) as GuiImpl?)?.sourceFile
        var message = ""

        message += "id: ${germGuiScreen.guiName}\n"
        message += "source_file: ${sourceFile?.absolutePath}\n"

        if (containsGermGuiParts) {
            germGuiScreen.guiParts.forEach {
                message += GermGuiPartToMessageConverter.convert(it).let { string ->
                    IndentAppender.addIndentToEachLine(2, string)
                }
                message += "\n"
            }
        }

        return message
    }
}
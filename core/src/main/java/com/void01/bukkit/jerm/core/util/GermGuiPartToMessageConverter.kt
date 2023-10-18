package com.void01.bukkit.jerm.core.util

import com.germ.germplugin.api.dynamic.gui.GermGuiPart

object GermGuiPartToMessageConverter {
    fun convert(handle: GermGuiPart<*>): String {
        return "id: ${handle.indexName}\n" +
                "type: ${handle.javaClass.typeName}"
    }
}
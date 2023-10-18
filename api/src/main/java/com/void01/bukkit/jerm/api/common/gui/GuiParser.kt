package com.void01.bukkit.jerm.api.common.gui

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

interface GuiParser {
    fun parseGuis(file: File): List<Gui>

    fun parseGuis(yaml: YamlConfiguration): List<Gui>

    fun parseGui(file: File, id: String): Gui

    fun parseGui(yaml: YamlConfiguration, id: String): Gui
}
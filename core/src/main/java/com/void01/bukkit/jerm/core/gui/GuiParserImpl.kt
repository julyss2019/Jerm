package com.void01.bukkit.jerm.core.gui

import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.GuiParser
import com.void01.bukkit.jerm.core.JermPlugin
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class GuiParserImpl(private val plugin: JermPlugin) : GuiParser {
    override fun parseGuis(file: File): List<Gui> {
        val yaml = YamlConfiguration.loadConfiguration(file)

        return yaml.getKeys(false).map {
            GuiImpl(GermGuiScreen.getGermGuiScreen(it, YamlConfiguration.loadConfiguration(file)), file, plugin)
        }
    }

    override fun parseGuis(yaml: YamlConfiguration): List<Gui> {
        return yaml.getKeys(false).map {
            GuiImpl(GermGuiScreen.getGermGuiScreen(it, yaml), null, plugin)
        }
    }

    override fun parseGui(file: File, id: String): Gui {
        return GuiImpl(GermGuiScreen.getGermGuiScreen(id, YamlConfiguration.loadConfiguration(file)), file, plugin)
    }

    override fun parseGui(yaml: YamlConfiguration, id: String): Gui {
        return GuiImpl(GermGuiScreen.getGermGuiScreen(id, yaml), null, plugin)
    }
}

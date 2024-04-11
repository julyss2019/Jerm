package com.void01.bukkit.jerm.core.command

import com.github.julyss2019.bukkit.voidframework.command.CommandGroup
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandBody
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandMapping
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandParam
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.GuiManager
import com.void01.bukkit.jerm.api.common.player.JermPlayer
import com.void01.bukkit.jerm.api.common.player.JermPlayerManager
import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.gui.GuiImpl
import com.void01.bukkit.jerm.core.util.GermUtils
import com.void01.bukkit.jerm.core.util.MessageUtils
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

@CommandMapping(value = "gui")
class GuiCommandGroup(plugin: JermPlugin) : CommandGroup {
    private val jermPlayerManager: JermPlayerManager = plugin.jermPlayerManager
    private val guiManager: GuiManager = plugin.guiManager

    @CommandBody(value = "listGUIs", description = "列出所有 GUI")
    fun list(
        sender: CommandSender,
        @CommandParam(description = "是否显示详情(false)", optional = true) detail: Boolean?
    ) {
        guiManager.getGuis()
            .sortedBy { it.id }
            .forEach {
                val processedMessage: String = if (detail == true) {
                    val guiFolderPath = GermUtils.getGuiFolder().absolutePath
                    val sourceFilePath = (it as GuiImpl).sourceFile!!.absolutePath
                    val displayPath: String = if (sourceFilePath.startsWith(guiFolderPath)) {
                        sourceFilePath.substring(guiFolderPath.length + 1)
                    } else {
                        sourceFilePath
                    }

                    "- ${it.id}($displayPath)"
                } else {
                    "- ${it.id}"
                }

                if (sender is ConsoleCommandSender) {
                    MessageUtils.sendMessage(sender, processedMessage)
                } else {
                    MessageUtils.sendCommandRawMessage(sender as Player, "$processedMessage &c[点击打开]", "/jerm gui openGUI ${it.id}")
                }
            }
    }

    @CommandBody(value = "listUsingGUIs", description = "列出玩家所有正在使用的 GUI")
    fun getAllUsingGui(sender: CommandSender, @CommandParam(description = "玩家") jermPlayer: JermPlayer) {
        val usingGuis = jermPlayer.getUsingGuis()

        MessageUtils.sendMessage(sender, "玩家正在使用 ${usingGuis.size} 个 GUI: ")
        usingGuis.forEach {
            MessageUtils.sendMessage(sender, "- ${it.handle.guiName}")
        }
    }

    @CommandBody(value = "openHUD", description = "为玩家打开 HUD")
    fun openHud(
        sender: CommandSender,
        @CommandParam(description = "GUI") gui: Gui,
        @CommandParam(description = "玩家名(Player: 自己)", optional = true) player: Player?,
    ) {
        if (player != null) {
            gui.openAsHud(player)
        } else {
            if (checkPlayerSender(sender)) {
                gui.openAsHud(sender as Player)
            }
        }
    }

    @CommandBody(value = "openGUI", description = "为玩家打开 GUI")
    fun open(
        sender: CommandSender,
        @CommandParam(description = "GUI") gui: Gui,
        @CommandParam(description = "玩家名(Player: 自己)", optional = true) player: Player?,
    ) {
        if (player != null) {
            gui.openAsGui(player)
        } else {
            if (checkPlayerSender(sender)) {
                gui.openAsGui(sender as Player)
            }
        }
    }

    private fun checkPlayerSender(sender: CommandSender): Boolean {
        if (sender !is Player) {
            MessageUtils.sendMessage(sender, "命令执行者必须是玩家.");
            return false
        }

        return true
    }
}

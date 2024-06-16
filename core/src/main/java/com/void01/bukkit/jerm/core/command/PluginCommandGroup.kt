package com.void01.bukkit.jerm.core.command

import com.github.julyss2019.bukkit.voidframework.command.CommandGroup
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandBody
import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.util.MessageUtils
import org.bukkit.command.CommandSender

class PluginCommandGroup(private val plugin: JermPlugin) : CommandGroup {
    @CommandBody(value = "reload", description = "重载插件")
    fun reload(sender: CommandSender) {
        plugin.reload()
        MessageUtils.sendMessage(sender, "重载完毕.")
    }
}

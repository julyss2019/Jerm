package com.void01.bukkit.jerm.core.util

import com.github.julyss2019.bukkit.voidframework.common.Messages
import com.github.julyss2019.bukkit.voidframework.text.Texts
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.json.simple.JSONObject

internal object MessageUtils {
    fun sendMessage(sender: CommandSender, msg: String) {
        msg.split("\n").forEach {
            Messages.sendColoredMessage(sender, "&a[Jerm] &f$it")
        }
    }

    fun sendCommandRawMessage(player: Player, message: String, command: String) {
        Bukkit.dispatchCommand(
            Bukkit.getConsoleSender(),
            """tellraw ${player.name} [{"text":"${JSONObject.escape(Texts.getColoredText("&a[Jerm] &f$message"))}","clickEvent":{"action":"run_command","value":"${
                JSONObject.escape(
                    command
                )
            }"}}]
        """.trimIndent()
        )
    }
}
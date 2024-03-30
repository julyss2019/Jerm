package com.void01.bukkit.jerm.core.command

import com.github.julyss2019.bukkit.voidframework.command.CommandGroup
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandBody
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandMapping
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandParam
import com.void01.bukkit.jerm.api.common.Jerm
import com.void01.bukkit.jerm.core.util.MessageUtils
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandMapping(value = "animation")
class AnimationCommandGroup : CommandGroup {
    @CommandBody(value = "play", description = "播放动画")
    fun playerToPlayerPlay(
        sender: CommandSender,
        @CommandParam(description = "动画") animation: String,
        @CommandParam(description = "表演者", optional = true) performer: Player?,
        @CommandParam(description = "速度(Float: 1)", optional = true) speed: Float?,
    ) {
        if (performer == null) {
            if (sender !is Player) {
                MessageUtils.sendMessage(sender, "命令执行者必须是玩家.")
                return
            }
        }

        val actualPerformer = performer ?: sender as Player

        Jerm.getAnimationManager().playAnimationToSelfAndNearbyPlayers(
            actualPerformer,
            animation,
            speed ?: 1F
        )
    }
}
package com.void01.bukkit.jerm.core.command

import com.void01.bukkit.jerm.core.JermPlugin
import com.github.julyss2019.bukkit.voidframework.command.CommandGroup
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandBody
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandMapping
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandParam
import com.void01.bukkit.jerm.api.common.animation.Animation
import com.void01.bukkit.jerm.core.util.MessageUtils
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.lang.Exception
import java.util.UUID

@CommandMapping(value = "animation")
class AnimationCommandGroup(private val plugin: JermPlugin) : CommandGroup {
    @CommandBody(value = "list", description = "列出所有动画")
    fun list(
        sender: CommandSender,
        @CommandParam(description = "是否显示详情(Boolean: false)", optional = true) detail: Boolean? = null
    ) {
        val animations = plugin.animationManager.getAnimations()

        MessageUtils.sendMessage(sender, "共有 ${animations.size} 个动画: ")
        animations
            .sortedBy { it.id }
            .forEach {
                val message = if (detail == true) {
                    "- $it"
                } else {
                    "- ${it.id}"
                }

                if (sender is Player) {
                    MessageUtils.sendCommandRawMessage(
                        sender,
                        "$message &c[点击表演给自己]",
                        "/jerm animation playerToPlayerPlay ${it.id} ${sender.name} ${sender.name}"
                    )
                } else {
                    MessageUtils.sendMessage(sender, message)
                }
            }
    }

    @CommandBody(value = "playerToPlayerPlay", description = "一个玩家对一个玩家表演")
    fun playerToPlayerPlay(
        sender: CommandSender,
        @CommandParam(description = "动画") animation: Animation,
        @CommandParam(description = "表演者玩家") performer: Player,
        @CommandParam(description = "观众玩家") viewer: Player,
        @CommandParam(description = "速度(Float: 1)", optional = true) speed: Float?,
        @CommandParam(description = "反转(Boolean: false)", optional = true) reverse: Boolean?,
    ) {
        animation.play(performer, viewer, speed ?: 1F, reverse != false)
    }

    @CommandBody(value = "playerToNearbyPlayersPlay", description = "一个玩家对附近玩家表演")
    fun playerToNearbyPlayersPlay(
        sender: CommandSender,
        @CommandParam(description = "动画 ID") animation: Animation,
        @CommandParam(description = "表演者玩家 ID") performer: Player,
        @CommandParam(description = "匹配附近观众玩家的最大距离(Double: 64)", optional = true) maxDistance: Double? = null,
        @CommandParam(description = "速度(Float: 1)", optional = true) speed: Float?,
        @CommandParam(description = "反转(Boolean: false)", optional = true) reverse: Boolean?,
    ) {
        performer.getNearbyEntities(maxDistance ?: 64.0, 255.0, maxDistance ?: 64.0)
            .filterIsInstance<Player>()
            .let {
                it.forEach { entity ->
                    animation.play(performer, entity, speed ?: 1F, reverse != false)
                }
            }
    }

    @CommandBody(value = "entityByUuidToPlayerPlay", description = "一个实体(UUID)对一个玩家表演")
    fun playerToNearbyPlayers(
        sender: CommandSender,
        @CommandParam(description = "动画") animation: Animation,
        @CommandParam(description = "表演者实体") performerUuidStr: String,
        @CommandParam(description = "观众玩家") viewer: Player,
        @CommandParam(description = "速度(Float: 1)", optional = true) speed: Float?,
        @CommandParam(description = "反转(Boolean: false)", optional = true) reverse: Boolean?,
    ) {
        val performerEntityUuid: UUID

        try {
            performerEntityUuid = UUID.fromString(performerUuidStr)
        } catch (ex: Exception) {
            MessageUtils.sendMessage(sender, "不是有效的 UUID: $performerUuidStr.")
            return
        }

        val performerEntity = Bukkit.getEntity(performerEntityUuid)

        if (performerEntity == null) {
            MessageUtils.sendMessage(sender, "实体不存在: $performerEntityUuid")
            return
        }

        animation.play(performerEntity, viewer, speed ?: 1F, reverse != false)
    }

    @CommandBody(value = "entitiesByDisplayNameToPlayerPlay", description = "多个实体(基于展示名匹配)对一个玩家表演")
    fun entitiesByDisplayNameToPlayerPlay(
        sender: CommandSender,
        @CommandParam(description = "动画") animation: Animation,
        @CommandParam(description = "表演者实体展示名") performerEntityDisplayName: String,
        @CommandParam(description = "观众玩家") viewer: Player,
        @CommandParam(description = "速度(Float: 1)", optional = true) speed: Float?,
        @CommandParam(description = "反转(Boolean: false)", optional = true) reverse: Boolean?,
    ) {
        Bukkit.getWorlds()
            .flatMap {
                it.entities
            }
            .filter {
                it.customName == performerEntityDisplayName
            }
            .forEach {
                animation.play(it, viewer, speed ?: 1F, reverse != false)
            }
    }
}
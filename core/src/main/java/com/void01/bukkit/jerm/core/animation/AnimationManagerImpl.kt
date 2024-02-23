package com.void01.bukkit.jerm.core.animation

import com.germ.germplugin.api.GermPacketAPI
import com.germ.germplugin.api.bean.AnimDataDTO
import com.void01.bukkit.jerm.api.common.animation.AnimationManager
import com.void01.bukkit.jerm.core.JermPlugin
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

class AnimationManagerImpl(val plugin: JermPlugin) : AnimationManager {
    override fun playAnimation(performer: Entity, viewers: List<Player>, animationId: String) {
        playAnimation(performer, viewers, animationId, 1F)
    }

    override fun playAnimation(performer: Entity, viewers: List<Player>, animationId: String, speed: Float) {
        playAnimation(performer, viewers, animationId, speed, false)
    }

    override fun playAnimation(
        performer: Entity,
        viewers: List<Player>,
        animationId: String,
        speed: Float,
        reverse: Boolean
    ) {
        viewers.forEach { viewer ->
            GermPacketAPI.sendBendAction(
                viewer, performer.entityId, AnimDataDTO().setName(animationId).setSpeed(speed).setReverse(reverse)
            )
            GermPacketAPI.sendModelAnimation(
                viewer, performer.entityId, AnimDataDTO().setName(animationId).setSpeed(speed).setReverse(reverse)
            )
        }

        plugin.voidLogger.debug("动画播放指令已发送给客户端, 表演者 = $performer, 观众 = $viewers, 动画 = $animationId, 速度 = $speed.")
    }
}

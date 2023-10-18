package com.void01.bukkit.jerm.core.animation

import com.germ.germplugin.api.GermPacketAPI
import com.germ.germplugin.api.bean.AnimDataDTO
import com.void01.bukkit.jerm.api.common.animation.Animation
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import java.io.File

open class BaseAnimation(override val id: String, open val sourceFile: File?) : Animation {
    override fun play(performer: Entity, viewer: Player, speed: Float, reverse: Boolean) {
        GermPacketAPI.sendBendAction(
            viewer, performer.entityId, AnimDataDTO().setName(id).setSpeed(speed).setReverse(reverse)
        )
        GermPacketAPI.sendModelAnimation(
            viewer, performer.entityId, AnimDataDTO().setName(id).setSpeed(speed).setReverse(reverse)
        )
    }
}
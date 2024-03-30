package com.void01.bukkit.jerm.core.animation

import com.germ.germplugin.api.GermPacketAPI
import com.germ.germplugin.api.bean.AnimDataDTO
import com.github.julyss2019.bukkit.voidframework.yaml.Yaml
import com.google.gson.Gson
import com.void01.bukkit.jerm.api.common.animation.Animation
import com.void01.bukkit.jerm.api.common.animation.AnimationManager
import com.void01.bukkit.jerm.api.common.animation.PreparedAnimation
import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.animation.pojo.AnimationDataPojo
import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import java.io.File

class AnimationManagerImpl(val plugin: JermPlugin) : AnimationManager {
    companion object {
        val GSON = Gson()
    }

    private val logger = plugin.pluginLogger
    private val animationMap = mutableMapOf<String, Animation>()
    private var playNearByAnimationDistance = -1.0
    override val animations: List<Animation>
        get() = animationMap.values.toList()

    fun reload() {
        animationMap.clear()
        load()
    }

    fun load() {
        val yaml = Yaml.fromPluginConfigFile(plugin)
        val animationsFolder = File(plugin.dataFolder, "animations")

        animationsFolder
            .walkTopDown()
            .filter {
                it.isFile && it.name.endsWith(".json")
            }
            .map {
                GSON.fromJson(it.readText(), AnimationDataPojo::class.java)
            }
            .forEach {
                val animationPojoMap = it.animations

                animationPojoMap.forEach { (animationId, animationPojo) ->
                    animationMap[animationId] = Animation(animationId, animationPojo.animationLength)
                }
            }

        this.playNearByAnimationDistance = yaml.getDouble("play-nearby-animation-distance")

        logger.info("载入了 ${animationMap.size} 个动画: ")
        animations.forEach {
            logger.info("- ${it.id}")
        }
    }

    override fun getAnimationLength(id: String): Double {
        if (!animationMap.containsKey(id)) {
            throw IllegalArgumentException("Unable to find animation: $id")
        }

        return animationMap[id]!!.animationLength
    }

    override fun playAnimationToSelfAndNearbyPlayers(performer: Entity, animationId: String) {
        playAnimationToSelfAndNearbyPlayers(performer, animationId, 1F)
    }

    override fun playAnimationToSelfAndNearbyPlayers(performer: Entity, animationId: String, speed: Float) {
        playAnimation(
            performer,
            performer
                .getNearbyEntities(64.0, 64.0, 64.0)
                .filterIsInstance<Player>()
                .toMutableList()
                .apply {
                    if (performer is Player) {
                        add(performer)
                    }
                },
            animationId,
            speed
        )
    }

    override fun playAnimationToSelfAndNearbyPlayers(performer: Entity, preparedAnimation: PreparedAnimation) {
        playAnimation(
            performer,
            Bukkit.getOnlinePlayers()
                .filter { it.location.distance(performer.location) <= playNearByAnimationDistance },
            preparedAnimation.animationId,
            preparedAnimation.speed
        )
    }

    override fun playAnimation(performer: Entity, viewers: List<Player>, animationId: String) {
        playAnimation(performer, viewers, animationId, 1F)
    }

    override fun playAnimation(
        performer: Entity,
        viewers: List<Player>,
        animationId: String,
        speed: Float
    ) {
        viewers.forEach { viewer ->
            GermPacketAPI.sendBendAction(
                viewer, performer.entityId, AnimDataDTO().setName(animationId).setSpeed(speed).setReverse(false)
            )
            GermPacketAPI.sendModelAnimation(
                viewer, performer.entityId, AnimDataDTO().setName(animationId).setSpeed(speed).setReverse(false)
            )
        }
    }

    override fun playAnimation(performer: Entity, viewers: List<Player>, preparedAnimation: PreparedAnimation) {
        playAnimation(performer, viewers, preparedAnimation.animationId, preparedAnimation.speed)
    }
}

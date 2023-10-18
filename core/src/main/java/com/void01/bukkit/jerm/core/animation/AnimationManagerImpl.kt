package com.void01.bukkit.jerm.core.animation

import com.void01.bukkit.jerm.core.util.GermUtils
import com.github.julyss2019.bukkit.voidframework.yaml.Yaml
import com.google.gson.JsonParser
import com.void01.bukkit.jerm.api.common.animation.Animation
import com.void01.bukkit.jerm.api.common.animation.AnimationManager
import com.void01.bukkit.jerm.core.JermPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.io.File

class AnimationManagerImpl(plugin: JermPlugin) : AnimationManager {
    private val logger = plugin.voidLogger
    private var animationImplMap = mutableMapOf<String, Animation>()

    init {
        object : BukkitRunnable() {
            override fun run() {
                load()
            }
        }.runTaskLater(plugin, 40L)
    }

    fun reload() {
        load()
    }

    fun load() {
        val tmpMap = mutableMapOf<String, Animation>()
        val animationFolder = GermUtils.getAnimationFolder()

        fun checkAndPut(animation: Animation) {
            val animationId = animation.id

            if (tmpMap.containsKey(animationId)) {
                logger.warn("存在相同 ID 的动画文件: $animation, ${tmpMap[animation.id]}")
            }

            tmpMap[animationId] = animation
        }

        animationFolder.listFiles()
            ?.filter {
                it.name.endsWith(".yml")
            }
            ?.forEach { sourceFile ->
                val yaml = Yaml.fromFile(sourceFile)

                yaml.subSections.forEach { section ->
                    when (section.getString("type")) {
                        "bedrock_animation" -> {
                            val bedrockPath = section.getString("animationPath")
                            val bedrockAnimationFile = File(animationFolder, bedrockPath)

                            if (bedrockAnimationFile.exists()) {
                                val animationsJsonObject =
                                    JsonParser().parse(bedrockAnimationFile.bufferedReader().use {
                                        it.readText()
                                    }).asJsonObject.getAsJsonObject("animations")

                                animationsJsonObject.entrySet()
                                    .map { animationId ->
                                        checkAndPut(BedrockAnimation(animationId.key, sourceFile, bedrockPath))
                                    }
                            } else {
                                logger.warn("无法找到本地基岩动画文件: ${sourceFile.absolutePath}(${section.name}).")
                            }
                        }

                        "yaml" -> checkAndPut(YamlAnimation(section.name, sourceFile))

                        else -> {
                            throw RuntimeException("未知的动画类型: ${sourceFile.absolutePath}(${section.name})")
                        }
                    }
                }
            }

        animationImplMap = tmpMap
        logger.info("载入了 ${tmpMap.size} 个动画: ")
        getAnimations().forEach {
            logger.info("- ${it.id}")
        }
    }

    override fun getAnimations(): List<Animation> {
        return animationImplMap.values.toList()
    }

    override fun getAnimation(id: String): Animation? {
        return animationImplMap[id]
    }
}

package com.void01.bukkit.jerm.core.command.param

import com.github.julyss2019.bukkit.voidframework.command.param.tab.completer.ParamTabCompleter
import com.void01.bukkit.jerm.api.common.animation.Animation
import com.void01.bukkit.jerm.core.JermPlugin
import org.bukkit.command.CommandSender

class AnimationParamTabCompleter (private val plugin: JermPlugin) : ParamTabCompleter {
    override fun complete(sender: CommandSender, paramType: Class<*>): List<String> {
        return plugin.animationManager.getAnimations().map { it.id }
    }

    override fun getSupportedParamTypes(): Array<Class<*>> {
        return arrayOf(Animation::class.java)
    }
}
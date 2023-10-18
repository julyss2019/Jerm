package com.void01.bukkit.jerm.core.command.param

import com.github.julyss2019.bukkit.voidframework.command.param.parser.ParamParser
import com.github.julyss2019.bukkit.voidframework.command.param.parser.Response
import com.void01.bukkit.jerm.api.common.animation.Animation
import com.void01.bukkit.jerm.core.JermPlugin
import org.bukkit.command.CommandSender

class AnimationParamParser(private val plugin: JermPlugin) : ParamParser {
    override fun parse(sender: CommandSender, paramType: Class<*>, param: String): Response {
        plugin.animationManager.getAnimation(param).let {
            if (it == null) {
                return Response.failure("动画不存在")
            } else {
                return Response.success(it)
            }
        }
    }

    override fun getSupportedParamTypes(): Array<Class<*>> {
        return arrayOf(Animation::class.java)
    }
}
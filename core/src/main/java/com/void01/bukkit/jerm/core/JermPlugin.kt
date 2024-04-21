package com.void01.bukkit.jerm.core

import com.github.julyss2019.bukkit.voidframework.VoidFramework
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandMapping
import com.void01.bukkit.jerm.api.common.Jerm
import com.void01.bukkit.jerm.api.common.Jerm2
import com.void01.bukkit.jerm.api.common.animation.AnimationManager
import com.void01.bukkit.jerm.api.common.gui.GuiManager
import com.void01.bukkit.jerm.api.common.gui.GuiParser
import com.void01.bukkit.jerm.api.common.player.JermPlayerManager
import com.void01.bukkit.jerm.api.internal.Context
import com.void01.bukkit.jerm.core.animation.AnimationManagerImpl
import com.void01.bukkit.jerm.core.command.AnimationCommandGroup
import com.void01.bukkit.jerm.core.command.GuiCommandGroup
import com.void01.bukkit.jerm.core.command.PluginCommandGroup
import com.void01.bukkit.jerm.core.command.param.GuiParamParser
import com.void01.bukkit.jerm.core.command.param.GuiParamTabCompleter
import com.void01.bukkit.jerm.core.command.param.JermPlayerParamParser
import com.void01.bukkit.jerm.core.command.param.JermPlayerTabCompleter
import com.void01.bukkit.jerm.core.gui.GuiManagerImpl
import com.void01.bukkit.jerm.core.gui.GuiParserImpl
import com.void01.bukkit.jerm.core.listener.GuiDebugListener
import com.void01.bukkit.jerm.core.listener.GuiListener
import com.void01.bukkit.jerm.core.listener.PlayerListener
import com.void01.bukkit.jerm.core.player.JermPlayerManagerImpl
import com.void01.bukkit.jerm.core.util.GermUtils
import com.void01.bukkit.voidframework.api.common.extension.VoidPlugin
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

@CommandMapping(value = "jerm", permission = "jerm.admin")
class JermPlugin : VoidPlugin(), Context {
    companion object {
        @JvmStatic
        lateinit var instance: JermPlugin
            private set
    }

    override lateinit var jermPlayerManager: JermPlayerManager
        private set
    override lateinit var animationManager: AnimationManager
        private set
    override lateinit var guiParser: GuiParser
        private set
    override lateinit var guiManager: GuiManager
        private set

    override fun onPluginEnable() {
        instance = this
        Jerm.setContext(this)
        Jerm2.setContext(this)
        saveDefaults()
        jermPlayerManager = JermPlayerManagerImpl(this)
        animationManager = AnimationManagerImpl(this)
        guiParser = GuiParserImpl(this)
        guiManager = GuiManagerImpl(this)

        VoidFramework.getCommandManager().createCommandFramework(this).run {
            addParamParser(GuiParamParser(this@JermPlugin))
            addParamParser(JermPlayerParamParser(this@JermPlugin))

            addParamTabCompleter(JermPlayerTabCompleter())
            addParamTabCompleter(GuiParamTabCompleter(this@JermPlugin))

            registerCommandGroup(PluginCommandGroup(this@JermPlugin))
            registerCommandGroup(GuiCommandGroup(this@JermPlugin))
            registerCommandGroup(AnimationCommandGroup())
        }

        Bukkit.getPluginManager().registerEvents(GuiDebugListener(this), this)
        Bukkit.getPluginManager().registerEvents(GuiListener(this), this)
        Bukkit.getPluginManager().registerEvents(PlayerListener(this), this)

        // 等待萌芽载入
        object : BukkitRunnable() {
            override fun run() {
                (guiManager as GuiManagerImpl).load()
                (animationManager as AnimationManagerImpl).load()
            }
        }.runTaskLater(this, 40L)

        pluginLogger.info("插件已加载.")
    }

    override fun onPluginDisable() {
        VoidFramework.getCommandManager().unregisterCommandFrameworks(this)
        pluginLogger.info("插件已卸载.")
    }

    fun reload() {
        saveDefaults()
        GermUtils.reload()
        (guiManager as GuiManagerImpl).reload()
        (animationManager as AnimationManagerImpl).reload()
    }
}

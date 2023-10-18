package com.void01.bukkit.jerm.core

import com.github.julyss2019.bukkit.voidframework.VoidFramework
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandMapping
import com.github.julyss2019.bukkit.voidframework.logging.logger.Logger
import com.void01.bukkit.jerm.api.common.Jerm
import com.void01.bukkit.jerm.api.common.animation.AnimationManager
import com.void01.bukkit.jerm.api.common.gui.GuiManager
import com.void01.bukkit.jerm.api.common.gui.GuiParser
import com.void01.bukkit.jerm.api.common.player.JermPlayerManager
import com.void01.bukkit.jerm.api.internal.Context
import com.void01.bukkit.jerm.core.animation.AnimationManagerImpl
import com.void01.bukkit.jerm.core.command.AnimationCommandGroup
import com.void01.bukkit.jerm.core.command.GuiCommandGroup
import com.void01.bukkit.jerm.core.command.PluginCommandGroup
import com.void01.bukkit.jerm.core.command.param.*
import com.void01.bukkit.jerm.core.gui.GuiManagerImpl
import com.void01.bukkit.jerm.core.gui.GuiParserImpl
import com.void01.bukkit.jerm.core.listener.GuiDebugListener
import com.void01.bukkit.jerm.core.listener.GuiListener
import com.void01.bukkit.jerm.core.player.JermPlayerManagerImpl
import com.void01.bukkit.jerm.core.util.GermUtils
import com.void01.bukkit.voidframework.api.common.VoidFramework2
import com.void01.bukkit.voidframework.api.common.library.Library
import com.void01.bukkit.voidframework.api.common.library.Repository
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

@CommandMapping(value = "jerm", permission = "jerm.admin")
class JermPlugin : JavaPlugin(), Context {
    init {
        VoidFramework2.getLibraryManager().load(Library.Builder.create()
            .setClassLoaderByBukkitPlugin(this)
            .setDependencyByGradleStyleExpression("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
            .addRepositories(Repository.ALIYUN, Repository.CENTRAL)
            .addSafeRelocation("_kotlin_", "_com.void01.bukkit.jerm.core.libs.kotlin_")
            .build())
    }

    companion object {
        @JvmStatic
        lateinit var instance: JermPlugin
            private set
    }

    lateinit var voidLogger: Logger
        private set
    override lateinit var playerManager: JermPlayerManager
        private set
    override lateinit var animationManager: AnimationManager
        private set
    override lateinit var guiParser: GuiParser
        private set
    override lateinit var guiManager: GuiManager
        private set

    override fun onEnable() {
        instance = this
        voidLogger = VoidFramework.getLogManager().createSimpleLogger(this)
        playerManager = JermPlayerManagerImpl(this)
        animationManager = AnimationManagerImpl(this)
        guiParser = GuiParserImpl(this)
        guiManager = GuiManagerImpl(this)

        Jerm.setContext(this)

        VoidFramework.getCommandManager().createCommandFramework(this).run {
            addParamParser(AnimationParamParser(this@JermPlugin))
            addParamParser(GuiParamParser(this@JermPlugin))
            addParamParser(JermPlayerParamParser(this@JermPlugin))

            addParamTabCompleter(AnimationParamTabCompleter(this@JermPlugin))
            addParamTabCompleter(JermPlayerTabCompleter())
            addParamTabCompleter(GuiParamTabCompleter(this@JermPlugin))

            registerCommandGroup(PluginCommandGroup(this@JermPlugin))
            registerCommandGroup(GuiCommandGroup(this@JermPlugin))
            registerCommandGroup(AnimationCommandGroup(this@JermPlugin))
        }

        Bukkit.getPluginManager().registerEvents(GuiDebugListener(this), this)
        Bukkit.getPluginManager().registerEvents(GuiListener(this), this)

        voidLogger.info("插件已加载.")
    }

    override fun onDisable() {
        VoidFramework.getCommandManager().unregisterCommandFrameworks(this)
        voidLogger.info("插件已卸载.")
    }

    fun reload() {
        GermUtils.reload()
        guiManager.reload()
    }
}

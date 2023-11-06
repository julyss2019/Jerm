package com.void01.bukkit.jerm.core.gui

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.player.JermPlayerImpl
import org.bukkit.entity.Player
import java.io.File

class GuiImpl(override val handle: GermGuiScreen, val sourceFile: File? = null, val plugin: JermPlugin) : Gui {
    private val componentGroup = ComponentGroupImpl(this, handle)
    override var components: List<Component<*>>
        get() = componentGroup.components
        set(value) {
            componentGroup.components = value
        }

    override fun clearComponents() {
        componentGroup.clearComponents()
    }

    override val id: String = handle.guiName

    /** 打开 GUI
     * @param bukkitPlayer 玩家
     * @param cover 覆盖
     */
    override fun openAsGui(bukkitPlayer: Player, cover: Boolean) {
        if (cover) {
            handle.openGui(bukkitPlayer)
        } else {
            handle.openChildGui(bukkitPlayer)
        }

        (plugin.playerManager.getPlayer(bukkitPlayer) as JermPlayerImpl).addJermUsingGui(this)
    }

    override fun openAsHud(bukkitPlayer: Player) {
        handle.openHud(bukkitPlayer)

        (plugin.playerManager.getPlayer(bukkitPlayer) as JermPlayerImpl).addJermUsingGui(this)
    }

    override fun close() {
        handle.close()
    }

    override fun toString(): String {
        return "Gui(id='$id')"
    }

    override fun <T : GermGuiPart<T>> getComponentHandle(id: String, clazz: Class<T>): T? {
        return componentGroup.getComponentHandle(id, clazz)
    }

    override fun <T : Component<*>> getComponent(id: String, clazz: Class<T>): T? {
        return componentGroup.getComponent(id, clazz)
    }

    override fun removeComponent(id: String) {
        componentGroup.removeComponent(id)
    }

    override fun addComponent(component: Component<*>) {
        componentGroup.addComponent(component)
    }

    override fun addComponent(componentHandle: GermGuiPart<*>) {
        componentGroup.addComponent(componentHandle)
    }

    override fun existsComponent(id: String): Boolean {
        return componentGroup.existsComponent(id)
    }

    override fun clone(): Gui {
        return GuiImpl(handle.clone(), sourceFile, plugin)
    }
}

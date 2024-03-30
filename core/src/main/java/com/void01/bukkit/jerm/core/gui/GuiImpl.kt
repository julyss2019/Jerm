package com.void01.bukkit.jerm.core.gui

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.RootComponent
import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.gui.component.RootComponentImpl
import com.void01.bukkit.jerm.core.player.JermPlayerImpl
import org.bukkit.entity.Player
import java.io.File
import java.util.UUID

class GuiImpl(override val handle: GermGuiScreen, val sourceFile: File? = null, val plugin: JermPlugin) : Gui,
    ComponentGroup {
    override val id: String = handle.guiName
    override val instanceId: String = "jerm-gui-$id-instance-${UUID.randomUUID()}"
    override var onCloseListener: Gui.OnCloseListener? = null
    override var onOpenListener: Gui.OnOpenListener? = null
    override var onGuiClickListener: Gui.OnClickListener? = null
    override val rootComponent: RootComponent = RootComponentImpl(this)

/*    init {
        handle.guiName = instanceId
    }*/

    /** 打开 GUI
     * @param bukkitPlayer 玩家
     * @param cover 覆盖
     */
    override fun openAsGui(bukkitPlayer: Player, cover: Boolean) {
        (plugin.jermPlayerManager.getPlayer(bukkitPlayer) as JermPlayerImpl).addUsingGui(this)

        if (cover) {
            handle.openGui(bukkitPlayer)
        } else {
            handle.openChildGui(bukkitPlayer)
        }
    }

    override fun openAsHud(bukkitPlayer: Player) {
        (plugin.jermPlayerManager.getPlayer(bukkitPlayer) as JermPlayerImpl).addUsingGui(this)
        handle.openHud(bukkitPlayer)
    }

    override fun close() {
        handle.close()
    }

    override fun toString(): String {
        return "Gui(id='$id')"
    }

    // delegate
    override val components: List<Component<*>>
        get() {
            return rootComponent.components
        }

    override fun clearComponents() {
        rootComponent.clearComponents()
    }

    override fun getComponentsRecursively(): List<Component<*>> {
        return rootComponent.getComponentsRecursively()
    }

    override fun <T : GermGuiPart<T>> getComponentHandle(id: String, clazz: Class<T>): T? {
        return rootComponent.getComponentHandle(id, clazz)
    }

    override fun <T : Component<*>> getComponent(id: String, clazz: Class<T>): T? {
        return rootComponent.getComponent(id, clazz)
    }

    override fun removeComponent(id: String) {
        rootComponent.removeComponent(id)
    }

    override fun addComponent(component: Component<*>) {
        rootComponent.addComponent(component)
    }

    override fun addComponent(componentHandle: GermGuiPart<*>) {
        rootComponent.addComponent(componentHandle)
    }

    override fun existsComponent(id: String): Boolean {
        return rootComponent.existsComponent(id)
    }
    // delegate

    override fun clone(): Gui {
        return GuiImpl(handle.clone(), sourceFile, plugin)
    }
}

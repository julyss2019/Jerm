package com.void01.bukkit.jerm.core.gui

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.RootComponent
import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.gui.component.RootComponentImpl
import com.void01.bukkit.jerm.core.player.JermPlayerImpl
import org.bukkit.entity.Player
import java.io.File

class GuiImpl(override val handle: GermGuiScreen, val sourceFile: File? = null, val plugin: JermPlugin) : Gui {
    override val id: String = handle.guiName
    override var onCloseListener: Gui.OnCloseListener? = null
    override var onOpenListener: Gui.OnOpenListener? = null
    override var onGuiClickListener: Gui.OnClickListener? = null
    override val rootComponent: RootComponent = RootComponentImpl(this)
    override var isEnabled: Boolean
        get() = !handle.isInvalid
        set(value) {
            handle.isInvalid = !value
        }

    /** 打开 GUI
     * @param bukkitPlayer 玩家
     * @param cover 覆盖
     */
    override fun openAsGui(bukkitPlayer: Player, cover: Boolean) {
        (plugin.jermPlayerManager.getJermPlayer(bukkitPlayer) as JermPlayerImpl).addUsingGui(this)

        if (cover) {
            handle.openGui(bukkitPlayer)
        } else {
            handle.openChildGui(bukkitPlayer)
        }
    }

    override fun openAsHud(bukkitPlayer: Player) {
        (plugin.jermPlayerManager.getJermPlayer(bukkitPlayer) as JermPlayerImpl).addUsingGui(this)
        handle.openHud(bukkitPlayer)
    }

    override fun close() {
        handle.close()
    }

    // delegate
    override val components: List<Component<*>> get() = rootComponent.components

    override fun clearComponents() = rootComponent.clearComponents()

    override fun getComponentsRecursively(): List<Component<*>> = rootComponent.getComponentsRecursively()

    override fun <T : GermGuiPart<T>> getComponentHandle2OrNull(id: String, type: Class<T>): T? =
        rootComponent.getComponentHandle2OrNull(id, type)

    override fun <T : GermGuiPart<T>> getComponentHandle2(id: String, type: Class<T>): T =
        rootComponent.getComponentHandle2(id, type)

    override fun <T : Component<*>> getComponent2(id: String, type: Class<T>): T = rootComponent.getComponent2(id, type)

    override fun <T : Component<*>> getComponent2OrNull(id: String, type: Class<T>): T? =
        rootComponent.getComponent2OrNull(id, type)

    override fun removeComponent(component: Component<*>) = rootComponent.removeComponent(component)

    override fun removeComponent(id: String) = rootComponent.removeComponent(id)

    override fun addComponent(component: Component<*>) = rootComponent.addComponent(component)

    override fun addComponent(componentHandle: GermGuiPart<*>) = rootComponent.addComponent(componentHandle)

    override fun existsComponent(id: String): Boolean = rootComponent.existsComponent(id)

    override fun <T : Component<*>> getComponentByPath2(path: String, type: Class<T>): T =
        rootComponent.getComponentByPath2(path, type)

    override fun <T : Component<*>> getComponentByPath2OrNull(path: String, type: Class<T>): T? =
        rootComponent.getComponentByPath2OrNull(path, type)

    override fun getHierarchyString(): String {
        return rootComponent.getHierarchyString()
    }
    // delegate

    override fun clone(): Gui {
        return GuiImpl(handle.clone(), sourceFile, plugin)
    }
}

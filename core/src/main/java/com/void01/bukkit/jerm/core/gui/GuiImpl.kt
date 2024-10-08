package com.void01.bukkit.jerm.core.gui

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.RootComponent
import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.gui.component.RootComponentImpl
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.io.File

class GuiImpl(
    override val handle: GermGuiScreen,
    val sourceFile: File? = null,
    val plugin: JermPlugin,
    /**
     * 表示这个 GUI 不是通过 Jerm 打开的.
     *
     */
    val isExternal: Boolean = false
) : Gui {
    companion object {
        /**
         * 检查玩家
         *
         * 由于萌芽打开 GUI 时不会对 Player 参数进行验证（离线、在线但非最新的玩家实例），这会造成 GUI 无法正常打开，且没任何提示
         * 本方法对 Player 进行检查，在必要时抛出异常
         */
        private fun checkBukkitPlayer(bukkitPlayer: Player) {
            val actualBukkitPlayer = Bukkit.getPlayer(bukkitPlayer.uniqueId)

            require(actualBukkitPlayer != null) { "Player is offline" }
            require(bukkitPlayer == actualBukkitPlayer) { "Player is invalid because it is not a latest Player instance" }
        }
    }

    private val jermPlayerManager = plugin.jermPlayerManager
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
    var isOpening = false

    /** 打开 GUI
     * @param bukkitPlayer 玩家
     * @param isCover 覆盖
     */
    override fun openAsGui(bukkitPlayer: Player, isCover: Boolean) {
        checkBukkitPlayer(bukkitPlayer)
        jermPlayerManager.getJermPlayer(bukkitPlayer).addUsingGui(this)

        if (isCover) {
            handle.openGui(bukkitPlayer)
        } else {
            handle.openChildGui(bukkitPlayer)
        }

        this.isOpening = true
    }

    override fun openAsHud(bukkitPlayer: Player) {
        checkBukkitPlayer(bukkitPlayer)
        jermPlayerManager.getJermPlayer(bukkitPlayer).addUsingGui(this)
        handle.openHud(bukkitPlayer)
        this.isOpening = true
    }

    override fun close() {
        handle.close()
        this.isOpening = false
    }

    // delegate
    override val components: List<Component<*>> get() = rootComponent.components

    override fun clearComponents() = rootComponent.clearComponents()

    override fun getComponentsRecursively(): List<Component<*>> = rootComponent.getComponentsRecursively()

    @Suppress("DEPRECATION")
    @Deprecated("使用 getComponent", ReplaceWith(""))
    override fun <T : GermGuiPart<T>> getComponentHandle2OrNull(id: String, type: Class<T>): T? =
        rootComponent.getComponentHandle2OrNull(id, type)

    @Suppress("DEPRECATION")
    @Deprecated("使用 getComponent", ReplaceWith(""))
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

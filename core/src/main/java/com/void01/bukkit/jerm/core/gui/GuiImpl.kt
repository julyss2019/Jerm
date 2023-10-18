package com.void01.bukkit.jerm.core.gui

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.germ.germplugin.api.dynamic.gui.GermGuiScreen
import com.germ.germplugin.api.dynamic.gui.IGuiPartContainer
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.core.JermPlugin
import com.void01.bukkit.jerm.core.gui.component.GermComponentToJermComponentConverter
import com.void01.bukkit.jerm.core.player.JermPlayerImpl
import org.bukkit.entity.Player
import java.io.File

class GuiImpl(override val handle: GermGuiScreen, val sourceFile: File? = null, val plugin: JermPlugin) : Gui {
    private val originalHandle = handle.clone()
    private val componentMap = mutableMapOf<String, Component<*>>()
    private val ambiguousComponentIds = mutableSetOf<String>()
    override val id: String = handle.guiName

    init {
        putIGuiPartContainerComponents(handle)
    }

    private fun putIGuiPartContainerComponents(iGuiPartContainer: IGuiPartContainer) {
        iGuiPartContainer.guiParts.forEach {
            if (it is IGuiPartContainer) {
                putIGuiPartContainerComponents(it)
            } else {
                val component = GermComponentToJermComponentConverter.convert(this, it)

                putComponent(component)
            }
        }
    }

    private fun putComponent(component: Component<*>) {
        val componentId = component.getId()

        if (componentMap.containsKey(componentId)) {
            ambiguousComponentIds.add(componentId)
        }

        componentMap[componentId] = component
    }

    fun getComponentByHandle(handle: GermGuiPart<*>): Component<*>? {
        require(!ambiguousComponentIds.contains(handle.indexName)) {
            "Ambiguous component id: ${handle.indexName}"
        }

        return getComponents().firstOrNull { it.getId() == handle.indexName }
    }

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

    override fun getComponents(): List<Component<*>> {
        return componentMap.values.toList()
    }

    override fun <T : GermGuiPart<T>> getComponentHandle(id: String, clazz: Class<T>): T? {
        return getComponents().firstOrNull { it.getId() == id } as T?
    }

    override fun <T : Component<*>> getComponent(id: String, clazz: Class<T>): T? {
        return componentMap[id] as T?
    }

    override fun close() {
        handle.close()
    }

    override fun toString(): String {
        return "Gui(id='$id')"
    }

    override fun clone(): Gui {
        return GuiImpl(originalHandle.clone(), sourceFile, plugin)
    }
}

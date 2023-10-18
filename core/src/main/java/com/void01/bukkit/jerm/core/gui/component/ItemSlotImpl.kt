package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiSlot
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.ItemSlot
import org.bukkit.inventory.ItemStack

class ItemSlotImpl(gui: Gui, handle: GermGuiSlot) : BaseComponent<GermGuiSlot>(gui, handle), ItemSlot {
    override fun getItem(): ItemStack? {
        return handle.itemStack
    }

    override fun setItem(itemStack: ItemStack) {
        handle.itemStack = itemStack
    }

    override fun setInteractive(boolean: Boolean) {
        handle.setInteract(boolean)
    }

    override fun isInteractive(): Boolean {
        return handle.isInteract
    }

    override fun getSlotId(id: String): String? {
        return handle.identity
    }

    override fun setSlotId(id: String?) {
        // Fix: 萌芽槽位 ID 不允许为 null，默认 ID 为索引名
        handle.identity = id ?: handle.indexName
    }
}
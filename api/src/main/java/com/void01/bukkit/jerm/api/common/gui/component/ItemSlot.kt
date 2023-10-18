package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiSlot
import org.bukkit.inventory.ItemStack

interface ItemSlot : Component<GermGuiSlot> {
    fun getItem() : ItemStack?

    fun setItem(itemStack: ItemStack)

    fun setSlotId(id : String?)

    fun getSlotId(id : String) : String?

    fun disableInteractive() {
        setInteractive(false)
    }

    fun enableInteractive() {
        setInteractive(true)
    }

    fun setInteractive(boolean: Boolean)

    fun isInteractive() : Boolean
}
package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiSlot
import org.bukkit.inventory.ItemStack

interface ItemSlot : Component<GermGuiSlot> {
    @Deprecated("改为 itemStack")
    var item: ItemStack?
    var itemStack : ItemStack?
    var binding : String?
    var interactive : Boolean

    @Deprecated("改为 slotItemBinding(String)")
    fun setSlotId(id: String?)

    @Deprecated("改为 getItemBinding()")
    fun getSlotId(): String?

    fun disableInteractive() {
        interactive = false
    }

    fun enableInteractive() {
        interactive = true
    }

    public override fun clone(): ItemSlot
}
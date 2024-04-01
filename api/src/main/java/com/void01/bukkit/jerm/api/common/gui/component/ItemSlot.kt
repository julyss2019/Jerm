package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiSlot
import com.germ.germplugin.api.event.gui.GermGuiSlotClickEvent
import org.bukkit.inventory.ItemStack


interface ItemSlot : Component<GermGuiSlot> {
    var itemStack: ItemStack?
    var binding: String?

    /**
     * 是否可点击
     * 若为 true，将触发 OnClickListener；若为 false，不会触发 OnClickListener
     */
    var isClickable: Boolean

    public override fun clone(): ItemSlot
}
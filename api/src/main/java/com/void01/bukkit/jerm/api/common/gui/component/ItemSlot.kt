package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiSlot
import org.bukkit.inventory.ItemStack


interface ItemSlot : Component<GermGuiSlot> {
    var itemStack: ItemStack?
    var binding: String?

    /**
     * 是否可以交互
     * 当 interactive 为 true 时，物品可以被拿走，槽位点击事件可以被触发
     * 当 interactive 为 false 时，物品不可以被拿走，槽位点击事件不可以被触发
     */
    var interactive: Boolean

    /**
     * 是否可以拿走物品
     * 仅当 interactive 为 true 时有效
     */
    var canTakeAway: Boolean

    @Deprecated("改为 itemStack")
    var item: ItemStack?

    fun disableInteractive() {
        interactive = false
    }

    fun enableInteractive() {
        interactive = true
    }

    public override fun clone(): ItemSlot

    @Deprecated("改为 binding")
    fun setSlotId(id: String?)

    @Deprecated("改为 binding")
    fun getSlotId(): String?
}
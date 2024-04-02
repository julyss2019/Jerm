package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiSlot
import org.bukkit.inventory.ItemStack

interface ItemSlot : Component<GermGuiSlot> {
    var itemStack: ItemStack?
    @Deprecated("命名不规范")
    var item: ItemStack?

    var binding: String?

    /**
     * 是否可交互
     * 仅当 isInteractive 为 true 时物品才能被拿走，GermGuiSlotClickEvent 才会触发
     */
    var isInteractive: Boolean

    /**
     * 是否可以点击（拿走物品）
     * 仅当 isInteractive 为 true 时有效
     * 不管值为 true 或 false，onClickListener 永远会触发
     */
    var isClickable: Boolean

    public override fun clone(): ItemSlot

    @Deprecated("弃用", ReplaceWith("isInteractive = false"))
    fun disableInteractive() {
        isInteractive = false
    }

    @Deprecated("弃用", ReplaceWith("isInteractive = true"))
    fun enableInteractive() {
        isInteractive = true
    }

    @Deprecated("命名不规范", replaceWith = ReplaceWith("binding = id"))
    fun setSlotId(id: String?)

    @Deprecated("命名不规范", replaceWith = ReplaceWith("binding"))
    fun getSlotId(): String?
}
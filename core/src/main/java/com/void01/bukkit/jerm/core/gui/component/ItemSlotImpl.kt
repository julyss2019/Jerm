package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiSlot
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.ItemSlot
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup
import com.void01.bukkit.jerm.core.util.GermUtils
import org.bukkit.inventory.ItemStack

// isInvalid = true, isInteract = true，无悬浮，无法拿走，GermGuiSlotClickEvent 不触发
// isInvalid = true, isInteract = false，无悬浮，无法拿走，GermGuiSlotClickEvent 不触发
// isInvalid = false, isInteract = true，有悬浮，可以拿走，GermGuiSlotClickEvent 触发
// isInvalid = false, isInteract = false，有悬浮，不能拿走，GermGuiSlotClickEvent 不触发
/*
需求：不允许拿走，但需要监听事件。需要设置 isInvalid = false, isInteract = true
 */
class ItemSlotImpl(gui: Gui, parent: JermComponentGroup<*>?, handle: GermGuiSlot) :
    BaseComponent<GermGuiSlot>(gui, parent, handle), ItemSlot {
    override var itemStack: ItemStack?
        get() = handle.itemStack
        set(value) {
            handle.itemStack = value
        }
    override var interactive: Boolean
        get() = handle.isInteract
        set(value) {
            handle.isInteract = value
        }
    override var canTakeAway: Boolean = true
    override var binding: String?
        get() {
            return handle.identity
        }
        set(value) {
            handle.identity = value ?: handle.indexName
        }

    override fun clone(): ItemSlot {
        return ItemSlotImpl(gui, parent, GermUtils.cloneGuiPart(handle).apply {
            identity = binding
        })
    }

    @Deprecated("改为 itemStack")
    override var item: ItemStack?
        get() = this.itemStack
        set(value) {
            this.itemStack = value
        }

    @Deprecated("弃用", ReplaceWith("binding"))
    override fun setSlotId(id: String?) {
        binding = id
    }

    @Deprecated("弃用", ReplaceWith("binding"))
    override fun getSlotId(): String? {
        return binding
    }
}
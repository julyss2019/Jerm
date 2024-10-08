package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiSlot
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.ItemSlot
import com.void01.bukkit.jerm.api.common.gui.component.JermComponentGroup
import com.void01.bukkit.jerm.core.gui.GuiImpl
import com.void01.bukkit.jerm.core.util.GermUtils
import org.bukkit.inventory.ItemStack
import java.util.*

// isInvalid = true, isInteract = true，无悬浮，无法拿走，GermGuiSlotClickEvent 不触发
// isInvalid = true, isInteract = false，无悬浮，无法拿走，GermGuiSlotClickEvent 不触发
// isInvalid = false, isInteract = true，有悬浮，可以拿走，GermGuiSlotClickEvent 触发
// isInvalid = false, isInteract = false，有悬浮，不能拿走，GermGuiSlotClickEvent 不触发
// 一个很常见的需求：不可拿走，但要点击触发事件。
// 解决方法：先设置 isInteract 为 true，在事件触发时判断 isClickable，若为 false 在事件触发时取消。
class ItemSlotImpl(gui: Gui, parent: JermComponentGroup<*>?, handle: GermGuiSlot) :
    BaseComponent<GermGuiSlot>(gui, parent, handle), ItemSlot {
    @Deprecated("命名不规范")
    override var item: ItemStack?
        get() = itemStack
        set(value) {
            itemStack = value
        }
    override var itemStack: ItemStack?
        get() = handle.itemStack
        set(value) {
            handle.itemStack = value
        }
    override var isInteractive: Boolean
        get() = handle.isInteract
        set(value) {
            handle.isInteract = value
        }
    override var isViewOnly: Boolean = false
    override var binding: String?
        get() {
            return handle.identity
        }
        set(value) {
            handle.identity = value ?: handle.indexName
        }

    init {
        gui as GuiImpl
        if (!gui.isExternal) {
            // Germ fix: 修复一个 canvas 内多个 slot 使用同一个 binding 导致所有物品都一样的 bug
            binding = "jerm-patch-${UUID.randomUUID()}"
        }
    }

    override fun clone(): ItemSlot {
        return ItemSlotImpl(gui, parent, GermUtils.cloneGuiPart(handle))
    }

    @Deprecated("命名不规范", replaceWith = ReplaceWith("binding = iD"))
    override fun setSlotId(id: String?) {
        binding = id
    }

    @Deprecated("命名不规范", replaceWith = ReplaceWith("binding"))
    override fun getSlotId(): String? {
        return binding
    }
}
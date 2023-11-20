package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiSlot
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.ItemSlot
import com.void01.bukkit.jerm.core.util.GermUtils
import org.bukkit.inventory.ItemStack
import java.util.UUID

class ItemSlotImpl(gui: Gui, group: ComponentGroup, handle: GermGuiSlot) :
    BaseComponent<GermGuiSlot>(gui, group, handle), ItemSlot {
    override val origin: ItemSlot by lazy { clone() }
    override var item: ItemStack?
        get() = handle.itemStack
        set(value) {
            binding = null
            handle.itemStack = value
        }
    override var itemStack: ItemStack?
        get() = handle.itemStack
        set(value) {
            binding = null // 设置了 ItemStack 就让 binding 失效
            handle.itemStack = value
        }
    override var interactive: Boolean
        get() = handle.isInteract
        set(value) {
            handle.isInteract = value
        }
    override var binding: String?
        get() {
            itemStack = null  // 设置了 binding 就让 ItemStack 失效
            return if (handle.identity == handle.indexName) null else handle.indexName // 不应该默认 binding 为索引名
        }
        set(value) {
            handle.identity = value ?: UUID.randomUUID().toString() // 萌芽 bug，不允许为空
        }

    @Deprecated("弃用", ReplaceWith("binding"))
    override fun setSlotId(id: String?) {
        binding = id
    }

    @Deprecated("弃用", ReplaceWith("binding"))
    override fun getSlotId(): String? {
        return binding
    }

    override fun clone(): ItemSlot {
        return ItemSlotImpl(gui, group, GermUtils.cloneGuiPart(handle).apply {
            identity = binding
        })
    }
}
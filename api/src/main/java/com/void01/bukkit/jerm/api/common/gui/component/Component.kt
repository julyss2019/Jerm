package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.gui.Gui


@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
interface Component<T : GermGuiPart<*>> : Cloneable {
    val parent: JermComponentGroup<*>?
    val gui: Gui
    val handle: T
    var id: String
    var tooltips: List<String>
    var isEnabled: Boolean
    var onClickListener: OnClickListener?

    enum class ClickType {
        LEFT,
        RIGHT,
        MIDDLE,
    }

    /**
     * 点击监听器
     * 注意：ItemSlot 只有 interactive 为 true 时才会触发
     */
    interface OnClickListener {
        fun onClick(clickType: ClickType) {}

        fun onClick(clickType: ClickType, shift: Boolean) {}

        /** 点击事件（v2版本）
         * @param isShift 是否 SHIFT
         * @return 是否取消
         */
        fun onClick2(clickType: ClickType, isShift: Boolean): Boolean {
            return false
        }
    }

    fun performClick(clickType: ClickType)

    fun disable() {
        isEnabled = false
    }

    fun enable() {
        isEnabled = true
    }

    fun <T : Component<*>> getPseudoComponentOrThrow(id: String, clazz: Class<T>): Component<*> {
        return getPseudoComponent(id, clazz) as T? ?: throw RuntimeException("Unable to find component: $id")
    }

    fun <T : Component<*>> getPseudoComponent(id: String, clazz: Class<T>): Component<*>?

    override fun clone(): Component<T>
}
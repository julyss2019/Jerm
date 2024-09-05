package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiInput

interface Input : Component<GermGuiInput> {
    enum class Type {
        NUMBER, TEXT
    }

    var type: Type
    var isSync: Boolean
        set(value) {
            handle.isSync = value
        }
        get() = handle.isSync
    var maxLength: Int
        set(value) {
            handle.limit = value
        }
        get() = handle.limit
    var value: String?
        set(value) {
            handle.input = value
        }
        get() = handle.input
}
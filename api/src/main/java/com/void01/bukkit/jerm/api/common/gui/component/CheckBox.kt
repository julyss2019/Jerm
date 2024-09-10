package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiCheckbox

interface CheckBox : Component<GermGuiCheckbox> {
    interface OnCheckedChangeListener {
        fun onCheckedChanged(isChecked: Boolean)
    }

    var onCheckedChangeListener: OnCheckedChangeListener?
    var isChecked
        get() = handle.isChecked
        set(value) {
            handle.isChecked = value
        }
}
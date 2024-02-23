package com.void01.bukkit.jerm.core.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiCanvas
import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component
import com.void01.bukkit.jerm.api.common.gui.component.RootComponent
import java.util.*

class RootComponentImpl(
    gui: Gui
) : RootComponent,
    BaseJermComponentGroup<GermGuiPart<*>>(
        gui,
        null,
        GermGuiCanvas("Jerm Fake Root Component ${UUID.randomUUID()}"),
        gui.handle
    ) {
    override fun clone(): Component<GermGuiPart<*>> {
        throw UnsupportedOperationException("Use Gui.clone()")
    }
}
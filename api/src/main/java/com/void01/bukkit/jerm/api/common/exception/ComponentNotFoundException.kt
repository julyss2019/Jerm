package com.void01.bukkit.jerm.api.common.exception

import com.void01.bukkit.jerm.api.common.gui.Gui
import com.void01.bukkit.jerm.api.common.gui.component.Component

class ComponentNotFoundException(gui: Gui, component: Component<*>, nextPath: String) : RuntimeException(
    "Component not found in path '${component.path}.${nextPath}' of GUI '${gui.id}'"
)
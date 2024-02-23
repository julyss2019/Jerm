@file:Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")

package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup

interface JermComponentGroup<out T : GermGuiPart<*>> : Component<T>, ComponentGroup
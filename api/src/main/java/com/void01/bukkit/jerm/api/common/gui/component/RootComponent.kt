package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart

/**
 * 萌芽 GUI 并不是一个组件，这导致了许多操作非常麻烦，故引入了 RootComponent 来衔接 GUI 的容器和 GUI 的子容器
 */
interface RootComponent : JermComponentGroup<GermGuiPart<*>>
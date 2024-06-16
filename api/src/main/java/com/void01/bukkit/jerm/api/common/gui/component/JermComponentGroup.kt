@file:Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")

package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.gui.ComponentGroup

/**
 * Jerm 控件组
 * 原生萌芽 GUI 是不属于组件的，这造成了一些开发不便。本接口将 GUI 和其他控件组统一定义为 JermComponentGroup。
 * 在实现中，GUI 会虚拟化出一个假的控件。
 */
interface JermComponentGroup<T : GermGuiPart<*>> : Component<T>, ComponentGroup
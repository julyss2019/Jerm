package com.void01.bukkit.jerm.api.common.gui.component

import com.germ.germplugin.api.dynamic.gui.GermGuiPart

@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
/**
 * 伪部件
 * 概念参考 HTML 伪元素，目前用于 ScrollBox 的 ScrollBar 部分
 */
interface PseudoComponent<T : GermGuiPart<*>> : Component<T>
package com.void01.bukkit.jerm.api.common.gui

import com.germ.germplugin.api.dynamic.gui.GermGuiPart
import com.void01.bukkit.jerm.api.common.gui.component.Component

interface ComponentGroup : Cloneable {
    val components: List<Component<*>>

    fun clearComponents()

    fun getComponentsRecursively(): List<Component<*>>

    /**
     * 获取萌芽控件，如果无法找到则抛出异常
     */
    @Deprecated(message = "命名优化", replaceWith = ReplaceWith("getComponentHandle2(id, clazz)"))
    fun <T : GermGuiPart<T>> getComponentHandleOrThrow(id: String, type: Class<T>): T = getComponentHandle2(id, type)

    /**
     * 获取萌芽控件
     */
    @Deprecated(message = "命名优化", replaceWith = ReplaceWith("getComponentHandle2OrNull(id, clazz)"))
    fun <T : GermGuiPart<T>> getComponentHandle(id: String, type: Class<T>): T? = getComponentHandle2OrNull(id, type)

    /**
     * 获取萌芽控件
     */
    fun <T : GermGuiPart<T>> getComponentHandle2OrNull(id: String, type: Class<T>): T?

    /**
     * 获取萌芽控件，如果无法找到则抛出异常
     */
    fun <T : GermGuiPart<T>> getComponentHandle2(id: String, type: Class<T>): T

    /**
     * 获取控件，如果无法找到则抛出异常
     */
    @Deprecated(message = "命名优化", replaceWith = ReplaceWith("getComponent2(id, clazz)"))
    fun <T : Component<*>> getComponentOrThrow(id: String, type: Class<T>): T = getComponent2(id, type)

    /**
     * 获取控件
     */
    @Deprecated(message = "命名优化", replaceWith = ReplaceWith("getComponent2OrNull(id, clazz)"))
    fun <T : Component<*>> getComponent(id: String, type: Class<T>): T? = getComponent2OrNull(id, type)

    /**
     * 获取控件，如果无法找到则抛出异常
     */
    fun <T : Component<*>> getComponent2(id: String, type: Class<T>): T

    /**
     * 获取控件
     */
    fun <T : Component<*>> getComponent2OrNull(id: String, type: Class<T>): T?

    fun removeComponent(component: Component<*>)

    fun removeComponent(id: String)

    fun addComponent(component: Component<*>)

    fun addComponent(componentHandle: GermGuiPart<*>)

    fun existsComponent(id: String): Boolean

    /**
     * 使用路径获取控件
     * @param path 路径表达式，使用点分法来表示，e.g. 'a.b.c'
     */
    fun <T : Component<*>> getComponentByPath2(path: String, type: Class<T>): T

    /**
     * 使用路径获取控件
     * @param path 路径表达式，使用点分法来表示，e.g. 'a.b.c'
     */
    fun <T : Component<*>> getComponentByPath2OrNull(path: String, type: Class<T>): T?

    /**
     * 使用路径获取控件
     * @param path 路径表达式，使用点分法来表示，e.g. 'a.b.c'
     */
    @Deprecated(message = "命名优化", replaceWith = ReplaceWith("getComponentByPath2(path, type)"))
    fun <T : Component<*>> getComponentByPathOrThrow(path: String, type: Class<T>): T = getComponentByPath2(path, type)

    /**
     * 使用路径获取控件
     * @param path 路径表达式，使用点分法来表示，e.g. 'a.b.c'
     */
    @Deprecated(message = "命名优化", replaceWith = ReplaceWith("getComponentByPath2OrNull(path, type)"))
    fun <T : Component<*>> getComponentByPath(path: String, type: Class<T>): T? = getComponentByPath2OrNull(path, type)
}
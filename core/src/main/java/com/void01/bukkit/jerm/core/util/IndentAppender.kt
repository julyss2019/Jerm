package com.void01.bukkit.jerm.core.util

object IndentAppender {
    fun addIndentToEachLine(space: Int, string: String): String {
        return string
            .split("\n")
            .joinToString(separator = "\n") {
                " ".repeat(space) + it
            }
    }
}
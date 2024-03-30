package com.void01.bukkit.jerm.api.common.animation

object PreparedAnimationParser {
    fun parse(expression: String): PreparedAnimation {
        val array = expression.split(",")

        require(array.size !in 1..3) {
            "Invalid expression"
        }

        val animationId = array[0]
        val strSpeed = array.getOrNull(2)
        val strReverse = array.getOrNull(1)

        val speed = if (strSpeed != null) {
            strSpeed.toFloatOrNull() ?: throw IllegalArgumentException("Invalid speed")
        } else {
            1F
        }
        val reverse = if (strReverse != null) {
            strReverse.toBooleanStrictOrNull() ?: throw IllegalArgumentException("Invalid reverse")
        } else {
            false
        }

        return PreparedAnimation(animationId, speed, reverse)
    }
}
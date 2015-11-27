package com.mapalarm.entities

class Alarm(val position: Position, val radius: Double) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Alarm

        if (position != other.position) return false
        if (radius != other.radius) return false

        return true
    }

    override fun hashCode(): Int {
        var result = position.hashCode()
        result += 31 * result + radius.hashCode()
        return result
    }
}
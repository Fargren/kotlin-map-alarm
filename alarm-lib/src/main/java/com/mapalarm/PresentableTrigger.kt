package com.mapalarm

import com.mapalarm.datatypes.Position

class PresentableTrigger(val position: Position) {
    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as PresentableTrigger

        if (position != other.position) return false

        return true
    }

    override fun hashCode(): Int{
        return position.hashCode()
    }
}
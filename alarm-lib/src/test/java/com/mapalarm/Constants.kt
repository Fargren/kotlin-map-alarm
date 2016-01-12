package com.mapalarm

import com.mapalarm.datatypes.Position

class Constants {
    companion object {
        val defaultLat = 0.0
        val defaultLng = 0.0
        val defaultPosition = Position(defaultLat, defaultLng)
        val defaultRadius = 100.0

        val positionOutOfDefaultRadius = Position(0.0, 0.001)
        val positionInsideOfDefaultRadius = Position(0.00063, 0.00063)
    }
}

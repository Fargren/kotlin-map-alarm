package com.mapalarm.entities

import com.mapalarm.datatypes.Position

data class PositionTrigger(val position: Position, val radius: Double) : Trigger {

}
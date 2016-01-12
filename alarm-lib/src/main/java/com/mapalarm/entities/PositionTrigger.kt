package com.mapalarm.entities

import com.mapalarm.datatypes.Position
import com.mapalarm.datatypes.UserSituation

data class PositionTrigger(override val position: Position, override val radius: Double) : Trigger {
    override fun matches(situation: UserSituation): Boolean {
        return position.distanceInMeters(situation.position) < radius
    }
}



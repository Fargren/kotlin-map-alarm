package com.mapalarm.entities

import com.mapalarm.datatypes.Position
import com.mapalarm.datatypes.UserSituation

interface Trigger {
    val radius: Double
    val position: Position

    open fun matches(situation: UserSituation): Boolean
}
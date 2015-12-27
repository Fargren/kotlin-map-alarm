package com.mapalarm.mocks

import com.mapalarm.Constants
import com.mapalarm.TriggersGateway
import com.mapalarm.entities.PositionTrigger
import java.util.*

class DefaultTriggersGateway() : TriggersGateway {
    override fun getTriggers(): Set<PositionTrigger> {
        val trigger = PositionTrigger(Constants.defaultPosition, Constants.defaultRadius)
        return Collections.singleton(trigger)
    }
}
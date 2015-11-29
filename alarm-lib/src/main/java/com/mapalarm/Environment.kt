package com.mapalarm

import com.mapalarm.datatypes.Position
import com.mapalarm.entities.Alarm
import com.mapalarm.entities.PositionTrigger

class Environment {
    companion  object {
        var triggersGateway: TriggersGateway? = null
        var locationGateway: LocationGateway? = null
        var addAlarmGateway: AddAlarmGateway? = null
    }
}

interface TriggersGateway {
    open fun getGateways(): Set<PositionTrigger>
}

interface LocationGateway {
    open fun getCurrentPosition(callback: (Position) -> Unit, onError: () -> Unit)
}

interface AddAlarmGateway {
    fun addAlarm(alarm: Alarm)
}

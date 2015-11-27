package com.mapalarm

import com.mapalarm.entities.Alarm
import com.mapalarm.entities.Position

class Environment {
    companion  object {
        var locationGateway: LocationGateway? = null
        var addAlarmGateway: AddAlarmGateway? = null
    }
}

interface LocationGateway {
    open fun getCurrentPosition(callback: (Position) -> Unit, onError: () -> Unit)
}

interface AddAlarmGateway {
    fun addAlarm(alarm: Alarm)
}

package com.mapalarm

import com.mapalarm.datatypes.Position
import com.mapalarm.entities.PositionTrigger

class Environment {
    companion  object {
        var triggersGateway: TriggersGateway? = null
        var locationGateway: LocationGateway? = null
        var addTriggerGateway: AddTriggerGateway? = null
    }
}

interface TriggersGateway {
    open fun getGateways(): Set<PositionTrigger>
}

interface LocationGateway {
    open fun getCurrentPosition(callback: (Position) -> Unit, onError: () -> Unit)
}

interface AddTriggerGateway {
    open fun addTrigger(trigger: PositionTrigger)
}

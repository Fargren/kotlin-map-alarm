package com.mapalarm

import com.mapalarm.datatypes.UserSituation
import com.mapalarm.entities.PositionTrigger
import com.mapalarm.entities.Trigger

class Environment {
    companion object {
        lateinit var triggersGateway: TriggersGateway
        lateinit var situationGateway: UserSituationGateway
        lateinit var addTriggerGateway: AddTriggerGateway
    }
}

interface TriggersGateway {
    open fun getTriggers(): Set<Trigger>
}

interface UserSituationGateway {
    open fun getSituation(callback: (UserSituation) -> Unit, onError: () -> Unit)
}

interface AddTriggerGateway {
    open fun addTrigger(trigger: PositionTrigger)
}

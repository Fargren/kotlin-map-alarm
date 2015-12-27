package com.mapalarm.usecases

import com.mapalarm.Environment
import com.mapalarm.PresentableTrigger
import com.mapalarm.datatypes.Position
import java.util.*

class ListTriggersUseCase(val presenter: ListTriggersPresenter) {

    fun listAll() {
        val triggers = Environment.triggersGateway.getTriggers().map {
            PresentableTrigger(Position(it.position.latitude , it.position.longitude), it.radius)
        }
        presenter.showTriggers(HashSet(triggers))
    }
}

interface ListTriggersPresenter {
    open fun showTriggers(triggers: Set<PresentableTrigger>)
}

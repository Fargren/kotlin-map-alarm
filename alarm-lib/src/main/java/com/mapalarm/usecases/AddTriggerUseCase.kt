package com.mapalarm.usecases

import com.mapalarm.Environment
import com.mapalarm.PresentableTrigger
import com.mapalarm.datatypes.Position
import com.mapalarm.entities.PositionTrigger

class AddTriggerUseCase(val presenter: AddAlarmPresenter) : AddTrigger {
    private val DEFAULT_RADIUS = 200.0

    override fun addTriggerAt(position: Position) {
        Environment.addTriggerGateway.addTrigger(PositionTrigger(position, DEFAULT_RADIUS))
        presenter.presentTrigger(PresentableTrigger(position, DEFAULT_RADIUS))
    }
}

interface AddTrigger {
    open fun addTriggerAt(position: Position)
}


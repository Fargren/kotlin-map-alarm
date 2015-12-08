package com.mapalarm.usecases

import com.mapalarm.Environment
import com.mapalarm.PresentableTrigger
import com.mapalarm.datatypes.Position
import com.mapalarm.entities.PositionTrigger

class AddTriggerUseCase(val presenter: AddAlarmPresenter) : AddTrigger {
    override fun addTriggerAt(position: Position) {
        val DEFAULT_RADIUS = 200.0

        Environment.addTriggerGateway!!.addTrigger(PositionTrigger(position, DEFAULT_RADIUS))
        presenter.presentTrigger(PresentableTrigger(position, DEFAULT_RADIUS))
    }
}

interface AddTrigger {

    open fun addTriggerAt(position: Position)
}


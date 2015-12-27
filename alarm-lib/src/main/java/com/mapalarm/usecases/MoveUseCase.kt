package com.mapalarm.usecases

import com.mapalarm.Environment
import com.mapalarm.datatypes.UserSituation

class MoveUseCase(private val presenter: MovePresenter, private val alarm: AlarmPresenter) {

    fun refreshPosition() {
        Environment.situationGateway.getSituation(onSituated, onPositioningError)
    }

    private val onSituated: (UserSituation) -> Unit = {
        situation ->
        presenter.showUserAt(situation.position)
        ringAlarm(situation)
    }

    private fun ringAlarm(situation: UserSituation) {
        val triggered = Environment.triggersGateway.getTriggers().filter { (it.matches(situation)) }
        if (!triggered.isEmpty()) alarm.ring()
    }

    private val onPositioningError = { presenter.showPositionUnknown() }
}


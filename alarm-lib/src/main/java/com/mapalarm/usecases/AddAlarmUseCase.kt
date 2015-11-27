package com.mapalarm.usecases

import com.mapalarm.usecases.AddAlarmPresenter
import com.mapalarm.entities.Alarm
import com.mapalarm.Environment
import com.mapalarm.entities.Position

class AddAlarmUseCase(val presenter: AddAlarmPresenter) {
    fun addAlarmAt(position: Position) {
        Environment.addAlarmGateway!!.addAlarm(Alarm(position, 50.0))
        presenter.presentAlarm(Alarm(position, 50.0))
    }
}


package com.mapalarm.usecases

import com.mapalarm.entities.Alarm

interface AddAlarmPresenter {
    open fun presentAlarm(alarm: Alarm)
}
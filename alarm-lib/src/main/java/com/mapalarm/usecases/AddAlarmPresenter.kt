package com.mapalarm.usecases

import com.mapalarm.PresentableTrigger

interface AddAlarmPresenter {
    open fun presentTrigger(trigger: PresentableTrigger)
}
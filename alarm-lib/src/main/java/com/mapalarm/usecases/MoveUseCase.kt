package com.mapalarm.usecases

import com.mapalarm.Environment
import com.mapalarm.PresentablePosition
import com.mapalarm.entities.Position

class MoveUseCase(val presenter: MovePresenter) {

    fun refreshPosition() {
        Environment.locationGateway!!.getCurrentPosition(onPositionAcquired, onPositioningError)
    }

    val onPositionAcquired: (Position) -> Unit = {
        pos ->
        presenter.showUserAt(PresentablePosition(pos.latitude, pos.longitude))
    }

    val onPositioningError = { -> presenter.showPositionUnknown() }
}


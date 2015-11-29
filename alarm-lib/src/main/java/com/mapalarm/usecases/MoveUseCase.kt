package com.mapalarm.usecases

import com.mapalarm.Environment
import com.mapalarm.datatypes.Position

class MoveUseCase(val presenter: MovePresenter) {

    fun refreshPosition() {
        Environment.locationGateway!!.getCurrentPosition(onPositionAcquired, onPositioningError)
    }

    val onPositionAcquired: (Position) -> Unit = {
        pos ->
        presenter.showUserAt(Position(pos.latitude, pos.longitude))
    }

    val onPositioningError = { -> presenter.showPositionUnknown() }
}


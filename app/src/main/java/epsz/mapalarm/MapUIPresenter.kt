package epsz.mapalarm

import com.mapalarm.PresentablePosition
import com.mapalarm.usecases.MovePresenter

class MapUIPresenter(val ui: MapUI) : MovePresenter {
    override fun showPositionUnknown() {
        ui.showUpdatingPositionToast()
    }

    override fun showUserAt(position: PresentablePosition) {
        ui.moveMapTo(position.latitude, position.longitude)
    }
}
package epsz.mapalarm

import com.mapalarm.PresentableTrigger
import com.mapalarm.datatypes.Position
import com.mapalarm.usecases.AddAlarmPresenter
import com.mapalarm.usecases.ListTriggersPresenter
import com.mapalarm.usecases.MovePresenter

class MapUIPresenter(val ui: MapUI) : MovePresenter, ListTriggersPresenter, AddAlarmPresenter {
    override fun presentTrigger(trigger: PresentableTrigger) {
        ui.showCircleAt( trigger.position, trigger.radius)
    }

    override fun showUserAt(position: Position) {
        ui.moveMapTo(position.latitude, position.longitude)
    }

    override fun showPositionUnknown() {
        ui.showUpdatingPositionToast()
    }

    override fun showTriggers(triggers: Set<PresentableTrigger>) {
        triggers.forEach { ui.showCircleAt( it.position, it.radius) }
    }

}
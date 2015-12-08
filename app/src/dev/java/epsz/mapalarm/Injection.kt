package epsz.mapalarm

import android.content.Context
import com.mapalarm.AddTriggerGateway
import com.mapalarm.Environment
import com.mapalarm.TriggersGateway
import com.mapalarm.datatypes.Position
import com.mapalarm.entities.PositionTrigger
import epsz.mapalarm.gateways.GoogleApiLocationGateway

class Injection(val context: Context) {

    init {
        Environment.triggersGateway = PalermoTriggersGateway()
        Environment.locationGateway = GoogleApiLocationGateway(context)
        Environment.addTriggerGateway = EmptyAddTriggerGateway()
    }
}

class EmptyAddTriggerGateway : AddTriggerGateway {
    override fun addTrigger(trigger: PositionTrigger) {

    }
}

class PalermoTriggersGateway : TriggersGateway {
    override fun getGateways(): Set<PositionTrigger> {
        var triggers = hashSetOf(PositionTrigger(Position(-34.579397619164375, -58.42875838279724), 200.0))
        return triggers
    }

}

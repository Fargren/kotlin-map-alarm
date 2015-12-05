package epsz.mapalarm

import com.mapalarm.Environment
import com.mapalarm.TriggersGateway
import com.mapalarm.datatypes.Position
import com.mapalarm.entities.PositionTrigger

class Injection {
    init {
        Environment.triggersGateway = PalermoTriggersGateway()
    }
}

class PalermoTriggersGateway : TriggersGateway {
    override fun getGateways(): Set<PositionTrigger> {
        var triggers = hashSetOf(PositionTrigger(Position(-34.579397619164375, -58.42875838279724), 200.0))
        return triggers
    }

}

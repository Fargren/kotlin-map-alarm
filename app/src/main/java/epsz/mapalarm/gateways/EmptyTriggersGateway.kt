package epsz.mapalarm.gateways

import com.mapalarm.TriggersGateway
import com.mapalarm.entities.PositionTrigger
import java.util.*

class EmptyTriggersGateway : TriggersGateway {
    override fun getGateways(): Set<PositionTrigger> {
        return HashSet()
    }

}
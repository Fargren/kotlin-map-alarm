package epsz.mapalarm

import android.content.Context
import com.mapalarm.AddTriggerGateway
import com.mapalarm.Environment
import com.mapalarm.TriggersGateway
import com.mapalarm.entities.PositionTrigger
import com.mapalarm.entities.Trigger
import epsz.mapalarm.gateways.GoogleApiConnector
import epsz.mapalarm.gateways.GoogleApiUserSituationGateway
import java.util.*

class Injection(val context: Context) {

    private val googleApiConnector: GoogleApiConnector = GoogleApiConnector(context)

    init {
        val inMemoryTriggersGateway = InMemoryTriggersGateway()
        //inMemoryTriggersGateway.addTrigger(PositionTrigger(Position(-34.579397619164375, -58.42875838279724), 300.0))
        with(Environment) {
            triggersGateway = inMemoryTriggersGateway
            addTriggerGateway = inMemoryTriggersGateway
            situationGateway = GoogleApiUserSituationGateway(googleApiConnector)
        }
    }
}

class InMemoryTriggersGateway() : TriggersGateway, AddTriggerGateway {
    val triggers = HashSet<Trigger>()

    override fun getTriggers(): Set<Trigger> {
        val triggerSet = HashSet<Trigger>()
        triggerSet.addAll(triggers)
        return triggerSet
    }

    override fun addTrigger(trigger: PositionTrigger) {
        triggers.add(trigger)
    }

}

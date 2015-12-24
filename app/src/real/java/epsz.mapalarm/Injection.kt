package epsz.mapalarm

import android.content.Context
import com.mapalarm.Environment
import epsz.mapalarm.gateways.EmptyTriggersGateway
import epsz.mapalarm.gateways.GeofenceAddTriggerGateway
import epsz.mapalarm.gateways.GoogleApiConnector
import epsz.mapalarm.gateways.GoogleApiLocationGateway

class Injection(val context: Context) {

    private val googleApiConnector: GoogleApiConnector = GoogleApiConnector(context)

    init {
        Environment.locationGateway = GoogleApiLocationGateway(googleApiConnector)
        Environment.addTriggerGateway = GeofenceAddTriggerGateway(context, googleApiConnector)
        Environment.triggersGateway = EmptyTriggersGateway()
    }

}


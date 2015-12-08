package epsz.mapalarm

import com.mapalarm.Environment
import com.mapalarm.TriggersGateway
import com.mapalarm.datatypes.Position
import com.mapalarm.entities.PositionTrigger

class Injection(val context: Context) {

    init {
        Environment.locationGateway = GoogleApiLocationGateway(context)
    }
}
package epsz.mapalarm

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.mapalarm.AddTriggerGateway
import com.mapalarm.Environment
import com.mapalarm.TriggersGateway
import com.mapalarm.datatypes.Position
import com.mapalarm.entities.PositionTrigger
import epsz.mapalarm.gateways.GoogleApiConnector
import epsz.mapalarm.gateways.GoogleApiLocationGateway
import java.util.*

class Injection(val context: Context) {

    private val googleApiConnector: GoogleApiConnector = GoogleApiConnector(context)

    init {
        Environment.locationGateway = GoogleApiLocationGateway(googleApiConnector)
        Environment.addTriggerGateway = GeofenceAddTriggerGateway(context, googleApiConnector)
        Environment.triggersGateway = EmptyTriggersGateway()
    }

}

class EmptyTriggersGateway : TriggersGateway {
    override fun getGateways(): Set<PositionTrigger> {
        return HashSet()
    }

}

class GeofenceAddTriggerGateway(val context: Context, val googleApiConnector: GoogleApiConnector)
: AddTriggerGateway, ResultCallback<Status> {
    override fun onResult(p0: Status?) {
        Log.d("GEOFENCE", "created")
    }

    override fun addTrigger(trigger: PositionTrigger) {
        googleApiConnector.doWithGoogleClient { createGeofence(trigger) }
    }

    private fun createGeofence(trigger: PositionTrigger) {
        LocationServices.GeofencingApi.addGeofences(
                googleApiConnector.googleApiClient,
                getNewGeofenceRequest(trigger.position, trigger.radius.toFloat()),
                getGeofencePendingIntent()
        ).setResultCallback(this)
    }

    fun getNewGeofenceRequest(position: Position, radius: Float): GeofencingRequest {
        val builder = GeofencingRequest.Builder()

        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)

        builder.addGeofence(com.google.android.gms.location.Geofence.Builder()
                .setRequestId("")
                .setExpirationDuration(0)
                .setCircularRegion(position.latitude,
                        position.longitude,
                        radius)
                .setTransitionTypes(com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER +
                        com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT)
                .build())

        return builder.build()
    }

    fun getGeofencePendingIntent(): PendingIntent {
        val intent = Intent(context, GeofenceTransitionsIntentService::class.java)
        return PendingIntent.getService(context, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT)
    }

}

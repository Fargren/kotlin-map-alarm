package epsz.mapalarm.gateways

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.mapalarm.AddTriggerGateway
import com.mapalarm.datatypes.Position
import com.mapalarm.entities.PositionTrigger
import epsz.mapalarm.GeofenceTransitionsIntentService

class GeofenceAddTriggerGateway(val context: Context, val googleApiConnector: GoogleApiConnector)
: AddTriggerGateway, ResultCallback<Status> {
    override fun onResult(status: Status?) {
        if (status!!.isSuccess)
            Log.d("GEOFENCE", "created")
        else
            Log.d("GEOFENCE", "failed")
    }

    override fun addTrigger(trigger: PositionTrigger) {
        fun createGeofence(trigger: PositionTrigger) {
            LocationServices.GeofencingApi.addGeofences(
                    googleApiConnector.googleApiClient,
                    getNewGeofenceRequest(trigger.position, trigger.radius.toFloat()),
                    getGeofencePendingIntent()
            ).setResultCallback(this)
        }
        googleApiConnector.doWithGoogleClient { createGeofence(trigger) }
    }


    private fun getNewGeofenceRequest(position: Position, radius: Float): GeofencingRequest {
        return GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(buildGeofence(position, radius))
                .build()
    }

    private fun buildGeofence(position: Position, radius: Float): Geofence? {
        return Geofence.Builder()
                .setRequestId("")
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setCircularRegion(position.latitude, position.longitude, radius)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER + Geofence.GEOFENCE_TRANSITION_EXIT)
                .build()
    }

    private fun getGeofencePendingIntent(): PendingIntent {
        val intent = Intent(context, GeofenceTransitionsIntentService::class.java)
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

}
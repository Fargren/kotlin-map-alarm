package epsz.mapalarm

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT
import com.google.android.gms.location.GeofencingEvent

class GeofenceTransitionsIntentService() : IntentService("GeofenceTransitionsIntentService") {
    private val TAG: String = "GeofenceTransitionsIntentService"

    override fun onHandleIntent(intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            val errorMessage = "ERROR"
            Log.e(TAG, errorMessage)
            return;
        }

        // Get the transition type.
        val geofenceTransition = geofencingEvent.getGeofenceTransition()

        // Test that the reported transition was of interest.
        if (geofenceTransition == GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            val triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            /*
            val geofenceTransitionDetails = getGeofenceTransitionDetails(

                    this,
            geofenceTransition,
            triggeringGeofences
            );

            // Send notification and log the transition details.
            sendNotification(geofenceTransitionDetails);
            Log.i(TAG, geofenceTransitionDetails);
            */
        }

    }
}
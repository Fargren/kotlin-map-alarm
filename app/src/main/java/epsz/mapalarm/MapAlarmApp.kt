package epsz.mapalarm

import android.app.Application
import android.content.Intent

class MapAlarmApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Injection(this)

        val intent = Intent(this, GeofenceTransitionsIntentService::class.java)
    }
}


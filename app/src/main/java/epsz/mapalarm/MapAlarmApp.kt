package epsz.mapalarm

import android.app.Application

class MapAlarmApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Injection(this)
    }
}


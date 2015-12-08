package epsz.mapalarm

import android.app.Application

class MapAlarmApp : Application() {
    init {
        Injection(this)
    }
}


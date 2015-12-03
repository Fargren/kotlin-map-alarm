package epsz.mapalarm

import android.app.Application
import epsz.mapalarm.Injection

class MapAlarmApp : Application() {
    init {
        Injection()
    }
}


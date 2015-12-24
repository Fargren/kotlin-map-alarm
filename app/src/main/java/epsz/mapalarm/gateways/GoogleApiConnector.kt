package epsz.mapalarm.gateways

import android.content.Context
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import java.util.*

class GoogleApiConnector(val context: Context)
: GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private var callbacks: MutableCollection<() -> Unit> = HashSet()
    private var errorCallbacks: MutableCollection<() -> Unit> = HashSet()
    var googleApiClient: GoogleApiClient? = null
        private set

    init {
        connectAPI()
    }

    fun doWithGoogleClient(callback: () -> Unit, onError: () -> Unit) {
        if (!googleApiClient!!.isConnected)
            errorCallbacks.add(onError)
        doWithGoogleClient (callback)
    }

    fun doWithGoogleClient(callback: () -> Unit) {
        if (googleApiClient!!.isConnected)
            callback()
        else
            callbacks.add(callback)

        connectAPI()
    }

    private fun connectAPI() {
        googleApiClient?.let {
            if (it.isConnected || it.isConnecting) return
        }

        googleApiClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        googleApiClient?.connect()
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnected(p0: Bundle?) {
        callbacks.forEach { it() }
        callbacks.clear()
    }

    override fun onConnectionFailed(p0: ConnectionResult?) {
        errorCallbacks.forEach { it() }
        errorCallbacks.clear()
    }
}


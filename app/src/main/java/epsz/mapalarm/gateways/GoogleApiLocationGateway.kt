package epsz.mapalarm.gateways

import android.content.Context
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.mapalarm.LocationGateway
import com.mapalarm.datatypes.Position

class GoogleApiLocationGateway(val context: Context) : LocationGateway, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private var callback: (Position) -> Unit = { }
    private var onError: () -> Unit = {}
    private var googleApiClient: GoogleApiClient? = null


    override fun getCurrentPosition(callback: (Position) -> Unit, onError: () -> Unit) {
        this.callback = callback
        this.onError = onError

        googleApiClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        googleApiClient?.connect()
    }

    override fun onConnectionSuspended(p0: Int) {
        onError()
    }

    override fun onConnected(p0: Bundle?) {
        var mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (mLastLocation != null)
            callback(Position(mLastLocation.latitude, mLastLocation.longitude));
    }

    override fun onConnectionFailed(p0: ConnectionResult?) {
        onError()
    }
}
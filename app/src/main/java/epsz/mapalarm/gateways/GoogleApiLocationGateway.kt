package epsz.mapalarm.gateways

import com.google.android.gms.location.LocationServices
import com.mapalarm.LocationGateway
import com.mapalarm.datatypes.Position

class GoogleApiLocationGateway(val googleApiConnector: GoogleApiConnector) : LocationGateway {
    private var callback: (Position) -> Unit = { }
    private var onError: () -> Unit = {}


    override fun getCurrentPosition(callback: (Position) -> Unit, onError: () -> Unit) {
        this.callback = callback
        this.onError = onError

        googleApiConnector.doWithGoogleClient( { returnLastLocation() }, onError)

    }

    private fun returnLastLocation() {
        var lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiConnector.googleApiClient);
        if (lastLocation != null)
            callback(Position(lastLocation.latitude, lastLocation.longitude));
    }

}
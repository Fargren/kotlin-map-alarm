package epsz.mapalarm.gateways

import com.google.android.gms.location.LocationServices
import com.mapalarm.UserSituationGateway
import com.mapalarm.datatypes.Position
import com.mapalarm.datatypes.UserSituation

class GoogleApiUserSituationGateway(val apiConnector: GoogleApiConnector) : UserSituationGateway {
    override fun getSituation(callback: (UserSituation) -> Unit, onError: () -> Unit) {
        apiConnector.doWithGoogleClient( { returnLastLocation(callback, onError) }, onError)
    }

    private fun returnLastLocation(callback: (UserSituation) -> Unit, onError: () -> Unit) {
        var location = LocationServices.FusedLocationApi.getLastLocation(apiConnector.googleApiClient)
        if (location != null)
            callback(UserSituation(Position(location.latitude, location.longitude)))
        else
            onError()
    }

}
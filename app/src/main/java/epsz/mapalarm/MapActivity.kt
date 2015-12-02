package epsz.mapalarm

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.mapalarm.Environment
import com.mapalarm.LocationGateway
import com.mapalarm.datatypes.Position
import com.mapalarm.usecases.ListTriggersUseCase
import com.mapalarm.usecases.MoveUseCase

class MapActivity : FragmentActivity(), OnMapReadyCallback, MapUI {

    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Environment.locationGateway = GoogleApiLocationGateway(this)

        setContentView(R.layout.activity_map)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.isMyLocationEnabled = true
        map = googleMap

        MoveUseCase(MapUIPresenter(this)).refreshPosition()

        ListTriggersUseCase(MapUIPresenter(this)).listAll()
    }

    override fun moveMapTo(latitude: Double, longitude: Double) {
        val here = LatLng(latitude, longitude)
        map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(here, 14f))
        map!!.animateCamera(CameraUpdateFactory.newLatLng(LatLng(latitude, longitude)))
    }

    override fun showUpdatingPositionToast() {
        throw UnsupportedOperationException()
    }

    override fun showCircleAt(pos: Position) {
        throw UnsupportedOperationException()
    }
}

class GoogleApiLocationGateway (val context:Context) : LocationGateway,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
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

interface MapUI {
    fun moveMapTo(latitude: Double, longitude: Double)

    open fun showUpdatingPositionToast()
    open fun showCircleAt(pos: Position)
}

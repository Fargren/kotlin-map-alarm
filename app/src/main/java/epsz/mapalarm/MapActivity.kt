package epsz.mapalarm

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.mapalarm.datatypes.Position
import com.mapalarm.usecases.*
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : FragmentActivity(), OnMapReadyCallback, MapUI {
    private val presenter = MapUIPresenter(this)
    lateinit private var controller:MapActivityController

    lateinit private var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller = MapActivityController(presenter, ToastAlarm(this), AddTriggerUseCase(presenter))

        setContentView(R.layout.activity_map)
        loadMap()

        this.add_alarm_button.setOnClickListener({
            enterEditMode()
        })
    }

    private fun loadMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        createMap(googleMap)

        controller.refreshPosition()
        controller.listAllTriggers()
    }

    private fun createMap(googleMap: GoogleMap) {
        googleMap.isMyLocationEnabled = true
        map = googleMap
        map.setOnMapClickListener {
            controller.addTrigger(it)
            exitEditMode()
        }
    }

    private fun enterEditMode() {
        controller.editMode = true
        this.toolbar_title.setText(R.string.instructions_add_mode)
    }

    private fun exitEditMode() {
        controller.editMode = false
        this.toolbar_title.setText(R.string.app_name)
    }

    override fun moveMapTo(latitude: Double, longitude: Double) {
        val here = LatLng(latitude, longitude)
        with(map) {
            moveCamera(CameraUpdateFactory.newLatLngZoom(here, 14f))
            animateCamera(CameraUpdateFactory.newLatLng(LatLng(latitude, longitude)))
        }
    }

    override fun showUpdatingPositionToast() {
        throw UnsupportedOperationException()
    }

    override fun showCircleAt(pos: Position, radius: Double) {
        map.addCircle(CircleOptions()
                .center(LatLng(pos.latitude, pos.longitude))
                .radius(radius)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));
    }
}

class ToastAlarm(val context: Context) : AlarmPresenter {
    override fun ring() {
        Toast.makeText(context, "Alarm!", Toast.LENGTH_LONG).show()
    }

}

class MapActivityController(val presenter: MapUIPresenter,
                            val alarm: AlarmPresenter,
                            val addTriggerUseCase: AddTrigger) : MapUIController {
    var editMode: Boolean = false
    private val moveUseCase = MoveUseCase(presenter, alarm)
    private val listTriggersUseCase = ListTriggersUseCase(presenter)

    override fun refreshPosition() {
        moveUseCase.refreshPosition()
    }

    override fun listAllTriggers() {
        listTriggersUseCase.listAll()
    }

    override fun addTrigger(position: LatLng) {
        if (editMode)
            addTriggerUseCase.addTriggerAt(Position(position.latitude, position.longitude))
    }

}

interface MapUIController {
    open fun addTrigger(position: LatLng)
    open fun refreshPosition()
    open fun listAllTriggers()
}

interface MapUI {
    open fun moveMapTo(latitude: Double, longitude: Double)
    open fun showUpdatingPositionToast()
    open fun showCircleAt(pos: Position, radius: Double)
}

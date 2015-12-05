package epsz.mapalarm

import com.mapalarm.PresentableTrigger
import com.mapalarm.datatypes.Position
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MapUIPresenterTest {
    val ui = MapUISpy()
    val sut = MapUIPresenter(ui)

    @Test
    fun showPositionUnknown_always_showsUpdatingToast() {
        sut.showPositionUnknown()

        assertTrue(ui.showedUpdatingPositionToast)
    }

    @Test
    fun showUserAt_aPosition_movesTheUIThePosition() {
        sut.showUserAt(Position(5.0, 48.0))

        assertEquals(5.0, ui.latitude)
        assertEquals(48.0, ui.longitude)
    }

    @Test
    fun showTriggers_withSomeTriggers_addsCircleToTheUI() {
        sut.showTriggers(hashSetOf(PresentableTrigger(Position(5.0, 48.0), 100.0), PresentableTrigger(Position(0.5, 4.0), 200.0)))

        assertEquals(ui.circles, hashSetOf(Pair(Position(5.0, 48.0), 100.0), Pair(Position(0.5, 4.0), 200.0)))
    }
}

class MapUISpy : MapUI {
    var circles: HashSet<Pair<Position, Double>> = HashSet()
    var showedUpdatingPositionToast: Boolean = false
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    override fun showUpdatingPositionToast() {
        showedUpdatingPositionToast = true
    }

    override fun moveMapTo(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
    }

    override fun showCircleAt(pos:Position, radius: Double) {
        this.circles.add(Pair(pos, radius))
    }

}

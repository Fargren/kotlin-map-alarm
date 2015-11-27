package epsz.mapalarm

import com.mapalarm.PresentablePosition
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MapUIPresenterTest {

    @Test
    fun testShowPositionUnknown() {
        val ui = MapUISpy()
        val sut = MapUIPresenter(ui)

        sut.showPositionUnknown()

        assertTrue(ui.showedUpdatingPositionToast)
    }

    @Test
    fun testShowUserAt() {
        val ui = MapUISpy()
        val sut = MapUIPresenter(ui)

        sut.showUserAt(PresentablePosition(5.0, 48.0))

        assertEquals(5.0, ui.latitude)
        assertEquals(48.0, ui.longitude)
    }
}

class MapUISpy : MapUI {
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

}

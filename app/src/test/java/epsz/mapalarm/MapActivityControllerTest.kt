package epsz.mapalarm

import com.google.android.gms.maps.model.LatLng
import com.mapalarm.datatypes.Position
import com.mapalarm.usecases.AddTrigger
import org.junit.Test
import org.mockito.Mockito.mock
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MapActivityControllerTest {

    @Test
    fun addTrigger_onEditMode_activatesUseCase() {
        val addTriggerUseCase = AddTriggerUseCaseSpy()
        var sut = MapActivityController(MapUIPresenter(mock(MapUI::class.java)), addTriggerUseCase)
        sut.editMode = true

        sut.addTrigger(LatLng(0.0, 0.0))

        assertEquals(addTriggerUseCase.position, Position(0.0, 0.0))
    }

    @Test
    fun addTrigger_noyOnEditMode_doesNothing() {
        val addTriggerUseCase = AddTriggerUseCaseSpy()
        var sut = MapActivityController(MapUIPresenter(mock(MapUI::class.java)), addTriggerUseCase)

        sut.addTrigger(LatLng(0.0, 0.0))

        assertNull(addTriggerUseCase.position)
    }
}

class AddTriggerUseCaseSpy : AddTrigger {
    var position: Position? = null

    override fun addTriggerAt(position: Position) {
        this.position = position
    }

}
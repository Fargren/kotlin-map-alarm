package com.mapalarm

import com.mapalarm.datatypes.Position
import com.mapalarm.usecases.MovePresenter
import com.mapalarm.usecases.MoveUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNull
import kotlin.test.assertTrue

public class MoveUseCaseTest {
    lateinit var presenter: MovePresenterSpy
    lateinit var sut: MoveUseCase

    @Before
    fun setUp() {
        presenter = MovePresenterSpy()
        sut = MoveUseCase(presenter)
    }

    @Test @Throws(Exception::class) fun move_presentsUserAtNewPosition() {
        val fakeLocationGateway = FakeLocationGateway()
        Environment.locationGateway = fakeLocationGateway

        sut.refreshPosition()

        assertEquals(presenter.userPosition,
                Position(fakeLocationGateway.lat, fakeLocationGateway.lng))
    }

    @Test @Throws(Exception::class) fun cantFindPosition_presentsPositioningError() {
        Environment.locationGateway = FailingLocationGateway()

        sut.refreshPosition()

        assertTrue(presenter.positionUnknown)
        assertNull(presenter.userPosition)
    }
}

class FakeLocationGateway : LocationGateway {
    public val lat = 34.5
    public val lng = -18.44
    public val position = Position(lat, lng)

    override fun getCurrentPosition(callback: (Position) -> Unit, onError: () -> Unit) {
        callback(position)
    }

}

class FailingLocationGateway : LocationGateway {
    override fun getCurrentPosition(callback: (Position) -> Unit, onError: () -> Unit) {
        onError()
    }
}

class MovePresenterSpy : MovePresenter {
    var positionUnknown: Boolean = false
    var userPosition: Position? = null

    override fun showUserAt(position: Position) {
        userPosition = position
    }

    override fun showPositionUnknown() {
        positionUnknown = true
    }

}

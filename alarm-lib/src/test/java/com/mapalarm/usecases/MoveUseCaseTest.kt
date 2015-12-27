package com.mapalarm.usecases

import com.mapalarm.Constants.Companion.defaultLat
import com.mapalarm.Constants.Companion.defaultLng
import com.mapalarm.Constants.Companion.defaultPosition
import com.mapalarm.Environment
import com.mapalarm.TriggersGateway
import com.mapalarm.UserSituationGateway
import com.mapalarm.datatypes.Position
import com.mapalarm.datatypes.UserSituation
import com.mapalarm.entities.Trigger
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

public class MoveUseCaseTest {
    lateinit var presenter: MovePresenterSpy
    lateinit var alarm: AlarmSpy
    lateinit var sut: MoveUseCase

    @Before
    fun setUp() {
        alarm = AlarmSpy()
        presenter = MovePresenterSpy()
        sut = MoveUseCase(presenter, alarm)
    }

    @Test @Throws(Exception::class) fun cannotFindPosition_presentsPositioningError() {
        Environment.situationGateway = FailingUserSituationGateway()

        sut.refreshPosition()

        assertTrue(presenter.positionUnknown)
        assertNull(presenter.userPosition)
    }

    @Test @Throws(Exception::class) fun move_presentsUserAtNewPosition() {
        Environment.situationGateway = FakeUserSituationGateway(defaultPosition)

        sut.refreshPosition()

        assertEquals(presenter.userPosition, Position(defaultLat, defaultLng))
    }

    @Test fun moveWithoutMatchingTriggers_doesNotRingTheAlarm() {
        Environment.situationGateway = FakeUserSituationGateway(defaultPosition)
        Environment.triggersGateway = MissingTriggersGateway()

        sut.refreshPosition()

        assertFalse(alarm.ringing)
    }

    @Test fun moveIntoTrigger_ringsTheAlarm() {
        Environment.situationGateway = FakeUserSituationGateway(defaultPosition)
        Environment.triggersGateway = MatchingTriggersGateway()

        sut.refreshPosition()

        assertTrue(alarm.ringing)
    }
}

class MatchingTriggersGateway : TriggersGateway {
    override fun getTriggers(): Set<Trigger> {
        return hashSetOf(
                MissingTrigger(),
                MatchingTrigger()
        )
    }
}

class MatchingTrigger : Trigger {
    override val position: Position
        get() = throw UnsupportedOperationException()
    override val radius: Double
        get() = throw UnsupportedOperationException()

    override fun matches(situation: UserSituation): Boolean {
        return true
    }
}

class MissingTriggersGateway : TriggersGateway {
    override fun getTriggers(): Set<Trigger> {
        return hashSetOf(
                MissingTrigger()
        )
    }
}

class MissingTrigger : Trigger {
    override val position: Position
        get() = throw UnsupportedOperationException()
    override val radius: Double
        get() = throw UnsupportedOperationException()

    override fun matches(situation: UserSituation): Boolean {
        return false
    }
}

class AlarmSpy : AlarmPresenter {
    var ringing: Boolean = false

    override fun ring() {
        ringing = true
    }
}

class FakeUserSituationGateway(val position: Position) : UserSituationGateway {
    override fun getSituation(callback: (UserSituation) -> Unit, onError: () -> Unit) {
        callback(UserSituation(position))
    }
}

class FailingUserSituationGateway : UserSituationGateway {
    override fun getSituation(callback: (UserSituation) -> Unit, onError: () -> Unit) {
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

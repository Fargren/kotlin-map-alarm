package com.mapalarm

import com.mapalarm.datatypes.Position
import com.mapalarm.entities.PositionTrigger
import com.mapalarm.usecases.AddAlarmPresenter
import com.mapalarm.usecases.AddTriggerUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class AddTriggerUseCaseTests {
    var gatewaySpy = addTriggerGatewaySpy()
    var presenter = AddAlarmPresenterSpy()
    var sut = AddTriggerUseCase(presenter)

    private val DEFAULT_POSITION = Position(34.5, -18.44)

    @Before fun setUp() {
        gatewaySpy = addTriggerGatewaySpy()
        Environment.addTriggerGateway = gatewaySpy
        presenter = AddAlarmPresenterSpy()
        sut = AddTriggerUseCase(presenter)
    }

    @Test @Throws(Exception::class) fun addTriggerAt_newPosition_isRegistered() {
        sut.addTriggerAt(DEFAULT_POSITION)

        assertEquals(Collections.singleton(PositionTrigger(DEFAULT_POSITION, 50.0)),
                gatewaySpy.triggers)
    }

    @Test @Throws(Exception::class) fun addTriggerAt_newAlarm_isPresented() {
        sut.addTriggerAt(DEFAULT_POSITION)

        assertEquals(Collections.singleton(PresentableTrigger(DEFAULT_POSITION, 50.0)),
                presenter.triggers)
    }
}

class AddAlarmPresenterSpy : AddAlarmPresenter {
    var triggers: HashSet<PresentableTrigger> = HashSet()

    override fun presentTrigger(trigger: PresentableTrigger) {
        triggers.add(trigger)
    }
}

class addTriggerGatewaySpy : AddTriggerGateway {
    var triggers: HashSet<PositionTrigger> = HashSet()

    override fun addTrigger(trigger: PositionTrigger) {
        triggers.add(trigger)
    }
}

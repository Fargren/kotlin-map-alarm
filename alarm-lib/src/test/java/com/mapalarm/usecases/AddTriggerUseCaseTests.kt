package com.mapalarm.usecases

import com.mapalarm.AddTriggerGateway
import com.mapalarm.Environment
import com.mapalarm.PresentableTrigger
import com.mapalarm.datatypes.Position
import com.mapalarm.entities.PositionTrigger
import com.mapalarm.usecases.AddAlarmPresenter
import com.mapalarm.usecases.AddTriggerUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class AddTriggerUseCaseTests {
    lateinit var gatewaySpy: AddTriggerGatewaySpy
    lateinit var presenter: AddAlarmPresenterSpy
    lateinit var sut: AddTriggerUseCase

    private val DEFAULT_POSITION = Position(34.5, -18.44)

    @Before fun setUp() {
        gatewaySpy = AddTriggerGatewaySpy()
        Environment.addTriggerGateway = gatewaySpy
        presenter = AddAlarmPresenterSpy()
        sut = AddTriggerUseCase(presenter)
    }

    @Test @Throws(Exception::class) fun addTriggerAt_newPosition_isRegistered() {
        sut.addTriggerAt(DEFAULT_POSITION)

        assertEquals(Collections.singleton(PositionTrigger(DEFAULT_POSITION, 200.0)),
                gatewaySpy.triggers)
    }

    @Test @Throws(Exception::class) fun addTriggerAt_newAlarm_isPresented() {
        sut.addTriggerAt(DEFAULT_POSITION)

        assertEquals(Collections.singleton(PresentableTrigger(DEFAULT_POSITION, 200.0)),
                presenter.triggers)
    }
}

class AddAlarmPresenterSpy : AddAlarmPresenter {
    var triggers: HashSet<PresentableTrigger> = HashSet()

    override fun presentTrigger(trigger: PresentableTrigger) {
        triggers.add(trigger)
    }
}

class AddTriggerGatewaySpy : AddTriggerGateway {
    var triggers: HashSet<PositionTrigger> = HashSet()

    override fun addTrigger(trigger: PositionTrigger) {
        triggers.add(trigger)
    }
}

package com.mapalarm

import com.mapalarm.AddAlarmGateway
import com.mapalarm.Environment
import com.mapalarm.entities.Alarm
import com.mapalarm.usecases.AddAlarmPresenter
import com.mapalarm.usecases.AddAlarmUseCase
import com.mapalarm.entities.Position
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class AddAlarmUseCaseTests {
    val gatewaySpy = AddAlarmGatewaySpy()
    val presenter = AddAlarmPresenterSpy()
    val sut: AddAlarmUseCase = AddAlarmUseCase(presenter)

    @Before
    fun setUp() {
        Environment.addAlarmGateway = gatewaySpy
    }

    @Test @Throws(Exception::class) fun newAlarm_isRegistered() {
        sut.addAlarmAt(Position(34.5, -18.44))

        assertEquals(Collections.singleton(Alarm(Position(34.5, -18.44), 50.0)), gatewaySpy.alarms)
    }

    @Test @Throws(Exception::class) fun newAlarm_isPresented() {
        sut.addAlarmAt(Position(34.5, -18.44))

        assertEquals(Collections.singleton(Alarm(Position(34.5, -18.44), 50.0)), presenter.alarms)
    }
}

class AddAlarmPresenterSpy : AddAlarmPresenter {
    var alarms: HashSet<Alarm> = HashSet()

    override fun presentAlarm(alarm: Alarm) {
        alarms.add(alarm)
    }
}

class AddAlarmGatewaySpy : AddAlarmGateway {
    var alarms: HashSet<Alarm> = HashSet()

    override fun addAlarm(alarm: Alarm) {
        alarms.add(alarm)
    }


}

package com.mapalarm

import com.mapalarm.datatypes.Position
import com.mapalarm.entities.PositionTrigger
import com.mapalarm.usecases.ListTriggersPresenter
import com.mapalarm.usecases.ListTriggersUseCase
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ListTriggersUseCaseTests {
    var presenter = ListTriggersPresenterSpy()
    var sut = ListTriggersUseCase(presenter)

    @Before
    fun setUp() {
        presenter = ListTriggersPresenterSpy()
        sut = ListTriggersUseCase(presenter)
    }

    @Test
    fun listWithoutAlarms_showsNothing() {
        Environment.triggersGateway = EmptyTriggersGateway()

        sut.listAll()

        assertEquals(Collections.emptySet<PresentableTrigger>(), presenter.triggers)
    }

    @Test
    fun listWithOneAlarm_showsTheAlarm() {
        Environment.triggersGateway = OneTriggerTriggersGateway()

        sut.listAll()

        assertEquals(1, presenter.triggers!!.size)
        assertTrue(presenter.triggers!!.contains(PresentableTrigger(Position(5.0, 42.0))))
    }

    @Test
    fun listTwoAlarms_showsBothAlarms() {
        Environment.triggersGateway = TwoTriggersGateway()

        sut.listAll()

        assertEquals(2, presenter.triggers!!.size)
        assertTrue(presenter.triggers!!.contains(PresentableTrigger(Position(5.0, 42.0))))
        assertTrue(presenter.triggers!!.contains(PresentableTrigger(Position(-5.0, -2.0))))
    }
}

class TwoTriggersGateway : TriggersGateway {
    override fun getGateways(): Set<PositionTrigger> {
        return hashSetOf(PositionTrigger(Position(5.0, 42.0)), PositionTrigger(Position(-5.0, -2.0)))
    }
}

class OneTriggerTriggersGateway : TriggersGateway {
    override fun getGateways(): Set<PositionTrigger> {
        return Collections.singleton(PositionTrigger(Position(5.0, 42.0)))
    }
}

class EmptyTriggersGateway : TriggersGateway {
    override fun getGateways(): Set<PositionTrigger> {
        return Collections.emptySet<PositionTrigger>()
    }

}

class ListTriggersPresenterSpy : ListTriggersPresenter {
    var triggers: Set<PresentableTrigger>? = null

    override fun showTriggers(triggers: Set<PresentableTrigger>) {
        this.triggers = triggers
    }

}
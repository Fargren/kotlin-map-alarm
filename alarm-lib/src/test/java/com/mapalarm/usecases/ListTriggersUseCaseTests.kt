package com.mapalarm.usecases

import com.mapalarm.Constants.Companion.defaultPosition
import com.mapalarm.Constants.Companion.defaultRadius
import com.mapalarm.Environment
import com.mapalarm.PresentableTrigger
import com.mapalarm.TriggersGateway
import com.mapalarm.datatypes.Position
import com.mapalarm.entities.PositionTrigger
import com.mapalarm.mocks.DefaultTriggersGateway
import com.mapalarm.usecases.ListTriggersPresenter
import com.mapalarm.usecases.ListTriggersUseCase
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ListTriggersUseCaseTests {
    lateinit var presenter: ListTriggersPresenterSpy
    lateinit var sut: ListTriggersUseCase

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
        Environment.triggersGateway = DefaultTriggersGateway()

        sut.listAll()

        with(presenter.triggers!!) {
            assertEquals(1, size)
            assertTrue(contains(PresentableTrigger(defaultPosition, defaultRadius)))
        }
    }

    @Test
    fun listTwoAlarms_showsBothAlarms() {
        Environment.triggersGateway = TwoTriggersGateway()

        sut.listAll()

        with(presenter.triggers!!) {
            assertEquals(2, size)
            assertTrue(contains(PresentableTrigger(defaultPosition, defaultRadius)))
            assertTrue(contains(PresentableTrigger(Position(-5.0, -2.0), 200.0)))
        }
    }
}

class TwoTriggersGateway : TriggersGateway {
    override fun getTriggers(): Set<PositionTrigger> {
        return hashSetOf(
                PositionTrigger(defaultPosition, defaultRadius),
                PositionTrigger(Position(-5.0, -2.0), 200.0))
    }
}

class EmptyTriggersGateway : TriggersGateway {
    override fun getTriggers(): Set<PositionTrigger> {
        return Collections.emptySet<PositionTrigger>()
    }

}

class ListTriggersPresenterSpy : ListTriggersPresenter {
    var triggers: Set<PresentableTrigger>? = null

    override fun showTriggers(triggers: Set<PresentableTrigger>) {
        this.triggers = triggers
    }
}
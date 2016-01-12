package com.mapalarm.entities

import com.mapalarm.Constants
import com.mapalarm.Constants.Companion.defaultPosition
import com.mapalarm.Constants.Companion.defaultRadius
import com.mapalarm.Constants.Companion.positionOutOfDefaultRadius
import com.mapalarm.datatypes.UserSituation
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PositionTriggerTests {
    @Test
    fun matches_withUserAtTheAlarmPosition_returnsTrue() {
        var sut = PositionTrigger(defaultPosition, defaultRadius)

        val matches = sut.matches(UserSituation(defaultPosition))

        assertTrue(matches)
    }

    @Test
    fun matches_withJustOutsideTheAlarmPosition_returnsFalse() {
        var sut = PositionTrigger(defaultPosition, defaultRadius)

        val matches = sut.matches(UserSituation(positionOutOfDefaultRadius))

        assertFalse(matches)
    }


    @Test
    fun matches_withJustInsideTheAlarmPosition_returnsTrue() {
        var sut = PositionTrigger(defaultPosition, defaultRadius)

        val matches = sut.matches(UserSituation(Constants.positionInsideOfDefaultRadius))

        assertTrue(matches)
    }

}

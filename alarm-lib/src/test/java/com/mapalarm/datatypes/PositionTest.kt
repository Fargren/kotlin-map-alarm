package com.mapalarm.datatypes

import org.junit.Assert.assertEquals
import org.junit.Test

class PositionTest {
    @Test
    fun distanceInMeters_InOneAxis_isLinearDistance() {
        var sut = Position(0.0, 0.0)

        val distanceLongitude = sut.distanceInMeters(Position(0.0, 50.0))

        assertEquals(distanceLongitude, 5559746.0, 1.0)

        val distanceLatitude = sut.distanceInMeters(Position(-50.0, 0.0))

        assertEquals(distanceLatitude, 5559746.0, 1.0)
    }

    @Test
    fun distanceInMeters_diagonal_isRealDistance() {
        var sut = Position(0.0, 0.0)

        val distance = sut.distanceInMeters(Position(50.0, -50.0))

        assertEquals(distance, 7293887.0, 1.0)
    }

    @Test
    fun distanceInMeters_inBuenosAires_isRealDistance() {
        var sut = Position(-34.5796865,-58.4314084)

        val distance = sut.distanceInMeters(Position(-34.6036840,-58.3815590))

        assertEquals(distance, 5286.0, 1.0)
    }
}
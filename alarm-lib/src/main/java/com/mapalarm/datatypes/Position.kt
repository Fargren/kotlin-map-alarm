package com.mapalarm.datatypes

data class Position(val latitude: Double, val longitude: Double) {
    fun distanceInMeters(to: Position): Double {
        var R = 6371000; // metres
        var φ1 = latitude.toRad();
        var φ2 = to.latitude.toRad();
        var Δφ = (to.latitude - latitude).toRad();
        var Δλ = (to.longitude - longitude).toRad();

        var a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
                Math.cos(φ1) * Math.cos(φ2) *
                        Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    fun Double.toRad(): Double {
        return Math.PI * this / 180
    }
}

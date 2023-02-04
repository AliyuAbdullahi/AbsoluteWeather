package com.lek.absoluteweather.ui.systemservices.location.model

data class UserLocation(
    val city: String,
    val status: LocationStatus
) {

    val playServiceRequired = status == LocationStatus.REQUIRES_PLAY_SERVICE

    companion object {
        val EMPTY = UserLocation(city = "", status = LocationStatus.UNKNOWN)
    }
}

enum class LocationStatus {
    SUCCESS, FAILED, REQUIRES_PLAY_SERVICE, UNKNOWN
}
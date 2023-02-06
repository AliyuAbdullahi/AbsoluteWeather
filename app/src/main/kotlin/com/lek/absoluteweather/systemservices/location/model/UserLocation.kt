package com.lek.absoluteweather.systemservices.location.model

data class UserLocation(
    val city: String,
    val status: LocationStatus
) {

    val isValid = status != LocationStatus.FAILED && city.isNotBlank()

    companion object {
        val EMPTY = UserLocation(city = "", status = LocationStatus.UNKNOWN)
    }
}

enum class LocationStatus {
    SUCCESS, FAILED, REQUIRES_PLAY_SERVICE, UNKNOWN
}
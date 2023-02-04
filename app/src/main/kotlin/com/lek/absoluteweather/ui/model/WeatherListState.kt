package com.lek.absoluteweather.ui.model

import com.lek.domain.model.Weather

data class WeatherListState(
    val isLocationPermissionGranted: Boolean,
    val isNotificationPermissionGranted: Boolean,
    val weatherList: List<Weather>,
    val isLoading: Boolean,
    val shouldQuit: Boolean,
    val alreadyAskedForNotification: Boolean
) {
    companion object {
        val EMPTY = WeatherListState(
            isLocationPermissionGranted = true,
            isNotificationPermissionGranted = true,
            weatherList = listOf(),
            isLoading = true,
            shouldQuit = false,
            alreadyAskedForNotification = false
        )
    }
}

sealed interface ViewEvent {
    object StartDetail: ViewEvent
    object RefreshList: ViewEvent
    object UserDenyNotification: ViewEvent
    object RequestLocationPermission: ViewEvent
    object RequestNotificationPermission: ViewEvent
}
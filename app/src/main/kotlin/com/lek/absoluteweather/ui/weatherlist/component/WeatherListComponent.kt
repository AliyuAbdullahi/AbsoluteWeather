package com.lek.absoluteweather.ui.weatherlist.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.lek.absoluteweather.R
import com.lek.absoluteweather.ui.model.WeatherListState
import com.lek.absoluteweather.ui.weatherlist.model.getDayOfTheWeek
import com.lek.domain.model.Weather

@Composable
fun WeatherListScreen(
    state: WeatherListState,
    onLocationEnableClicked: () -> Unit,
    onLocationDenyClicked: () -> Unit,
    onNotificationEnableClicked: () -> Unit,
    onNotificationDenyClicked: () -> Unit,
    onShowDetailsClicked: (Weather) -> Unit
) {
    when {
        state.isLoading -> {
            LoadingScreen()
        }
        state.isLocationPermissionGranted.not() -> {
            PermissionComponent(
                isVisible = state.isLocationPermissionGranted.not(),
                title = R.string.location_request_title,
                subtitle = R.string.location_permission_message,
                onEnableClicked = onLocationEnableClicked,
                onDenyClicked = onLocationDenyClicked
            )
        }
        state.isNotificationPermissionGranted.not() && state.alreadyAskedForNotification.not() -> {
            PermissionComponent(
                isVisible = state.isNotificationPermissionGranted.not(),
                title = R.string.notification_request_title,
                subtitle = R.string.notification_permission_message,
                onEnableClicked = onNotificationEnableClicked,
                onDenyClicked = onNotificationDenyClicked
            )
        }

        else -> {
            WeatherList(weatherItems = state.weatherList, onShowDetailsClicked = onShowDetailsClicked)
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = stringResource(id = R.string.loading))
    }
}

@Composable
fun WeatherList(weatherItems: List<Weather>, onShowDetailsClicked: (Weather) -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(weatherItems) { weather ->
                if (weather.isToday) {
                    WeatherToday(
                        iconUrl = weather.iconUrl,
                        currentTemp = weather.getCurrentTemp(),
                        currentFeelsLike = weather.getCurrentFeelsLike(),
                        main = weather.main,
                        formattedDate = weather.getFormattedDate(),
                        city = weather.city,
                        weatherStatus = weather.getStatus(),
                        onClick = { onShowDetailsClicked(weather) }
                    )
                } else {
                    WeatherItem(
                        dayOfTheWeek = weather.getDayOfTheWeek(LocalContext.current),
                        formattedDayMonth = weather.getFormattedDayMonth(),
                        currentTemp = stringResource(
                            id = R.string.temperature_celsius, weather.getCurrentTemp()
                        ),
                        iconUrl = weather.iconUrl,
                        onClick = { onShowDetailsClicked(weather) }
                    )
                }
            }
        }
    }
}
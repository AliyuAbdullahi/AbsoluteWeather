package com.lek.absoluteweather.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lek.absoluteweather.R
import com.lek.absoluteweather.ui.model.WeatherListState
import com.lek.domain.model.Weather

@Composable
fun WeatherListScreen(
    state: WeatherListState,
    onLocationEnableClicked: () -> Unit,
    onLocationDenyClicked: () -> Unit,
    onNotificationEnableClicked: () -> Unit,
    onNotificationDenyClicked: () -> Unit
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
            WeatherList(weatherItems = state.weatherList)
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Loading...")
    }
}

@Composable
fun WeatherList(weatherItems: List<Weather>) {
    LazyColumn {
        items(weatherItems) { weather ->
            WeatherItem(weather = weather)
        }
    }
}

@Composable
fun WeatherItem(weather: Weather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        AsyncImage(
            modifier = Modifier.size(40.dp),
            model = weather.iconUrl,
            contentDescription = "weather icon"
        )
        Column {
            Text(text = weather.dayOfTheWeek)
            Text(text = weather.main)
            Text(text = weather.description)
        }
    }
}
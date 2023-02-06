package com.lek.absoluteweather.ui.details.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lek.absoluteweather.R
import com.lek.domain.model.Weather

@Composable
fun WeatherDetailsScreen(
    weather: Weather,
    onBackPressed: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        DetailsTopSection(
            weatherStatus = weather.getStatus(),
            temperatureStatus = weather.getTemperatureStatus(),
            city = weather.city,
            formattedDate = weather.getFormattedDate(),
            temperature = stringResource(
                id = R.string.temperature_celsius,
                weather.getCurrentTemp()
            ),
            main = weather.main,
            iconUrl = weather.iconUrl,
            description = weather.description,
            onBackPressed = onBackPressed
        )
        WindDescriptionSection(
            windSpeed = stringResource(id = R.string.wind_speed, weather.windSpeed),
            humidity = stringResource(id = R.string.humidity_in_percent, weather.humidity),
            pressure = stringResource(id = R.string.pressure_value, weather.pressure)
        )
        TemperatureDescription(
            maxTemp = weather.maxTemp,
            maxTempString = stringResource(id = R.string.temperature_celsius, weather.maxTemp),
            minTemp = weather.minTemp,
            minTempString = stringResource(id = R.string.temperature_celsius, weather.minTemp),
            morningTemp = stringResource(id = R.string.temperature_celsius, weather.morningTemp),
            afternoonTemp = stringResource(id = R.string.temperature_celsius, weather.dayTemp),
            eveningTemp = stringResource(id = R.string.temperature_celsius, weather.eveningTemp),
            nightTemp = stringResource(id = R.string.temperature_celsius, weather.nightTemp),
            morningFeelsLikeTemp = stringResource(
                id = R.string.temperature_celsius,
                weather.morningFeelsLike
            ),
            afternoonFeelsLikeTemp = stringResource(
                id = R.string.temperature_celsius,
                weather.dayFeelsLike
            ),
            eveningFeelsLikeTemp = stringResource(
                id = R.string.temperature_celsius,
                weather.eveningFeelsLike
            ),
            nightFeelsLikeTemp = stringResource(
                id = R.string.temperature_celsius,
                weather.nightFeelsLike
            )
        )
    }
}
package com.lek.domain

import com.lek.domain.model.WeatherResult
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {
    val weatherResultStream: Flow<WeatherResult>

    suspend fun fetchWeather(city: String, units: String): WeatherResult
}
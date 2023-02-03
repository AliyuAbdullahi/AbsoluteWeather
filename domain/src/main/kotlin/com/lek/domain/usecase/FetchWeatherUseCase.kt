package com.lek.domain.usecase

import com.lek.domain.IWeatherRepository
import com.lek.domain.model.WeatherRequest
import com.lek.domain.model.WeatherResult

class FetchWeatherUseCase(
    private val repository: IWeatherRepository
) : UseCase<WeatherRequest, WeatherResult>() {

    override suspend fun run(param: WeatherRequest): WeatherResult =
        repository.fetchWeather(param.city, param.units.value)
}
package com.lek.domain.usecase

import com.lek.domain.IWeatherRepository
import com.lek.domain.model.Weather

class SetSelectedWeatherUseCase(private val repository: IWeatherRepository) {

    operator fun invoke(weather: Weather) = repository.setSelectedWeather(weather)
}
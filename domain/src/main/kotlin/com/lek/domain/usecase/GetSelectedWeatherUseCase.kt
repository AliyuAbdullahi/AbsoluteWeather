package com.lek.domain.usecase

import com.lek.domain.IWeatherRepository

class GetSelectedWeatherUseCase(private val repository: IWeatherRepository) {
    operator fun invoke() = repository.getSelectedWeather()
}
package com.lek.domain.usecase

import com.lek.domain.IWeatherRepository
import com.lek.domain.model.WeatherResult
import kotlinx.coroutines.flow.Flow

class GetWeatherResultStreamUseCase(
  private val  repository: IWeatherRepository
) : StreamUseCase<Unit, WeatherResult>() {

    override fun run(param: Unit): Flow<WeatherResult> = repository.weatherResultStream
}
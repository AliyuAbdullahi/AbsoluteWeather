package com.lek.domain.usecase

import com.lek.domain.IWeatherRepository
import com.lek.domain.model.Weather
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

internal class SetSelectedWeatherUseCaseTest {

    private val repository: IWeatherRepository = mockk(relaxed = true)
    private val useCase = SetSelectedWeatherUseCase(repository)

    @Test
    fun `WHEN useCase is invoked - sets weather selected in repository`() {
        val weather: Weather = mockk()
        useCase(weather)
        verify { repository.setSelectedWeather(weather) }
    }
}
package com.lek.absoluteweather.ui.details

import com.lek.domain.model.Weather
import com.lek.domain.usecase.GetSelectedWeatherUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class WeatherDetailsViewModelTest {
    private val useCase: GetSelectedWeatherUseCase = mockk()
    private val viewModel = WeatherDetailsViewModel(useCase)

    @Test
    fun `WHEN viewModel is invoked - returns weather`() {
        val weather: Weather = mockk()
        every { useCase() }.returns(weather)
        assertEquals(weather, viewModel.weather)
    }
}
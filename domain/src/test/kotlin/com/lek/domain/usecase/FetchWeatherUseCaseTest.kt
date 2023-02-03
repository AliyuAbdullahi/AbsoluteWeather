package com.lek.domain.usecase

import com.lek.domain.IWeatherRepository
import com.lek.domain.model.MeasuringUnit
import com.lek.domain.model.WeatherRequest
import com.lek.domain.model.WeatherResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

internal class FetchWeatherUseCaseTest {

    private val repository: IWeatherRepository = mockk()
    private val fetchWeatherUseCase = FetchWeatherUseCase(repository)

    @Test
    fun `WHEN FetchWeatherUseCase is invoked - returns data in repository`() = runBlocking {
        val mockedData: WeatherResult = mockk()
        coEvery { repository.fetchWeather(any(), any()) }.coAnswers { mockedData }
        val result = fetchWeatherUseCase(
            WeatherRequest(
                city = "Hamburg",
                units = MeasuringUnit.METRIC
            )
        )

        assertEquals(result, mockedData)
    }
}
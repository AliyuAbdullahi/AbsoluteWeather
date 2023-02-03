package com.lek.domain.usecase

import app.cash.turbine.test
import com.lek.domain.IWeatherRepository
import com.lek.domain.model.WeatherResult
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

internal class GetWeatherResultStreamUseCaseTest {

    private val repository: IWeatherRepository = mockk()
    private val useCase = GetWeatherResultStreamUseCase(repository)

    @Test
    fun `WHEN useCase is invoked - result in the repository can be observed`() = runBlocking {
        val mockData: WeatherResult = mockk()
        every { repository.weatherResultStream }.returns(flowOf(mockData))
        useCase().test {
            assertEquals(awaitItem(), mockData)
            awaitComplete()
        }
    }
}
package com.lek.domain.usecase

import com.lek.domain.IWeatherRepository
import com.lek.domain.model.Weather
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test

internal class GetSelectedWeatherUseCaseTest {

   private val repository: IWeatherRepository = mockk(relaxed = true)
   private val useCase = GetSelectedWeatherUseCase(repository)

    @Test
    fun `WHEN useCase is invoked - returns selected weather in repository`() {
        val weather: Weather = mockk()
        every { repository.getSelectedWeather() }.returns(weather)
        val result = useCase()
        assertEquals(weather, result)
    }
}
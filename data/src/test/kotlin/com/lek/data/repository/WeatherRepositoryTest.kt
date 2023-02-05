package com.lek.data.repository

import app.cash.turbine.test
import com.lek.data.api.WeatherService
import com.lek.data.api.model.WeatherResponse
import com.lek.data.repository.resource.testWeatherResponseData
import com.lek.data.room.WeatherDao
import com.lek.domain.model.MeasuringUnit
import com.lek.domain.model.WeatherResult
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import retrofit2.Response

internal class WeatherRepositoryTest {

    private val weatherService: WeatherService = mockk()
    private val weatherDao: WeatherDao = mockk(relaxed = true)

    private val repository = WeatherRepository(weatherService, weatherDao)

    @Test
    fun `WHEN weatherService result IS successful - repository#fetchWeather returns the result`() =
        runBlocking {
            val response: Response<WeatherResponse> = mockk()
            every { response.isSuccessful }.returns(true)
            every { response.body() }.returns(testWeatherResponseData)
            every { response.code() }.returns(200)
            coEvery {
                weatherService.getWeatherFor(any(), any(), any(), any())
            }.coAnswers { response }

            coEvery { weatherDao.add(any()) }.just(Runs)
            coEvery { weatherDao.deleteAll() }.just(Runs)

            val result = repository.fetchWeather(
                city = "Hamburg",
                units = MeasuringUnit.METRIC.value
            )

            assertTrue(result is WeatherResult.Success)
            val resultValue = (result as WeatherResult.Success).data
            assertTrue(resultValue.size == 1)
            assertTrue(resultValue[0].id == 400)
            assertEquals(1675425057353L, resultValue[0].date)
            assertEquals(1.0, resultValue[0].minTemp)
            assertEquals(6.0, resultValue[0].maxTemp)
            assertEquals(200, resultValue[0].pressure)
            assertEquals(50, resultValue[0].humidity)
            assertEquals(5.8, resultValue[0].dayFeelsLike)
            assertEquals("Cloudy", resultValue[0].main)
            assertEquals("It will be cloudy today", resultValue[0].description)
            assertEquals("20b", resultValue[0].icon)

            repository.weatherResultStream.test {
                assertEquals((awaitItem() as WeatherResult.Success).data[0].id, 400)
            }
            coVerify { weatherDao.deleteAll() }
        }

    @Test
    fun `WHEN weatherService result FAILS - local data is returned`() = runBlocking {
        val response: Response<WeatherResponse> = mockk()
        every { response.isSuccessful }.returns(false)
        every { response.body() }.returns(null)
        every { response.code() }.returns(404)
        every { TimeHelper.getNow() }.returns(1675425057353L * 1000)
        every { response.errorBody() }.returns(mockk())
        coEvery { weatherService.getWeatherFor(any(), any(), any(), any()) }.coAnswers { response }

        coEvery { weatherDao.add(any()) }.just(Runs)

        val result = repository.fetchWeather(city = "Hamburg", units = MeasuringUnit.METRIC.value)
        coVerify { weatherDao.getWeather() }
        assert(result is WeatherResult.Failure)
    }
}
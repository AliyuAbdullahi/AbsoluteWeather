package com.lek.data.repository

import app.cash.turbine.test
import com.lek.data.api.WeatherService
import com.lek.data.api.model.MoreDetailWeatherDataObject
import com.lek.data.api.model.WeatherDataContent
import com.lek.data.api.model.WeatherDataObject
import com.lek.data.api.model.WeatherResponse
import com.lek.data.room.RoomWeatherEntity
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
    private val weatherDao: WeatherDao = mockk()

    private val repository = WeatherRepository(weatherService, weatherDao)

    @Test
    fun `WHEN weatherService result IS successful - repository#fetchWeather returns the result`() =
        runBlocking {
            val response: Response<WeatherResponse> = mockk()
            every { response.isSuccessful }.returns(true)
            every { response.body() }.returns(testWeatherResponseData)
            every { response.code() }.returns(200)
            coEvery { weatherService.getWeatherFor(any(), any(), any()) }.coAnswers { response }

            coEvery { weatherDao.add(any()) }.just(Runs)

            val result = repository.fetchWeather(
                city = "Hamburg",
                units = MeasuringUnit.METRIC.value
            )

            assertTrue(result is WeatherResult.Success)
            val resultValue = (result as WeatherResult.Success).data
            assertTrue(resultValue.size == 1)
            assertTrue(resultValue[0].id == 200)
            assertEquals(1675425057353L, resultValue[0].date)
            assertEquals(11.0, resultValue[0].feelsLike)
            assertEquals(7.0, resultValue[0].minTemp)
            assertEquals(13.0, resultValue[0].maxTemp)
            assertEquals(300, resultValue[0].pressure)
            assertEquals(60, resultValue[0].humidity)
            assertEquals("Cloudy", resultValue[0].main)
            assertEquals("It will be cloudy today", resultValue[0].description)
            assertEquals("20b", resultValue[0].icon)
            assertEquals(500L, resultValue[0].visibility)
            assertEquals("2023-02-03 06:00:00", resultValue[0].formattedDateTime)

            repository.weatherResultStream.test {
                 assertEquals((awaitItem() as WeatherResult.Success).data[0].id, 200)
            }
        }

    @Test
    fun `WHEN weatherService result FAILS - local data is returned`() = runBlocking {
        val response: Response<WeatherResponse> = mockk()
        every { response.isSuccessful }.returns(false)
        every { response.body() }.returns(null)
        every { response.code() }.returns(404)
        every { response.errorBody() }.returns(mockk())
        coEvery { weatherService.getWeatherFor(any(), any(), any()) }.coAnswers { response }

        coEvery { weatherDao.add(any()) }.just(Runs)
        coEvery { weatherDao.getWeather() }.coAnswers {
            listOf(entity)
        }
        val result = repository.fetchWeather(city = "Hamburg", units = MeasuringUnit.METRIC.value)
        coVerify { weatherDao.getWeather() }
        assert(result is WeatherResult.Failure)
    }

    private val entity = RoomWeatherEntity(
        id = 400,
        date = 1675425057400L,
        temp = 11.0,
        feelsLike = 12.0,
        minTemp = 9.0,
        maxTemp = 14.0,
        pressure = 200,
        humidity = 60,
        main = "Cloudy",
        description = "A cloudy day",
        icon = "20b",
        visibility = 600,
        formattedDateTime = "2023-02-03 06:00:00"
    )

    private val content = WeatherDataContent(
        date = 1675425057353L,
        main = MoreDetailWeatherDataObject(
            temp = 10.2,
            feelsLike = 11.0,
            minTemp = 7.0,
            maxTemp = 13.0,
            pressure = 300,
            humidity = 60
        ),
        weather = listOf(
            WeatherDataObject(
                id = 200,
                main = "Cloudy",
                description = "It will be cloudy today",
                icon = "20b"
            )
        ),
        visibility = 500L,
        formattedDate = "2023-02-03 06:00:00"
    )

    private val testWeatherResponseData = WeatherResponse(
        result = listOf(content)
    )
}
package com.lek.data.repository

import com.lek.data.api.model.WeatherResponse
import com.lek.data.repository.ModelMapper.isTomorrow
import com.lek.data.repository.resource.testWeatherResponseData
import com.lek.domain.model.WeatherResult
import com.lek.domain.model.WeatherStatus
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import retrofit2.Response

internal class WeatherModelTest {

    @Test
    fun `WHEN model is mapped - correct result is obtained`() = mockkObject(TimeHelper) {
        val response: Response<WeatherResponse> = mockk()
        every { response.code() }.returns(200)
        every { response.body() }.returns(testWeatherResponseData)
        every { response.isSuccessful }.returns(true)
        every { TimeHelper.getNow() }.returns(1675425057353L * 1000)
        val weatherList = ModelMapper.mapToResult(response) as WeatherResult.Success
        val weather = weatherList.data[0]
        assertTrue(weather.isToday)
        assertEquals(WeatherStatus.NORMAL, weather.getStatus())
        assertEquals("08 Feb", weather.getFormattedDayMonth())
        assertFalse(weather.isTomorrow())
    }
}

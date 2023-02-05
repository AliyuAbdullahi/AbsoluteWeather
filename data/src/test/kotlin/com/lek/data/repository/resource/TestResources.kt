package com.lek.data.repository.resource

import com.lek.data.api.model.City
import com.lek.data.api.model.FeelsLike
import com.lek.data.api.model.Temperature
import com.lek.data.api.model.WeatherDataContent
import com.lek.data.api.model.WeatherDataObject
import com.lek.data.api.model.WeatherResponse

private val content = WeatherDataContent(
    date = 1675425057353L,
    temperature = Temperature(
        day = 5.5,
        night = 2.1,
        min = 1.0,
        max = 6.0,
        morning = 4.0,
        evening = 3.5
    ),
    weatherData = listOf(
        WeatherDataObject(
            id = 400,
            main = "Cloudy",
            description = "It will be cloudy today",
            icon = "20b"
        )
    ),
    pressure = 200,
    humidity = 50,
    windSpeed = 2.5,
    feelsLike = FeelsLike(
        day = 5.8,
        night = 2.0,
        morning = 4.4,
        evening = 3.4
    )
)

internal val testWeatherResponseData = WeatherResponse(
    result = listOf(content),
    city = City(name = "Hamburg")
)
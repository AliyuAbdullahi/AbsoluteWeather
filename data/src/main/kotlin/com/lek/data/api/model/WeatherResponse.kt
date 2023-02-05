package com.lek.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @field:Json(name = "list") val result: List<WeatherDataContent>,
    @field:Json(name = "city") val city: City
)

@JsonClass(generateAdapter = true)
data class WeatherDataContent(
    @field:Json(name = "dt") val date: Long,
    @field:Json(name = "temp") val temperature: Temperature,
    @field:Json(name = "weather") val weatherData: List<WeatherDataObject>,
    @field:Json(name = "pressure") val pressure: Long,
    @field:Json(name = "humidity") val humidity: Int,
    @field:Json(name = "speed") val windSpeed: Double,
    @field:Json(name = "feels_like") val feelsLike: FeelsLike
)

@JsonClass(generateAdapter = true)
data class WeatherDataObject(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "main") val main: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "icon") val icon: String
)

@JsonClass(generateAdapter = true)
data class Temperature(
    @field:Json(name = "day") val day: Double,
    @field:Json(name = "night") val night: Double,
    @field:Json(name = "min") val min: Double,
    @field:Json(name = "max") val max: Double,
    @field:Json(name = "morn") val morning: Double,
    @field:Json(name = "eve") val evening: Double
)

@JsonClass(generateAdapter = true)
data class FeelsLike(
    @field:Json(name = "day") val day: Double,
    @field:Json(name = "night") val night: Double,
    @field:Json(name = "morn") val morning: Double,
    @field:Json(name = "eve") val evening: Double
)

@JsonClass(generateAdapter = true)
data class City(
    @field:Json(name = "name") val name: String
)


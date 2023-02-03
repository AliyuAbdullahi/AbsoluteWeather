package com.lek.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @field:Json(name = "list") val result: List<WeatherDataContent>
)

@JsonClass(generateAdapter = true)
data class WeatherDataContent(
    @field:Json(name = "dt") val date: Long,
    @field:Json(name = "main") val main: MoreDetailWeatherDataObject,
    @field:Json(name = "weather") val weather: List<WeatherDataObject>,
    @field:Json(name = "visibility") val visibility: Long,
    @field:Json(name = "dt_txt") val formattedDate: String
)

@JsonClass(generateAdapter = true)
data class MoreDetailWeatherDataObject(
    @field:Json(name = "temp") val temp: Double,
    @field:Json(name = "feels_like") val feelsLike: Double,
    @field:Json(name = "temp_min") val minTemp: Double,
    @field:Json(name = "temp_max") val maxTemp: Double,
    @field:Json(name = "pressure") val pressure: Long,
    @field:Json(name = "humidity") val humidity: Int
)

@JsonClass(generateAdapter = true)
data class WeatherDataObject(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "main") val main: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "icon") val icon: String
)


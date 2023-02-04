package com.lek.domain.model

data class Weather(
    val id: Int,
    val date: Long,
    val temp: Double,
    val feelsLike: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val pressure: Long,
    val humidity: Int,
    val main: String,
    val description: String,
    val icon: String,
    val visibility: Long,
    val formattedDateTime: String,
    val isToday: Boolean,
    val dayOfTheWeek: DayOfTheWeek
) {
    val isHot = temp > 25
    val isCold = temp < 10
    val iconUrl = "https://openweathermap.org/img/wn/${icon}@2x.png"
}

data class WeatherRequest(
    val city: String,
    val units: MeasuringUnit = MeasuringUnit.METRIC
)

enum class MeasuringUnit(val value: String) {
    METRIC("metric"),
    // We are only supporting metric system for now
}

enum class DayOfTheWeek {
    Mon, Tue, Wed, Thu, Fri, Sat, Sun
}
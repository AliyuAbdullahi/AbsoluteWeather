package com.lek.domain.model

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val EPOCH_TIME_TO_CURRENT_DATE_MULTIPLIER = 1000

private const val morningHoursMax = 12
private const val afternoonHoursMax = 15
private const val eveningHoursMax = 18
private const val nightHoursMax = 24

data class Weather(
    val id: Int,
    val date: Long,
    val minTemp: Double,
    val maxTemp: Double,
    val morningTemp: Double,
    val dayTemp: Double,
    val eveningTemp: Double,
    val nightTemp: Double,
    val morningFeelsLike: Double,
    val dayFeelsLike: Double,
    val eveningFeelsLike: Double,
    val nightFeelsLike: Double,
    val pressure: Long,
    val humidity: Int,
    val main: String,
    val description: String,
    val icon: String,
    val isToday: Boolean,
    val city: String,
    val windSpeed: Double,
    val dayOfTheWeek: DayOfTheWeek
) {
    val iconUrl = "https://openweathermap.org/img/wn/${icon}@2x.png"

    fun getCurrentFeelsLike(): Double {
        val calendar = Calendar.getInstance()
        calendar.time = Date(System.currentTimeMillis())
        val hourOfTheDay = calendar.get(Calendar.HOUR_OF_DAY) + 1 // range is 0 to 23
        return when {
            hourOfTheDay < morningHoursMax -> morningFeelsLike
            hourOfTheDay < afternoonHoursMax -> dayFeelsLike
            hourOfTheDay < eveningHoursMax -> eveningFeelsLike
            hourOfTheDay < nightHoursMax -> nightFeelsLike
            else -> throw IllegalArgumentException("Wrong time for feels like")
        }
    }

    fun getCurrentTemp(): Double {
        val calendar = Calendar.getInstance()
        calendar.time = Date(System.currentTimeMillis())
        val hourOfTheDay = calendar.get(Calendar.HOUR_OF_DAY)
        return when {
            hourOfTheDay < morningHoursMax -> morningTemp
            hourOfTheDay < afternoonHoursMax -> dayTemp
            hourOfTheDay < eveningHoursMax -> eveningTemp
            hourOfTheDay < nightHoursMax -> nightTemp
            else -> throw IllegalArgumentException("Wrong time for feels like")
        }
    }

    fun getFormattedDate(): String {
        val formatter = SimpleDateFormat("EE, dd MMM", Locale.getDefault())
        return formatter.format(date * EPOCH_TIME_TO_CURRENT_DATE_MULTIPLIER)
    }

    fun getFormattedDayMonth(): String {
        val formatter = SimpleDateFormat("dd MMM", Locale.getDefault())
        return formatter.format(date * EPOCH_TIME_TO_CURRENT_DATE_MULTIPLIER)
    }

    fun getStatus(): WeatherStatus {
        val currentMain = main.lowercase()
        return when {
            currentMain.contains("snow") -> WeatherStatus.SNOW
            currentMain.contains("rain") -> WeatherStatus.RAIN
            else -> WeatherStatus.NORMAL
        }
    }

    fun getTemperatureStatus(): TemperatureStatus {
        val temp = getCurrentTemp()
        return when {
            temp > 25.0 -> TemperatureStatus.HOT
            temp < 10.0 -> TemperatureStatus.COLD
            else -> TemperatureStatus.NORMAL
        }
    }
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

enum class WeatherStatus {
    RAIN, SNOW, NORMAL
}

enum class TemperatureStatus {
    HOT, COLD, NORMAL
}
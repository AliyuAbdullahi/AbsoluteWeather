package com.lek.data.repository

import com.lek.data.api.model.WeatherDataContent
import com.lek.data.api.model.WeatherResponse
import com.lek.data.room.RoomWeatherEntity
import com.lek.domain.model.Weather
import com.lek.domain.model.WeatherResult
import com.lek.domain.model.WeatherResult.Failure
import java.util.Calendar
import java.util.Date
import retrofit2.Response

object ModelMapper {

    fun mapToResult(result: Response<WeatherResponse>): WeatherResult =
        if (result.isSuccessful.not()) {
            Failure(result.errorBody().toString(), listOf())
        } else {
            result.body()?.let { weatherResponse ->
                WeatherResult.Success(weatherResponse.result.toDomainModel())
            } ?: Failure("Unknown network failure", listOf())
        }


    private fun List<WeatherDataContent>.toDomainModel(): List<Weather> = map { dataList ->
        val (date, main, weather, visibility, formattedDate) = dataList
        val currentWeather = weather.firstOrNull() ?: throw IllegalArgumentException("Weather object is empty")
        Weather(
            id = currentWeather.id,
            date = date,
            temp = main.temp,
            feelsLike = main.feelsLike,
            minTemp = main.minTemp,
            maxTemp = main.maxTemp,
            pressure = main.pressure,
            humidity = main.humidity,
            main = currentWeather.main,
            description = currentWeather.description,
            icon = currentWeather.icon,
            visibility = visibility,
            formattedDateTime = formattedDate,
            isToday = date.isToday(),
            dayOfTheWeek = date.toDayOfTheWeek()
        )
    }

    internal fun Long.isFromToday(): Boolean {
        val current = Calendar.getInstance()
        val target = Calendar.getInstance()
        val currentTimeInMill = System.currentTimeMillis()
        target.time = Date(this)
        current.time = Date(currentTimeInMill)

        return when {
            current.get(Calendar.YEAR) > target.get(Calendar.YEAR) -> false
            current.get(Calendar.MONTH) > target.get(Calendar.MONTH) -> false
            current.get(Calendar.DAY_OF_MONTH) > target.get(Calendar.DAY_OF_MONTH) -> false
            else -> true
        }
    }

    private fun Long.isToday(): Boolean {
        val current = Calendar.getInstance()
        val target = Calendar.getInstance()
        val currentTimeInMill = System.currentTimeMillis()
        target.time = Date(this * 1000)
        current.time = Date(currentTimeInMill)

        val currentYear = current.get(Calendar.YEAR)
        val targetYear = target.get(Calendar.YEAR)
        val currentMonth = current.get(Calendar.MONTH)
        val targetMonth = target.get(Calendar.MONTH)
        val currentDayOfMonth = current.get(Calendar.DAY_OF_MONTH)
        val targetDayOfMonth = target.get(Calendar.DAY_OF_MONTH)

        return currentYear == targetYear
                    && currentMonth == targetMonth
                    && currentDayOfMonth == targetDayOfMonth
    }

    private val days =
        listOf("Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday", "Monday")

    private fun Long.toDayOfTheWeek(): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date(this)
        return days[calendar.get(Calendar.DAY_OF_WEEK)]
    }

    internal fun List<RoomWeatherEntity>.toDomainModel(): List<Weather> = map { entity ->
        Weather(
            id = entity.id,
            date = entity.date,
            temp = entity.temp,
            feelsLike = entity.feelsLike,
            minTemp = entity.minTemp,
            maxTemp = entity.maxTemp,
            pressure = entity.pressure,
            humidity = entity.humidity,
            main = entity.main,
            description = entity.description,
            icon = entity.icon,
            visibility = entity.visibility,
            formattedDateTime = entity.formattedDateTime,
            isToday = entity.date.isToday(),
            dayOfTheWeek = entity.date.toDayOfTheWeek()
        )
    }

    internal fun Weather.toRoomEntity(): RoomWeatherEntity =
        RoomWeatherEntity(
            id = id,
            date = date,
            temp = temp,
            feelsLike = feelsLike,
            minTemp = minTemp,
            maxTemp = maxTemp,
            pressure = pressure,
            humidity = humidity,
            main = main,
            description = description,
            icon = icon,
            visibility = visibility,
            formattedDateTime = formattedDateTime
        )
}
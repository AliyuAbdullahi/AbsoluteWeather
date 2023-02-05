package com.lek.data.repository

import com.lek.data.api.model.WeatherDataContent
import com.lek.data.api.model.WeatherResponse
import com.lek.data.room.RoomWeatherEntity
import com.lek.domain.model.DayOfTheWeek
import com.lek.domain.model.EPOCH_TIME_TO_CURRENT_DATE_MULTIPLIER
import com.lek.domain.model.Weather
import com.lek.domain.model.WeatherResult
import com.lek.domain.model.WeatherResult.Failure
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import retrofit2.Response

object ModelMapper {

    fun mapToResult(result: Response<WeatherResponse>): WeatherResult =
        if (result.isSuccessful.not()) {
            Failure(result.errorBody().toString(), listOf())
        } else {
            result.body()?.let { weatherResponse ->
                WeatherResult.Success(
                    weatherResponse.result.toDomainModel()
                        .map { it.copy(city = weatherResponse.city.name) })
            } ?: Failure("Unknown network failure", listOf())
        }

    private fun List<WeatherDataContent>.toDomainModel(): List<Weather> = map { dataList ->
        val (date, temperature, weatherData, pressure, humidity, windSpeed, feelsLike) = dataList
        val currentWeather =
            weatherData.firstOrNull() ?: throw IllegalArgumentException("Weather object is empty")
        Weather(
            id = currentWeather.id,
            date = date,
            minTemp = temperature.min,
            maxTemp = temperature.max,
            morningTemp = temperature.morning,
            dayTemp = temperature.day,
            eveningTemp = temperature.evening,
            nightTemp = temperature.night,
            morningFeelsLike = feelsLike.morning,
            dayFeelsLike = feelsLike.day,
            eveningFeelsLike = feelsLike.evening,
            nightFeelsLike = feelsLike.night,
            pressure = pressure,
            humidity = humidity,
            main = currentWeather.main,
            description = currentWeather.description,
            icon = currentWeather.icon,
            isToday = date.isToday(),
            city = "",
            windSpeed = windSpeed,
            dayOfTheWeek = date.toDayOfTheWeek()
        )
    }

    fun Weather.isTomorrow(): Boolean {
        val current = Calendar.getInstance()
        val target = Calendar.getInstance()
        val currentTimeInMill = TimeHelper.getNow()
        target.time = Date(this.date * EPOCH_TIME_TO_CURRENT_DATE_MULTIPLIER)
        current.time = Date(currentTimeInMill)

        val currentYear = current.get(Calendar.YEAR)
        val targetYear = target.get(Calendar.YEAR)
        val currentMonth = current.get(Calendar.MONTH)
        val targetMonth = target.get(Calendar.MONTH)
        val currentDayOfMonth = current.get(Calendar.DAY_OF_MONTH)
        val targetDayOfMonth = target.get(Calendar.DAY_OF_MONTH)

        return currentYear == targetYear
                && currentMonth == targetMonth
                && (currentDayOfMonth + 1) == targetDayOfMonth
    }

    internal fun Long.isFromToday(): Boolean {
        val current = Calendar.getInstance()
        val target = Calendar.getInstance()
        val currentTimeInMill = TimeHelper.getNow()
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
        val currentTimeInMill = TimeHelper.getNow()
        target.time = Date(this * EPOCH_TIME_TO_CURRENT_DATE_MULTIPLIER)
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

    private fun Long.toDayOfTheWeek(): DayOfTheWeek {
        val calendar = GregorianCalendar()
        calendar.time = Date(this * EPOCH_TIME_TO_CURRENT_DATE_MULTIPLIER)
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> DayOfTheWeek.Mon
            Calendar.TUESDAY -> DayOfTheWeek.Tue
            Calendar.WEDNESDAY -> DayOfTheWeek.Wed
            Calendar.THURSDAY -> DayOfTheWeek.Thu
            Calendar.FRIDAY -> DayOfTheWeek.Fri
            Calendar.SATURDAY -> DayOfTheWeek.Sat
            Calendar.SUNDAY -> DayOfTheWeek.Sun
            else -> throw IllegalArgumentException("Invalid date $this")
        }
    }

    internal fun List<RoomWeatherEntity>.toDomainModel(): List<Weather> = map { entity ->
        Weather(
            id = entity.id,
            date = entity.date,
            minTemp = entity.minTemp,
            maxTemp = entity.maxTemp,
            morningTemp = entity.morningTemp,
            dayTemp = entity.dayTemp,
            eveningTemp = entity.eveningTemp,
            nightTemp = entity.nightTemp,
            morningFeelsLike = entity.morningFeelsLike,
            dayFeelsLike = entity.dayFeelsLike,
            eveningFeelsLike = entity.eveningFeelsLike,
            nightFeelsLike = entity.nightFeelsLike,
            pressure = entity.pressure,
            humidity = entity.humidity,
            main = entity.main,
            description = entity.description,
            icon = entity.icon,
            isToday = entity.date.isToday(),
            city = entity.city,
            windSpeed = entity.windSpeed,
            dayOfTheWeek = entity.date.toDayOfTheWeek()
        )
    }

    internal fun Weather.toRoomEntity(): RoomWeatherEntity =
        RoomWeatherEntity(
            id = id,
            date = date,
            minTemp = minTemp,
            maxTemp = maxTemp,
            morningTemp = morningTemp,
            dayTemp = dayTemp,
            eveningTemp = eveningTemp,
            nightTemp = nightTemp,
            morningFeelsLike = morningFeelsLike,
            dayFeelsLike = dayFeelsLike,
            eveningFeelsLike = eveningFeelsLike,
            nightFeelsLike = nightFeelsLike,
            pressure = pressure,
            humidity = humidity,
            main = main,
            description = description,
            icon = icon,
            city = city,
            windSpeed = windSpeed
        )
}

object TimeHelper {
    fun getNow() = System.currentTimeMillis()
}
package com.lek.data.repository

import com.lek.data.api.WeatherService
import com.lek.data.repository.ModelMapper.isFromToday
import com.lek.data.repository.ModelMapper.mapToResult
import com.lek.data.repository.ModelMapper.toDomainModel
import com.lek.data.repository.ModelMapper.toRoomEntity
import com.lek.data.room.WeatherDao
import com.lek.domain.IWeatherRepository
import com.lek.domain.model.WeatherResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

private const val appId = "648a3aac37935e5b45e09727df728ac2" // grab app id from open weather api

class WeatherRepository(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao
) : IWeatherRepository {

    private val mutableInfo: MutableSharedFlow<WeatherResult> = MutableSharedFlow(replay = 1)

    override val weatherResultStream: Flow<WeatherResult>
        get() = mutableInfo

    override suspend fun fetchWeather(city: String, units: String): WeatherResult {
        val response = weatherService.getWeatherFor(city = city, units = units, appId = appId)
        return mapToResult(response).also { weatherResult ->
            when (weatherResult) {
                is WeatherResult.Failure -> {
                    val currentData =
                        weatherDao.getWeather().toDomainModel().filter { it.date.isFromToday() }
                    mutableInfo.emit(weatherResult.copy(backUp = currentData))
                }
                is WeatherResult.Success -> {
                    weatherResult.data.forEach {
                        weatherDao.add(it.toRoomEntity())
                    }
                    mutableInfo.emit(weatherResult)
                }
            }
        }
    }
}
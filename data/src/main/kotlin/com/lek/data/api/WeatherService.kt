package com.lek.data.api

import com.lek.data.api.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET(Routes.GET_WEATHER_FORECAST)
    suspend fun getWeatherFor(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("cnt") count: Int,
        @Query("appid") appId: String
    ): Response<WeatherResponse>
}
package com.lek.domain.model

sealed interface WeatherResult {
    data class Success(val data: List<Weather>) : WeatherResult
    data class Failure(val reason: String, val backUp: List<Weather>) : WeatherResult
}
package com.lek.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class RoomWeatherEntity(
    @PrimaryKey val id: Int,
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
    val city: String,
    val windSpeed: Double
)
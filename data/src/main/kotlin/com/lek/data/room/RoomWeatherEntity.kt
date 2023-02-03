package com.lek.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class RoomWeatherEntity(
    @PrimaryKey val id: Int,
    val date: Long,
    val temp: Double,
    @ColumnInfo(name = "feels_like") val feelsLike: Double,
    @ColumnInfo(name = "min_temperature") val minTemp: Double,
    @ColumnInfo(name = "max_temperature") val maxTemp: Double,
    val pressure: Long,
    val humidity: Int,
    val main: String,
    val description: String,
    val icon: String,
    val visibility: Long,
    @ColumnInfo(name = "formatted_date_time") val formattedDateTime: String
)
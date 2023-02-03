package com.lek.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather")
    suspend fun getWeather(): List<RoomWeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(weather: RoomWeatherEntity)

    @Query("DELETE FROM weather")
    suspend fun deleteAll()
}
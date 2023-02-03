package com.lek.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RoomWeatherEntity::class],
    version = WeatherDbProperties.VERSION,
    exportSchema = true
)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
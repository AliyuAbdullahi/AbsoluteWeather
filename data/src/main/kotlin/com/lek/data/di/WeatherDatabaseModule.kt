package com.lek.data.di

import android.content.Context
import androidx.room.Room
import com.lek.data.room.WeatherDao
import com.lek.data.room.WeatherDatabase
import com.lek.data.room.WeatherDbProperties
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class WeatherDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): WeatherDatabase {
        return Room.databaseBuilder(
            appContext,
            WeatherDatabase::class.java,
            WeatherDbProperties.NAME
        ).build()
    }

    @Provides
    fun provideWeatherDao(db: WeatherDatabase): WeatherDao = db.weatherDao()
}
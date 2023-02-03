package com.lek.data.di

import com.lek.data.api.WeatherService
import com.lek.data.repository.WeatherRepository
import com.lek.data.room.WeatherDao
import com.lek.domain.IWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        weatherService: WeatherService,
        weatherDao: WeatherDao
    ): IWeatherRepository = WeatherRepository(weatherService, weatherDao)
}
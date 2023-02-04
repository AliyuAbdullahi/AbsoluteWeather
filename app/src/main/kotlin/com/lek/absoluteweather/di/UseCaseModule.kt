package com.lek.absoluteweather.di

import com.lek.domain.IWeatherRepository
import com.lek.domain.usecase.FetchWeatherUseCase
import com.lek.domain.usecase.GetWeatherResultStreamUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideFetchWeatherUseCase(
        repository: IWeatherRepository
    ): FetchWeatherUseCase = FetchWeatherUseCase(repository)

    @Provides
    fun provideGetWeatherResultStreamUseCase(
        repository: IWeatherRepository
    ): GetWeatherResultStreamUseCase = GetWeatherResultStreamUseCase(repository)
}
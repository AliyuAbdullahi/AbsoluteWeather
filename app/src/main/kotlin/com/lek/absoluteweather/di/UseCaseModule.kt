package com.lek.absoluteweather.di

import com.lek.domain.IWeatherRepository
import com.lek.domain.usecase.FetchWeatherUseCase
import com.lek.domain.usecase.GetSelectedWeatherUseCase
import com.lek.domain.usecase.GetWeatherResultStreamUseCase
import com.lek.domain.usecase.SetSelectedWeatherUseCase
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

    @Provides
    fun provideSetSelectedWeatherUseCase(
        repository: IWeatherRepository
    ): SetSelectedWeatherUseCase = SetSelectedWeatherUseCase(repository)

    @Provides
    fun provideGetSelectedWeatherUseCase(
        repository: IWeatherRepository
    ): GetSelectedWeatherUseCase = GetSelectedWeatherUseCase(repository)
}
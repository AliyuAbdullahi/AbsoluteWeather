package com.lek.absoluteweather.di

import com.lek.domain.IWeatherRepository
import com.lek.domain.usecase.FetchWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class UseCaseModule {

    @Provides
    fun provideFetchWeatherUseCase(
        repository: IWeatherRepository
    ): FetchWeatherUseCase = FetchWeatherUseCase(repository)
}
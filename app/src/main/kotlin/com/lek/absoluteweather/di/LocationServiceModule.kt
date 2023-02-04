package com.lek.absoluteweather.di

import android.content.Context
import com.lek.absoluteweather.systemservices.location.UserLocationService
import com.lek.absoluteweather.systemservices.location.ILocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationServiceModule {

    @Provides
    @Singleton
    fun provideUserLocationService(@ApplicationContext context: Context): ILocationService =
        UserLocationService(context)
}
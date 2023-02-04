package com.lek.absoluteweather.di

import com.lek.absoluteweather.systemservices.permission.IPermissionService
import com.lek.absoluteweather.systemservices.permission.PermissionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PermissionsServiceModule {

    @Provides
    fun providePermissionService(): IPermissionService = PermissionService()
}
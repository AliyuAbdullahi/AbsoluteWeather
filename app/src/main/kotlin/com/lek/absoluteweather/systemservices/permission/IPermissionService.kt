package com.lek.absoluteweather.systemservices.permission

import androidx.activity.ComponentActivity
import kotlinx.coroutines.flow.Flow


interface IPermissionService {
    val locationPermissionGranted: Flow<Boolean>
    val notificationPermissionGranted: Flow<Boolean>
    suspend fun initIn(activity: ComponentActivity)
    fun requestLocationPermission()
    fun requestNotificationPermission()
}
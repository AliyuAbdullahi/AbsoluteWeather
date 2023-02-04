package com.lek.absoluteweather.systemservices.permission

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class PermissionService : IPermissionService {

    private val locationPermissionEnabled: MutableSharedFlow<Boolean> =
        MutableSharedFlow(replay = 1)
    private val notificationPermissionEnabled: MutableSharedFlow<Boolean> =
        MutableSharedFlow(replay = 1)

    override val locationPermissionGranted: Flow<Boolean>
        get() = locationPermissionEnabled
    override val notificationPermissionGranted: Flow<Boolean>
        get() = notificationPermissionEnabled

    private lateinit var locationPermissionRequest: ActivityResultLauncher<String>
    private lateinit var notificationPermissionRequest: ActivityResultLauncher<String>

    override suspend fun initIn(activity: ComponentActivity) {
        var locationEnabled = false
        var areNotificationsEnabled = false
        if (
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationEnabled = true
        }

        val notificationManager: NotificationManager =
            activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (notificationManager.areNotificationsEnabled()) {
            areNotificationsEnabled = true
        }

        locationPermissionEnabled.emit(locationEnabled)
        notificationPermissionEnabled.emit(areNotificationsEnabled)

        locationPermissionRequest =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                locationPermissionEnabled.tryEmit(granted)
            }

        notificationPermissionRequest =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                notificationPermissionEnabled.tryEmit(granted)
            }
    }

    override fun requestLocationPermission() {
        locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    override fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= 33) {
            notificationPermissionRequest.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
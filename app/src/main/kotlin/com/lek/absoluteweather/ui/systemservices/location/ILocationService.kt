package com.lek.absoluteweather.ui.weatherlist.systemservices.location

import androidx.activity.ComponentActivity
import com.lek.absoluteweather.ui.systemservices.location.model.UserLocation
import kotlinx.coroutines.flow.Flow

interface ILocationService {
    val locationStatus: Flow<UserLocation>
    suspend fun fetchLastLocation()
}
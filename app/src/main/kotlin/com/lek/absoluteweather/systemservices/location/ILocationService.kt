package com.lek.absoluteweather.systemservices.location

import com.lek.absoluteweather.systemservices.location.model.UserLocation
import kotlinx.coroutines.flow.Flow

interface ILocationService {
    val locationStatus: Flow<UserLocation>
    suspend fun fetchLastLocation()
}
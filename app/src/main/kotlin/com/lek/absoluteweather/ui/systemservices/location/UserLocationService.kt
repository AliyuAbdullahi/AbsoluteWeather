package com.lek.absoluteweather.ui.systemservices.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.lek.absoluteweather.ui.systemservices.location.model.LocationStatus
import com.lek.absoluteweather.ui.systemservices.location.model.UserLocation
import com.lek.absoluteweather.ui.weatherlist.systemservices.location.ILocationService
import java.util.Locale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class UserLocationService(private val context: Context) : ILocationService {

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private var userLocation = UserLocation.EMPTY
    private val mutableLocationStatus: MutableSharedFlow<UserLocation> =
        MutableSharedFlow(replay = 1)

    override val locationStatus: Flow<UserLocation>
        get() = mutableLocationStatus

    init {
        val availability = GoogleApiAvailability.getInstance()
        if (availability.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS) {
            userLocation = userLocation.copy(status = LocationStatus.REQUIRES_PLAY_SERVICE)
            mutableLocationStatus.tryEmit(userLocation)
        }
    }

    @SuppressLint("MissingPermission")
    override suspend fun fetchLastLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let { currentLocation ->
                    val lat = currentLocation.latitude
                    val lng = currentLocation.longitude
                    val maxCount = 1
                    val geocoder = Geocoder(context, Locale.getDefault())
                    if (Build.VERSION.SDK_INT >= 33) {
                        geocoder.getFromLocation(
                            lat,
                            lng,
                            maxCount
                        ) { addresses ->
                            publishUserLocation(addresses)
                        }
                    } else {
                        geocoder.getFromLocation(lat, lng, 1)?.let { addresses ->
                            publishUserLocation(addresses)
                        }
                    }
                } ?: kotlin.run {
                    userLocation = userLocation.copy(status = LocationStatus.FAILED)
                    mutableLocationStatus.tryEmit(userLocation)
                }
            }
    }

    private fun publishUserLocation(addresses: List<Address>) {
        if (addresses.isNotEmpty()) {
            userLocation = UserLocation(
                addresses[0].locality,
                status = LocationStatus.SUCCESS
            )
            mutableLocationStatus.tryEmit(userLocation)
        }
    }
}
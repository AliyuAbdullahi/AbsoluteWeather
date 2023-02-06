package com.lek.absoluteweather.ui.weatherlist

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lek.absoluteweather.systemservices.location.ILocationService
import com.lek.absoluteweather.systemservices.location.WeatherRequestWorkManager
import com.lek.absoluteweather.systemservices.permission.IPermissionService
import com.lek.absoluteweather.ui.model.ViewEvent
import com.lek.absoluteweather.ui.model.WeatherListState
import com.lek.domain.model.WeatherResult
import com.lek.domain.usecase.GetWeatherResultStreamUseCase
import com.lek.domain.usecase.SetSelectedWeatherUseCase
import com.lek.domain.usecase.invoke
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val permissionService: IPermissionService,
    private val locationService: ILocationService,
    private val weatherRequestWorkManager: WeatherRequestWorkManager,
    private val getWeatherResultStreamUseCase: GetWeatherResultStreamUseCase,
    private val setSelectedWeatherUseCase: SetSelectedWeatherUseCase
) : ViewModel() {

    private val mutableState: MutableSharedFlow<WeatherListState> = MutableSharedFlow(replay = 1)
    val state: Flow<WeatherListState> = mutableState
    private var weatherListState = WeatherListState.EMPTY

    suspend fun init(activity: ComponentActivity) {
        startObservingLocationPermissionChange()
        startObservingNotificationPermission()
        permissionService.initIn(activity)
    }

    private fun startListeningForWeatherUpdates() {
        viewModelScope.launch {
            getWeatherResultStreamUseCase().distinctUntilChanged().collect { result ->
                if (result is WeatherResult.Success) {
                    updateState { copy(isLoading = false, weatherList = result.data) }
                    mutableState.emit(weatherListState)
                }
            }
        }
    }

    private fun startObservingNotificationPermission() {
        viewModelScope.launch {
            permissionService.notificationPermissionGranted.collect { isGranted ->
                updateState { copy(isNotificationPermissionGranted = isGranted) }
                mutableState.emit(weatherListState)
            }
        }
    }

    private fun startObservingLocationUpdate() {
        viewModelScope.launch {
            locationService.locationStatus.collect { userLocation ->
                if (userLocation.isValid) {
                    weatherRequestWorkManager.publishLocation(userLocation.city)
                }
            }
        }
    }

    private fun requestWeatherForLocation() {
        startObservingLocationUpdate()
        startListeningForWeatherUpdates()
        viewModelScope.launch {
            locationService.fetchLastLocation()
        }
    }

    private fun startObservingLocationPermissionChange() {
        viewModelScope.launch {
            permissionService.locationPermissionGranted.collect { isGranted ->
                if (isGranted) {
                    requestWeatherForLocation()
                }
                updateState { copy(isLocationPermissionGranted = isGranted, isLoading = false) }
                mutableState.emit(weatherListState)
            }
        }
    }

    private fun requestLocationPermission() = permissionService.requestLocationPermission()

    private fun requestNotificationPermission() = permissionService.requestNotificationPermission()

    private fun updateState(reducer: WeatherListState.() -> WeatherListState) {
        weatherListState = weatherListState.reducer()
    }

    fun onEvent(viewEvent: ViewEvent) {
        when (viewEvent) {
            ViewEvent.RefreshList -> { /* TODO start worker */
            }
            ViewEvent.StartDetail -> { /* TODO Open Details Screen */
            }
            ViewEvent.UserDenyNotification -> {
                updateState { copy(alreadyAskedForNotification = true) }
                mutableState.tryEmit(weatherListState)
            }
            ViewEvent.RequestLocationPermission -> requestLocationPermission()
            ViewEvent.RequestNotificationPermission -> requestNotificationPermission()
            is ViewEvent.WeatherSelected -> setSelectedWeatherUseCase(viewEvent.data)
        }
    }
}
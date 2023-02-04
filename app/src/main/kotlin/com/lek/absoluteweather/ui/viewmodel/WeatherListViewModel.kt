package com.lek.absoluteweather.ui.viewmodel

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lek.absoluteweather.ui.model.ViewEvent
import com.lek.absoluteweather.ui.model.WeatherListState
import com.lek.absoluteweather.ui.systemservices.permission.IPermissionService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val permissionService: IPermissionService
) : ViewModel() {

    private val mutableState: MutableSharedFlow<WeatherListState> = MutableSharedFlow(replay = 1)
    val state: Flow<WeatherListState> = mutableState
    private var weatherListState = WeatherListState.EMPTY

    suspend fun init(activity: ComponentActivity) {
        startObservingLocationPermissionChange()
        startObservingNotificationPermission()
        permissionService.initIn(activity)
    }

    private fun startObservingNotificationPermission() {
        viewModelScope.launch {
            permissionService.notificationPermissionGranted.collect { isGranted ->
                updateState { copy(isNotificationPermissionGranted = isGranted) }
                mutableState.emit(weatherListState)
            }
        }
    }

    private fun startObservingLocationPermissionChange() {
        viewModelScope.launch {
            permissionService.locationPermissionGranted.collect { isGranted ->
                if (isGranted) {
                    // TODO Start Worker
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
            ViewEvent.RefreshList -> { /* TODO start worker */ }
            ViewEvent.StartDetail -> { /* TODO Open Details Screen */ }
            ViewEvent.UserDenyNotification -> {
                updateState { copy(alreadyAskedForNotification = true, isLoading = true) }
            }
            ViewEvent.RequestLocationPermission -> requestLocationPermission()
            ViewEvent.RequestNotificationPermission -> requestNotificationPermission()
        }
    }
}
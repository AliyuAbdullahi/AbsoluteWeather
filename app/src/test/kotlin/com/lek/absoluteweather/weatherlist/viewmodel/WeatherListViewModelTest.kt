package com.lek.absoluteweather.weatherlist.viewmodel

import androidx.activity.ComponentActivity
import app.cash.turbine.test
import com.lek.absoluteweather.systemservices.location.ILocationService
import com.lek.absoluteweather.systemservices.location.WeatherRequestWorkManager
import com.lek.absoluteweather.systemservices.location.model.LocationStatus
import com.lek.absoluteweather.systemservices.location.model.UserLocation
import com.lek.absoluteweather.systemservices.permission.IPermissionService
import com.lek.absoluteweather.ui.model.ViewEvent
import com.lek.absoluteweather.ui.weatherlist.WeatherListViewModel
import com.lek.domain.model.Weather
import com.lek.domain.usecase.GetWeatherResultStreamUseCase
import com.lek.domain.usecase.SetSelectedWeatherUseCase
import com.lek.domain.usecase.invoke
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class WeatherListViewModelTest {

    private val permissionService: IPermissionService = mockk(relaxed = true)
    private val locationService: ILocationService = mockk(relaxed = true)
    private val weatherRequestWorkManager: WeatherRequestWorkManager = mockk(relaxed = true)
    private val getWeatherResultStreamUseCase: GetWeatherResultStreamUseCase = mockk(relaxed = true)
    private val setSelectedWeatherUseCase: SetSelectedWeatherUseCase = mockk(relaxed = true)
    private val viewModel = WeatherListViewModel(
        permissionService,
        locationService,
        weatherRequestWorkManager,
        getWeatherResultStreamUseCase,
        setSelectedWeatherUseCase
    )
    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = UnconfinedTestDispatcher(testScheduler)

    @Test
    fun `WHEN location permission is granted - view model updates state`() = runTest {
        setupPermissionsMocks()
        every { locationService.locationStatus }.returns(flowOf(UserLocation(city = "Hamburg", LocationStatus.SUCCESS)))
        coEvery { getWeatherResultStreamUseCase() }.coAnswers { mockk() }
        val activity: ComponentActivity = mockk()

        viewModel.state.test {
            viewModel.init(activity)
            assertTrue(awaitItem().isLocationPermissionGranted)
            expectMostRecentItem()
        }
        coVerify { locationService.fetchLastLocation() }
        coVerify { getWeatherResultStreamUseCase() }
    }

    @Test
    fun `WHEN notification permission is granted - view model updates state`() = runTest {
        setupPermissionsMocks()
        val activity: ComponentActivity = mockk()

        viewModel.state.test {
            viewModel.init(activity)
            assertTrue(awaitItem().isNotificationPermissionGranted)
            expectMostRecentItem()
        }
    }

    @Test
    fun `WHEN view#requestLocationPermission is invoked - permission service request location permission`() =
        runTest {
            viewModel.onEvent(ViewEvent.RequestLocationPermission)
            verify { permissionService.requestLocationPermission() }
        }

    @Test
    fun `WHEN view#requestNotificationPermission is invoked - permission service request notification permission`() =
        runTest {
            viewModel.onEvent(ViewEvent.RequestNotificationPermission)
            verify { permissionService.requestNotificationPermission() }
        }

    @Test
    fun `WHEN weather item is selected - selection is saved`() {
        val weather: Weather = mockk()
        viewModel.onEvent(ViewEvent.WeatherSelected(weather))
        verify { setSelectedWeatherUseCase(weather = weather) }
    }

    private fun setupPermissionsMocks() {
        Dispatchers.setMain(testDispatcher)
        coEvery { permissionService.initIn(any()) }.coAnswers { }
        every { permissionService.notificationPermissionGranted }.returns(flowOf(true))
        every { permissionService.locationPermissionGranted }.returns(flowOf(true))
    }
}
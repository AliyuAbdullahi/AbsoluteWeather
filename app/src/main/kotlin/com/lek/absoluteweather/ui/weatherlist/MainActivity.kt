package com.lek.absoluteweather.ui.weatherlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.lek.absoluteweather.ui.details.WeatherDetailsActivity
import com.lek.absoluteweather.ui.model.ViewEvent
import com.lek.absoluteweather.ui.model.WeatherListState
import com.lek.absoluteweather.ui.theme.AbsoluteWeatherTheme
import com.lek.absoluteweather.ui.weatherlist.component.WeatherListScreen
import com.lek.domain.model.Weather
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.init(this@MainActivity)
        }

        setContent {
            AbsoluteWeatherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val state = viewModel.state.collectAsState(initial = WeatherListState.EMPTY).value
                    WeatherListScreen(
                        state = state,
                        onLocationEnableClicked = { viewModel.onEvent(ViewEvent.RequestLocationPermission) },
                        onLocationDenyClicked = { this.finish() },
                        onNotificationEnableClicked = { viewModel.onEvent(ViewEvent.RequestNotificationPermission) },
                        onNotificationDenyClicked = { viewModel.onEvent(ViewEvent.UserDenyNotification) },
                        onShowDetailsClicked = { openDetailScreen(it) }
                    )
                }
            }
        }
    }

    private fun openDetailScreen(weather: Weather) {
        viewModel.onEvent(ViewEvent.WeatherSelected(weather))
        WeatherDetailsActivity.startFrom(this)
    }
}
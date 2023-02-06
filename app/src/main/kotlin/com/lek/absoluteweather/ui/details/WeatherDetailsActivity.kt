package com.lek.absoluteweather.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.lek.absoluteweather.ui.details.component.WeatherDetailsScreen
import com.lek.absoluteweather.ui.theme.AbsoluteWeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherDetailsActivity : ComponentActivity() {

    private val viewModel: WeatherDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val weather = remember { mutableStateOf(viewModel.weather) }
            AbsoluteWeatherTheme {
                weather.value?.let {
                    WeatherDetailsScreen(
                        weather = it,
                        onBackPressed = { finish() }
                    )
                }
            }
        }
    }

    companion object {
        fun startFrom(context: Context) {
            context.startActivity(Intent(context, WeatherDetailsActivity::class.java))
        }
    }
}
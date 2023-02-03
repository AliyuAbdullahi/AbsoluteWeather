package com.lek.absoluteweather

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.lek.absoluteweather.ui.theme.AbsoluteWeatherTheme
import com.lek.data.ConsoleLogger
import com.lek.domain.model.WeatherRequest
import com.lek.domain.model.WeatherResult
import com.lek.domain.usecase.FetchWeatherUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var useCase: FetchWeatherUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val result = useCase(WeatherRequest("hamburg"))
            if (result is WeatherResult.Success) {
                Log.v(">>> Result ", result.data.toString())
            } else {
                Log.v(">>> Result Failed ", result.toString())
            }
        }
        setContent {
            AbsoluteWeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting(name = ConsoleLogger().log())
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AbsoluteWeatherTheme {
        Greeting("Android")
    }
}
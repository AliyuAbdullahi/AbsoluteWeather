package com.lek.absoluteweather.ui.weatherlist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.absoluteweather.R
import com.lek.absoluteweather.ui.component.ImageComponent
import com.lek.absoluteweather.ui.theme.AbsoluteWeatherTheme
import com.lek.absoluteweather.ui.theme.Blue500
import com.lek.absoluteweather.ui.theme.Blue900
import com.lek.absoluteweather.ui.theme.Gey200
import com.lek.absoluteweather.ui.theme.Grey700
import com.lek.absoluteweather.ui.theme.PaleBlue200
import com.lek.absoluteweather.ui.theme.PaleBlue600
import com.lek.domain.model.WeatherStatus

@Composable
fun WeatherToday(
    iconUrl: Any,
    currentTemp: Double,
    currentFeelsLike: Double,
    main: String,
    formattedDate: String,
    city: String,
    onClick: () -> Unit = {},
    weatherStatus: WeatherStatus
) {

    val cornerRadius = with(LocalDensity.current) { 20.dp.toPx() }
    val interactionSource = remember { MutableInteractionSource() }
    val rippleIndication = rememberRipple()
    Box(modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp)) {
        val colors = when (weatherStatus) {
            WeatherStatus.RAIN -> listOf(PaleBlue200, PaleBlue600)
            WeatherStatus.SNOW -> listOf(Gey200, Grey700)
            WeatherStatus.NORMAL -> listOf(Blue500, Blue900)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(cornerRadius))
                .clickable(
                    interactionSource = interactionSource,
                    indication = rippleIndication,
                    onClick = onClick
                )
                .background(brush = Brush.verticalGradient(colors = colors))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when (weatherStatus) {
                    WeatherStatus.RAIN -> R.drawable.rain
                    WeatherStatus.SNOW -> R.drawable.snow
                    WeatherStatus.NORMAL -> null
                }?.let {
                    ImageComponent(
                        source = it,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(100.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            ImageComponent(
                                source = iconUrl,
                                modifier = Modifier.size(80.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Column {
                            Text(
                                text = stringResource(
                                    id = R.string.temperature_celsius,
                                    currentTemp
                                ),
                                style = MaterialTheme.typography.h4,
                                color = MaterialTheme.colors.onPrimary
                            )
                            Text(
                                text = stringResource(id = R.string.feels_like, currentFeelsLike),
                                style = MaterialTheme.typography.subtitle1,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = main,
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onPrimary
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.today, formattedDate),
                                style = MaterialTheme.typography.subtitle1,
                                color = MaterialTheme.colors.onPrimary
                            )
                            Text(
                                text = city,
                                style = MaterialTheme.typography.h5,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun WeatherTodayPreview() {
    AbsoluteWeatherTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            WeatherToday(
                iconUrl = R.drawable.ic_launcher_foreground,
                currentTemp = 10.1,
                currentFeelsLike = 12.0,
                main = "Cloudy",
                formattedDate = "Sun, 5 Feb",
                city = "Hamburg",
                weatherStatus = WeatherStatus.RAIN
            )
        }
    }
}
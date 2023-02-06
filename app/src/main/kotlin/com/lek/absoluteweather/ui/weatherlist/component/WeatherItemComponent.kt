package com.lek.absoluteweather.ui.weatherlist.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.absoluteweather.R
import com.lek.absoluteweather.ui.component.ImageComponent
import com.lek.absoluteweather.ui.theme.AbsoluteWeatherTheme

@Composable
fun WeatherItem(
    dayOfTheWeek: String,
    formattedDayMonth: String,
    currentTemp: String,
    iconUrl: Any,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val rippleIndication = rememberRipple()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = rippleIndication,
                onClick = onClick
            )
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
    ) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1F)) {
                Text(text = dayOfTheWeek, style = MaterialTheme.typography.body1, fontWeight = FontWeight.Bold)
                Text(text = formattedDayMonth, style = MaterialTheme.typography.subtitle1)
            }
            Box(modifier = Modifier.weight(1F)) {
                Text(
                    text = currentTemp,
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.h6
                )
            }
            Box(modifier = Modifier.weight(1F)) {
                ImageComponent(
                    source = iconUrl, modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.CenterEnd)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun WeatherItemPreview() {
    AbsoluteWeatherTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            WeatherItem(
                dayOfTheWeek = "Tomorrow",
                formattedDayMonth = "Feb 6",
                currentTemp = "50°C",
                iconUrl = R.drawable.ic_launcher_foreground
            )
        }
    }
}
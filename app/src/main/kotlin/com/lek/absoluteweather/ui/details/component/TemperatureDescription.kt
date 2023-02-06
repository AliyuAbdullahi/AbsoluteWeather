package com.lek.absoluteweather.ui.details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.absoluteweather.R
import com.lek.absoluteweather.ui.theme.TransparentGrey
import com.lek.absoluteweather.ui.theme.Typography

@Composable
fun TemperatureDescription(
    maxTemp: Double,
    maxTempString: String,
    minTemp: Double,
    minTempString: String,
    morningTemp: String,
    afternoonTemp: String,
    eveningTemp: String,
    nightTemp: String,
    morningFeelsLikeTemp: String,
    afternoonFeelsLikeTemp: String,
    eveningFeelsLikeTemp: String,
    nightFeelsLikeTemp: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(TransparentGrey)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.temperature_label),
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(8.dp))
        TemperatureBar(
            value = maxTemp,
            valueString = maxTempString,
            title = stringResource(id = R.string.max_temp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        TemperatureBar(
            value = minTemp,
            valueString = minTempString,
            title = stringResource(id = R.string.min_temp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row {
            TimeOfTheDayTemp(
                modifier = Modifier.weight(1F),
                timeOfDay = stringResource(id = R.string.morning),
                morningTemp
            )
            TimeOfTheDayTemp(
                modifier = Modifier.weight(1F),
                timeOfDay = stringResource(id = R.string.afternoon),
                afternoonTemp
            )
            TimeOfTheDayTemp(
                modifier = Modifier.weight(1F),
                timeOfDay = stringResource(id = R.string.evening),
                eveningTemp
            )
            TimeOfTheDayTemp(
                modifier = Modifier.weight(1F),
                timeOfDay = stringResource(id = R.string.night),
                nightTemp
            )
        }
        Text(
            text = stringResource(id = R.string.feels_like_label),
            style = MaterialTheme.typography.subtitle1
        )
        Row() {
            TimeOfTheDayTemp(
                modifier = Modifier.weight(1F),
                timeOfDay = stringResource(id = R.string.morning),
                morningFeelsLikeTemp
            )
            TimeOfTheDayTemp(
                modifier = Modifier.weight(1F),
                timeOfDay = stringResource(id = R.string.afternoon),
                afternoonFeelsLikeTemp
            )
            TimeOfTheDayTemp(
                modifier = Modifier.weight(1F),
                timeOfDay = stringResource(id = R.string.evening),
                eveningFeelsLikeTemp
            )
            TimeOfTheDayTemp(
                modifier = Modifier.weight(1F),
                timeOfDay = stringResource(id = R.string.night),
                nightFeelsLikeTemp
            )
        }
    }
}

@Composable
private fun TimeOfTheDayTemp(
    modifier: Modifier,
    timeOfDay: String,
    temperatureText: String
) {
    Column(
        modifier = modifier.padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = timeOfDay, style = Typography.caption, fontWeight = FontWeight.Bold)
        Text(text = temperatureText, style = Typography.subtitle2)
    }
}

@Composable
@Preview(showBackground = true)
private fun TemperatureDescriptionPreview() {
    TemperatureDescription(
        maxTemp = 12.0,
        maxTempString = "12°C",
        minTemp = 9.0,
        minTempString = "6.0°C",
        morningTemp = "8°C",
        afternoonTemp = "12°C",
        eveningTemp = "10°C",
        nightTemp = "9°C",
        morningFeelsLikeTemp = "7°C",
        afternoonFeelsLikeTemp = "11°C",
        eveningFeelsLikeTemp = "10°C",
        nightFeelsLikeTemp = "6°C"
    )
}


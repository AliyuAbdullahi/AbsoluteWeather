package com.lek.absoluteweather.ui.details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lek.absoluteweather.R
import com.lek.absoluteweather.ui.component.ImageComponent
import com.lek.absoluteweather.ui.theme.Blue500
import com.lek.absoluteweather.ui.theme.Blue900
import com.lek.absoluteweather.ui.theme.Grey700
import com.lek.absoluteweather.ui.theme.TransParentBlue
import com.lek.domain.model.TemperatureStatus
import com.lek.domain.model.WeatherStatus

@Composable
fun DetailsTopSection(
    weatherStatus: WeatherStatus,
    temperatureStatus: TemperatureStatus,
    city: String,
    formattedDate: String,
    temperature: String,
    main: String,
    iconUrl: String,
    description: String,
    onBackPressed: () -> Unit = {}
) {
    val height = LocalConfiguration.current.screenHeightDp.toFloat() * 0.4
    val colors = when (weatherStatus) {
        WeatherStatus.RAIN -> listOf(Blue900, TransParentBlue)
        WeatherStatus.SNOW -> listOf(Grey700, TransParentBlue)
        WeatherStatus.NORMAL -> listOf(Blue500, TransParentBlue)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp)
                .background(brush = Brush.verticalGradient(colors = colors))
        )
        when (weatherStatus) {
            WeatherStatus.RAIN -> R.drawable.rain
            WeatherStatus.SNOW -> R.drawable.snow
            WeatherStatus.NORMAL -> null
        }?.let {
            ImageComponent(
                source = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height.dp),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            val interactionSource = remember { MutableInteractionSource() }
            val rippleIndication = rememberRipple()
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = rippleIndication,
                        onClick = onBackPressed
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = city,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onPrimary
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = temperature,
                        style = MaterialTheme.typography.h2,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Text(
                        text = main,
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onPrimary
                    )
                }

                Column {
                    ImageComponent(source = iconUrl, modifier = Modifier.size(80.dp))
                    TemperatureStatusComponent(temperatureStatus)
                }
            }
        }
    }
}

@Composable
private fun TemperatureStatusComponent(temperatureStatus: TemperatureStatus) {
    when (temperatureStatus) {
        TemperatureStatus.HOT -> stringResource(id = R.string.hot)
        TemperatureStatus.COLD -> stringResource(id = R.string.cold)
        TemperatureStatus.NORMAL -> null
    }?.let { tempStatus ->
        Text(
            text = tempStatus,
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            color = Grey700
        )
    }
}
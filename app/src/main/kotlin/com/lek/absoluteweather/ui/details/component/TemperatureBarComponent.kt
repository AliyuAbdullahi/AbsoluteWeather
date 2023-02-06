package com.lek.absoluteweather.ui.details.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.absoluteweather.ui.theme.Blue700
import com.lek.absoluteweather.ui.theme.Gey200
import com.lek.absoluteweather.ui.theme.Pink200

@Composable
fun TemperatureBar(
    value: Double,
    valueString: String,
    title: String
) {
    val fiftyPercentOfScreen = LocalConfiguration.current.screenWidthDp.toFloat() * 0.5
    val actualValue = value * 2
    val started = remember {
        mutableStateOf(false)
    }
    val currentTemp = animateFloatAsState(
        targetValue = if (started.value) actualValue.toFloat() else 0.0.toFloat(),
        animationSpec = tween(500)
    )

    val height by remember {
        derivedStateOf {
            if (currentTemp.value > 0.0) {
                24.dp
            } else {
                0.dp
            }
        }
    }

    val background = listOf(Pink200, Gey200)

    // Section Background
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.weight(2F)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(8F)) {
            Box {
                Box(
                    modifier = Modifier
                        .width(fiftyPercentOfScreen.dp)
                        .height(24.dp)
                        .clip(
                            RoundedCornerShape(
                                topEnd = 8.dp,
                                bottomEnd = 8.dp,
                                topStart = 8.dp,
                                bottomStart = 8.dp
                            )
                        )
                        .background(brush = Brush.verticalGradient(background))
                )
                Box(
                    modifier = Modifier
                        .width(currentTemp.value.dp)
                        .height(height)
                        .clip(
                            RoundedCornerShape(
                                topEnd = 8.dp,
                                bottomEnd = 8.dp,
                                topStart = 8.dp,
                                bottomStart = 8.dp
                            )
                        )
                        .background(Blue700)
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = valueString,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.width(40.dp)
            )
        }
    }
    LaunchedEffect(Unit) {
        started.value = true
    }
}

@Composable
@Preview
fun TemperatureBarPreview() {
    TemperatureBar(
        value = 24.0,
        title = "Max Temp",
        valueString = "24°C"
    )
}
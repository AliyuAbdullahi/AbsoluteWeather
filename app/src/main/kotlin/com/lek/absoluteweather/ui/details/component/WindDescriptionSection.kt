package com.lek.absoluteweather.ui.details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.absoluteweather.R
import com.lek.absoluteweather.ui.component.ImageComponent
import com.lek.absoluteweather.ui.theme.TransParentBlue

@Composable
fun WindDescriptionSection(
    windSpeed: String,
    humidity: String,
    pressure: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TransParentBlue)
            .clip(RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SectionInfo(
            icon = R.drawable.wind,
            title = stringResource(id = R.string.wind),
            data = windSpeed,
            modifier = Modifier.weight(1F)
        )
        SectionInfo(
            icon = R.drawable.water,
            title = stringResource(id = R.string.humidity),
            data = humidity,
            modifier = Modifier.weight(1F)
        )
        SectionInfo(
            icon = R.drawable.pressure,
            title = stringResource(id = R.string.pressure),
            data = pressure,
            modifier = Modifier.weight(1F)
        )
    }
}

@Composable
private fun SectionInfo(modifier: Modifier, icon: Int, title: String, data: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        ImageComponent(
            source = icon,
            modifier = Modifier.size(32.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = data, style = MaterialTheme.typography.subtitle1)
        Text(text = title, style = MaterialTheme.typography.caption)
    }
}

@Composable
@Preview(showBackground = true)
private fun WindDescriptionSectionPreview() {
    WindDescriptionSection(
        windSpeed = "10 m/s",
        humidity = "88%",
        pressure = "100hPa"
    )
}
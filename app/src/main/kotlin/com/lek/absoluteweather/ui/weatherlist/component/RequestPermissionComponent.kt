package com.lek.absoluteweather.ui.weatherlist.component

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.absoluteweather.R

@Composable
fun PermissionComponent(
    isVisible: Boolean,
    @StringRes title: Int,
    @StringRes subtitle: Int,
    onEnableClicked: () -> Unit = {},
    onDenyClicked: () -> Unit = {}
) {
    AnimatedVisibility(visible = isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(16.dp)
        ) {
            BodyItems(title, subtitle, scope = this)
            FooterItems(
                scope = this,
                onEnableClicked = onEnableClicked,
                onDenyClicked = onDenyClicked
            )
        }
    }
}

@Composable
private fun FooterItems(
    scope: BoxScope,
    onEnableClicked: () -> Unit = {},
    onDenyClicked: () -> Unit = {}
) {
    scope.apply {
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                onClick = onDenyClicked
            ) {
                Text(text = stringResource(id = R.string.will_deny_notification_permission))
            }

            Button(onClick = onEnableClicked) {
                Text(text = stringResource(id = R.string.will_accept_notification_permission))
            }
        }
    }
}

@Composable
private fun BodyItems(
    @StringRes title: Int,
    @StringRes subtitle: Int,
    scope: BoxScope
) {
    scope.apply {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(200.dp)
                    .aspectRatio(1.5F),
                painter = painterResource(id = R.drawable.ic_app_icon),
                contentDescription = "app icon"
            )
            Text(
                text = stringResource(id = title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
            Text(
                text = stringResource(id = subtitle),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
private fun RequestLocationPermissionComponentPreview() {
    PermissionComponent(
        isVisible = true,
        title = R.string.location_request_title,
        subtitle = R.string.location_permission_message
    )
}
package com.lek.absoluteweather.ui.component

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

@Composable
fun ImageComponent(
    source: Any,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String = ""
) {
    val context = LocalContext.current
    val imgLoader = ImageLoader.Builder(context).components {
        if (SDK_INT >= 28) {
            add(ImageDecoderDecoder.Factory())
        } else {
            add(GifDecoder.Factory())
        }
    }.build()

    val painter = rememberAsyncImagePainter(model = source, imageLoader = imgLoader)

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}
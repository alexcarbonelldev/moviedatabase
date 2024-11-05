package com.bd.ui.design_system.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun AsyncImageUiComponent(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(imageUrl)
        //    .dispatcher(Dispatcher.IO)
        .memoryCacheKey(imageUrl)
        .diskCacheKey(imageUrl)
//        .placeholder(placeholder)
//        .error(placeholder)
//        .fallback(placeholder)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .crossfade(500)
        .build()
    AsyncImage(
        modifier = modifier,
        model = imageRequest,
        contentDescription = "Image",
        contentScale = ContentScale.Crop,
        placeholder = ColorPainter(Color.Black)
    )
}
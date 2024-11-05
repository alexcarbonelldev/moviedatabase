package com.bd.ui.design_system.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CardItemUiComponent(
    imageUrl: String?,
    rating: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageHeight: Dp = 240.dp,
    imageWidth: Dp? = null
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(8.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
        ) {
            val widthModifier = imageWidth?.let { Modifier.width(it) } ?: Modifier
            val imageModifier = Modifier
                .height(imageHeight)
                .then(widthModifier)

            AsyncImageUiComponent(
                modifier = imageModifier,
                imageUrl = imageUrl,
            )
            RatingUiComponent(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                value = rating,
                size = RatingUiComponentSize.SMALL
            )
        }
    }
}

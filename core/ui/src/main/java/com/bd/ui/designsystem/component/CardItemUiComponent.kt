package com.bd.ui.designsystem.component

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
import com.bd.ui.designsystem.component.rating.RatingUiComponent
import com.bd.ui.designsystem.component.rating.RatingUiComponentSize

@Composable
fun CardItemUiComponent(
    imageUrl: String?,
    rating: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageHeight: Dp = 140.dp,
    imageWidth: Dp = 100.dp
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
            AsyncImageUiComponent(
                modifier = Modifier
                    .height(imageHeight)
                    .width(imageWidth),
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

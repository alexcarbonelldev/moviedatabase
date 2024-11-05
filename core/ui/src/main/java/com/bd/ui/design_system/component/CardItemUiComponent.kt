package com.bd.ui.design_system.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CardItemUiComponent(
    imageUrl: String?,
    rating: Float,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(8.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
        ) {
            AsyncImageUiComponent(
                modifier = Modifier
                    .height(240.dp),
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

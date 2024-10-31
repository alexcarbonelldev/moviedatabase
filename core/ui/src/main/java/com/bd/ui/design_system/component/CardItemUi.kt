package com.bd.ui.design_system.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun CardItemUi(
    imageUrl: String?,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    val cardWidth = 160.dp
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .padding(8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
        ) {
            AsyncImageUi(
                modifier = Modifier.size(cardWidth),
                imageUrl = imageUrl,
            )
            Column(
                modifier = Modifier
                    .width(cardWidth)
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = description,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}

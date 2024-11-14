package com.bd.ui.designsystem.component.rating

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bd.common.extensions.toStringWithDecimals
import com.bd.ui.common.circleLayout

private const val BACKGROUND_ALPHA = 0.6f
private const val BORDER_STROKE_WIDTH = 2

enum class RatingUiComponentSize {
    SMALL,
    MEDIUM
}

@Composable
fun RatingUiComponent(
    value: Float,
    size: RatingUiComponentSize = RatingUiComponentSize.MEDIUM,
    modifier: Modifier = Modifier
) {
    val padding = when (size) {
        RatingUiComponentSize.SMALL -> 8.dp
        RatingUiComponentSize.MEDIUM -> 12.dp
    }
    Box(
        modifier = modifier
            .border(
                BorderStroke(BORDER_STROKE_WIDTH.dp, Color.Black.copy(alpha = BACKGROUND_ALPHA)),
                shape = CircleShape
            )
            .padding(BORDER_STROKE_WIDTH.dp)
            .border(
                BorderStroke(BORDER_STROKE_WIDTH.dp, mapRatingToColor(value)),
                shape = CircleShape
            )
            .background(
                Color.Black.copy(alpha = BACKGROUND_ALPHA),
                CircleShape
            )
            .circleLayout()
            .padding(vertical = padding)
            .wrapContentSize(Alignment.Center)
    ) {

        val style = when (size) {
            RatingUiComponentSize.SMALL -> MaterialTheme.typography.labelSmall
            RatingUiComponentSize.MEDIUM -> MaterialTheme.typography.titleMedium
        }

        val stringValue = when (value) {
            RatingColor.VALUE_10.number.toFloat() -> value.toInt().toString()
            else -> value.toStringWithDecimals()
        }
        Text(
            stringValue,
            style = style,
            color = Color.White,
        )
    }
}

private fun mapRatingToColor(rating: Float): Color {
    return getRatingColorEnumByNumber(rating.toInt())?.color
        ?: RatingColor.VALUE_1.color
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
private fun PreviewSmall() {
    val min = 1
    val max = 10
    FlowRow {
        for (i in min..max) {
            RatingUiComponent(i.toFloat(), RatingUiComponentSize.SMALL)

            if (i != max) {
                Spacer(Modifier.height(8.dp))
                RatingUiComponent(i.toFloat() + 0.5f, RatingUiComponentSize.SMALL)
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
private fun PreviewMedium() {
    val min = 1
    val max = 10
    FlowRow {
        for (i in min..max) {
            RatingUiComponent(i.toFloat(), RatingUiComponentSize.MEDIUM)

            if (i != max) {
                Spacer(Modifier.height(8.dp))
                RatingUiComponent(i.toFloat() + 0.5f, RatingUiComponentSize.MEDIUM)
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

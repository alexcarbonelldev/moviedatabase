package com.bd.ui.design_system.component

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
import com.bd.ui.common.hexToColor

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
            10f -> "10"
            else -> value.toStringWithDecimals()
        }
        Text(
            stringValue,
            style = style,
            color = Color.White,
        )
    }
}

private sealed class RatingColor(val color: Color) {
    data object Value10 : RatingColor("#12AD49".hexToColor())
    data object Value9 : RatingColor("#61B458".hexToColor())
    data object Value8 : RatingColor("#90D22A".hexToColor())
    data object Value7 : RatingColor("#CBE00C".hexToColor())
    data object Value6 : RatingColor("#F7DD02".hexToColor())
    data object Value5 : RatingColor("#F6B304".hexToColor())
    data object Value4 : RatingColor("#F77600".hexToColor())
    data object Value3 : RatingColor("#EF3806".hexToColor())
    data object Value2 : RatingColor("#EA0B0B".hexToColor())
    data object Value1 : RatingColor("#D3012A".hexToColor())
}

private fun mapRatingToColor(rating: Float): Color = when (rating.toInt()) {
    10 -> RatingColor.Value10.color
    9 -> RatingColor.Value9.color
    8 -> RatingColor.Value8.color
    7 -> RatingColor.Value7.color
    6 -> RatingColor.Value6.color
    5 -> RatingColor.Value5.color
    4 -> RatingColor.Value4.color
    3 -> RatingColor.Value3.color
    2 -> RatingColor.Value2.color
    else -> RatingColor.Value1.color
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
private fun PreviewSmall() {
    FlowRow {
        for (i in 1..10) {
            RatingUiComponent(i.toFloat(), RatingUiComponentSize.SMALL)

            if (i != 10) {
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
    FlowRow {
        for (i in 1..10) {
            RatingUiComponent(i.toFloat(), RatingUiComponentSize.MEDIUM)

            if (i != 10) {
                Spacer(Modifier.height(8.dp))
                RatingUiComponent(i.toFloat() + 0.5f, RatingUiComponentSize.MEDIUM)
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}
package com.bd.ui.designsystem

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bd.ui.designsystem.theme.AppTheme

@Preview(
    name = "Light Theme",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Preview(
    name = "Dark Theme",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
annotation class ThemePreview

@ThemePreview
annotation class AppPreview

@Composable
fun PreviewContainer(
    content: @Composable () -> Unit
) {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            content()
        }
    }
}

package com.bd.ui.design_system.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bd.ui.R

@Composable
fun ErrorStateUiComponent(
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.error_screen_message),
                textAlign = TextAlign.Center
            )
            Button(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = onRetryClick
            ) {
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}

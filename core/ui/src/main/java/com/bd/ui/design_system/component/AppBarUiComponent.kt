package com.bd.ui.design_system.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults.MediumAppBarCollapsedHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import com.bd.ui.design_system.Icons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBarUiComponent(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .height(MediumAppBarCollapsedHeight),
        contentAlignment = Alignment.Center,
    ) {
        NavBackIcon(onBackClick = onBackClick)
    }
}

@Composable
private fun NavBackIcon(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = onBackClick
    ) {
        Icon(
            painter = painterResource(id = Icons.NavBack),
            contentDescription = "Back"
        )
    }
}

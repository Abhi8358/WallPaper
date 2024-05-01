package com.vedic.deepinsea.components.indicators

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressBar(
    color: Color = MaterialTheme.colorScheme.background,
    strokeCap: StrokeCap = StrokeCap.Butt,
    strokeWidth: Dp = 6.dp,
    modifier: Modifier = Modifier.wrapContentWidth().wrapContentHeight()
) {
    CircularProgressIndicator(
        color = color,
        strokeWidth = strokeWidth,
        strokeCap = strokeCap,
        modifier = modifier
    )
}

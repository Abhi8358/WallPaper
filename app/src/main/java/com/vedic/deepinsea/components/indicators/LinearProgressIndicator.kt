package com.vedic.deepinsea.components.indicators

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomLinearProgressIndicator(
    modifier: Modifier = Modifier
        .padding(start = 12.dp, end = 8.dp, top = 8.dp, bottom = 12.dp)
        .fillMaxWidth()
        .wrapContentHeight(align = Alignment.CenterVertically)
        .height(7.dp)
) {
    androidx.compose.material3.LinearProgressIndicator(
        modifier = modifier
    )
}
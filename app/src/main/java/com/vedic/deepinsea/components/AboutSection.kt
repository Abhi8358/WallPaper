package com.vedic.deepinsea.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.vedic.deepinsea.R
import com.vedic.deepinsea.components.indicators.CustomLinearProgressIndicator

@Composable
fun AboutSection(aboutWP: String?, alt: String = "") {

    Column(modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {

        Text(
            modifier = Modifier
                .padding(start = 6.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.wallpaper_info),
            fontSize = TextUnit(20f, TextUnitType.Sp),
            color = MaterialTheme.colorScheme.scrim,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )

        if (alt.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .padding(start = 6.dp, end = 6.dp)
                    .fillMaxWidth(),
                text = alt,
                fontSize = TextUnit(18f, TextUnitType.Sp),
                color = MaterialTheme.colorScheme.scrim,
                fontWeight = FontWeight.W500,
            )
        }

        if (!aboutWP.isNullOrEmpty()) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp),
                text = aboutWP,
                overflow = TextOverflow.Visible,
                fontSize = TextUnit(16f, TextUnitType.Sp),
                color = MaterialTheme.colorScheme.scrim,
                fontWeight = FontWeight.Normal
            )
        } else {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp),
                text = stringResource(R.string.loading_text),
                overflow = TextOverflow.Visible,
                fontSize = TextUnit(16f, TextUnitType.Sp),
                color = MaterialTheme.colorScheme.scrim,
                fontWeight = FontWeight.Normal
            )
            CustomLinearProgressIndicator()
        }
    }
}

@Preview
@Composable
fun AboutSectionPreview() {
    AboutSection(
        aboutWP = "lorum lorumlor umlorumlorumlorumlorumlorum lorumlorumlo " +
                "rumlorumloru mlorumlorumlo rumloruml orumlorumlorumlo" +
                "rumlorumlorumlorumlorumlorumlorumlorum", alt = "sdgfg"
    )
}

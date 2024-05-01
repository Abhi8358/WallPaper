package com.vedic.deepinsea.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.vedic.deepinsea.R

@Composable
fun PaginationInternetError(errorText: String, onRetryClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = MaterialTheme.colorScheme.onPrimary)
    ) {
        val (text, button) = createRefs()
        Text(
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(button.start)
                    width = Dimension.fillToConstraints
                }
                .padding(start = 12.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = TextUnit(18f, TextUnitType.Sp),
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.W500,
            text = errorText
        )
        Text(
            modifier = Modifier
                .constrainAs(button) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)

                }
                .padding(start = 4.dp, end = 12.dp, top = 4.dp, bottom = 4.dp)
                .clickable {
                    onRetryClick.invoke()
                },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = TextUnit(18f, TextUnitType.Sp),
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.W700,
            text = "Retry",
            textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
fun InternetError(onRetryClick: () -> Unit, modifier: Modifier, @DrawableRes id: Int, contentText: String) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (text, errorText, button) = createRefs()
        Image(
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(errorText.top)
                    width = Dimension.value(100.dp)
                    height = Dimension.value(100.dp)
                }
                .padding(start = 12.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
            painter = painterResource(id = id),
            contentDescription = contentText,
            alignment = Alignment.Center
        )
        Text(
            modifier = Modifier
                .constrainAs(errorText) {
                    top.linkTo(text.bottom)
                    width = Dimension.wrapContent
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = 14.dp, end = 14.dp, top = 4.dp, bottom = 4.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = TextUnit(18f, TextUnitType.Sp),
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.W700,
            text = contentText,
            textDecoration = TextDecoration.Underline,
            textAlign = TextAlign.Center
        )
        Button(
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(errorText.bottom, margin = 8.dp)
                    width = Dimension.wrapContent
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            onClick = { onRetryClick.invoke() },
        ) {
            Text(text = "Retry")
        }
    }
}

@Preview
@Composable
fun PaginationInternetErrorPreview() {
    PaginationInternetError(errorText = "Something went wrong", onRetryClick = {})
}

@Preview
@Composable
fun InternetErrorPreview() {
    InternetError(modifier = Modifier, onRetryClick = {}, id = R.drawable.error, contentText = "no internet")
}
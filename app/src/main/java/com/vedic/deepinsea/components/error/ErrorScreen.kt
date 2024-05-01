package com.vedic.deepinsea.components.error

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vedic.deepinsea.R
import com.vedic.deepinsea.components.InternetError
import com.vedic.deepinsea.components.PaginationInternetError

@Composable
inline fun ErrorScreen(
    isInternetConnected: Boolean,
    isEmptyList: Boolean,
    modifier: Modifier,
    pagingModifier: Modifier = Modifier,
    noinline onRetryClick: () -> Unit
) {

    if (!isInternetConnected && !isEmptyList) {
        Box(
            modifier = pagingModifier,
            contentAlignment = Alignment.BottomCenter
        ) {
            PaginationInternetError(
                errorText = "Please connect internet",
                onRetryClick = onRetryClick
            )
        }
    } else if (!isInternetConnected) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
        ) {
            InternetError(
                modifier = Modifier
                    .wrapContentSize(),
                contentText = "Oops, looks like there's no Internet connection",
                id = R.drawable.no_wifi,
                onRetryClick = onRetryClick
            )
        }

    } else if (!isEmptyList) {
        PaginationInternetError(errorText = "Something went wrong", onRetryClick = onRetryClick)
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
        ) {
            InternetError(
                modifier = Modifier
                    .wrapContentSize(),
                contentText = "Something went wrong, Please try again",
                id = R.drawable.error,
                onRetryClick = onRetryClick
            )
        }
    }
}

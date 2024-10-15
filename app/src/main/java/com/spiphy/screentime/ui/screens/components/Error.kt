package com.spiphy.screentime.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.spiphy.screentime.R

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.browser),
            contentDescription = null
        )
        Text(
            text = "Error loading data. Please try again.",
            modifier = modifier
        )
        Button(onClick = retryAction) {
            Text(text = stringResource(R.string.retry))
        }
    }
}
package com.spiphy.screentime.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spiphy.screentime.R

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.browser),
            contentDescription = null, modifier = modifier
                .size(64.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Error loading data. Please try again.",
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
        Button(
            onClick = retryAction,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)

        ) {
            Text(
                text = stringResource(R.string.retry)

            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreen({ }, Modifier)
}
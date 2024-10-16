package com.spiphy.screentime.ui.screens.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spiphy.screentime.R

@Composable
fun LoadingScreen(modifier: Modifier, contentPadding: PaddingValues) {
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(750, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "rotation"
    )
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Spacer(
            modifier = modifier
                .weight(1f)
        )
        Image(
            modifier = modifier
                .width(64.dp)
                .align(Alignment.CenterHorizontally)
                .rotate(rotation),
            painter = painterResource(id = R.drawable.loading),
            contentDescription = null
        )
        Spacer(
            modifier = modifier
                .heightIn(32.dp)
        )
        Text(
            text = "loading",
            modifier = modifier
                .fillMaxWidth()
                .padding(contentPadding),
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = modifier
                .weight(1f)
        )
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    LoadingScreen(modifier = Modifier, contentPadding = PaddingValues(0.dp))
}
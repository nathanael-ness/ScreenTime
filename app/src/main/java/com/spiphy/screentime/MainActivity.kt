package com.spiphy.screentime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.spiphy.screentime.ui.ScreenTimeApp
import com.spiphy.screentime.ui.theme.ScreenTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScreenTimeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ScreenTimeApp()
                }
            }
        }
    }
}
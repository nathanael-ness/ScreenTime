package com.spiphy.screentime.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spiphy.screentime.R
import com.spiphy.screentime.ui.screens.HomeScreen
import com.spiphy.screentime.ui.screens.TicketViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTimeApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = { ScreenTimeAppBar(scrollBehavior = scrollBehavior) },
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val ticketViewModel: TicketViewModel =
                viewModel(factory = TicketViewModel.Companion.Factory)
            HomeScreen(
                viewModel = ticketViewModel,
                ticketUiState = ticketViewModel.ticketUiState,
                retryAction = ticketViewModel::getTickets,
                contentPadding = it
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTimeAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        colors = topAppBarColors(
            MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Row {
                Text(
                    text = "Screen Time",
                    style = MaterialTheme.typography.displayLarge
                )
                Image(
                    painter = painterResource(id = R.drawable.screentime),
                    contentDescription = null,
                    modifier = Modifier
                        .height(32.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        },
        modifier = modifier
    )
}
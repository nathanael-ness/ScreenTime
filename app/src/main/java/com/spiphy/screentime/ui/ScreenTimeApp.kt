package com.spiphy.screentime.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spiphy.screentime.R
import com.spiphy.screentime.ui.screens.HistoryScreen
import com.spiphy.screentime.ui.screens.HistoryViewModel
import com.spiphy.screentime.ui.screens.HomeScreen
import com.spiphy.screentime.ui.screens.StarViewModel
import com.spiphy.screentime.ui.screens.StarsScreen
import com.spiphy.screentime.ui.screens.TicketViewModel

private const val homeScreen = "home"
private const val historyScreen = "history"
private const val starsScreen = "stars"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTimeApp() {
    val navController = rememberNavController()
    val ticketViewModel: TicketViewModel =
        viewModel(factory = TicketViewModel.Companion.Factory)
    var historyViewModel: HistoryViewModel =
        viewModel(factory = HistoryViewModel.Companion.Factory)
    val starViewModel: StarViewModel = viewModel(factory = StarViewModel.Companion.Factory)

    Scaffold(
        topBar = { ScreenTimeAppBar() },
        bottomBar = { BottomBar(navController, ticketViewModel, historyViewModel, starViewModel) },
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            NavHost(navController = navController, startDestination = homeScreen) {
                composable(route = homeScreen) {
                    HomeScreen(
                        viewModel = ticketViewModel,
                        ticketUiState = ticketViewModel.ticketUiState,
                        retryAction = ticketViewModel::getTickets,
                        contentPadding = innerPadding
                    )
                }
                composable(route = historyScreen) {
                    HistoryScreen(
                        historyUiState = historyViewModel.historyUiState,
                        retryAction = historyViewModel::getAllTickets,
                        contentPadding = innerPadding
                    )
                }
                composable(route = starsScreen) {
                    StarsScreen(
                        viewModel = starViewModel,
                        retryAction = starViewModel::getAllStars,
                        contentPadding = innerPadding
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    ticketViewModel: TicketViewModel,
    historyViewModel: HistoryViewModel,
    starViewModel: StarViewModel
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    NavigationBar {
        NavigationBarItem(
            selected = selectedTabIndex == 0,
            onClick = {
                selectedTabIndex = 0
                navController.navigate(homeScreen)
                ticketViewModel.getTickets()
            },
            icon = { Icon(Icons.Filled.Home, stringResource(R.string.home)) }
        )
        NavigationBarItem(
            selected = selectedTabIndex == 1,
            onClick = {
                selectedTabIndex = 1
                navController.navigate(historyScreen)
                historyViewModel.getAllTickets()
            },
            icon = { Icon(Icons.Filled.Info, stringResource(R.string.history)) }
        )
        NavigationBarItem(
            selected = selectedTabIndex == 2,
            onClick = {
                selectedTabIndex = 2
                starViewModel.getAllStars()
                navController.navigate(starsScreen)
            },
            icon = { Icon(Icons.Filled.Star, stringResource(R.string.stars)) }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTimeAppBar(modifier: Modifier = Modifier) {
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
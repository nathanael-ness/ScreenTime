package com.spiphy.screentime.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spiphy.screentime.R
import com.spiphy.screentime.ui.screens.HistoryScreen
import com.spiphy.screentime.ui.screens.HomeScreen
import com.spiphy.screentime.ui.screens.TicketViewModel
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

@Serializable
object Home

@Serializable
object History



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTimeApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { ScreenTimeAppBar() },
        bottomBar = { BottomBar(navController) },
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val ticketViewModel: TicketViewModel =
                viewModel(factory = TicketViewModel.Companion.Factory)
            NavHost(navController = navController, startDestination = "home") {
                composable(route = "home") {
                    HomeScreen(
                        viewModel = ticketViewModel,
                        ticketUiState = ticketViewModel.ticketUiState,
                        retryAction = ticketViewModel::getTickets,
                        contentPadding = innerPadding
                    )
                }
                composable(route = "history") {
                    HistoryScreen(
                        contentPadding = innerPadding
                    )
                }
                composable(route = "stars") {
                    HistoryScreen(
                        contentPadding = innerPadding
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBar(x0: NavHostController) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    NavigationBar (
    ) {
        NavigationBarItem(
            selected = selectedTabIndex == 0,
            onClick = {
                selectedTabIndex = 0
                x0.navigate("home")
            },
            icon = {Icon(Icons.Filled.Home, "Home")}
        )
        NavigationBarItem(
            selected = selectedTabIndex == 1,
            onClick = {
                selectedTabIndex = 1
                x0.navigate("history")
            },
            icon = {Icon(Icons.Filled.Info, "History")}
        )
        NavigationBarItem(
            selected = selectedTabIndex == 2,
            onClick = {
                selectedTabIndex = 2
                x0.navigate("stars")
            },
            icon = {Icon(Icons.Filled.Star, "Stars")}
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
package com.spiphy.screentime.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spiphy.screentime.R
import com.spiphy.screentime.model.Ticket
import com.spiphy.screentime.model.TicketRedemption
import com.spiphy.screentime.model.TicketRedemptionMap
import com.spiphy.screentime.model.TicketType
import com.spiphy.screentime.model.testTickets
import com.spiphy.screentime.ui.screens.components.ErrorScreen
import com.spiphy.screentime.ui.screens.components.GenericDialog
import com.spiphy.screentime.ui.screens.components.LoadingScreen
import kotlinx.datetime.Clock

private val showRedeemDialog = mutableStateOf(false)
private var onRedeemTicket: (ticket: Ticket) -> Unit = {}
private val showAwardDialog = mutableStateOf(false)
private val selectedCard = mutableStateOf<Ticket?>(null)
private var onAwardTicket: (String, String) -> Unit = { _, _ -> }

private const val max_screen_time = 120

@Composable
fun HomeScreen(
    viewModel: TicketViewModel,
    ticketUiState: TicketUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (ticketUiState) {
        is TicketUiState.Loading -> LoadingScreen(
            modifier = Modifier,
            contentPadding = contentPadding
        )

        is TicketUiState.Success -> {
            onAwardTicket = { ticketType, note -> viewModel.awardTicket(ticketType, note) }
            onRedeemTicket = { ticket ->
                if (ticketUiState.screenTime < max_screen_time) viewModel.redeemTicket(ticket)
            }
            TicketsScreen(
                ticketUiState.tickets,
                ticketUiState.screenTime,
                contentPadding = contentPadding
            )
        }

        is TicketUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun TicketsScreenPreview() {
    TicketsScreen(
        testTickets,
        20,
        contentPadding = PaddingValues(0.dp)
    )
}

@Composable
fun TicketsScreen(
    tickets: List<Ticket>,
    screenTime: Int,
    contentPadding: PaddingValues
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAwardDialog.value = true }
            ) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.award_ticket))
            }
        },
        modifier = Modifier.padding(contentPadding)
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            TodayScreenTime(screenTime)
            Tickets(tickets)
        }
        //Redeem Ticket Dialog
        val redeemOptions =
            TicketRedemption.entries.filter { it.id < 5 }.map { stringResource(id = it.textRes) }
        GenericDialog(
            showDialog = showRedeemDialog.value,
            toggleDialog = { showRedeemDialog.value = !showRedeemDialog.value },
            options = redeemOptions,
            title = stringResource(R.string.redeem_ticket),
            hintText = "",
            textLabel = stringResource(R.string.note),
            ok = stringResource(R.string.redeem),
            cancel = stringResource(R.string.cancel),
            onOk = { selected, text ->
                if (selectedCard.value != null) {
                    val t = selectedCard.value!!.copy(
                        used = true,
                        usedDate = Clock.System.now().toString(),
                        note = text,
                        redemption = TicketRedemptionMap[selected]!!
                    )
                    onRedeemTicket(t)
                }
            }
        )
        //Award Ticket Dialog
        val awardOptions =
            TicketType.entries.filter { it.id < 3 }.map { stringResource(id = it.textRes) }
        GenericDialog(
            showDialog = showAwardDialog.value,
            toggleDialog = { showAwardDialog.value = !showAwardDialog.value },
            options = awardOptions,
            title = stringResource(R.string.award_ticket),
            hintText = "",
            textLabel = stringResource(R.string.note),
            ok = stringResource(R.string.award),
            cancel = stringResource(R.string.cancel),
            onOk = { selected, text ->
                onAwardTicket(selected, text)
            }
        )
    }

}

@Composable
fun TodayScreenTime(screenTime: Int = 20) {
    Text(
        text = "Screen Time used today: $screenTime minutes",
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun Tickets(tickets: List<Ticket>) {
    LazyColumn {
        items(tickets) { ticket ->
            TicketCard(ticket)
        }
    }
}

@Composable
fun TicketCard(ticket: Ticket) {
    val displayTime = Utilities.ticketToTimeString(ticket)
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .defaultMinSize(minHeight = 64.dp),
        onClick = {
            if (!ticket.used) {
                showRedeemDialog.value = true
                selectedCard.value = ticket
            }
        }
    ) {
        Box {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ticket),
                        contentDescription = null,
                        modifier = Modifier
                            .height(64.dp)
                    )
                    Text(
                        modifier = Modifier
                            .weight(1.0f)
                            .align(Alignment.CenterVertically)
                            .padding(10.dp),
                        text = stringResource(id = ticket.exclamationResourceId)
                    )
                    Image(
                        modifier = Modifier
                            .height(64.dp),
                        painter = painterResource(id = if (ticket.used) ticket.redemption.imageRes else ticket.type.imageRes),
                        contentDescription = null
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = displayTime,
                        style = MaterialTheme.typography.labelSmall,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = ticket.note,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
            if (ticket.used) {
                Text(
                    text = "CLAIMED!",
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .alpha(0.6f),
                    textAlign = TextAlign.Center,
                    color = Color.Red

                )
            }
        }
    }
}
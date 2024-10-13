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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spiphy.screentime.R
import com.spiphy.screentime.model.Ticket
import com.spiphy.screentime.model.TicketRedemption
import com.spiphy.screentime.model.TicketType
import com.spiphy.screentime.model.testTickets
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.datetime.Clock

private val showRedeemDialog = mutableStateOf(false)
private val showAwardDialog = mutableStateOf(false)
private val selectedCard = mutableStateOf<Ticket?>(null)
private var onAwardTicket: (String, String) -> Unit = { _, _ -> }
private var onRedeemTicket: (ticket: Ticket) -> Unit = {}

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
        is TicketUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize(), contentPadding = contentPadding)
        is TicketUiState.Success -> {
            onAwardTicket = { ticketType, note -> viewModel.awardTicket(ticketType, note) }
            onRedeemTicket = { ticket -> if(ticketUiState.screenTime < max_screen_time) viewModel.redeemTicket(ticket) }
            TicketsScreen(
                ticketUiState.tickets, ticketUiState.screenTime, contentPadding = contentPadding, modifier = modifier.fillMaxWidth()
            )
        }
        is TicketUiState.Error -> TicketsScreen(
            testTickets, 20, contentPadding = contentPadding, modifier = modifier.fillMaxWidth()
        )
    //ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun TicketsScreenPreview() {
    TicketsScreen(testTickets, 20, contentPadding = PaddingValues(0.dp), modifier = Modifier.fillMaxWidth())
}

@Composable
fun TicketsScreen(tickets: List<Ticket>, screenTime: Int, contentPadding: PaddingValues, modifier: Modifier) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
            onClick = { showAwardDialog.value = true }
        ) {
            Icon(Icons.Filled.Add, stringResource(id = R.string.award_ticket))
        }}
    ){ innerPadding ->
        Column(
            modifier = Modifier.padding(contentPadding)
        ) {
            TodayScreenTime(screenTime, innerPadding)
            Tickets(tickets)
        }
        RedeemTicketDialog()
        AwardTicketDialog()
    }

}

@Composable
fun TodayScreenTime(screenTime: Int = 20, contentPadding: PaddingValues) {
    Text(
        text = "Screen Time used today: $screenTime minutes",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
    val displayTime = timeToString(ticket)
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
            } }
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

private fun timeToString(ticket: Ticket): String {
    val timestamp = if (ticket.used) ticket.usedDate else ticket.earnedDate
    val time = Instant.parse(timestamp).toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
    val displayTime = "${time.dayOfWeek} ${time.month} ${time.dayOfMonth}, ${time.year}"
    return displayTime
}

@Composable
fun RedeemTicketDialog() {
    val options = TicketRedemption.entries.filter { it.id < 5 }.map { Pair(stringResource(id = it.textRes), it) }
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(options[0]) }
    var noteText by remember { mutableStateOf("") }
    if (showRedeemDialog.value) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(text = stringResource(R.string.redeem_ticket))
            },
            text = {
                Column() {
                    Text(text = stringResource(R.string.redeem_ticket_text))
                    Column(Modifier.selectableGroup()) {
                        options.forEach { optionPair ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected = (optionPair.first == selectedOption.first),
                                        onClick = { onOptionSelected(optionPair) },
                                        role = Role.RadioButton
                                    )
                                    .padding(8.dp),
                            ) {
                                RadioButton(
                                    selected = (optionPair == selectedOption),
                                    onClick = null
                                )
                                Text(
                                    text = optionPair.first,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(start = 8.dp)
                                )
                            }
                        }
                    }
                    OutlinedTextField(
                        value = noteText,
                        onValueChange = {text -> noteText = text},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text(stringResource(R.string.note)) }
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = {
                    showRedeemDialog.value = false
                    if(selectedCard.value != null) {
                        val t = selectedCard.value!!.copy(
                            used = true,
                            usedDate = Clock.System.now().toString(),
                            note = noteText,
                            redemption = selectedOption.second
                        )
                        onRedeemTicket(t)
                    }
                }) {
                    Text(text = stringResource(R.string.redeem))
                }
            },
            dismissButton = {
                TextButton(onClick = { showRedeemDialog.value = false }) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun AwardTicketDialog() {
    val options = TicketType.entries.filter { it.id < 4 }.map { stringResource(id = it.textRes) }
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(options[0]) }
    var noteText by remember { mutableStateOf("") }
    if (showAwardDialog.value) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(text = stringResource(R.string.award_ticket))
            },
            text = {
                Column() {
                    Text(text = stringResource(R.string.award_ticket_text))
                    Column(Modifier.selectableGroup()) {
                        options.forEach { text ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected = (text == selectedOption),
                                        onClick = { onOptionSelected(text) },
                                        role = Role.RadioButton
                                    )
                                    .padding(8.dp),
                            ) {
                                RadioButton(
                                    selected = (text == selectedOption),
                                    onClick = null
                                )
                                Text(
                                    text = text,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(start = 8.dp)
                                )
                            }
                        }
                    }
                    OutlinedTextField(
                        value = noteText,
                        onValueChange = { text -> noteText = text },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text(stringResource(R.string.note)) }
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = {
                    onAwardTicket(selectedOption, noteText)
                    showAwardDialog.value = false
                }) {
                    Text(text = stringResource(R.string.award))
                }
            },
            dismissButton = {
                TextButton(onClick = { showAwardDialog.value = false }) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun ErrorScreen(x0: () -> Unit, modifier: Modifier) {
    Text(
        text = "error"
    )
}

@Composable
fun LoadingScreen(modifier: Modifier, contentPadding: PaddingValues) {
    Text(
        text = "loading",
        modifier = Modifier
            .fillMaxWidth()
            .padding(contentPadding),
        textAlign = TextAlign.Center
    )
}
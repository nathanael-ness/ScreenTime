package com.spiphy.screentime.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spiphy.screentime.model.Ticket
import com.spiphy.screentime.model.testTickets
import com.spiphy.screentime.ui.screens.components.ErrorScreen
import com.spiphy.screentime.ui.screens.components.LoadingScreen

@Composable
fun HistoryScreen(
    historyUiState: HistoryUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (historyUiState) {
        is HistoryUiState.Loading -> LoadingScreen(
            modifier = Modifier,
            contentPadding = contentPadding
        )

        is HistoryUiState.Success -> {
            History(
                historyUiState.tickets,
                contentPadding = contentPadding,
                modifier = modifier.fillMaxWidth()
            )
        }

        is HistoryUiState.Error -> ErrorScreen(retryAction, Modifier)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun History(
    tickets: List<Ticket>,
    contentPadding: PaddingValues,
    modifier: Modifier
) {
    val usedTickets = tickets.filter { it.used }
    val earnedTickets = tickets.filter { !it.used }
    val earnedByDate = earnedTickets.groupBy { Utilities.ticketToTimeString(it) }
    val usedTicketsByDate = usedTickets.groupBy { Utilities.ticketToTimeString(it) }
    LazyColumn(
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        stickyHeader {
            Text(
                text = "Earned Tickets",
                modifier = Modifier
                    .padding(4.dp),
                style = MaterialTheme.typography.displaySmall
            )
        }
        earnedByDate.forEach { (date, tickets) ->
            stickyHeader {
                Text(
                    text = date,
                    modifier = Modifier
                        .padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            items(tickets) { ticket ->
                HistoryCard(ticket)
            }
        }
        if (!usedTickets.isEmpty()) {
            stickyHeader {
                Text(
                    text = "Used Tickets",
                    modifier = Modifier
                        .padding(4.dp),
                    style = MaterialTheme.typography.displaySmall
                )
            }
            usedTicketsByDate.forEach { (date, tickets) ->
                stickyHeader {
                    Text(
                        text = date,
                        modifier = Modifier
                            .padding(8.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                items(tickets) { ticket ->
                    HistoryCard(ticket)
                }
            }
        }
    }
}

@Composable
fun HistoryCard(ticket: Ticket) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "${ticket.type} - Note: ${ticket.note}")
        }
    }
}


@Preview
@Composable
fun HistoryScreenPreview() {
    History(testTickets, contentPadding = PaddingValues(0.dp), modifier = Modifier.fillMaxWidth())
}
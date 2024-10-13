package com.spiphy.screentime.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spiphy.screentime.model.Ticket
import com.spiphy.screentime.model.testTickets
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun HistoryScreen(modifier: Modifier = Modifier,
                  contentPadding: PaddingValues = PaddingValues(0.dp)
) {

}

@Composable
fun History(tickets: List<Ticket>) {
    LazyColumn {
        items(tickets) { ticket ->
            HistoryCard(ticket)
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
            Text(text = "${ticket.type} - ${if (ticket.used) "USED" else "EARNED"} on ${timeToString(ticket)}")
            Text(text = "Note: ${ticket.note}")
        }
    }
}


@Preview
@Composable
fun HistoryScreenPreview() {
    History(testTickets)
}

private fun timeToString(ticket: Ticket): String {
    val timestamp = if (ticket.used) ticket.usedDate else ticket.earnedDate
    val time = Instant.parse(timestamp).toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
    val displayTime = "${time.dayOfWeek} ${time.month} ${time.dayOfMonth}, ${time.year}"
    return displayTime
}
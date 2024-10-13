package com.spiphy.screentime.data

import com.spiphy.screentime.model.Ticket
import com.spiphy.screentime.model.TicketRedemption
import com.spiphy.screentime.model.TicketType
import com.spiphy.screentime.model.exclamations
import com.spiphy.screentime.network.TicketApiService
import kotlinx.datetime.Clock
import java.util.UUID

interface TicketRepository {
    suspend fun getActiveTickets(): List<Ticket>
    suspend fun getTodaysUsedScreenTime(): Int
    suspend fun awardTicket(ticketType: String, note: String)
    suspend fun createDailyTicket()
    suspend fun redeemTicket(ticket: Ticket)
}

class NetworkTicketRepository(private val ticketApiService: TicketApiService) : TicketRepository {
    override suspend fun getActiveTickets(): List<Ticket> {
        return ticketApiService.getActiveTickets()
    }

    override suspend fun getTodaysUsedScreenTime(): Int {
        return ticketApiService.getTodaysUsedScreenTime()
    }

    override suspend fun awardTicket(ticketType: String, note: String) {
        val exclaim = exclamations.random()
        val ticket = Ticket(
            UUID.randomUUID().toString(),
            exclaim,
            note,
            Clock.System.now().toString(),
            TicketType.valueOf(ticketType.uppercase()),
            "0001-01-01T00:00:00.0000000+00:00",
            false,
            TicketRedemption.NONE,
            20
        )
        return ticketApiService.awardTicket(ticket)
    }

    override suspend fun createDailyTicket() {
        return ticketApiService.createDailyTicket()
    }

    override suspend fun redeemTicket(ticket: Ticket) {
        return ticketApiService.redeemTicket(ticket)
    }
}
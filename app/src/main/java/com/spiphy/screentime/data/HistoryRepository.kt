package com.spiphy.screentime.data

import com.spiphy.screentime.model.Ticket
import com.spiphy.screentime.network.HistoryApiService

interface HistoryRepository {
    suspend fun getAllTickets(): List<Ticket>
}

class NetworkHistoryRepository(private val historyApiService: HistoryApiService) :
    HistoryRepository {
    override suspend fun getAllTickets(): List<Ticket> {
        return historyApiService.getAllTickets()
    }
}
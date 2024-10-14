package com.spiphy.screentime.network

import com.spiphy.screentime.model.Ticket
import retrofit2.http.GET

interface HistoryApiService {
    @GET("GetAllTickets")
    suspend fun getAllTickets(): List<Ticket>
}
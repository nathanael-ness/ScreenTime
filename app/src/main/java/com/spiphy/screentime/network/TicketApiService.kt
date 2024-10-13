package com.spiphy.screentime.network


import com.spiphy.screentime.model.Ticket
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TicketApiService {

    @GET("GetActiveTickets")
    suspend fun getActiveTickets(): List<Ticket>

    @GET("TodaysUsedScreenTime")
    suspend fun getTodaysUsedScreenTime(): Int

    @POST("AddTicket")
    suspend fun awardTicket(@Body ticket: Ticket)

    @POST("CreateDailyTicket")
    suspend fun createDailyTicket()

    @POST("UpdateTicket")
    suspend fun redeemTicket(@Body ticket: Ticket)
}
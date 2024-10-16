package com.spiphy.screentime.network


import com.spiphy.screentime.model.Ticket
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TicketApiService {

    @GET("Ticket/GetActiveTickets")
    suspend fun getActiveTickets(): List<Ticket>


    @GET("Ticket/GetAllTickets")
    suspend fun getAllTickets(): List<Ticket>

    @GET("Ticket/TodaysUsedScreenTime")
    suspend fun getTodaysUsedScreenTime(): Int

    @POST("Ticket/AddTicket")
    suspend fun awardTicket(@Body ticket: Ticket)

    @POST("Ticket/CreateDailyTicket")
    suspend fun createDailyTicket()

    @POST("Ticket/UpdateTicket")
    suspend fun redeemTicket(@Body ticket: Ticket)

    @DELETE("Ticket/DeleteTicket")
    suspend fun deleteTicket(@Query("ticketId") ticketId: String)
}
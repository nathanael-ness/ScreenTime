package com.spiphy.screentime.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.spiphy.screentime.network.TicketApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val ticketRepository: TicketRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://screentime.ness.nohost.me/api/Ticket/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: TicketApiService by lazy {
        retrofit.create(TicketApiService::class.java)
    }

    override val ticketRepository: TicketRepository by lazy {
        NetworkTicketRepository(retrofitService)
    }
}
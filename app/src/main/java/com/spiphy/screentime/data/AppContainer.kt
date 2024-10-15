package com.spiphy.screentime.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.spiphy.screentime.network.HistoryApiService
import com.spiphy.screentime.network.StarApiService
import com.spiphy.screentime.network.TicketApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val ticketRepository: TicketRepository
    val historyRepository: HistoryRepository
    val starRepository: StarRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://screentime.ness.nohost.me/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitTicketService: TicketApiService by lazy {
        retrofit.create(TicketApiService::class.java)
    }

    private val retrofitHistoryService: HistoryApiService by lazy {
        retrofit.create(HistoryApiService::class.java)
    }

    private val retrofitStarService: StarApiService by lazy {
        retrofit.create(StarApiService::class.java)
    }

    override val ticketRepository: TicketRepository by lazy {
        NetworkTicketRepository(retrofitTicketService)
    }

    override val historyRepository: HistoryRepository by lazy {
        NetworkHistoryRepository(retrofitHistoryService)
    }

    override val starRepository: StarRepository by lazy {
        NetworkStarRepository(retrofitStarService)
    }
}
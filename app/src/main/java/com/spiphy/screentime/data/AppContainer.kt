package com.spiphy.screentime.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.spiphy.screentime.network.HistoryApiService
import com.spiphy.screentime.network.StarApiService
import com.spiphy.screentime.network.TicketApiService
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import com.spiphy.screentime.BuildConfig

interface AppContainer {
    val ticketRepository: TicketRepository
    val historyRepository: HistoryRepository
    val starRepository: StarRepository
}

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader("X-API-Key", BuildConfig.SERVER_APIKEY)
            .build()
        return chain.proceed(request)
    }
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://screentime.ness.nohost.me/api/"

    private val apiKeyInterceptor = ApiKeyInterceptor()
    private val builder = OkHttpClient().newBuilder()
        .addInterceptor(apiKeyInterceptor)


    private val retrofit: Retrofit = Retrofit.Builder()
        .client(builder.build())
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
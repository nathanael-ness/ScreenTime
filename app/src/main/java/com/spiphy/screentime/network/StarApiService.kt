package com.spiphy.screentime.network


import com.spiphy.screentime.model.Star
import com.spiphy.screentime.model.StarGroup
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface StarApiService {

    @GET("Star/GetAllGroups")
    suspend fun getAllGroups(): List<StarGroup>

    @POST("Star/GetStarDetails")
    suspend fun getStarDetails(@Body starGroup: StarGroup): List<Star>

    @POST("Star/AddStar")
    suspend fun addStar(@Body note: String)

    @POST("Star/UpdateStarGroup")
    suspend fun updateStarGroup(@Body starsGroup: StarGroup)
}
package com.spiphy.screentime.data

import com.spiphy.screentime.model.Star
import com.spiphy.screentime.model.StarGroup
import com.spiphy.screentime.model.Ticket
import com.spiphy.screentime.model.TicketRedemption
import com.spiphy.screentime.model.TicketType
import com.spiphy.screentime.model.exclamations
import com.spiphy.screentime.network.StarApiService
import com.spiphy.screentime.network.TicketApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.UUID

interface StarRepository {
    suspend fun getAllGroups(): List<StarGroup>
    suspend fun getStarDetails(@Body starGroup: StarGroup): List<Star>
    suspend fun addStar(@Body note: String)
    suspend fun updateStarGroup(@Body starsGroup: StarGroup)
}

class NetworkStarRepository(private val starApiService: StarApiService) : StarRepository {
    override suspend fun getAllGroups(): List<StarGroup> {
        return starApiService.getAllGroups()
    }

    override suspend fun getStarDetails(starGroup: StarGroup): List<Star> {
        return starApiService.getStarDetails(starGroup)
    }

    override suspend fun addStar(note: String) {
        return starApiService.addStar(note)
    }

    override suspend fun updateStarGroup(starsGroup: StarGroup) {
        return starApiService.updateStarGroup(starsGroup)
    }


}
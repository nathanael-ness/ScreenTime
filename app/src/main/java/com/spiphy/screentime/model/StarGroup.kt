package com.spiphy.screentime.model

import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class StarGroup(
    val id: String,
    val date: String,
    var used: Boolean,
    val note: String,
    val earned: Int,
    var stars: List<Star> = emptyList()
)

val testStarGroup = listOf(
    StarGroup(
        id = UUID.randomUUID().toString(),
        date = Clock.System.now().toString(),
        used = false,
        note = "Test",
        earned = 3
    ), StarGroup(
        id = UUID.randomUUID().toString(),
        date = Clock.System.now().toString(),
        used = false,
        note = "Test",
        earned = 10
    ),
    StarGroup(
        id = UUID.randomUUID().toString(),
        date = Clock.System.now().toString(),
        used = true,
        note = "Test",
        earned = 10
    )
)